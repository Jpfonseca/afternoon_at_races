package clients;

import communication.ClientCom;
import communication.Message;
import entities.Broker;
import entities.HorseJockey;
import extras.config;
import shared_regions.RacingTrackInterface;
import shared_regions.Winners;

public class RacingTrackStub implements RacingTrackInterface {
    @Override
    public void startTheRace(int k) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.START_THE_RACE, k);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_START_THE_RACE)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((Broker)Thread.currentThread()).setBrokerState(reply.getBrokerState());
    }

    @Override
    public void proceedToStartLine(int hj_number) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.PROCEED_TO_START_LINE, hj_number);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_PROCEED_TO_START_LINE)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((HorseJockey)Thread.currentThread()).setHjState(reply.getHorseJockeyState());
    }

    @Override
    public void makeAMove(int hj_number) {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.MAKE_A_MOVE, hj_number);
        message.setHorsejockeyAgility(((HorseJockey)Thread.currentThread()).getAgility());

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_MAKE_A_MOVE)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    @Override
    public boolean hasFinishLineBeenCrossed() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.HAS_FINISH_LINE_BEEN_CROSSED);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_HAS_FINISH_LINE_BEEN_CROSSED)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        ((HorseJockey)Thread.currentThread()).setHjState(reply.getHorseJockeyState());

        return reply.getFinishLineCrossed();
    }

    @Override
    public Winners[] reportResults() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.REPORT_RESULTS);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.REPLY_REPORT_RESULTS)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();

        return reply.getWinners();
    }

    public void shutdown() {
        ClientCom conn = clientConn();
        Message message, reply;

        message = new Message(Message.SHUTDOWN, config.racingTrackServerPort);

        conn.writeObject(message);
        reply = (Message) conn.readObject();

        if (reply.getType() != Message.SHUTDOWN)
            System.out.println("ERROR: Wrong Message Type = " + reply.getType());

        conn.close();
    }

    /**
     * Communication with RacingTrack Server running in port 22223 (default)
     * @return ClientCom object
     */
    private ClientCom clientConn(){
        ClientCom conn = new ClientCom(config.racingTrackServer, config.racingTrackServerPort);

        // Needs to be in while cycle
        while (!conn.open()){
            System.out.println("Issue with (RacingTrack) in \"localhost:"+config.racingTrackServerPort+"\"");
            try{
                Thread.sleep((long) (1));
            }catch (InterruptedException ex) {}
        }
        //if (!conn.open())
        //    System.out.println("");

        return conn;
    }
}
