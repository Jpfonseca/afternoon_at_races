package shared_regions;

/**
 * ControlCentreInterface
 * Used to instantiate ControlCentre Server with the "public"
 */
public interface ControlCentreInterface {

    /**
     * This method is used by the Broker to summon the Horses to the Paddock
     */
    void summonHorsesToPaddock();

    /**
     * This method is used to start the race.<br>
     * It is invoked by the Broker to star the race.
     */
    void startTheRace();

    /**
     * This method is used to by the broker to report the results
     */
    void reportResults();

    /**
     * This method is used by the winner to entertain the guests
     */
    void entertainTheGuests();

    /**
     * This method is used to wake up the spectator after all horses have reached the paddock
     */
    void proceedToPaddock();

    /**
     * This method is used to know the current state of the Spectators, which will be waiting to start a race
     * @return <b>true</b>if they are waiting, or <b>false</b> if they are not
     */
    boolean waitForNextRace();

    /**
     * This method will be used by the Spectators to wake up the broker after they have finished evaluating the horses.
     */
    void goCheckHorses();

    /**
     * This method will be used by the Spectator to start watching a race.
     */
    void goWatchTheRace();

    /**
     * This method will be used by the Spectator to relax after all the races are finished
     */
    void relaxABit();

    /**
     * This method will tell whether last horse has already crossed the finishing line.
     */
    void lastHorseCrossedLine();
}
