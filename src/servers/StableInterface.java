package servers;

import communication.Message;
import shared_regions.Stable;

public class StableInterface implements InterfaceServers{

    /**
     * Instance of Stable
     * @serialField st
     */
    private final Stable st;

    /**
     * Constructor
     * Instantiates the Stable Object to either a new instance or an already existing
     */
    public StableInterface() {
        st = Stable.getInstance();
    }

    /**
     * This method processes and replies to all messages.
     * It's the interface between the actual Stable and the "world"
     *
     * @param message Receiving Message
     * @return Message type reply
     */
    public Message processAndReply(Message message){
        Message reply;
        reply = new Message();

        switch(message.getType()){
            case Message.SUMMON_HORSES_TO_PADDOCK:
                st.summonHorsesToPaddock(message.getCurrentRace(), message.getTotalAgility());
                reply = new Message(Message.REPLY_SUMMON_HORSES_TO_PADDOCK);
                break;
            case Message.PROCEED_TO_STABLE:
                st.proceedToStable();
                reply = new Message(Message.REPLY_PROCEED_TO_STABLE);
                break;
            case Message.PROCEED_TO_STABLE2:
                st.proceedToStable2();
                reply = new Message((Message.REPLY_PROCEED_TO_STABLE2));
                break;
            default:
                break;
        }

        return reply;
    }
}
