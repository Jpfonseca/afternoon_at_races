package clients;

import communication.ClientCom;
import communication.Message;
import entities.Broker;
import entities.HorseJockey;
import extras.config;
import shared_regions.RacingTrackInterface;
import shared_regions.Winners;

public class RacingTrackStub implements RacingTrackInterface {

    /**
     * Method used by the Broker to start the race
     * @param k current race number
     */
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

    /**
     * Method used by the HorseJockeys to proceed to the start line
     * @param hj_number HorseJockey index number
     */
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

    /**
     * Method used by every HorseJockey to make a move in the Racing track while running
     * @param hj_number HorseJockey index number
     */
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

    /**
     * Method used by the HorseJockeys to know if they have crossed the finish line
     * @return <b>true</b> if he has crossed or <b>false</b>, if he has not.
     */
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

    /**
     * Method used to return an array with all the winning Spectators information
     * @return winners
     */
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

    /**
     * This method sends a Message object containing a Shutdown type
     * */
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
        ClientCom conn = new ClientCom("localhost", config.racingTrackServerPort);

        // Needs to be in while cycle
        while (!conn.open()){
            System.out.println("Issue with (RacingTrack) in \"localhost:"+config.racingTrackServerPort+"\"");
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
