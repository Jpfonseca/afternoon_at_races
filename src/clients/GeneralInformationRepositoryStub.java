package clients;

import communication.ClientCom;
import communication.Message;
import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import extras.config;
import shared_regions.GeneralInformationRepositoryInterface;

public class GeneralInformationRepositoryStub implements GeneralInformationRepositoryInterface {

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
     * ShutDown of Repository Server
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
        ClientCom conn = new ClientCom(config.repoServer, config.repoServerPort);

        // Needs to be in while cycle
        while (!conn.open()){
            System.out.println("Issue with (REPOSITORY) in \"localhost:"+config.repoServerPort+"\"");
            try{
                Thread.sleep((long) (1));
            }catch (InterruptedException ex) {}
        }
        //if (!conn.open())
        //    System.out.println("");

        return conn;
    }
}
