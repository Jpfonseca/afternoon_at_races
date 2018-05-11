package servers;

import communication.Message;
import shared_regions.RacingTrack;
import shared_regions.Winners;

/**
 * RacingTrackInterface
 * Used to Interface the RacingTrack Server with the "public"
 */

public class RacingTrackInterface implements InterfaceServers{

    /**
     * Instance of RacingTrack
     * @serialField rt
     */
    private final RacingTrack rt;

    /**
     * Constructor
     * Instantiates the RacingTrack Object to either a new instance or an already existing
     */
    public RacingTrackInterface() {
        rt = RacingTrack.getInstance();
    }

    /**
     * This method processes and replies to all messages.
     * It's the interface between the actual RacingTrack and the "world"
     *
     * @param message Receiving Message
     * @return Message type reply
     */
    public Message processAndReply(Message message){
        Message reply;
        reply = new Message();

        switch(message.getType()){
            case Message.START_THE_RACE:
                rt.startTheRace(message.getCurrentRace());
                reply = new Message(Message.REPLY_START_THE_RACE);
                break;
            case Message.PROCEED_TO_START_LINE:
                rt.proceedToStartLine(message.getHorseJockeyNumber());
                reply = new Message(Message.REPLY_PROCEED_TO_START_LINE);
                break;
            case Message.MAKE_A_MOVE:
                rt.makeAMove(message.getHorseJockeyNumber());
                reply = new Message(Message.REPLY_MAKE_A_MOVE);
                break;
            case Message.HAS_FINISH_LINE_BEEN_CROSSED:
                boolean finishLineCrossed = rt.hasFinishLineBeenCrossed();
                reply = new Message(Message.REPLY_HAS_FINISH_LINE_BEEN_CROSSED, finishLineCrossed);
                break;
            case Message.REPORT_RESULTS:
                Winners[] winners = rt.reportResults();
                reply = new Message(Message.REPLY_REPORT_RESULTS, winners);
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

