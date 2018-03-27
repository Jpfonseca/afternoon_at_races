package shared_regions;
import entities.*;

/**
 *This class specifies the methods that will be executed on the Racing Track .
 */
public class RacingTrack{

    /**
     * General Repository
     * @serial repo
     */
    private GeneralInformationRepository repo;
    /**
     * Condition statement used know until when to wait in the start line
     * @serial waitForA
     */
    private boolean waitForA=true;
    /**
     * Variable used to keep track of the current race number
     * @serial currentRace
     */
    private int currentRace;
    /**
     * Number of HorseJockeys
     * @serial N
     */
    private int N;
    /**
     * Array that keeps Horses indexes by arrival order so that thy can run in order
     * @serial fifo
     */
    private int[] fifo;
    /**
     *  Total HorseJockeys in RacingTrack (FIFO)
     *  @serial totalHJ
     */
    private int totalHJ=0;
    /**
     * Array used to know all the race's distances
     * @serial D
     */
    private int[] D;
    /**
     * Array to keep track of all the Horses current position in the RaceTrack
     * @serial HJpos
     */
    private int[] HJPos;
    /**
     * Array to keep track of the iterations done by each Spectator in the current race
     * @serial iterations
     */
    private int[] iterations;
    /**
     * Array to keep all the HorseJockey position, iteration and standing position at the end of the current race
     */
    private Winners[] winners;
    /**
     * Variable to know what's the maximum standing position
     * @serial maxStanding
     */
    private int maxStanding;

    /**
     * RacingTrack Constructor
     * @param K Total amount of Races
     * @param N Total amount of HorseJockeys
     * @param repo General Repository
     */
    public RacingTrack(int K, int N, GeneralInformationRepository repo) {
        this.currentRace = 0;
        this.D = new int[K];
        this.N = N;
        this.fifo = new int[N];
        this.HJPos = new int[N];
        this.iterations = new int[N];
        this.winners = new Winners[N];
        this.repo = repo;
        this.maxStanding = 0;

        for (int i=0; i<K; i++)
            D[i] = (int) (Math.random() * 50 + 50);
        repo.setTrackDistance(D);

        for (int i=0; i<N; i++) {
            HJPos[i] = 0;
            iterations[i] = 0;
            winners[i]=new Winners();
        }
    }

    /**
     * Method used by the Broker to start the race
     * @param k current race number
     */

    public synchronized void startTheRace(int k){

        ((Broker)Thread.currentThread()).setBrokerState((BrokerState.SUPERVISING_THE_RACE));
        repo.setBrokerState(BrokerState.SUPERVISING_THE_RACE);

        this.currentRace = k;

        while (totalHJ != N)
            try {
                wait();
            } catch (Exception e) {
                System.out.println("Broker rt.startTheRace() Exception: "+e);
            }

        waitForA=false;
        notifyAll();
    }

    /**
     * Method used by the HorseJockeys to proceed to the start line
     * @param hj_number HorseJockey index number
     */
    public synchronized void proceedToStartLine(int hj_number){

        ((HorseJockey)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_START_LINE));
        repo.setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE,hj_number);

        fifo[totalHJ++] = hj_number;
        notifyAll();

        while (waitForA || fifo[0]!=hj_number)
            try {
                wait();
            } catch (Exception e) {
                System.out.println("HorseJockey rt.proceedToStartLine() Exception: "+e);
            }

        ((HorseJockey)Thread.currentThread()).setHjState((HorseJockeyState.RUNNING));
        repo.setHorseJockeyState(HorseJockeyState.RUNNING,hj_number);
    }

    /**
     * Method used by every HorseJockey to make a move in the Racing track while running
     * @param hj_number HorseJockey index number
     */
    public synchronized void makeAMove(int hj_number) {

        HJPos[hj_number] += (int)(Math.random()*((HorseJockey)Thread.currentThread()).getAgility()+1);

        //System.out.println("Cavalo:" +hj_number+" Posição:"+HJPos[hj_number]);

        iterations[hj_number]++;
        repo.setIterationStep(hj_number,iterations[hj_number]);
        repo.setCurrentPos(hj_number, HJPos[hj_number]);


        if (HJPos[hj_number] < D[currentRace-1]) {
            repo.reportStatus();
            fifoUpdate();
            notifyAll();
        }

        while(fifo[0]!=hj_number && (HJPos[hj_number] < D[currentRace-1]))
            try {
                wait();
            } catch (Exception e) {
                System.out.println("HorseJockey rt.makeAMove() Exception: "+e);
            }
    }

    /**
     * Method used by the HorseJockeys to know if they have crossed the finish line
     * @return <b>true</b> if he has crossed or <b>false</b>, if he has not.
     */
    public synchronized boolean hasFinishLineBeenCrossed(){

        if (HJPos[fifo[0]] < D[currentRace-1])
            return false;


        int standingCalc = maxStanding+1;

        for (int i=0; i<N; i++)
            if (winners[i].iteration == iterations[fifo[0]])
                if (winners[i].position < HJPos[fifo[0]]) {
                    winners[i].standing++;
                    repo.setStandingPos(i,winners[i].standing);
                    if (winners[i].standing > maxStanding)
                        maxStanding = winners[i].standing;
                    standingCalc--;
                }else if(winners[i].position == HJPos[fifo[0]]) {
                    standingCalc = winners[i].standing;
                    break;
                }

        if (standingCalc>maxStanding)
            maxStanding++;

        winners[fifo[0]].position=HJPos[fifo[0]];
        winners[fifo[0]].iteration=iterations[fifo[0]];
        winners[fifo[0]].standing = standingCalc;


        ((HorseJockey)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_FINNISH_LINE));
        repo.setStandingPos(fifo[0],standingCalc);
        repo.setHorseJockeyState(HorseJockeyState.AT_THE_FINNISH_LINE,fifo[0]);

        totalHJ--;

        if (totalHJ==0) {
            waitForA = true; // variable reset
            fifo = new int[N];
            HJPos = new int[N];
            iterations = new int[N];
            maxStanding=0;
        } else {
            int[] temp = new int[totalHJ];
            System.arraycopy(fifo, 1, temp, 0, fifo.length - 1);
            fifo = temp;
        }

        notifyAll();

        while(totalHJ>0)
            try {
                wait();
            } catch (Exception e) {
                System.out.println("HorseJockey rt.hasFinishedLineBeenCrossed() Exception: "+e);
            }

        return true;
    }

    /**
     * Method used to pop the first index and push into the last position
     */
    private void fifoUpdate(){
        int temp = fifo[0];
        for (int i=0; i<fifo.length-1; i++) {
            fifo[i] = fifo[i + 1];
            //System.out.print(fifo[i]);
        }
        fifo[fifo.length-1]=temp;
        //System.out.print(temp+"\n");
    }

    /**
     * Method used to return an array with all the winning Spectators information
     * @return winners
     */
    public Winners[] reportResults(){
        //Winners[] temp = winners;
        return winners;
    }


}