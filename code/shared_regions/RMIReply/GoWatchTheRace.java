package shared_regions.RMIReply;

import entities.SpectatorState;

import java.io.Serializable;

/**
 * GoWatchTheRace Class to return RMIReply
 */
public class GoWatchTheRace implements Serializable {

    /**
     * @serial spectatorState Spectator State
     */
    SpectatorState spectatorState;

    /**
     * GoWatchTheRace RMIReply
     * @param spectatorState Spectator State
     */
    public GoWatchTheRace(SpectatorState spectatorState) {
        this.spectatorState = spectatorState;
    }

    /**
     * Get Spectator State
     * @return SpectatorState RMIReply
     */
    public SpectatorState getSpectatorState() {
        return spectatorState;
    }
}
