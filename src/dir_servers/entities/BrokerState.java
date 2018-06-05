package entities;

import java.io.Serializable;

/**
 * This enumerate specifies the different Broker states which a Broker can dwell into.
 */
public enum BrokerState implements Serializable {

    /** initial state (transition) */
    OPENING_THE_EVENT("OPTE"),

    /** blocking state
     * the broker is waken up by the operation goCheckHorses of the last
     * spectator to reach the paddock */
    ANNOUNCING_NEXT_RACE("ANNR"),

    /** blocking state with transition
     *the broker is waken up by the operation placeABet of each of the spectators
     *and blocks again after the bet is accepted; transition only occurs after the
     *betting of all spectators */
    WAITING_FOR_BETS("WAFB"),

    /** blocking state
     * the broker is waken up by the operation makeAMove of the last pair
     * horse / jockey crossing the finishing line */
    SUPERVISING_THE_RACE("SUTR"),

    /** blocking state with transition
     * the broker is waken up by the operation goCollectTheGains of each
     * winning spectator and blocks again after honouring the bet; transition
     * only occurs when all spectators have been paid */
    SETTLING_ACCOUNTS("SETA"),

    /** final state (transition) */
    PLAYING_HOST_AT_THE_BAR("PHATB");



    private String shortName=null;

    BrokerState(String s){
        this.shortName=s;
    }
    public String getShortName() {
        return shortName;
    }

    public static BrokerState longName(String shortName) {
        for(BrokerState e : BrokerState.values())
            if(shortName.equals(e.getShortName()))
                return e;

        return null;
    }


}