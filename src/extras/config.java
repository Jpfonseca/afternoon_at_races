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

    /* Part 2 - Distributed Solution (Socket based communication */
    /**
     * Constant that defines the lower port to be used for Listening Sockets.
     * @serial baseListenPort
     */
    public static final int baseListenPort = 22220;
    public static final int stableServerPort = baseListenPort+0;
    public static final int controlCentreServerPort = baseListenPort+1;
    public static final int paddockServerPort = baseListenPort+2;
    public static final int racingTrackServerPort = baseListenPort+3;
    public static final int bettingCentreServerPort = baseListenPort+4;
    public static final int repoServerPort = baseListenPort+5;

    public static String stableServer="l040101-ws01.ua.pt";
    public static String controlCentreServer ="l040101-ws02.ua.pt";
    public static String paddockServer ="l040101-ws03.ua.pt";
    public static String racingTrackServer ="l040101-ws04.ua.pt";
    public static String bettingCentreServer ="l040101-ws05.ua.pt";
    public static String repoServer ="l040101-ws06.ua.pt";

}