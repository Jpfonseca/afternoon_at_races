package shared_regions.RMIReply;

import entities.HorseJockeyState;

import java.io.Serializable;

public class HasFinishLineBeenCrossed implements Serializable {
    private HorseJockeyState state;
    boolean status;

    public HasFinishLineBeenCrossed(HorseJockeyState state,boolean status){
        this.state=state;
        this.status=status;
    }

    public HorseJockeyState getState() {
        return state;
    }

    public boolean getStatus() {
        return status;
    }
}
