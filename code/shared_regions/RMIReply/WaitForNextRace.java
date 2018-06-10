package shared_regions.RMIReply;

import entities.SpectatorState;

import java.io.Serializable;

public class WaitForNextRace implements Serializable {

    /**
     * @serial spectatorState Spectator State
     */
    SpectatorState spectatorState;

    /**
     * @serial waitForNextRace Wait for next race boolean
     */
    boolean waitForNextRace;

    /**
     * WaitForNextRace RMIReply
     * @param spectatorState Spectator State
     * @param waitForNextRace Wait for next race boolean
     */
    public WaitForNextRace(SpectatorState spectatorState, boolean waitForNextRace) {
        this.spectatorState = spectatorState;
        this.waitForNextRace = waitForNextRace;
    }

    /**
     * Get Spectator State RMIReply
     * @return SpectatorState
     */
    public SpectatorState getSpectatorState() {
        return spectatorState;
    }

    /**
     * Get Wait for Next Race RMIReply
     * @return boolean WaitForNextRace RMIReply
     */

    public boolean getWaitForNextRace() {
        return waitForNextRace;
    }
}
