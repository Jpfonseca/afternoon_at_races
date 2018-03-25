package shared_regions;
import entities.*;
/**
 * This class specifies the methods that will be executed on the Control Centre and the Watching Stand .
 */
public class ControlCentre{
    private boolean waitForSpectatorEvaluation;
    private boolean waitForRaceToStart;
    private boolean waitForResults;
    private boolean waitForRaceToFinish;
    private int totalWatched=0;
    private int currentRace;
    private int K;
    private int M;
    private GeneralInformationRepository repo;
    /**
     *  Total Spectators in paddock (FIFO)
     *  @serialField queueSpec
     */
    private int totalSpec;

    public ControlCentre(int K, int M, GeneralInformationRepository repo) {
        this.K = K;
        this.M = M;
        this.currentRace=0;
        this.waitForSpectatorEvaluation=true;
        this.waitForRaceToStart=true;
        this.waitForResults=true;
        this.waitForRaceToFinish=true;
        this.repo = repo;

        totalSpec=0;
    }

    public synchronized void summonHorsesToPaddock(int k){
        // Mudar o estado -> ANNOUNCING_NEXT_RACE
        // bloquear em waitForSpectatorEvaluation

        // DO WE NEED k ???!???!???
        this.currentRace++;

        while(waitForSpectatorEvaluation)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Broker ccws.summonHorsesToPaddock() InterruptedException: "+e);
            }

        waitForSpectatorEvaluation=true; // variable reset
    }

    public synchronized void startTheRace(int k){
        // Mudar o estado -> SUPERVISING_THE_RACE
        // bloquear em waitForRaceToFinish

        while (waitForRaceToFinish)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Broker rt.startTheRace() InterruptedException: "+e);
            }

        waitForRaceToFinish=true; // variable reset
    }

    public synchronized void reportResults(int k){

        // Reports results
        waitForResults=false;
        notifyAll();
    }

    public synchronized void entertainTheGuests(){
        // Waiting for childs to die
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
        repo.setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);

        waitForRaceToStart=true;
        currentRace++;
        notifyAll();
    }
    public synchronized void proceedToPaddock(){
        // Wakes up Spectator that is in CCWS
        this.waitForRaceToStart=false;
        notifyAll();
    }

    public synchronized boolean waitForNextRace(){
        // Mudar o estado -> WAITING_FOR_A_RACE_TO_START
        // Block waitForRaceToStart (while(!raceStart && races<K-1))
        while(waitForRaceToStart && currentRace != K+1) {
            // Set in constructor
            //((Spectator) Thread.currentThread()).setState((SpectatorState.WAITING_FOR_A_RACE_TO_START));
            //repo.setSpectatorState(SpectatorState.WAITING_FOR_A_RACE_TO_START,((Spectator) Thread.currentThread()).getspecId());

            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator ccws.waitForNextRace() InterruptedException: " + e);
            }
        }

        totalSpec++;
        if (totalSpec==M) {
            this.waitForRaceToStart = true; // variable reset
            totalSpec = 0;
        }

        return (currentRace <= K);
    }

    public synchronized void goCheckHorses(){
        // Actualiza waitForSpectatorEvaluation
        // Acorda Broker

        waitForSpectatorEvaluation=false;
        notifyAll();
    }

    public synchronized void goWatchTheRace(){
        // Muda o estado -> WATCHING_A_RACE
        // Bloquear em waitForResults

        ((Spectator) Thread.currentThread()).setState((SpectatorState.WATCHING_A_RACE));
        repo.setSpectatorState(SpectatorState.WATCHING_A_RACE,((Spectator)Thread.currentThread()).getspecId());

        while(waitForResults)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator ccws.waitForNextRace() InterruptedException: " + e);
            }

        totalWatched++;
        if (totalWatched==M) {
            this.waitForResults = true; // variable reset
            totalWatched=0;
        }
    }

    public void relaxABit(){
        // muda o estado -> CELEBRATING
        // Preparar para terminar thread
        ((Spectator) Thread.currentThread()).setState((SpectatorState.CELEBRATING));
        repo.setSpectatorState(SpectatorState.CELEBRATING,((Spectator)Thread.currentThread()).getspecId());
    }

    public synchronized void lastHorseCrossedLine(){
        waitForRaceToFinish=false;
        notifyAll();
    }
}