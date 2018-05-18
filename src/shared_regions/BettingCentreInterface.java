package shared_regions;

/**
 * BettingCentreInterface
 * Used to Interface the BettingCentre Server with the "public"
 */
public interface BettingCentreInterface{
    /**
     * Method used by the broker to announce he can collect Spectator's bets
     */
    void acceptTheBets();

    /**
     * This method specifies the existence of winners in the current race.
     * @param winners winners array
     * @return <b>true</b>,if there winners or <b>false</b>, if there aren't any.
     */
    boolean areThereAnyWinners(Winners[] winners);

    /**
     * This method specifies the existence of winners in the current race.
     */
    void honourTheBets();

    /**
     * This method is used by each spectator to place a bet.
     */
    void placeABet();

    /**
     * This method is used by the spectator to check if it has won something from the bet
     * @return <b>true</b> or <b>false</b> whether the spectator won something or not.
     */
    boolean haveIWon();

    /**
     * This method is used by the spectators to collect their gains, if they have won something.
     */
    void goCollectTheGains();

    /**
     * This method sets each HorseJockey odd in the BettingCentre
     */
    void setHorseJockeyOdd();
}
