package servers;

import communication.Message;
import shared_regions.GeneralInformationRepository;

/**
 * RepositoryInterface
 * Used to Interface the Repository Server with the "public"
 */
public class GeneralInformationRepositoryInterface implements InterfaceServers{

    /**
     * Instance of GeneralInformationRepository
     * @serialField repo
     */
    private final GeneralInformationRepository repo;

    /**
     * Constructor
     * Instantiates the repo Object to either a new instance or an already existing
     */
    public GeneralInformationRepositoryInterface() {
        repo = GeneralInformationRepository.getInstance();
    }

    /**
     * This method processes and replies to all messages.
     * It's the interface between the actual Repo and the "world"
     *
     * @param message Receiving Message
     * @return Message type reply
     */
    public Message processAndReply(Message message){
        Message reply = null;
        reply = new Message();

        switch(message.getType()){
            case Message.REPORT_STATUS:
                repo.reportStatus();
                reply = new Message(Message.OK);
                break;
            case Message.SET_BROKER_STATE:
                repo.setBrokerState(message.getBrokerState());
                reply = new Message(Message.OK);
                break;
            case Message.SET_SPECTATOR_STATE:
                repo.setSpectatorState(message.getSpectatorState(), message.getIndex());
                reply = new Message(Message.OK);
                break;
            case Message.SET_SPECTATOR_MONEY:
                repo.setSpectatorMoney(message.getSpectatorMoney(), message.getIndex());
                reply = new Message(Message.OK);
                break;
            case Message.SET_RACE_NUMBER:
                repo.setRaceNumber(message.getRaceNumber());
                reply = new Message(Message.OK);
                break;
            case Message.SET_HORSEJOCKEY_STATE:
                repo.setHorseJockeyState(message.getHorseJockeyState(), message.getIndex());
                reply = new Message(Message.OK);
                break;
            case Message.SET_HORSEJOCKEY_AGILITY:
                repo.setHorseJockeyAgility(message.getHorseJockeyAgility(), message.getIndex());
                reply = new Message(Message.OK);
                break;
            case Message.SET_TRACK_DISTANCE:
                repo.setTrackDistance(message.getTrackDistance());
                reply = new Message(Message.OK);
                break;
            case Message.SET_SPECTATOR_BET:
                repo.setSpectatorBet(message.getIndex(), message.getBetSelection() ,message.getBetAmount());
                reply = new Message(Message.OK);
                break;
            case Message.SET_ODD:
                repo.setOdd(message.getIndex(), message.getOdd());
                reply = new Message(Message.OK);
                break;
            case Message.SET_ITERATION_STEP:
                repo.setIterationStep(message.getIndex(), message.getIterationStep());
                reply = new Message(Message.OK);
                break;
            case Message.SET_CURRENT_POS:
                repo.setCurrentPos(message.getIndex(), message.getPosition());
                reply = new Message(Message.OK);
                break;
            case Message.SET_STANDING_POS:
                repo.setStandingPos(message.getIndex(), message.getStandingPos());
                reply = new Message(Message.OK);
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
