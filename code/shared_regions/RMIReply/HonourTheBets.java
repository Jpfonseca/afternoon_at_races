package shared_regions.RMIReply;


import entities.BrokerState;

import java.io.Serializable;

/**
 * HonourTheBets Class to return RMIReply
 */
public class HonourTheBets implements Serializable {

    /**
     * @serial state
     */
    private BrokerState state;

    /**
     * HonourTheBets RMIReply
     * @param state Broker State
     */
    public HonourTheBets(BrokerState state){
        this.state=state;
    }

    /**
     * Get BBroker State
     * @return BrokerState RMIReply
     */
    public BrokerState getState() {
        return state;
    }
}
