package entities;

import java.io.Serializable;

/**
 * This enumerate specifies the different Broker states which a Broker can dwell into.
 */
public enum BrokerState implements Serializable {

    /** initial state (transition) */
    OPENING_THE_EVENT,

    /** blocking state
     * the broker is waken up by the operation goCheckHorses of the last
     * spectator to reach the paddock */
    ANNOUNCING_NEXT_RACE,

    /** blocking state with transition
     *the broker is waken up by the operation placeABet of each of the spectators
     *and blocks again after the bet is accepted; transition only occurs after the
     *betting of all spectators */
    WAITING_FOR_BETS,

    /** blocking state
     * the broker is waken up by the operation makeAMove of the last pair
     * horse / jockey crossing the finishing line */
    SUPERVISING_THE_RACE,

    /** blocking state with transition
     * the broker is waken up by the operation goCollectTheGains of each
     * winning spectator and blocks again after honouring the bet; transition
     * only occurs when all spectators have been paid */
    SETTLING_ACCOUNTS,

    /** final state (transition) */
    PLAYING_HOST_AT_THE_BAR

}