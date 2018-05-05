package shared_regions;
import clients.GeneralInformationRepositoryStub;
import entities.*;
import extras.config;
import servers.Aps;

import javax.naming.ldap.Control;

/**
 * This class specifies the methods that will be executed on the Control Centre and the Watching Stand .
 */
public class ControlCentre implements ControlCentreInterface {
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
     * General Repository Stub
     * @serial repo
     */
    //private GeneralInformationRepository repo;
    private GeneralInformationRepositoryStub repo;
    /**
     *  Total Spectators in paddock (FIFO)
     *  @serial totalSpec
     */
    private int totalSpec;

    /**
     * Instance of ControlCentre
     * @serialField instance
     */
    private static ControlCentre instance;

    /**
     * This constructor specifies the initialization of the Control Centre shared Region.
     * @param K  current race number
     * @param M current number spectators
     */
    public ControlCentre(int K, int M) {
        this.K = K;
        this.M = M;
        this.currentRace=0;
        this.waitForSpectatorEvaluation=true;
        this.waitForRaceToStart=true;
        this.waitForResults=true;
        this.waitForRaceToFinish=true;
        this.repo = new GeneralInformationRepositoryStub();

        totalSpec=0;
    }

    /**
     * This method is used by the Broker to summon the Horses to the Paddock
     */
    @Override
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
    @Override
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
    @Override
    public synchronized void reportResults(){

        // Reports results
        waitForResults=false;
        notifyAll();
    }

    /**
     * This method is used by the winner to entertain the guests
     */
    @Override
    public synchronized void entertainTheGuests(){
        // Waiting for childs to die
        ((Aps)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
        repo.setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);

        waitForRaceToStart=true;
        currentRace++;
        notifyAll();
    }

    /**
     * This method is used to wake up the spectator after all horses have reached the paddock
     */
    @Override
    public synchronized void proceedToPaddock(){
        // Wakes up Spectator that is in CCWS
        repo.reportStatus();

        this.waitForRaceToStart=false;
        notifyAll();
    }

    /**
     * This method is used to know the current state of the Spectators, which will be waiting to start a race
     * @return <b>true</b>if they are waiting, or <b>false</b> if they are not
     */
    @Override
    public synchronized boolean waitForNextRace(){

        Aps spec = ((Aps) Thread.currentThread());

        //repo.setOdd(spec.getSpecId(), -1);
        repo.setSpectatorBet(spec.getSpecId(), -1, -1);
        //repo.reportStatus();

        if (currentRace>0 && currentRace != K+1) {
            spec.setState((SpectatorState.WAITING_FOR_A_RACE_TO_START));
            repo.setSpectatorState(SpectatorState.WAITING_FOR_A_RACE_TO_START, spec.getSpecId());
            //repo.reportStatus();
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
    @Override
    public synchronized void goCheckHorses(){

        waitForSpectatorEvaluation=false;
        notifyAll();
    }

    /**
     * This method will be used by the Spectator to start watching a race.
     */
    @Override
    public synchronized void goWatchTheRace(){

        ((Aps) Thread.currentThread()).setState((SpectatorState.WATCHING_A_RACE));
        repo.setSpectatorState(SpectatorState.WATCHING_A_RACE,((Aps)Thread.currentThread()).getSpecId());
        repo.reportStatus();

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
    @Override
    public synchronized void relaxABit(){

        ((Aps) Thread.currentThread()).setState((SpectatorState.CELEBRATING));
        repo.setSpectatorState(SpectatorState.CELEBRATING,((Aps)Thread.currentThread()).getSpecId());
        repo.reportStatus();
    }

    /**
     * This method will tell whether last horse has already crossed the finishing line.
     */
    @Override
    public synchronized void lastHorseCrossedLine(){
        waitForRaceToFinish=false;
        notifyAll();
    }

    /**
     * Returns current instance of ControlCentre
     * @return instance of ControlCentre
     */
    public static ControlCentre getInstance(){
        if (instance==null)
            instance = new ControlCentre(config.K, config.M);

        return instance;
    }
}