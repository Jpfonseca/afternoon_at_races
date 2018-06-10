package shared_regions.RMIReply;

import entities.HorseJockeyState;

import java.io.Serializable;

/**
 * ProceedToStable2 Class to return RMIReply
 */
public class ProceedToStable2 implements Serializable {

    /**
     * @serial hjState HorseJockey State
     */
    HorseJockeyState hjState;

    /**
     * ProceedToStable2 RMIReply
     * @param hjState HorseJockey State
     */
    public ProceedToStable2(HorseJockeyState hjState) {
        this.hjState = hjState;
    }

    /**
     * Get HorseJockey State RMIReply
     * @return HorseJockeyState RMIReply
     */
    public HorseJockeyState getHjState() {
        return hjState;
    }
}
