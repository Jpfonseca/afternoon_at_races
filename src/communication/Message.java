package communication;

import java.io.Serializable;

/**
 * Message
 * This data type defined the messages that will be exchanged between Clients and Servers
 */

public class Message implements Serializable {

    // Message Types

    /** Message Type = Shutdown
     * @serial SHUTDOWN
     * */
    public static final int SHUTDOWN = -1;
    /** Message Type = OK
     * @serial OK
     * */
    public static final int OK = 1;

    // Repo
    /**
     * Message Type = REPORT_STATUS
     * @serial REPORT_STATUS
     */
    public static final int REPORT_STATUS = 2;
    /**
     * Message Type = SET_BROKER_STATE
      @serial SET_BROKER_STATE
     */
    public static final int SET_BROKER_STATE = 3;
    /**
     * Message Type = SET_SPECTATOR_STATE
      @serial SET_SPECTATOR_STATE
     */
    public static final int SET_SPECTATOR_STATE = 4;
    /**
     * Message Type = SET_SPECTATOR_MONEY
      @serial SET_SPECTATOR_MONEY
     */
    public static final int SET_SPECTATOR_MONEY = 5;
    /**
     * Message Type = SET_RACE_NUMBER
      @serial SET_RACE_NUMBER
     */
    public static final int SET_RACE_NUMBER = 6;
    /**
     * Message Type = SET_HORSEJOCKEY_STATE
      @serial SET_HORSEJOCKEY_STATE
     */
    public static final int SET_HORSEJOCKEY_STATE = 7;
    /**
     * Message Type = SET_HORSEJOCKEY_AGILITY
      @serial SET_HORSEJOCKEY_AGILITY
     */
    public static final int SET_HORSEJOCKEY_AGILITY = 8;
    /**
     * Message Type = SET_TRACK_DISTANCE
      @serial SET_TRACK_DISTANCE
     */
    public static final int SET_TRACK_DISTANCE = 9;
    /**
     * Message Type = SET_SPECTATOR_BET
      @serial SET_SPECTATOR_BET
     */
    public static final int SET_SPECTATOR_BET = 10;
    /**
     * Message Type = SET_ODD
      @serial SET_ODD
     */
    public static final int SET_ODD = 11;
    /**
     * Message Type = SET_ITERATION_STEP
      @serial SET_ITERATION_STEP
     */
    public static final int SET_ITERATION_STEP = 12;
    /**
     * Message Type = SET_CURRENT_POS
      @serial SET_CURRENT_POS
     */
    public static final int SET_CURRENT_POS = 13;
    /**
     * Message Type = SET_STANDING_POS
      @serial SET_STANDING_POS
     */
    public static final int SET_STANDING_POS = 14;
    // Racing Track
    /**
     * Message Type = START_THE_RACE
      @serial START_THE_RACE
     */
    public static final int START_THE_RACE = 15;
    /**
     * Message Type = PROCEED_TO_START_LINE
      @serial PROCEED_TO_START_LINE
     */
    public static final int PROCEED_TO_START_LINE = 16;
    /**
     * Message Type = MAKE_A_MOVE
      @serial MAKE_A_MOVE
     */
    public static final int MAKE_A_MOVE = 17;
    /**
     * Message Type = HAS_FINISH_LINE_BEEN_CROSSED
      @serial HAS_FINISH_LINE_BEEN_CROSSED
     */
    public static final int HAS_FINISH_LINE_BEEN_CROSSED = 18;
    /**
     * Message Type = REPORT_RESULTS
      @serial REPORT_RESULTS
     */
    public static final int REPORT_RESULTS = 19;
    /**
     * Message Type = REPLY_START_THE_RACE
      @serial REPLY_START_THE_RACE
     */
    public static final int REPLY_START_THE_RACE = 20;
    /**
     * Message Type = REPLY_PROCEED_TO_START_LINE
      @serial REPLY_PROCEED_TO_START_LINE
     */
    public static final int REPLY_PROCEED_TO_START_LINE = 21;
    /**
     * Message Type = REPLY_MAKE_A_MOVE
      @serial REPLY_MAKE_A_MOVE
     */
    public static final int REPLY_MAKE_A_MOVE = 22;
    /**
     * Message Type = REPLY_HAS_FINISH_LINE_BEEN_CROSSED
      @serial REPLY_HAS_FINISH_LINE_BEEN_CROSSED
     */
    public static final int REPLY_HAS_FINISH_LINE_BEEN_CROSSED = 23;
    /**
     * Message Type = REPLY_REPORT_RESULTS
      @serial REPLY_REPORT_RESULTS
     */
    public static final int REPLY_REPORT_RESULTS = 24;
    // Stable
    /**
     * Message Type = SUMMON_HORSES_TO_PADDOCK
      @serial SUMMON_HORSES_TO_PADDOCK
     */
    public static final int SUMMON_HORSES_TO_PADDOCK = 25;
    /**
     * Message Type = PROCEED_TO_STABLE
      @serial PROCEED_TO_STABLE
     */
    public static final int PROCEED_TO_STABLE = 26;
    /**
     * Message Type = PROCEED_TO_STABLE2
      @serial PROCEED_TO_STABLE2
     */
    public static final int PROCEED_TO_STABLE2 = 27;
    /**
     * Message Type = REPLY_SUMMON_HORSES_TO_PADDOCK
      @serial REPLY_SUMMON_HORSES_TO_PADDOCK
     */
    public static final int REPLY_SUMMON_HORSES_TO_PADDOCK = 28;
    /**
     * Message Type = REPLY_PROCEED_TO_STABLE
      @serial REPLY_PROCEED_TO_STABLE
     */
    public static final int REPLY_PROCEED_TO_STABLE = 29;
    /**
     * Message Type = REPLY_PROCEED_TO_STABLE2
      @serial REPLY_PROCEED_TO_STABLE2
     */
    public static final int REPLY_PROCEED_TO_STABLE2 = 30;
    // ControlCentre
    /**
     * Message Type = CCWS_SUMMON_HORSES_TO_PADDOCK
      @serial CCWS_SUMMON_HORSES_TO_PADDOCK
     */
    public static final int CCWS_SUMMON_HORSES_TO_PADDOCK = 31;
    /**
     * Message Type = CCWS_START_THE_RACE
      @serial CCWS_START_THE_RACE
     */
    public static final int CCWS_START_THE_RACE = 32;
    /**
     * Message Type = CCWS_REPORT_RESULTS
      @serial CCWS_REPORT_RESULTS
     */
    public static final int CCWS_REPORT_RESULTS = 33;
    /**
     * Message Type = ENTERTAIN_THE_GUESTS
      @serial ENTERTAIN_THE_GUESTS
     */
    public static final int ENTERTAIN_THE_GUESTS = 34;
    /**
     * Message Type = PROCEED_TO_PADDOCK
      @serial PROCEED_TO_PADDOCK
     */
    public static final int PROCEED_TO_PADDOCK = 35;
    /**
     * Message Type = WAIT_FOR_NEXT_RACE
      @serial WAIT_FOR_NEXT_RACE
     */
    public static final int WAIT_FOR_NEXT_RACE = 36;
    /**
     * Message Type = GO_CHECK_HORSES
      @serial GO_CHECK_HORSES
     */
    public static final int GO_CHECK_HORSES = 37;
    /**
     * Message Type = GO_WATCH_THE_RACE
      @serial GO_WATCH_THE_RACE
     */
    public static final int GO_WATCH_THE_RACE = 38;
    /**
     * Message Type = RELAX_A_BIT
      @serial RELAX_A_BIT
     */
    public static final int RELAX_A_BIT = 39;
    /**
     * Message Type = LAST_HORSE_CROSSED_LINE
      @serial LAST_HORSE_CROSSED_LINE
     */
    public static final int LAST_HORSE_CROSSED_LINE = 40;
    /**
     * Message Type = REPLY_CCWS_SUMMON_HORSES_TO_PADDOCK
      @serial REPLY_CCWS_SUMMON_HORSES_TO_PADDOCK
     */
    public static final int REPLY_CCWS_SUMMON_HORSES_TO_PADDOCK = 41;
    /**
     * Message Type = REPLY_CCWS_START_THE_RACE
      @serial REPLY_CCWS_START_THE_RACE
     */
    public static final int REPLY_CCWS_START_THE_RACE = 42;
    /**
     * Message Type = REPLY_CCWS_REPORT_RESULTS
      @serial REPLY_CCWS_REPORT_RESULTS
     */
    public static final int REPLY_CCWS_REPORT_RESULTS = 43;
    /**
     * Message Type = REPLY_ENTERTAIN_THE_GUESTS
      @serial REPLY_ENTERTAIN_THE_GUESTS
     */
    public static final int REPLY_ENTERTAIN_THE_GUESTS = 44;
    /**
     * Message Type = REPLY_PROCEED_TO_PADDOCK
      @serial REPLY_PROCEED_TO_PADDOCK
     */
    public static final int REPLY_PROCEED_TO_PADDOCK = 45;
    /**
     * Message Type = REPLY_WAIT_FOR_NEXT_RACE
      @serial REPLY_WAIT_FOR_NEXT_RACE
     */
    public static final int REPLY_WAIT_FOR_NEXT_RACE = 46;
    /**
     * Message Type = REPLY_GO_CHECK_HORSES
      @serial REPLY_GO_CHECK_HORSES
     */
    public static final int REPLY_GO_CHECK_HORSES = 47;
    /**
     * Message Type = REPLY_GO_WATCH_THE_RACE
      @serial REPLY_GO_WATCH_THE_RACE
     */
    public static final int REPLY_GO_WATCH_THE_RACE = 48;
    /**
     * Message Type = REPLY_RELAX_A_BIT
      @serial REPLY_RELAX_A_BIT
     */
    public static final int REPLY_RELAX_A_BIT = 49;
    /**
     * Message Type = REPLY_LAST_HORSE_CROSSED_LINE
      @serial REPLY_LAST_HORSE_CROSSED_LINE
     */
    public static final int REPLY_LAST_HORSE_CROSSED_LINE = 50;
    // BettingCentre
    /**
     * Message Type = ACCEPT_THE_BETS
      @serial ACCEPT_THE_BETS
     */
    public static final int ACCEPT_THE_BETS = 51;
    /**
     * Message Type = ARE_THERE_ANY_WINNERS
      @serial ARE_THERE_ANY_WINNERS
     */
    public static final int ARE_THERE_ANY_WINNERS = 52;
    /**
     * Message Type = HONOUR_THE_BETS
      @serial HONOUR_THE_BETS
     */
    public static final int HONOUR_THE_BETS = 53;
    /**
     * Message Type = PLACE_A_BET
      @serial PLACE_A_BET
     */
    public static final int PLACE_A_BET = 54;
    /**
     * Message Type = HAVE_I_WON
      @serial HAVE_I_WON
     */
    public static final int HAVE_I_WON = 55;
    /**
     * Message Type = GO_COLLECT_THE_GAINS
      @serial GO_COLLECT_THE_GAINS
     */
    public static final int GO_COLLECT_THE_GAINS = 56;
    /**
     * Message Type = SET_HORSEJOCKEY_ODD
      @serial SET_HORSEJOCKEY_ODD
     */
    public static final int SET_HORSEJOCKEY_ODD = 57;
    /**
     * Message Type = REPLY_ACCEPT_THE_BETS
      @serial REPLY_ACCEPT_THE_BETS
     */
    public static final int REPLY_ACCEPT_THE_BETS = 58;
    /**
     * Message Type = REPLY_ARE_THERE_ANY_WINNERS
      @serial REPLY_ARE_THERE_ANY_WINNERS
     */
    public static final int REPLY_ARE_THERE_ANY_WINNERS = 59;
    /**
     * Message Type = REPLY_HONOUR_THE_BETS
      @serial REPLY_HONOUR_THE_BETS
     */
    public static final int REPLY_HONOUR_THE_BETS = 60;
    /**
     * Message Type = REPLY_PLACE_A_BET
      @serial REPLY_PLACE_A_BET
     */
    public static final int REPLY_PLACE_A_BET = 61;
    /**
     * Message Type = REPLY_HAVE_I_WON
      @serial REPLY_HAVE_I_WON
     */
    public static final int REPLY_HAVE_I_WON = 62;
    /**
     * Message Type = REPLY_GO_COLLECT_THE_GAINS
      @serial REPLY_GO_COLLECT_THE_GAINS
     */
    public static final int REPLY_GO_COLLECT_THE_GAINS = 63;
    /**
     * Message Type = REPLY_SET_HORSEJOCKEY_ODD
      @serial REPLY_SET_HORSEJOCKEY_ODD
     */
    public static final int REPLY_SET_HORSEJOCKEY_ODD = 64;
    // Paddock
    /**
     * Message Type = PROCEED_TO_PADDOCK1
      @serial PROCEED_TO_PADDOCK1
     */
    public static final int PROCEED_TO_PADDOCK1 = 65;
    /**
     * Message Type = PROCEED_TO_PADDOCK2
      @serial PROCEED_TO_PADDOCK2
     */
    public static final int PROCEED_TO_PADDOCK2 = 66;
    /**
     * Message Type = GO_CHECK_HORSES1
      @serial GO_CHECK_HORSES1
     */
    public static final int GO_CHECK_HORSES1 = 67;
    /**
     * Message Type = GO_CHECK_HORSES
      @serial GO_CHECK_HORSES
     */
    public static final int GO_CHECK_HORSES2= 68;
    /**
     * Message Type = REPLY_PROCEED_TO_PADDOCK1
      @serial REPLY_PROCEED_TO_PADDOCK1
     */
    public static final int REPLY_PROCEED_TO_PADDOCK1 = 69;
    /**
     * Message Type = REPLY_PROCEED_TO_PADDOCK2
      @serial REPLY_PROCEED_TO_PADDOCK2
     */
    public static final int REPLY_PROCEED_TO_PADDOCK2 = 70;
    /**
     * Message Type = REPLY_GO_CHECK_HORSES1
      @serial REPLY_GO_CHECK_HORSES1
     */
    public static final int REPLY_GO_CHECK_HORSES1 = 71;
    /**
     * Message Type = REPLY_GO_CHECK_HORSES2
      @serial REPLY_GO_CHECK_HORSES2
     */
    public static final int REPLY_GO_CHECK_HORSES2 = 72;



    /**
     * Message type
     * @serial type int Message Type
     */
    private int type = 0;
    /**
     * Index
     * @serial index
     */
    private int index;
    /**
     * Broker State
     * @serial brokerState
     */
    private BrokerState brokerState;
    /**
     * Spectator State
     * @serial spectatorState
     */
    private SpectatorState spectatorState;
    /**
     * Spectator amount of Money
     * @serial spectatorMoney
     */
    private int spectatorMoney;
    /**
     * Race Number
     * @serial raceNumber
     */
    private int raceNumber;
    /**
     * HorseJockey State
     * @serial horseJockeyState
     */
    private HorseJockeyState horseJockeyState;
    /**
     * HorseJockey Agility
     * @serial horseJockeyAgility
     */
    private int horseJockeyAgility;
    /**
     * Track Distance array
     * @serial trackDistance
     */
    private int[] trackDistance;
    /**
     * Bet Selection
     * @serial betSelection
     */
    private int betSelection;
    /**
     * Bet amount
     * @serial betAmount
     */
    private int betAmount;
    /**
     * Odd
     * @serial odd
     */
    private int odd;
    /**
     * Current Iteration Step
     * @serial iterationStep
     */
    private int iterationStep;
    /**
     * Current Position
     * @serial position
     */
    private int position;
    /**
     * Final Standing position
     * @serial standingPos
     */
    private int standingPos;

    /**
     * Current Race Number
     * @serial currentRace
     */
    private int currentRace;
    /**
     * HorseJockey Number
     * @serial horseJockeyNumber
     */
    private int horseJockeyNumber;
    /**
     * Finish Line Crossed
     * @serial finishLineCrossed
     */
    private boolean finishLineCrossed;
    /**
     * Winners Array
     * @serial winners
     */
    private Winners[] winners;

    /**
     * Total Agility
     * @serial totalAgility
     */
    private int totalAgility;

    /**
     * Wait for next race
     * @serial waitForNextRace
     */
    private boolean waitForNextRace;

    /**
     * Are there any winners
     * @serial areThereAnyWinners
     */
    private boolean areThereAnyWinners;
    /**
     * Have I won
     * @serial haveIWon
     */
    private boolean haveIWon;
    /**
     * Wallet amount
     * @serial wallet
     */
    private int wallet;
    /**
     * Is it last check
     * @serial last
     */
    private boolean last;

    /**
     * Server
     * @serial server
     */
    private int server;

    /**
     * Message Constructor
     */
    public Message() {
        // TEMPORARY FOR ERROR CLEANSE
    }

    /**
     * Message Constructor
     *
     * @param type message type
     */
    public Message(int type) {
        this.type = type;
    }

    /**
     * Message Constructor
     *
     * @param type message type
     * @param val  integer value
     */
    public Message(int type, int val) {
        this.type = type;

        switch (type){
            case SET_RACE_NUMBER:
                this.raceNumber = val;
                break;
            case START_THE_RACE:
                this.currentRace = val;
                break;
            case PROCEED_TO_START_LINE:
                this.horseJockeyNumber = val;
                break;
            case MAKE_A_MOVE:
                this.horseJockeyNumber = val;
                break;
            case SHUTDOWN:
                this.server = val-22220;
                break;
            default:
                break;
        }
    }

    /**
     * Message Constructor
     *
     * @param type message type
     * @param val1 integer value
     * @param val2 integer value
     */
    public Message(int type, int val1, int val2) {
        this.type = type;

        switch (type){
            case SET_SPECTATOR_MONEY:
                this.spectatorMoney = val1;
                this.index = val2;
                break;
            case SET_HORSEJOCKEY_AGILITY:
                this.horseJockeyAgility = val1;
                this.index = val2;
                break;
            case SET_ODD:
                this.index = val1;
                this.odd = val2;
                break;
            case SET_ITERATION_STEP:
                this.index = val1;
                this.iterationStep = val2;
                break;
            case SET_CURRENT_POS:
                this.index = val1;
                this.position = val2;
                break;
            case SET_STANDING_POS:
                this.index = val1;
                this.standingPos = val2;
                break;
            case SUMMON_HORSES_TO_PADDOCK:
                this.currentRace = val1;
                this.totalAgility = val2;
                break;
            default:
                break;
        }
    }

    /**
     * Message Constructor
     *
     * @param type message type
     * @param spectatorIndex Spectator index
     * @param betSelection Bet Selection
     * @param betAmount Bet Amount
     */
    public Message(int type, int spectatorIndex, int betSelection, int betAmount) {
        this.type = type;

        switch(type){
            case SET_SPECTATOR_BET:
                this.index = spectatorIndex;
                this.betSelection = betSelection;
                this.betAmount = betAmount;
                break;
            default:
                break;
        }
    }

    /**
     * Message Constructor
     *
     * @param type message type
     * @param brokerState Broker State
     */
    public Message(int type, BrokerState brokerState) {
        this.type = type;

        switch (type){
            case SET_BROKER_STATE:
                this.brokerState = brokerState;
                break;
            default:
                break;
        }
    }

    /**
     * Message Constructor
     *
     * @param type message type
     * @param index Index
     * @param state Spectator State
     */
    public Message(int type, int index, SpectatorState state) {
        this.type = type;

        switch (type){
            case SET_SPECTATOR_STATE:
                this.index = index;
                this.spectatorState = state;
                break;
            default:
                break;
        }
    }

    /**
     * Message Constructor
     *
     * @param type message type
     * @param index Index
     * @param state HorseJockey State
     */
    public Message(int type, int index, HorseJockeyState state) {
        this.type = type;

        switch (type){
            case SET_HORSEJOCKEY_STATE:
                this.index = index;
                this.horseJockeyState = state;
                break;
            default:
                break;
        }
    }

    /**
     * Message Constructor
     *
     * @param type message type
     * @param d Track distance array
     */
    public Message(int type, int[] d) {
        this.type = type;

        switch(type){
            case SET_TRACK_DISTANCE:
                this.trackDistance = d;
                break;
            default:
                break;
        }
    }

    /**
     * Message Constructor
     *
     * @param type message type
     * @param val Boolean value
     */
    public Message(int type, boolean val){
        this.type = type;

        switch(type){
            case REPLY_HAS_FINISH_LINE_BEEN_CROSSED:
                this.finishLineCrossed = val;
                break;
            case REPLY_WAIT_FOR_NEXT_RACE:
                this.waitForNextRace = val;
                break;
            case REPLY_ARE_THERE_ANY_WINNERS:
                this.areThereAnyWinners = val;
                break;
            case REPLY_HAVE_I_WON:
                this.haveIWon = val;
                break;
            case REPLY_PROCEED_TO_PADDOCK1:
                this.last = val;
                break;
            case REPLY_GO_CHECK_HORSES1:
                this.last = val;
                break;
            case GO_CHECK_HORSES2:
                this.last = val;
                break;
            default:
                break;
        }
    }

    /**
     * Message Constructor
     *
     * @param type message type
     * @param winners Winners array
     */
    public Message(int type, Winners[] winners){
        this.type = type;

        switch(type){
            case REPLY_REPORT_RESULTS:
                this.winners = winners;
                break;
            case ARE_THERE_ANY_WINNERS:
                this.winners = winners;
                break;
            default:
                break;
        }
    }

    /**
     * Method that returns the Message type
     * @return Message Type
     */
    public int getType() {
        return type;
    }

    /**
     * Method that returns the Broker State
     * @return Broker State
     */
    public BrokerState getBrokerState() {
        return brokerState;
    }

    /**
     * Method that returns the Spectator State
     * @return Spectator State
     */
    public SpectatorState getSpectatorState() {
        return spectatorState;
    }

    /**
     * Method that returns the index
     * @return index
     */
    public int getIndex() {
        return index;
    }

    /**
     * This method returns the total amount in wallet
     * @return Spectator Money
     */
    public int getSpectatorMoney() {
        return spectatorMoney;
    }

    /**
     * This method returns the current Race number
     * @return Race Number
     */
    public int getRaceNumber() {
        return raceNumber;
    }

    /**
     * This method returns the HorseJockey State
     * @return HorseJockey State
     */
    public HorseJockeyState getHorseJockeyState() {
        return horseJockeyState;
    }

    /**
     * This method returns the HorseJockey agility
     * @return HorseJockey Agility
     */
    public int getHorseJockeyAgility() {
        return horseJockeyAgility;
    }

    /**
     * This method returns the array of track distances
     * @return Track distance array
     */
    public int[] getTrackDistance() {
        return trackDistance;
    }

    /**
     * This method returns the Bet selection
     * @return Bet Selection
     */
    public int getBetSelection() {
        return betSelection;
    }

    /**
     * This method returns the Bet Amount
     * @return Bet Amount
     */
    public int getBetAmount() {
        return betAmount;
    }

    /**
     * This method returns the Odd
     * @return HorseJockey odd
     */
    public int getOdd() {
        return odd;
    }

    /**
     * This method returns the current Iteration step
     * @return Iteration Step
     */
    public int getIterationStep() {
        return iterationStep;
    }

    /**
     * This method returns the current HorseJockey position
     * @return HorseJockey position
     */
    public int getPosition() {
        return position;
    }

    /**
     * This method returns the final standing position
     * @return HorseJockey standing position
     */
    public int getStandingPos() {
        return standingPos;
    }

    /**
     * This method returns the current race number
     * @return Current race number
     */
    public int getCurrentRace() {
        return currentRace;
    }

    /**
     * This method returns the HorseJockey index
     * @return HorseJockey index
     */
    public int getHorseJockeyNumber() {
        return horseJockeyNumber;
    }

    /**
     * This method returns the check on if the finish line has been crossed
     * @return Finish line crossed
     */
    public boolean getFinishLineCrossed() {
        return finishLineCrossed;
    }

    /**
     * This method returns the array of winners
     * @return Winners array
     */
    public Winners[] getWinners() {
        return winners;
    }

    /**
     * This method sets the Broker State
     * @param state Broker State
     */
    public void setBrokerState(BrokerState state){
        this.brokerState = state;
    }

    /**
     * This method sets the HorseJockey State
     * @param state HorseJockey State
     */
    public void setHjState(HorseJockeyState state) {
        horseJockeyState = state;
    }

    /**
     * This method sets the HorseJockey Agility
     * @param agility HorseJockey Agility
     */
    public void setHorsejockeyAgility(int agility){
        horseJockeyAgility = agility;
    }

    /**
     * This method sets the HorseJockey index
     * @param index HorseJockey Index
     */
    public void setHorsejockeyNumber(int index){
        horseJockeyNumber = index;
    }

    /**
     * This method returns the total HorseJockeys agilities summed
     * @return Total Agility
     */
    public int getTotalAgility() {
        return totalAgility;
    }

    /**
     * This method sets the HorseJockey odd
     * @param odd HorseJockey Odd
     */
    public void setOdd(int odd) {
        this.odd = odd;
    }

    /**
     * This method returns the check on Waiting for the next race
     * @return Wait For Next Race boolean
     */
    public boolean getWaitForNextRace() {
        return waitForNextRace;
    }

    /**
     * This method sets the Spectator State
     * @param state Spectator State
     */
    public void setSpectatorState(SpectatorState state) {
        this.spectatorState = state;
    }

    /**
     * This method sets the Index
     * @param index Index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * This method returns the check on if there are any winners
     * @return Are there any winners boolean
     */
    public boolean getAreThereAnyWinners() {
        return areThereAnyWinners;
    }

    /**
     * This method returns the check on if a spectator has won
     * @return Have I won boolean
     */
    public boolean getHaveIWon() {
        return haveIWon;
    }

    /**
     * This method returns the wallet amount
     * @return wallet amount
     */
    public int getWallet() {
        return wallet;
    }

    /**
     * This method sets the Wallet amount
     * @param wallet Wallet amount
     */
    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    /**
     * This method returns if an entity is the last
     * @return Last entity
     */
    public boolean getLast() {
        return last;
    }

    /**
     * This method sets if an entity is the last
     * @param last Last boolean
     */
    public void setLast(boolean last) {
        this.last = last;
    }

    /**
     * This method returns the server number
     * @return server number
     */
    public int getServer() { return this.server; }
}



