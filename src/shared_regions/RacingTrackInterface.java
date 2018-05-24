package shared_regions;

import java.rmi.Remote;

/**
 * RacingTrackInterface
 * Used to Interface the RacingTrack Server with the "public"
 */
public interface RacingTrackInterface extends Remote {

    /**
     * Method used by the Broker to start the race
     * @param k current race number
     */
    void startTheRace(int k);

    /**
     * Method used by the HorseJockeys to proceed to the start line
     * @param hj_number HorseJockey index number
     */
    void proceedToStartLine(int hj_number);

    /**
     * Method used by every HorseJockey to make a move in the Racing track while running
     * @param hj_number HorseJockey index number
     */
    void makeAMove(int hj_number);

    /**
     * Method used by the HorseJockeys to know if they have crossed the finish line
     * @return <b>true</b> if he has crossed or <b>false</b>, if he has not.
     */
    boolean hasFinishLineBeenCrossed();

    /**
     * Method used to return an array with all the winning Spectators information
     * @return winners
     */
    Winners[] reportResults();
}
