package clients;

import communication.ClientCom;
import communication.Message;
import entities.Broker;
import entities.HorseJockey;
import entities.Spectator;
import extras.config;
import shared_regions.BettingCentreInterface;
import shared_regions.Winners;

public class BettingCentreStub implements BettingCentreInterface {
    @Override
    public void acceptTheBets() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.ACCEPT_THE_BETS);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_ACCEPT_THE_BETS)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((Broker)Thread.currentThread()).setBrokerState(reply.getBrokerState());
    }

    @Override
    public boolean areThereAnyWinners(Winners[] winners) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.ARE_THERE_ANY_WINNERS, winners);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_ARE_THERE_ANY_WINNERS)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        return reply.getAreThereAnyWinners();
    }

    @Override
    public void honourTheBets() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.HONOUR_THE_BETS);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_HONOUR_THE_BETS)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((Broker)Thread.currentThread()).setBrokerState(reply.getBrokerState());
    }

    @Override
    public void placeABet() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.PLACE_A_BET);

        message.setIndex(((Spectator)Thread.currentThread()).getSpecId());
        message.setWallet(((Spectator)Thread.currentThread()).getWallet());

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_PLACE_A_BET)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((Spectator)Thread.currentThread()).setState(reply.getSpectatorState());
        ((Spectator)Thread.currentThread()).setWallet(reply.getWallet());
    }

    @Override
    public boolean haveIWon() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.HAVE_I_WON);

        message.setIndex(((Spectator)Thread.currentThread()).getSpecId());

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_HAVE_I_WON)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        return reply.getHaveIWon();
    }

    @Override
    public void goCollectTheGains() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.GO_COLLECT_THE_GAINS);

        message.setIndex(((Spectator)Thread.currentThread()).getSpecId());
        message.setWallet(((Spectator)Thread.currentThread()).getWallet());

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_GO_COLLECT_THE_GAINS)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((Spectator)Thread.currentThread()).setState(reply.getSpectatorState());
        ((Spectator)Thread.currentThread()).setWallet(reply.getWallet());
    }

    @Override
    public void setHorseJockeyOdd() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SET_HORSEJOCKEY_ODD);

        message.setOdd(((HorseJockey)Thread.currentThread()).getOdd());
        message.setHorsejockeyNumber(((HorseJockey)Thread.currentThread()).getHj_number());

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_SET_HORSEJOCKEY_ODD)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    public void shutdown() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SHUTDOWN, config.bettingCentreServerPort);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.SHUTDOWN)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Communication with BettingCentre Server running in port 22224 (default)
     * @return ClientCom object
     */
    private ClientCom clientConn(){
        ClientCom conn = new ClientCom("localhost", config.bettingCentreServerPort);

        // Needs to be in while cycle
        while (!conn.open()){
            System.out.println("Issue with (Stable) in \"localhost:"+config.bettingCentreServerPort+"\"");
            try{
                Thread.sleep((long) (1));
            }catch (InterruptedException ex) {}
        }
        //if (!conn.open())
        //    System.out.println("");

        return conn;
    }
}
