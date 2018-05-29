package shared_regions.RMIReply;

import entities.HorseJockeyState;

import java.io.Serializable;

public class ProceedToStable implements Serializable {
    int odd;
    HorseJockeyState hjState;

    public ProceedToStable(int odd, HorseJockeyState hjState) {
        this.odd = odd;
        this.hjState = hjState;
    }
}
