package shared_regions.RMIReply;

import entities.SpectatorState;

import java.io.Serializable;

public class RelaxABit implements Serializable {
    SpectatorState spectatorState;

    public RelaxABit(SpectatorState spectatorState) {
        this.spectatorState = spectatorState;
    }
}
