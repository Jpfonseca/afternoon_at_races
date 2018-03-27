package shared_regions;
import entities.*;
/**
 * This class specifies the methods that will be executed on the Control Centre and the Watching Stand .
 */
public class ControlCentre{
    /**
     * Condition Statement used to know when Spectators have appraised the horses and are ready to continue
     * @serial waitForSpectatorEvaluation
     */
    private boolean waitForSpectatorEvaluation;
    /**
     * Condition Statement used by the Spectators to know when the Race can begin
     * @serial waitForRaceToStart
     */
    private boolean waitForRaceToStart;
    /**
     * Condition Statement used by the Spectators to know when the Results are ready
     * @serial waitForResults
     */
    private boolean waitForResults;
    /**
     * Condition Statement used by the Broker to know when the Last HorseJockey has made its last move
     * @serial waitForRaceToFinish
     */
    private boolean waitForRaceToFinish;
    /**
     * Variable to count how many Spectators have finished watching the race
     * @serial totalWatched
     */
    private int totalWatched=0;
    /**
     * Variable to keep track of the current Race number
     * @serial currentRace
     */
    private int currentRace;
    /**
     * Variable to keep track of how many Races there are
     * @serial K
     */
    private int K;
    /**
     * Variable to keep track of how many Spectators there are
     * @serial M
     */
    private int M;
    /**
     * General Repository
     * @serial repo
     */
    private GeneralInformationRepository repo;
    /**
     *  Total Spectators in paddock (FIFO)
     *  @serial totalSpec
     */
    private int totalSpec;

    /**
     * This constructor specifies the initialization of the Control Centre shared Region.
     * @param K  current race number
     * @param M current number spectators
     * @param repo General Information Repository -Shared Region
     */
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

    /**
     * This method is used by the Broker to summon the Horses to the Paddock
     */
    public synchronized void summonHorsesToPaddock(){

        this.currentRace++;

        while(waitForSpectatorEvaluation)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Broker ccws.summonHorsesToPaddock() InterruptedException: "+e);
            }

        waitForSpectatorEvaluation=true; // variable reset
        waitForRaceToFinish=true;
    }

    /**
     * This method is used to start the race.<br>
     * It is invoked by the Broker to star the race.
     */
    public synchronized void startTheRace(){

        while (waitForRaceToFinish)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Broker ccws.startTheRace() InterruptedException: "+e);
            }

        waitForRaceToFinish=true; // variable reset
    }

    /**
     * This method is used to by the broker to report the results
     */
    public synchronized void reportResults(){

        // Reports results
        waitForResults=false;
        notifyAll();
    }

    /**
     * This method is used by the winner to entertain the guests
     */
    public synchronized void entertainTheGuests(){
        // Waiting for childs to die
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
        repo.setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);

        waitForRaceToStart=true;
        currentRace++;
        notifyAll();
    }

    /**
     * This method is used to wake up the spectator after all horses have reached the paddock
     */
    public synchronized void proceedToPaddock(){
        // Wakes up Spectator that is in CCWS
        this.waitForRaceToStart=false;
        notifyAll();
    }

    /**
     * This method is used to know the current state of the Spectators, which will be waiting to start a race
     * @return <b>true</b>if they are waiting, or <b>false</b> if they are not
     */
    public synchronized boolean waitForNextRace(){

        Spectator spec = ((Spectator) Thread.currentThread());

        repo.setOdd(spec.getSpecId(), -1);
        repo.setSpectatorBet(spec.getSpecId(), -1, -1);
        //repo.reportStatus();

        if (currentRace>1 && currentRace != K+1) {
            ((Spectator) Thread.currentThread()).setState((SpectatorState.WAITING_FOR_A_RACE_TO_START));
            repo.setSpectatorState(SpectatorState.WAITING_FOR_A_RACE_TO_START, ((Spectator) Thread.currentThread()).getSpecId());
        }

        while(waitForRaceToStart && currentRace != K+1)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator ccws.waitForNextRace() InterruptedException: " + e);
            }

        totalSpec++;
        if (totalSpec==M) {
            this.waitForRaceToStart = true; // variable reset
            totalSpec = 0;
        }

        return (currentRace <= K);
    }

    /**
     * This method will be used by the Spectators to wake up the broker after they have finished evaluating the horses.
     */
    public synchronized void goCheckHorses(){

        waitForSpectatorEvaluation=false;
        notifyAll();
    }

    /**
     * This method will be used by the Spectator to start watching a race.
     */
    public synchronized void goWatchTheRace(){

        ((Spectator) Thread.currentThread()).setState((SpectatorState.WATCHING_A_RACE));
        repo.setSpectatorState(SpectatorState.WATCHING_A_RACE,((Spectator)Thread.currentThread()).getSpecId());

        while(waitForResults)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator ccws.goWatchTheRace() InterruptedException: " + e);
            }

        totalWatched++;
        if (totalWatched==M) {
            this.waitForResults = true; // variable reset
            totalWatched=0;
        }
    }

    /**
     * This method will be used by the Spectator to relax after all the races are finished
     */
    public void relaxABit(){

        ((Spectator) Thread.currentThread()).setState((SpectatorState.CELEBRATING));
        repo.setSpectatorState(SpectatorState.CELEBRATING,((Spectator)Thread.currentThread()).getSpecId());
    }

    /**
     * This method will tell whether last horse has already crossed the finishing line.
     */
    public synchronized void lastHorseCrossedLine(){
        waitForRaceToFinish=false;
        notifyAll();
    }
}