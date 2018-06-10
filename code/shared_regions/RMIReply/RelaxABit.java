package shared_regions.RMIReply;

import entities.SpectatorState;

import java.io.Serializable;

/**
 * RelaxABit Class to return RMIReply
 */
public class RelaxABit implements Serializable {

    /**
     * @serial spectatorState Spectator State
     */
    SpectatorState spectatorState;

    /**
     * RelaxAit RMIReply
     * @param spectatorState Spectator State
     */
    public RelaxABit(SpectatorState spectatorState) {
        this.spectatorState = spectatorState;
    }

    /**
     * Get Spectator State RMIReply
     * @return SpectatorState RMIReply
     */
    public SpectatorState getSpectatorState() {
        return spectatorState;
    }
}
