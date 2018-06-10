package shared_regions.RMIReply;

import entities.BrokerState;

import java.io.Serializable;

/**
 * EntertainTheGuests Class to return RMIReply
 */
public class EntertainTheGuests implements Serializable {
    /**
     * @serial brokerState
     */
    BrokerState brokerState;

    /**
     * EntertainTheGuests RMIReply
     * @param brokerState Broker State
     */
    public EntertainTheGuests(BrokerState brokerState) {
        this.brokerState = brokerState;
    }

    /**
     * Get Broker State
     * @return BrokerState RMIReply
     */
    public BrokerState getState() {
        return brokerState;
    }
}
