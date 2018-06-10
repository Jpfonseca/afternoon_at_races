package shared_regions.RMIReply;

import entities.BrokerState;

import java.io.Serializable;

/**
 * SummonHorsesToPaddock Class to return RMIReply
 */
public class SummonHorsesToPaddock implements Serializable {

    /**
     * @serial brokerState Broker State
     */
    BrokerState brokerState;

    /**
     * SummonHorsesToPaddock RMIReply
     * @param brokerState Broker State
     */
    public SummonHorsesToPaddock(BrokerState brokerState) {
        this.brokerState = brokerState;
    }

    /**
     * Get Broker State RMIReply
     * @return BrokerState RMIReply
     */
    public BrokerState getState() {
        return brokerState;
    }
}
