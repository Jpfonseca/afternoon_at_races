package shared_regions.RMIReply;

import entities.BrokerState;

import java.io.Serializable;

public class AcceptTheBets implements Serializable {
    private BrokerState brokerState;

    public AcceptTheBets (BrokerState state){
        this.brokerState=state;
    }
}
