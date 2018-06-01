package shared_regions.RMIReply;

import entities.BrokerState;

import java.io.Serializable;

public class StartTheRace implements Serializable {
    private BrokerState state;
    public StartTheRace(BrokerState state){
        this.state=state;
    }
}
