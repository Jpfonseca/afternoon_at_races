package shared_regions;

import entities.BrokerState;
import entities.HorseJockeyState;

import interfaces.GeneralInformationRepositoryInterface;
import interfaces.RacingTrackInterface;
import shared_regions.RMIReply.StartTheRace;
import shared_regions.RMIReply.ProceedToStartLine;
import shared_regions.RMIReply.HasFinishLineBeenCrossed;

import java.rmi.RemoteException;

/**
 *This class specifies the methods that will be executed on the Racing Track .
 */
public class RacingTrack implements RacingTrackInterface {

    /**
     * General Repository Stub
     * @serial repo
     */
    private GeneralInformationRepositoryInterface repoStub;
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

    private int shutdownRequest;
    private boolean shutdown;


    /**
     * Instance of RacingTrack
     * @serialField instance
     */
    private static RacingTrack instance;

    /**
     * RacingTrack Constructor
     * @param K Total amount of Races
     * @param N Total amount of HorseJockeys
     * @param DMax Maximum distance
     * @param DMin Minimum Distance
     */
    public RacingTrack(int K, int N, int DMin, int DMax, GeneralInformationRepositoryInterface repoStub) {
        this.currentRace = 0;
        this.D = new int[K];
        this.N = N;
        this.fifo = new int[N];
        this.HJPos = new int[N];
        this.iterations = new int[N];
        this.winners = new Winners[N];
        this.repoStub = repoStub;
        this.maxStanding = 0;
        this.shutdownRequest = 1;

        for (int i=0; i<K; i++)
            D[i] = DMax - (int) (Math.random() * DMin);
        try {
            repoStub.setTrackDistance(D);
            repoStub.reportStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        for (int i=0; i<N; i++) {
            HJPos[i] = -1;
            iterations[i] = -1;
            //winners[i]=new Winners();
        }
    }

    /**
     * Method used by the Broker to start the race
     * @param k current race number
     */
    @Override
    public synchronized StartTheRace startTheRace(int k){

        try {
            repoStub.setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        this.currentRace = k;

        while (totalHJ != N)
            try {
                wait();
            } catch (Exception e) {
                System.out.println("Broker rt.startTheRace() Exception: "+e);
            }

        waitForA=false;
        for (int i=0; i<N; i++)
            winners[i] = new Winners();

        notifyAll();
        return new StartTheRace(BrokerState.SUPERVISING_THE_RACE);
    }

    /**
     * Method used by the HorseJockeys to proceed to the start line
     * @param hj_number HorseJockey index number
     */
    @Override
    public synchronized ProceedToStartLine proceedToStartLine1(int hj_number) {

        try {
            repoStub.setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE, hj_number);
            iterations[hj_number] = 0;
            HJPos[hj_number] = 0;
            repoStub.setIterationStep(hj_number, iterations[hj_number]);
            repoStub.setCurrentPos(hj_number, HJPos[hj_number]);
            repoStub.reportStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
        }



        fifo[totalHJ++] = hj_number;
        notifyAll();
        return new ProceedToStartLine(HorseJockeyState.AT_THE_START_LINE);
    }
    public synchronized ProceedToStartLine proceedToStartLine2(int hj_number) {
        while (waitForA || fifo[0]!=hj_number)
            try {
                wait();
            } catch (Exception e) {
                System.out.println("HorseJockey rt.proceedToStartLine() Exception: "+e);
            }

        try {
            repoStub.setHorseJockeyState(HorseJockeyState.RUNNING,hj_number);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return new ProceedToStartLine(HorseJockeyState.RUNNING);
    }

    /**
     * Method used by every HorseJockey to make a move in the Racing track while running
     * @param hj_number HorseJockey index number
     */
    @Override
    public synchronized void makeAMove(int hj_number,int hj_agility) {
        //TODO send horse_jockey number and agility
        HJPos[hj_number] += (int)(Math.random()*(hj_agility+1));

        //System.out.println("Cavalo:" +hj_number+" Posição:"+HJPos[hj_number]);

        iterations[hj_number]++;

        try {
            repoStub.setCurrentPos(hj_number, HJPos[hj_number]);
            repoStub.setIterationStep(hj_number,iterations[hj_number]);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        if (HJPos[hj_number] < D[currentRace-1]) {
            try {
                repoStub.reportStatus();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
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
    @Override
    public synchronized HasFinishLineBeenCrossed hasFinishLineBeenCrossed(){

        if (HJPos[fifo[0]] < D[currentRace-1])
            return new HasFinishLineBeenCrossed(HorseJockeyState.RUNNING,false);


        int standingCalc = maxStanding+1;

        for (int i=0; i<N; i++)
            if (winners[i].iteration == iterations[fifo[0]])
                if (winners[i].position < HJPos[fifo[0]]) {
                    winners[i].standing++;
                    //repoStub.setStandingPos(i,winners[i].standing);
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


        try {
            repoStub.setHorseJockeyState(HorseJockeyState.AT_THE_FINNISH_LINE,fifo[0]);
            repoStub.reportStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        totalHJ--;

        if (totalHJ==0) {
            waitForA = true; // variable reset
            fifo = new int[N];
            HJPos = new int[N];
            iterations = new int[N];
            maxStanding=0;

            try {
                for (int i=0; i<N; i++)
                    repoStub.setStandingPos(i,winners[i].standing);
                repoStub.reportStatus();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

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

        return new HasFinishLineBeenCrossed(HorseJockeyState.AT_THE_FINNISH_LINE,true);
    }

    /**
     * Method used to pop the first index and push into the last position
     */
    private void fifoUpdate(){
        int temp = fifo[0];
        //System.out.print(fifo[i]);
        System.arraycopy(fifo, 1, fifo, 0, fifo.length - 1);
        fifo[fifo.length-1]=temp;
        //System.out.print(temp+"\n");
    }

    /**
     * Method used to return an array with all the winning Spectators information
     * @return winners
     */
    @Override
    public Winners[] reportResults(){
        //Winners[] temp = winners;
        return winners;
    }

    @Override
    public synchronized void shutdown(int clientID){
        shutdownRequest=0;
        this.shutdown=true;
        notifyAll();
    }

    public synchronized boolean isShutdown() {

        while (!shutdown)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("RT InterruptedException: "+e);
            }
        return shutdown;

    }

}