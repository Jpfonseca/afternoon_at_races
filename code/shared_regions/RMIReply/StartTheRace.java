package shared_regions.RMIReply;

import entities.BrokerState;

import java.io.Serializable;

/**
 * StartTheRace Class to return RMIReply
 */
public class StartTheRace implements Serializable {

    /**
     * @serial state Broker State
     */
    private BrokerState state;

    /**
     * StartTheRace RMIReply
     * @param state Broker State
     */
    public StartTheRace(BrokerState state){
        this.state=state;
    }

    /**
     * Get Broker State RMIReply
     * @return BrokerState RMIReply
     */
    public BrokerState getState() {
        return state;
    }
}
