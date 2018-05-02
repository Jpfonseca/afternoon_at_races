package communication;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import shared_regions.Winners;

import java.io.Serializable;

/**
 * Message
 * This data type defined the messages that will be exchanged between Clients and Servers
 */

public class Message implements Serializable {

    /**
     * Message Types
     */

    public static final int SHUTDOWN = -1;
    public static final int OK = 1;

    // Repo
    public static final int REPORT_STATUS = 2;
    public static final int SET_BROKER_STATE = 3;
    public static final int SET_SPECTATOR_STATE = 4;
    public static final int SET_SPECTATOR_MONEY = 5;
    public static final int SET_RACE_NUMBER = 6;
    public static final int SET_HORSEJOCKEY_STATE = 7;
    public static final int SET_HORSEJOCKEY_AGILITY = 8;
    public static final int SET_TRACK_DISTANCE = 9;
    public static final int SET_SPECTATOR_BET = 10;
    public static final int SET_ODD = 11;
    public static final int SET_ITERATION_STEP = 12;
    public static final int SET_CURRENT_POS = 13;
    public static final int SET_STANDING_POS = 14;
    // Racing Track
    public static final int START_THE_RACE = 15;
    public static final int PROCEED_TO_START_LINE = 16;
    public static final int MAKE_A_MOVE = 17;
    public static final int HAS_FINISH_LINE_BEEN_CROSSED = 18;
    public static final int REPORT_RESULTS = 19;
    public static final int REPLY_START_THE_RACE = 20;
    public static final int REPLY_PROCEED_TO_START_LINE = 21;
    public static final int REPLY_MAKE_A_MOVE = 22;
    public static final int REPLY_HAS_FINISH_LINE_BEEN_CROSSED = 23;
    public static final int REPLY_REPORT_RESULTS = 24;
    // Stable
    public static final int SUMMON_HORSES_TO_PADDOCK = 25;
    public static final int PROCEED_TO_STABLE = 26;
    public static final int PROCEED_TO_STABLE2 = 27;
    public static final int REPLY_SUMMON_HORSES_TO_PADDOCK = 28;
    public static final int REPLY_PROCEED_TO_STABLE = 29;
    public static final int REPLY_PROCEED_TO_STABLE2 = 30;
    // ControlCentre
    public static final int CCWS_SUMMON_HORSES_TO_PADDOCK = 31;
    public static final int CCWS_START_THE_RACE = 32;
    public static final int CCWS_REPORT_RESULTS = 33;
    public static final int ENTERTAIN_THE_GUESTS = 34;
    public static final int PROCEED_TO_PADDOCK = 35;
    public static final int WAIT_FOR_NEXT_RACE = 36;
    public static final int GO_CHECK_HORSES = 37;
    public static final int GO_WATCH_THE_RACE = 38;
    public static final int RELAX_A_BIT = 39;
    public static final int LAST_HORSE_CROSSED_LINE = 40;
    public static final int REPLY_CCWS_SUMMON_HORSES_TO_PADDOCK = 41;
    public static final int REPLY_CCWS_START_THE_RACE = 42;
    public static final int REPLY_CCWS_REPORT_RESULTS = 43;
    public static final int REPLY_ENTERTAIN_THE_GUESTS = 44;
    public static final int REPLY_PROCEED_TO_PADDOCK = 45;
    public static final int REPLY_WAIT_FOR_NEXT_RACE = 46;
    public static final int REPLY_GO_CHECK_HORSES = 47;
    public static final int REPLY_GO_WATCH_THE_RACE = 48;
    public static final int REPLY_RELAX_A_BIT = 49;
    public static final int REPLY_LAST_HORSE_CROSSED_LINE = 50;
    // BettingCentre
    public static final int ACCEPT_THE_BETS = 51;
    public static final int ARE_THERE_ANY_WINNERS = 52;
    public static final int HONOUR_THE_BETS = 53;
    public static final int PLACE_A_BET = 54;
    public static final int HAVE_I_WON = 55;
    public static final int GO_COLLECT_THE_GAINS = 56;
    public static final int SET_HORSEJOCKEY_ODD = 57;
    public static final int REPLY_ACCEPT_THE_BETS = 58;
    public static final int REPLY_ARE_THERE_ANY_WINNERS = 59;
    public static final int REPLY_HONOUR_THE_BETS = 60;
    public static final int REPLY_PLACE_A_BET = 61;
    public static final int REPLY_HAVE_I_WON = 62;
    public static final int REPLY_GO_COLLECT_THE_GAINS = 63;
    public static final int REPLY_SET_HORSEJOCKEY_ODD = 64;
    // Paddock
    public static final int PROCEED_TO_PADDOCK1 = 65;
    public static final int PROCEED_TO_PADDOCK2 = 66;
    public static final int GO_CHECK_HORSES1 = 67;
    public static final int GO_CHECK_HORSES2= 68;
    public static final int REPLY_PROCEED_TO_PADDOCK1 = 69;
    public static final int REPLY_PROCEED_TO_PADDOCK2 = 70;
    public static final int REPLY_GO_CHECK_HORSES1 = 71;
    public static final int REPLY_GO_CHECK_HORSES2= 72;



    /**
     * @serialField type int Message Type
     */
    private int type = 0;
    private int index;
    private BrokerState brokerState;
    private SpectatorState spectatorState;
    private int spectatorMoney;
    private int raceNumber;
    private HorseJockeyState horseJockeyState;
    private int horseJockeyAgility;
    private int[] trackDistance;
    private int betSelection;
    private int betAmount;
    private int odd;
    private int iterationStep;
    private int position;
    private int standingPos;

    private int currentRace;
    private int horseJockeyNumber;
    private boolean finishLineCrossed;
    private Winners[] winners;

    private int totalAgility;

    private boolean waitForNextRace;

    private boolean areThereAnyWinners;
    private boolean haveIWon;
    private int wallet;
    private boolean last;

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

    public int getType() {
        return type;
    }

    public BrokerState getBrokerState() {
        return brokerState;
    }

    public SpectatorState getSpectatorState() {
        return spectatorState;
    }

    public int getIndex() {
        return index;
    }

    public int getSpectatorMoney() {
        return spectatorMoney;
    }

    public int getRaceNumber() {
        return raceNumber;
    }

    public HorseJockeyState getHorseJockeyState() {
        return horseJockeyState;
    }

    public int getHorseJockeyAgility() {
        return horseJockeyAgility;
    }

    public int[] getTrackDistance() {
        return trackDistance;
    }

    public int getBetSelection() {
        return betSelection;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public int getOdd() {
        return odd;
    }

    public int getIterationStep() {
        return iterationStep;
    }

    public int getPosition() {
        return position;
    }

    public int getStandingPos() {
        return standingPos;
    }

    public int getCurrentRace() {
        return currentRace;
    }

    public int getHorseJockeyNumber() {
        return horseJockeyNumber;
    }

    public boolean getFinishLineCrossed() {
        return finishLineCrossed;
    }

    public Winners[] getWinners() {
        return winners;
    }

    public void setBrokerState(BrokerState state){
        this.brokerState = state;
    }

    public void setHjState(HorseJockeyState state) {
        horseJockeyState = state;
    }

    public void setHorsejockeyAgility(int agility){
        horseJockeyAgility = agility;
    }

    public void setHorsejockeyNumber(int index){
        horseJockeyNumber = index;
    }

    public int getTotalAgility() {
        return totalAgility;
    }

    public void setOdd(int odd) {
        this.odd = odd;
    }

    public boolean getWaitForNextRace() {
        return waitForNextRace;
    }

    public void setSpectatorState(SpectatorState state) {
        this.spectatorState = state;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean getAreThereAnyWinners() {
        return areThereAnyWinners;
    }

    public boolean getHaveIWon() {
        return haveIWon;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public boolean getLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getServer() { return this.server; }
}



