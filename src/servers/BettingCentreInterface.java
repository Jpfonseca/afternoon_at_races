package servers;

import communication.Message;
import shared_regions.BettingCentre;

public class BettingCentreInterface implements InterfaceServers{

    /**
     * Instance of BettingCentre
     * @serialField bc
     */
    private final BettingCentre bc;

    /**
     * Constructor
     * Instantiates the BettingCentre Object to either a new instance or an already existing
     */
    public BettingCentreInterface() {
        this.bc = BettingCentre.getInstance();
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
            case Message.ACCEPT_THE_BETS:
                bc.acceptTheBets();
                reply = new Message(Message.REPLY_ACCEPT_THE_BETS);
                break;
            case Message.ARE_THERE_ANY_WINNERS:
                boolean areThereAnyWinners = bc.areThereAnyWinners(message.getWinners());
                reply = new Message(Message.REPLY_ARE_THERE_ANY_WINNERS, areThereAnyWinners);
                break;
            case Message.HONOUR_THE_BETS:
                bc.honourTheBets();
                reply = new Message(Message.REPLY_HONOUR_THE_BETS);
                break;
            case Message.PLACE_A_BET:
                bc.placeABet();
                reply = new Message(Message.REPLY_PLACE_A_BET);
                break;
            case Message.HAVE_I_WON:
                boolean haveIWon = bc.haveIWon();
                reply = new Message(Message.REPLY_HAVE_I_WON, haveIWon);
                break;
            case Message.GO_COLLECT_THE_GAINS:
                bc.goCollectTheGains();
                reply = new Message(Message.REPLY_GO_COLLECT_THE_GAINS);
                break;
            case Message.SET_HORSEJOCKEY_ODD:
                bc.setHorseJockeyOdd();
                reply = new Message(Message.REPLY_SET_HORSEJOCKEY_ODD);
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
