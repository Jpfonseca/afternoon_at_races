package extras;

/**
 * Configurations for the simulation
 */
public class config {
    /**
     * Constant that defines the log file name.
     * @serial logName
     */
    public static final String logName = "Afternoon_At_Races.log";
    /**
     * Constant that defines how many Races there are in the simulation.
     * @serial K
     */
    public static final int K = 5;
    /**
     * Constant that defines how many HorseJockeys there are in the simulation.
     * @serial N
     */
    public static final int N = 4;
    /**
     * Constant that defines how many Spectators there are in the simulation.
     * @serial M
     */
    public static final int M = 4;
    /**
     * Constant that defines the minimum size of the RaceTrack.
     * @serial DMin
     */
    public static final int DMin = 50;
    /**
     * Constant that defines the maximum size of the RaceTrack.
     * @serial DMax
     */
    public static final int DMax = 100;
    /**
     * Constant that defines the wallet starting amount
     * @serial wallet
     */
    public static final int wallet = 100;
    /**
     * Constant that defines the maximum agility of each HorseJockey pair.
     * @serial maxAgility
     */
    public static final int maxAgility = 20;
}
