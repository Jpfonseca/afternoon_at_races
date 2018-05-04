package clients;

import communication.ClientCom;
import communication.Message;
import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import extras.config;
import shared_regions.GeneralInformationRepositoryInterface;

public class GeneralInformationRepositoryStub implements GeneralInformationRepositoryInterface {

    /**
     * This method is used to print a pair of Log Lines into a file containing the current snapshot of the current simulation status
     */
    @Override
    public void reportStatus() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.REPORT_STATUS);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.OK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Used to set the Broker state
     * @param brokerState Broker state
     */
    @Override
    public void setBrokerState(BrokerState brokerState) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SET_BROKER_STATE, brokerState);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.OK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Used to set the Spectator State
     * @param state state to set
     * @param index index of the Spectator
     */
    @Override
    public void setSpectatorState(SpectatorState state, int index) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SET_SPECTATOR_STATE, index, state);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.OK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Used to set the Spectator amount of money in the wallet
     * @param money amount of money
     * @param index index of Spectator
     */
    @Override
    public void setSpectatorMoney(int money, int index) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SET_SPECTATOR_MONEY, money, index);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.OK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Used to set the current race number
     * @param raceNumber race number
     */
    @Override
    public void setRaceNumber(int raceNumber) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SET_RACE_NUMBER, raceNumber);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.OK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Used to set the HorseJockey state
     * @param state state to set
     * @param index HorseJockey's index
     */
    @Override
    public void setHorseJockeyState(HorseJockeyState state, int index) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SET_HORSEJOCKEY_STATE, index, state);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.OK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Used to set the HorseJockey agility
     * @param agility magility to set
     * @param index HorseJockey's index
     */
    @Override
    public void setHorseJockeyAgility(int agility, int index) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SET_HORSEJOCKEY_AGILITY, agility, index);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.OK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Used to set all the track distances
     * @param d distance
     */
    @Override
    public void setTrackDistance(int[] d) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SET_TRACK_DISTANCE, d);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.OK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Used to set the spectators bets
     * @param spectatorIndex Spectator's index
     * @param betSelection bet selection index
     * @param betAmount bet amount
     */
    @Override
    public void setSpectatorBet(int spectatorIndex, int betSelection, int betAmount) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SET_SPECTATOR_BET, spectatorIndex, betSelection, betAmount);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.OK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Used to the HorseJockey odd
     * @param horse HorseJockey index
     * @param odd odd
     */
    @Override
    public void setOdd(int horse, int odd) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SET_ODD, horse, odd);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.OK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Used to set the iteration step
     * @param horse HorseJockey index
     * @param iterationStep iteration number
     */
    @Override
    public void setIterationStep(int horse, int iterationStep) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SET_ITERATION_STEP, horse, iterationStep);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.OK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Used to set the current HorseJockey position in the race
     * @param horse HorseJockey index
     * @param position position
     */
    @Override
    public void setCurrentPos(int horse, int position) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SET_CURRENT_POS, horse, position);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.OK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Used to set the current HorseJockey standing position
     * @param horse HorseJockey index
     * @param standing standing position
     */
    @Override
    public void setStandingPos(int horse, int standing) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SET_STANDING_POS, horse, standing);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.OK)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * This method sends a Message object containing a Shutdown type
     */
    public void shutdown() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SHUTDOWN, config.repoServerPort);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.SHUTDOWN)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Communication with Repository Server running in port 22225 (default)
     * @return ClientCom object
     */
    private ClientCom clientConn(){
        ClientCom conn = new ClientCom("localhost", config.repoServerPort);

        // Needs to be in while cycle
        while (!conn.open()){
            System.out.println("Issue with (REPOSITORY) in \"localhost:"+config.repoServerPort+"\"");
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
