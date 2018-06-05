package entities;

import java.io.Serializable;

/**
 * This enumerate specifies the Spectator states
 */
public enum SpectatorState implements Serializable {

    /** blocking state
    * the spectator is waken up by the operation proceedTo
    * Paddock of the last pair horse / jockey to reach the paddock */
    WAITING_FOR_A_RACE_TO_START("WFR"),

    /** blocking state
    * the spectator is waken up by the operation proceedToStartLine of the
    * last pair horse / jockey to leave the paddock */
    APPRAISING_THE_HORSES("ATH"),

    /** blocking state with transition
    * the spectator blocks in queue while waiting to place the bet; he or she is waken
    * up by the broker when the bet is done */
    PLACING_A_BET("PAB"),

    /** blocking state
    *the spectator is waken up by the operation reportResults of the broker */
    WATCHING_A_RACE("WAR"),

    /** blocking state with transition
    * the spectator blocks in queue while waiting to receive the dividends;
    * he or she is waken up by the broker when the transaction is completed */
    COLLECTING_THE_GAINS("CTG"),

    /** final state (transition) */
    CELEBRATING("CEL");

    private String shortName=null;

    SpectatorState(String s){
        this.shortName=s;
    }
    public String getShortName() {
        return shortName;
    }

    public static SpectatorState longName(String shortName) {
        for(SpectatorState e : SpectatorState.values())
            if(shortName.equals(e.getShortName()))
                return e;

        return null;
    }

}