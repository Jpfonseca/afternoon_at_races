package clients;

import communication.ClientCom;
import communication.Message;
import entities.Spectator;
import extras.config;
import shared_regions.PaddockInterface;

public class PaddockStub implements PaddockInterface {
    @Override
    public boolean proceedToPaddock1() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.PROCEED_TO_PADDOCK1);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_PROCEED_TO_PADDOCK1)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        return reply.getLast();
    }

    @Override
    public void proceedToPaddock2() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.PROCEED_TO_PADDOCK2);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_PROCEED_TO_PADDOCK2)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    @Override
    public boolean goCheckHorses1() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.GO_CHECK_HORSES1);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_GO_CHECK_HORSES1)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        return reply.getLast();
    }

    @Override
    public void goCheckHorses2(boolean last) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.GO_CHECK_HORSES2, last);

        message.setIndex(((Spectator)Thread.currentThread()).getSpecId());

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_GO_CHECK_HORSES2)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((Spectator)Thread.currentThread()).setState(reply.getSpectatorState());
    }

    public void shutdown() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SHUTDOWN, config.paddockServerPort);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.SHUTDOWN)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Communication with Stable Server running in port 22222 (default)
     * @return ClientCom object
     */
    private ClientCom clientConn(){
        ClientCom conn = new ClientCom("localhost", config.paddockServerPort);

        // Needs to be in while cycle
        while (!conn.open()){
            System.out.println("Issue with (Paddock) in \"localhost:"+config.paddockServerPort+"\"");
            try{
                Thread.sleep((long) (1));
            }catch (InterruptedException ex) {}
        }
        //if (!conn.open())
        //    System.out.println("");

        return conn;
    }
}
