package shared_regions;

public interface StableInterface {

    /**
     * Method used by the Broker to summon the horses to Paddock
     * @param k number of current race
     * @param totalAgility Total agility
     */
    void summonHorsesToPaddock(int k, int totalAgility);

    /**
     * Method used by the HorseJockeys to proceed to Stable and wait for the next race
     */
    void proceedToStable();

    /**
     * Method used by the HorseJockeys when they finished running to proceed back to the Stable
     */
    void proceedToStable2();
}
