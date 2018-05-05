package clients;

import communication.ClientCom;
import communication.Message;
import entities.Spectator;
import extras.config;
import shared_regions.PaddockInterface;

public class PaddockStub implements PaddockInterface {

    /**
     * Method used for HorseJockey to know if he is the last one to proceed to paddock
     * @return <b>true</b> if he is the last or <b>false</b>, if he is not.
     * */
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

    /**
     * Method used for HorseJockey to wait in the Paddock
     * */
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

    /**
     * Method used by the Spectator to know if he is the last one to appraise the horses in the Paddock
     * @return <b>true</b> if he is the last or <b>false</b>, if he is not.
     */
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

    /**
     * Method used by the Spectator to wait while appraising the horses in the Paddock
     * @param last last Spectator
     */
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

    /**
     * This method sends a Message object containing a Shutdown type
     */
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
        ClientCom conn = new ClientCom(config.paddockServer, config.paddockServerPort);

        // Needs to be in while cycle
        while (!conn.open()){
            System.out.println("Issue with (Paddock) in \"localhost:"+config.paddockServerPort+"\"");
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
