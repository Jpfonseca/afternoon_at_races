package shared_regions.RMIReply;


import entities.SpectatorState;

import java.io.Serializable;

/**
 * GoCheckHorses2 Class to return RMIReply
 */
public class GoCheckHorses2 implements Serializable {

    /**
     * @serial state
     */
    private SpectatorState state;

    /**
     * GoCheckHorses2 RMIReply
     * @param state Spectator State
     */
    public GoCheckHorses2(SpectatorState state){
        this.state=state;
    }

    /**
     * Get Spectator State
     * @return SpectatorState RMIReply
     */
    public SpectatorState getState() {
        return state;
    }
}
