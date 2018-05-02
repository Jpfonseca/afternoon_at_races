package servers;

import communication.Message;
import shared_regions.Paddock;

public class PaddockInterface implements InterfaceServers{

    /**
     * Instance of Paddock
     * @serialField pd
     */
    private final Paddock pd;

    /**
     * Constructor
     * Instantiates the Paddock Object to either a new instance or an already existing
     */
    public PaddockInterface() {
        this.pd = Paddock.getInstance();
    }

    /**
     * This method processes and replies to all messages.
     * It's the interface between the actual Paddock and the "world"
     *
     * @param message Receiving Message
     * @return Message type reply
     */
    public Message processAndReply(Message message){
        Message reply;
        reply = new Message();

        switch(message.getType()){
            case Message.PROCEED_TO_PADDOCK1:
                boolean last = pd.proceedToPaddock1();
                reply = new Message(Message.REPLY_PROCEED_TO_PADDOCK1, last);
                break;
            case Message.PROCEED_TO_PADDOCK2:
                pd.proceedToPaddock2();
                reply = new Message(Message.REPLY_PROCEED_TO_PADDOCK2);
                break;
            case Message.GO_CHECK_HORSES1:
                boolean last2 = pd.goCheckHorses1();
                reply = new Message(Message.REPLY_GO_CHECK_HORSES1, last2);
                break;
            case Message.GO_CHECK_HORSES2:
                pd.goCheckHorses2(message.getLast());
                reply = new Message(Message.REPLY_GO_CHECK_HORSES2);
                break;
            case Message.SHUTDOWN:
                reply = new Message(Message.SHUTDOWN);
                break;
            default:
                break;
        }

        return reply;
    }
}
