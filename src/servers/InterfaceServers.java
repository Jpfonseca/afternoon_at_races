package servers;

import communication.Message;

/**
 * InterfaceServers
 */

public interface InterfaceServers {

    /**
     * This method processes and replies to all messages.
     * It's the interface between the actual Repo and the "world"
     * @param msg MEssage object to send
     * @return Message Object to return
     */
    Message processAndReply(Message msg);

}
