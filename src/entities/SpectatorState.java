package entities;

/**
 * This enumerate specifies the Spectator states
 */
public enum SpectatorState {

    /** blocking state
    * the spectator is waken up by the operation proceedTo
    * Paddock of the last pair horse / jockey to reach the paddock */
    WAITING_FOR_A_RACE_TO_START,

    /** blocking state
    * the spectator is waken up by the operation proceedToStartLine of the
    * last pair horse / jockey to leave the paddock */
    APPRAISING_THE_HORSES,

    /** blocking state with transition
    * the spectator blocks in queue while waiting to place the bet; he or she is waken
    * up by the broker when the bet is done */
    PLACING_A_BET,

    /** blocking state
    *the spectator is waken up by the operation reportResults of the broker */
    WATCHING_A_RACE,

    /** blocking state with transition
    * the spectator blocks in queue while waiting to receive the dividends;
    * he or she is waken up by the broker when the transaction is completed */
    COLLECTING_THE_GAINS,

    /** final state (transition) */
    CELEBRATING
}