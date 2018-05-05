package clients;

import communication.ClientCom;
import communication.Message;
import entities.Broker;
import entities.Spectator;
import extras.config;
import shared_regions.ControlCentreInterface;

public class ControlCentreStub implements ControlCentreInterface {

    /**
     * This method is used by the Broker to summon the Horses to the Paddock
     */
    @Override
    public void summonHorsesToPaddock() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.CCWS_SUMMON_HORSES_TO_PADDOCK);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_CCWS_SUMMON_HORSES_TO_PADDOCK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * This method is used to start the race.<br>
     * It is invoked by the Broker to star the race.
     */
    @Override
    public void startTheRace() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.CCWS_START_THE_RACE);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_CCWS_START_THE_RACE)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * This method is used to by the broker to report the results
     */
    @Override
    public void reportResults() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.CCWS_REPORT_RESULTS);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_CCWS_REPORT_RESULTS)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * This method is used by the winner to entertain the guests
     */
    @Override
    public void entertainTheGuests() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.ENTERTAIN_THE_GUESTS);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_ENTERTAIN_THE_GUESTS)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((Broker)Thread.currentThread()).setBrokerState(reply.getBrokerState());
    }

    /**
     * This method is used to wake up the spectator after all horses have reached the paddock
     */
    @Override
    public void proceedToPaddock() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.PROCEED_TO_PADDOCK);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_PROCEED_TO_PADDOCK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * This method is used to know the current state of the Spectators, which will be waiting to start a race
     * @return <b>true</b>if they are waiting, or <b>false</b> if they are not
     */
    @Override
    public boolean waitForNextRace() {
                ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.WAIT_FOR_NEXT_RACE);

        message.setIndex(((Spectator)Thread.currentThread()).getSpecId());

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_WAIT_FOR_NEXT_RACE)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((Spectator)Thread.currentThread()).setState(reply.getSpectatorState());

        return reply.getWaitForNextRace();
    }

    /**
     * This method will be used by the Spectators to wake up the broker after they have finished evaluating the horses.
     */
    @Override
    public void goCheckHorses() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.GO_CHECK_HORSES);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_GO_CHECK_HORSES)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * This method will be used by the Spectator to start watching a race.
     */
    @Override
    public void goWatchTheRace() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.GO_WATCH_THE_RACE);

        message.setIndex(((Spectator)Thread.currentThread()).getSpecId());

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_GO_WATCH_THE_RACE)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((Spectator)Thread.currentThread()).setState(reply.getSpectatorState());
    }

    /**
     * This method will be used by the Spectator to relax after all the races are finished
     */
    @Override
    public void relaxABit() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.RELAX_A_BIT);

        message.setIndex(((Spectator)Thread.currentThread()).getSpecId());

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_RELAX_A_BIT)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((Spectator)Thread.currentThread()).setState(reply.getSpectatorState());
    }

    /**
     * This method will tell whether last horse has already crossed the finishing line.
     */
    @Override
    public void lastHorseCrossedLine() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.LAST_HORSE_CROSSED_LINE);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_LAST_HORSE_CROSSED_LINE)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * This method sends a Message object containing a Shutdown type
     */
    public void shutdown() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SHUTDOWN, config.controlCentreServerPort);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.SHUTDOWN)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Communication with ControlCentre Server running in port 22220 (default)
     * @return ClientCom object
     */
    private ClientCom clientConn(){
        ClientCom conn = new ClientCom(config.controlCentreServer, config.controlCentreServerPort);

        // Needs to be in while cycle
        while (!conn.open()){
            System.out.println("Issue with (ControlCentre) in \"localhost:"+config.controlCentreServerPort+"\"");
            try{
                Thread.sleep((long) (1));
            }catch (InterruptedException ex) {
                ex.printStackTrace ();
            }
        }
        //if (!conn.open())
        //    System.out.println("");

        return conn;
    }
}
