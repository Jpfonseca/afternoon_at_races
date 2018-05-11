package communication;

/**
 * This is a type for exception messages
 * MessageException is returned if exceptions are caught
 */
public class MessageException extends Exception {

    /**
     * Message to be sent
     *
     * @serialField message Message Actual message
     */
    private Message message;

    private static final long serialVersionUID = 1001L;


    /**
     * Instantiate MessageException
     *
     * @param error Tells the error
     * @param message Exception message
     */
    public MessageException(String error, Message message){
        super(error);
        this.message = message;
    }

    /**
     * Method for getting the message
     *
     * @return message
     */
    public Message getMessageVal()
    {
        return (message);
    }
}