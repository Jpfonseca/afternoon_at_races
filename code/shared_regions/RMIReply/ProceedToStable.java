package shared_regions.RMIReply;

import entities.HorseJockeyState;

import java.io.Serializable;

/**
 * ProceedToStable Class to return RMIReply
 */
public class ProceedToStable implements Serializable {

    /**
     * @serial odd HorseJockey Odd
     */
    int odd;

    /**
     * @serial hjState HorseJockey State
     */
    HorseJockeyState hjState;

    /**
     * ProceedToStable RMIReply
     * @param odd HorseJockey Odd
     * @param hjState HorseJokcye State
     */
    public ProceedToStable(int odd, HorseJockeyState hjState) {
        this.odd = odd;
        this.hjState = hjState;
    }

    /**
     * Get Odd RMIReply
     * @return int Get HorseJockey Odd
     */
    public int getOdd() {
        return odd;
    }

    /**
     * Get HorseJockey State
     * @return HorseJockeyState RMIReply
     */
    public HorseJockeyState getHjState() {
        return hjState;
    }
}
