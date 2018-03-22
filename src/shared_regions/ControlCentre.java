package shared_regions;
import entities.*;
/**
 * This class specifies the methods that will be executed on the Control Centre and the Watching Stand .
 */
public class ControlCentre{
    private boolean waitForSpectatorEvaluation;
    private boolean waitForRaceToStart;
    private boolean waitForResults;
    private int currentRace;
    private int K;
    /**
     *  Total Spectators in paddock (FIFO)
     *  @serialField queueSpec
     */
    private int totalSpec;

    public ControlCentre(int K) {
        this.K = K;
        this.currentRace=0;
        this.waitForSpectatorEvaluation=true;
        this.waitForRaceToStart=true;
        this.waitForResults=true;

        totalSpec=0;
    }

    public synchronized void summonHorsesToPaddock(int k){
        // Mudar o estado -> ANNOUNCING_NEXT_RACE
        // bloquear em waitForSpectatorEvaluation

        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);

        // DO WE NEED k ???!???!???
        this.currentRace++;
        this.waitForResults = true; // variable reset

        while(waitForSpectatorEvaluation)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Broker ccws.summonHorsesToPaddock() InterruptedException: "+e);
            }

        waitForSpectatorEvaluation=true; // variable reset
    }

    public void startTheRace(int k){
        // Mudar o estado -> SUPERVISING_THE_RACE
        // bloquear em waitForRaceToFinish
    }

    public synchronized void reportResults(int k){
        // Reports results

        waitForResults=false;
        notifyAll();
    }

    public synchronized void entertainTheGuests(){
        // Waiting for childs to die
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
            ((Spectator) Thread.currentThread()).setState((SpectatorState.WAITING_FOR_A_RACE_TO_START));

            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator ccws.waitForNextRace() InterruptedException: " + e);
            }
        }

        totalSpec++;
        if (totalSpec==4) {
            this.waitForRaceToStart = true; // variable reset
            totalSpec = 0;
        }

        return (currentRace <= K);
    }

    public synchronized boolean goCheckHorses(){
        // Actualiza waitForSpectatorEvaluation
        // Acorda Broker


        // DO WE NEED RETURN?
        waitForSpectatorEvaluation=false;
        notifyAll();

        return false;
    }

    public synchronized void goWatchTheRace(){
        // Muda o estado -> WATCHING_A_RACE
        // Bloquear em waitForResults

        ((Spectator) Thread.currentThread()).setState((SpectatorState.WATCHING_A_RACE));

        while(waitForResults)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator ccws.waitForNextRace() InterruptedException: " + e);
            }
    }

    public void relaxABit(){
        // muda o estado -> CELEBRATING
        // Preparar para terminar thread
        ((Spectator) Thread.currentThread()).setState((SpectatorState.CELEBRATING));
    }
}