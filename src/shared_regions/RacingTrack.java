package shared_regions;
import entities.*;

/**
 *This class specifies the methods that will be executed on the Racing Track .
 */
public class RacingTrack{

    private GeneralInformationRepository repo;
    private boolean waitForA=true;
    private boolean lastHorseCrossed=false;
    private int currentRace;
    private int N;
    private int[] fifo;
    /**
     *  Total HorseJockeys in RacingTrack (FIFO)
     *  @serialField queueHJ
     */
    private int totalHJ=0;
    private int[] D;
    private int[] HJPos;
    private int[] iterations;
    private Winners[] winners;
    private int maxStanding;

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

    public synchronized void startTheRace(int k){
        // Mudar o estado -> SUPERVISING_THE_RACE
        // bloquear em waitForRaceToFinish

        ((Broker)Thread.currentThread()).setBrokerState((BrokerState.SUPERVISING_THE_RACE));
        repo.setBrokerState(BrokerState.SUPERVISING_THE_RACE);

        this.currentRace = k;
        this.lastHorseCrossed = false;

        waitForA=false;
        notifyAll();
    }

    public synchronized void proceedToStartLine(int hj_number){
        // sends horse_jockey to the start_Line
        // bloqueia em  waitForA

        // DID WE FORGET TO UPDATE STATE -> AT_THE_START_LINE ???!???!???
        // SHOULD THIS ALSO HAVE 1 METHOD IN PD DUE TO BROKER startTheRace ???!???!??? (Option 1)
        // OR SHOULD BROKER COME HERE TO startTheRace ???!???!??? (* Using Option 2 *)

        ((HorseJockey)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_START_LINE));
        repo.setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE,hj_number);

        fifo[totalHJ++] = hj_number;

        while (waitForA || fifo[0]!=hj_number)
            try {
                wait();
            } catch (Exception e) {
                System.out.println("HorseJockey rt.proceedToStartLine() Exception: "+e);
            }

        // SHOULD THIS LOOP ONLY ALLOW 1 HJ TO makeAMove ???!???!???
        /*
        the pair horse / jockey is waken up by the operation startTheRace of the
        broker (the first) or by the operation makeAMove of another horse / jockey
        pair
         */

        ((HorseJockey)Thread.currentThread()).setHjState((HorseJockeyState.RUNNING));
        repo.setHorseJockeyState(HorseJockeyState.RUNNING,hj_number);
    }



    public synchronized void makeAMove(int hj_number) {
        // makeAMove
        //bloqueia a andar e acorda o seguinte se o mesmo não tiver passado linha de corrida


        HJPos[hj_number] += (int)(Math.random()*((HorseJockey)Thread.currentThread()).getAgility()+1);
System.out.println("Cavalo:" +hj_number+" Posição:"+HJPos[hj_number]);

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
        notifyAll();

        if (totalHJ==0) {
            waitForA = true; // variable reset
            lastHorseCrossed=true;
            fifo = new int[N];
            HJPos = new int[N];
            iterations = new int[N];
            maxStanding=0;
            for (int i=0; i<N; i++)
                winners[i] = new Winners();
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

    private void fifoUpdate(){
        int temp = fifo[0];
        for (int i=0; i<fifo.length-1; i++) {
            fifo[i] = fifo[i + 1];
            System.out.print(fifo[i]);
        }
        fifo[fifo.length-1]=temp;
        System.out.print(temp+"\n");
    }

    public boolean hasLastHorseCrossed() {
        return lastHorseCrossed;
    }
}