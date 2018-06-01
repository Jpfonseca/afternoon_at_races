package shared_regions.RMIReply;


import entities.BrokerState;

import java.io.Serializable;

public class HonourTheBets implements Serializable {
    private  BrokerState state;
    public HonourTheBets(BrokerState state){
        this.state=state;
    }
}
