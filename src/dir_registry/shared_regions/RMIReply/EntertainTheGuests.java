package shared_regions.RMIReply;

import entities.BrokerState;

import java.io.Serializable;

public class EntertainTheGuests implements Serializable {
    BrokerState brokerState;

    public EntertainTheGuests(BrokerState brokerState) {
        this.brokerState = brokerState;
    }
}
