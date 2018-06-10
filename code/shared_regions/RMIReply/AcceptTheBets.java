package shared_regions.RMIReply;

import entities.BrokerState;

import java.io.Serializable;

/**
 * AcceptTheBets Class to return RMIReply
 */
public class AcceptTheBets implements Serializable {

    /**
     * @serial brokerState State of Broker
     */
    private BrokerState brokerState;

    /**
     * Accept The Bets RMIReturn
     * @param state Broker State
     */
    public AcceptTheBets (BrokerState state){
        this.brokerState=state;
    }

    /**
     * Get Broker State
     * @return BrokerState RMIReply
     */
    public BrokerState getState() {
        return brokerState;
    }
}
