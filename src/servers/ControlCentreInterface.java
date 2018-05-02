package servers;

import communication.Message;
import shared_regions.ControlCentre;

public class ControlCentreInterface implements InterfaceServers{

    /**
     * Instance of ControlCentre
     * @serialField st
     */
    private final ControlCentre ccws;

    /**
     * Constructor
     * Instantiates the ControlCentre Object to either a new instance or an already existing
     */
    public ControlCentreInterface() {
        this.ccws = ControlCentre.getInstance();
    }

    /**
     * This method processes and replies to all messages.
     * It's the interface between the actual ControlCentre and the "world"
     *
     * @param message Receiving Message
     * @return Message type reply
     */
    public Message processAndReply(Message message){
        Message reply;
        reply = new Message();

        switch(message.getType()){
            case Message.CCWS_SUMMON_HORSES_TO_PADDOCK:
                ccws.summonHorsesToPaddock();
                reply = new Message(Message.REPLY_CCWS_SUMMON_HORSES_TO_PADDOCK);
                break;
            case Message.CCWS_START_THE_RACE:
                ccws.startTheRace();
                reply = new Message(Message.REPLY_CCWS_START_THE_RACE);
                break;
            case Message.CCWS_REPORT_RESULTS:
                ccws.reportResults();
                reply = new Message(Message.REPLY_CCWS_REPORT_RESULTS);
                break;
            case Message.ENTERTAIN_THE_GUESTS:
                ccws.entertainTheGuests();
                reply = new Message(Message.REPLY_ENTERTAIN_THE_GUESTS);
                break;
            case Message.PROCEED_TO_PADDOCK:
                ccws.proceedToPaddock();
                reply = new Message(Message.REPLY_PROCEED_TO_PADDOCK);
                break;
            case Message.WAIT_FOR_NEXT_RACE:
                boolean answer = ccws.waitForNextRace();
                reply = new Message(Message.REPLY_WAIT_FOR_NEXT_RACE, answer);
                break;
            case Message.GO_CHECK_HORSES:
                ccws.goCheckHorses();
                reply = new Message(Message.REPLY_GO_CHECK_HORSES);
                break;
            case Message.GO_WATCH_THE_RACE:
                ccws.goWatchTheRace();
                reply = new Message(Message.REPLY_GO_WATCH_THE_RACE);
                break;
            case Message.RELAX_A_BIT:
                ccws.relaxABit();
                reply = new Message(Message.REPLY_RELAX_A_BIT);
                break;
            case Message.LAST_HORSE_CROSSED_LINE:
                ccws.lastHorseCrossedLine();
                reply = new Message(Message.REPLY_LAST_HORSE_CROSSED_LINE);
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
