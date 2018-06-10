package shared_regions.RMIReply;

import java.io.Serializable;

import entities.HorseJockeyState;

/**
 * ProceedToStartLine Class to return RMIReply
 */
public class  ProceedToStartLine implements Serializable {

    /**
     * @serial state HorseJockey State
     */
    private HorseJockeyState state;

    /**
     * ProceedToStartLine RMIReply
     * @param state HorseJockey State
     */
    public ProceedToStartLine(HorseJockeyState state){
        this.state=state;
    }

    /**
     * Get HorseJockey State RMIReply
     * @return HorseJockeyState RMIReply
     */
    public HorseJockeyState getState() {
        return state;
    }
}
