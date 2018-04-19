package clients;

import communication.ClientCom;
import communication.Message;
import entities.Broker;
import entities.Spectator;
import extras.config;
import shared_regions.ControlCentreInterface;

public class ControlCentreStub implements ControlCentreInterface {
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
     * Communication with ControlCentre Server running in port 22220 (default)
     * @return ClientCom object
     */
    private ClientCom clientConn(){
        ClientCom conn = new ClientCom("localhost", config.controlCentreServerPort);

        // Needs to be in while cycle
        while (!conn.open()){
            System.out.println("Issue with (ControlCentre) in \"localhost:"+config.controlCentreServerPort+"\"");
            try{
                Thread.sleep((long) (1));
            }catch (InterruptedException ex) {}
        }
        //if (!conn.open())
        //    System.out.println("");

        return conn;
    }
}
