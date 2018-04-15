package communication;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;

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

    /**
     * @serialField type int Message Type
     */
    private int type = -1;
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

}



