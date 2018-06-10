package shared_regions.RMIReply;

import entities.HorseJockeyState;

import java.io.Serializable;

/**
 * HasFinishedLineBeenCrossed Class to return RMIReply
 */
public class HasFinishLineBeenCrossed implements Serializable {

    /**
     * @serial state HorseJockey State
     */
    private HorseJockeyState state;

    /**
     * @serial status HorseJockey Status
     */
    boolean status;

    /**
     * HasFinishLineBeenCrossed RMIReply
     * @param state HorseJockey State
     * @param status HorseJockey status
     */
    public HasFinishLineBeenCrossed(HorseJockeyState state,boolean status){
        this.state=state;
        this.status=status;
    }

    /**
     * Get HorseJockey State
     * @return HorseJockeyState RMIReply
     */
    public HorseJockeyState getState() {
        return state;
    }

    /**
     * Get Status RMIReply
     * @return boolean Get Status
     */
    public boolean getStatus() {
        return status;
    }
}
