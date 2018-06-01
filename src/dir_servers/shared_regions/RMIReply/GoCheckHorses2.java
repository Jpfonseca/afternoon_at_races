package shared_regions.RMIReply;


import entities.SpectatorState;

import java.io.Serializable;

public class GoCheckHorses2 implements Serializable {

    private SpectatorState state;

    public GoCheckHorses2(SpectatorState state){
        this.state=state;
    }
}
