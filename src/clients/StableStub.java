package clients;

import communication.ClientCom;
import communication.Message;
import entities.Broker;
import entities.HorseJockey;
import extras.config;
import shared_regions.StableInterface;

public class StableStub implements StableInterface {
    @Override
    public void summonHorsesToPaddock(int k, int totalAgility) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SUMMON_HORSES_TO_PADDOCK, k, totalAgility);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_SUMMON_HORSES_TO_PADDOCK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((Broker)Thread.currentThread()).setBrokerState(reply.getBrokerState());
    }

    @Override
    public void proceedToStable() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.PROCEED_TO_STABLE);

        message.setHorsejockeyAgility(((HorseJockey)Thread.currentThread()).getAgility());
        message.setHorsejockeyNumber(((HorseJockey)Thread.currentThread()).getHj_number());

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_PROCEED_TO_STABLE)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((HorseJockey)Thread.currentThread()).setHjState(reply.getHorseJockeyState());
        ((HorseJockey)Thread.currentThread()).setOdd(reply.getOdd());
    }

    @Override
    public void proceedToStable2() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.PROCEED_TO_STABLE2);

        message.setHorsejockeyNumber(((HorseJockey)Thread.currentThread()).getHj_number());

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_PROCEED_TO_STABLE2)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((HorseJockey)Thread.currentThread()).setHjState(reply.getHorseJockeyState());
    }

    /**
     * Communication with Stable Server running in port 22220 (default)
     * @return ClientCom object
     */
    private ClientCom clientConn(){
        ClientCom conn = new ClientCom("localhost", config.stableServerPort);

        // Needs to be in while cycle
        while (!conn.open()){
            System.out.println("Issue with (Stable) in \"localhost:"+config.stableServerPort+"\"");
            try{
                Thread.sleep((long) (1));
            }catch (InterruptedException ex) {}
        }
        //if (!conn.open())
        //    System.out.println("");

        return conn;
    }
}
