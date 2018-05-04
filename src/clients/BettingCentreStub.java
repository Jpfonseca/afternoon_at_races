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

    /**
     * Method used by the broker to announce he can collect Spectator's bets
     */
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

    /**
     * This method specifies the existence of winners in the current race.
     * @param winners winners array
     * @return <b>true</b>,if there winners or <b>false</b>, if there aren't any.
     */
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

    /**
     * This method specifies the existence of winners in the current race.
     */
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

    /**
     * This method is used by each spectator to place a bet.
     */
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

    /**
     * This method is used by the spectator to check if it has won something from the bet
     * @return <b>true</b> or <b>false</b> whether the spectator won something or not.
     */
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

    /**
     * This method is used by the spectators to collect their gains, if they have won something.
     */
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

    /**
     * This method sets each HorseJockey odd in the BettingCentre
     */
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

    /**
     * This method sends a Message object containing a Shutdown type
     */
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
            }catch (InterruptedException ex) {
                ex.printStackTrace ();
            }
        }
        //if (!conn.open())
        //    System.out.println("");

        return conn;
    }
}
