package shared_regions.RMIReply;

import entities.HorseJockeyState;

import java.io.Serializable;

public class ProceedToStable2 implements Serializable {
    HorseJockeyState hjState;

    public ProceedToStable2(HorseJockeyState hjState) {
        this.hjState = hjState;
    }
}
