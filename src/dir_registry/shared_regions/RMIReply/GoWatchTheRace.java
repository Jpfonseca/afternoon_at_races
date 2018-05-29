package shared_regions.RMIReply;

import entities.SpectatorState;

import java.io.Serializable;

public class GoWatchTheRace implements Serializable {
    SpectatorState spectatorState;

    public GoWatchTheRace(SpectatorState spectatorState) {
        this.spectatorState = spectatorState;
    }
}
