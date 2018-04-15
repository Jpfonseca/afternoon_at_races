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

        switch (type) {

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

    public void setBrokerState(BrokerState brokerState) {
        this.brokerState = brokerState;
    }

    public SpectatorState getSpectatorState() {
        return spectatorState;
    }

    public void setSpectatorState(SpectatorState spectatorState) {
        this.spectatorState = spectatorState;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSpectatorMoney() {
        return spectatorMoney;
    }

    public void setSpectatorMoney(int spectatorMoney) {
        this.spectatorMoney = spectatorMoney;
    }

    public int getRaceNumber() {
        return raceNumber;
    }

    public void setRaceNumber(int raceNumber) {
        this.raceNumber = raceNumber;
    }

    public HorseJockeyState getHorseJockeyState() {
        return horseJockeyState;
    }

    public void setHorseJockeyState(HorseJockeyState horseJockeyState) {
        this.horseJockeyState = horseJockeyState;
    }

    public int getHorseJockeyAgility() {
        return horseJockeyAgility;
    }

    public void setHorseJockeyAgility(int horseJockeyAgility) {
        this.horseJockeyAgility = horseJockeyAgility;
    }

    public int[] getTrackDistance() {
        return trackDistance;
    }

    public void setTrackDistance(int[] trackDistance) {
        this.trackDistance = trackDistance;
    }

    public int getBetSelection() {
        return betSelection;
    }

    public void setBetSelection(int betSelection) {
        this.betSelection = betSelection;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public int getOdd() {
        return odd;
    }

    public void setOdd(int odd) {
        this.odd = odd;
    }

    public int getIterationStep() {
        return iterationStep;
    }

    public void setIterationStep(int iterationStep) {
        this.iterationStep = iterationStep;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getStandingPos() {
        return standingPos;
    }

    public void setStandingPos(int standingPos) {
        this.standingPos = standingPos;
    }
}



