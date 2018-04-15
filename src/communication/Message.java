package communication;

import java.io.Serializable;

/**
 * Message
 * This data type defined the messages that will be exchanged between Clients and Servers
 */

public class Message implements Serializable {

    /**
     * Message Types
     */

    public static final int SHUTDOWN = -1;
    public static final int OK = 1;

    // Repo
    public static final int REPORT_STATUS = 2;



    /**
     * @serialField type int Message Type
     */
    private int type = -1;


    /**
     * Message Constructor
     */
    public Message() {
        // TEMPORARY FOR ERROR CLEANSE
    }

    /**
     * Message Constructor
     *
     * @param type message type
     */
    public Message(int type) {
        this.type = type;
    }

    /**
     * Message Constructor
     *
     * @param type message type
     * @param val  integer value
     */
    public Message(int type, int val) {
        this.type = type;

        switch (type) {

            default:
                break;
        }
    }

    public int getType() {
        return type;
    }
}



