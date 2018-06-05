package shared_regions.RMIReply;

import entities.SpectatorState;

import java.io.Serializable;

public class WaitForNextRace implements Serializable {
    SpectatorState spectatorState;
    boolean waitForNextRace;

    public WaitForNextRace(SpectatorState spectatorState, boolean waitForNextRace) {
        this.spectatorState = spectatorState;
        this.waitForNextRace = waitForNextRace;
    }

    public SpectatorState getSpectatorState() {
        return spectatorState;
    }

    public boolean getWaitForNextRace() {
        return waitForNextRace;
    }
}
