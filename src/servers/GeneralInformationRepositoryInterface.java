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
            default:
                break;
        }

        return reply;
    }
}
