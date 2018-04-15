package servers;

import communication.Message;

/**
 * InterfaceServers
 */

public interface InterfaceServers {
    Message processAndReply(Message msg);

}
