package shared_regions.RMIReply;

import entities.BrokerState;

import java.io.Serializable;

public class SummonHorsesToPaddock implements Serializable {
    BrokerState brokerState;

    public SummonHorsesToPaddock(BrokerState brokerState) {
        this.brokerState = brokerState;
    }
}
