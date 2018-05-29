package shared_regions;
//import clients.GeneralInformationRepositoryStub;

import entities.BrokerState;
import entities.SpectatorState;
import extras.config;
import shared_regions.RMIReply.EntertainTheGuests;
import shared_regions.RMIReply.GoWatchTheRace;
import shared_regions.RMIReply.RelaxABit;
import shared_regions.RMIReply.WaitForNextRace;

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
    //TODO private GeneralInformationRepositoryStub repoStub;
    /**
     *  Total Spectators in paddock (FIFO)
     *  @serial totalSpec
     */
    private int totalSpec;

    /**
     * Instance of ControlCentre
     * @serialField instance
     */
    private static shared_regions.ControlCentre instance;

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
        //TODO this.repoStub = new GeneralInformationRepositoryStub();

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
    public synchronized EntertainTheGuests entertainTheGuests(){
        // Waiting for childs to die
        //((Aps)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
        //TODO repoStub.setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);

        waitForRaceToStart=true;
        currentRace++;
        notifyAll();

        return new EntertainTheGuests(BrokerState.PLAYING_HOST_AT_THE_BAR);
    }

    /**
     * This method is used to wake up the spectator after all horses have reached the paddock
     */
    @Override
    public synchronized void proceedToPaddock(){
        // Wakes up Spectator that is in CCWS
        //TODO repoStub.reportStatus();

        this.waitForRaceToStart=false;
        notifyAll();
    }

    /**
     * This method is used to know the current state of the Spectators, which will be waiting to start a race
     * @return <b>true</b>if they are waiting, or <b>false</b> if they are not
     */
    @Override
    public synchronized WaitForNextRace waitForNextRace(int specId){

        //Aps spec = ((Aps) Thread.currentThread());

        //repoStub.setOdd(spec.getSpecId(), -1);
        //TODO repoStub.setSpectatorBet(specId, -1, -1);
        //repoStub.reportStatus();

        if (currentRace>0 && currentRace != K+1) {
            //spec.setState((SpectatorState.WAITING_FOR_A_RACE_TO_START));
            //TODO repoStub.setSpectatorState(SpectatorState.WAITING_FOR_A_RACE_TO_START, specId);
            //repoStub.reportStatus();
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

        //return (currentRace <= K);

        return new WaitForNextRace(SpectatorState.WAITING_FOR_A_RACE_TO_START, (currentRace <= K));
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
    public synchronized GoWatchTheRace goWatchTheRace(int specId){

        //((Aps) Thread.currentThread()).setState((SpectatorState.WATCHING_A_RACE));
        //TODO repoStub.setSpectatorState(SpectatorState.WATCHING_A_RACE, specId);
        //TODO repoStub.reportStatus();

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

        return new GoWatchTheRace(SpectatorState.WATCHING_A_RACE);
    }

    /**
     * This method will be used by the Spectator to relax after all the races are finished
     */
    @Override
    public synchronized RelaxABit relaxABit(int specId){

        //((Aps) Thread.currentThread()).setState((SpectatorState.CELEBRATING));
        //TODO repoStub.setSpectatorState(SpectatorState.CELEBRATING,specId);
        //TODO repoStub.reportStatus();

        return new RelaxABit(SpectatorState.CELEBRATING);
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
    public static shared_regions.ControlCentre getInstance(){
        if (instance==null)
            instance = new shared_regions.ControlCentre(config.K, config.M);

        return instance;
    }
}