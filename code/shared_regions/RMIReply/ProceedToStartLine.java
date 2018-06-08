package shared_regions.RMIReply;

import java.io.Serializable;

import entities.HorseJockeyState;

public class  ProceedToStartLine implements Serializable {
    private HorseJockeyState state;
    public ProceedToStartLine(HorseJockeyState state){
        this.state=state;
    }

    public HorseJockeyState getState() {
        return state;
    }
}
