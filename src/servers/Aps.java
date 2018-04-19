package servers;

import communication.Message;
import communication.ServerCom;
import entities.BrokerState;
import entities.HorseJockey;
import entities.HorseJockeyState;

/**
 * Aps
 * Agente Prestador de Serviço
 * Where each thread is created to Process And Reply to each Message.
 */

public class Aps extends Thread{

    /**
     * @serialField server InterfaceServer
     * object for all the servers
     */
    InterfaceServers server;

    /**
     * @serialField sconi Communication channel
     */
    ServerCom sconi;          // Communication channels

    private BrokerState brokerState;
    private HorseJockeyState horseJockeyState;
    private int horseJockeyAgility;
    private int horseJockeyNumber;
    private int odd;

    public Aps(ServerCom sconi, InterfaceServers server) {
        this.sconi = sconi;
        this.server = server;
    }

    /**
     * Main method
     * APS "life cycle"
     */
    @Override
    public void run(){
        Message message, reply;

        message = (Message) sconi.readObject();
System.out.println("Message = "+ message.getType());

        switch (message.getType()){
            case Message.MAKE_A_MOVE:
                horseJockeyAgility = message.getHorseJockeyAgility();
                break;
            case Message.PROCEED_TO_STABLE:
                horseJockeyAgility = message.getHorseJockeyAgility();
                horseJockeyNumber = message.getHorseJockeyNumber();
                break;
            case Message.PROCEED_TO_STABLE2:
                horseJockeyNumber = message.getHorseJockeyNumber();
                break;
            default:
                break;
        }

        reply = server.processAndReply(message);
System.out.println("Message Reply = "+ reply.getType());

        switch (reply.getType()){
            case Message.REPLY_START_THE_RACE:
            case Message.REPLY_SUMMON_HORSES_TO_PADDOCK:
                reply.setBrokerState(brokerState);
                break;
            case Message.REPLY_PROCEED_TO_START_LINE:
                reply.setHjState(horseJockeyState);
                break;
            case Message.REPLY_HAS_FINISH_LINE_BEEN_CROSSED:
            case Message.REPLY_PROCEED_TO_STABLE2:
                reply.setHjState(horseJockeyState);
                break;
            case Message.REPLY_PROCEED_TO_STABLE:
                reply.setHjState(horseJockeyState);
                reply.setOdd(odd);
                break;
            default:
                break;
        }

        sconi.writeObject(reply);
        sconi.close();
    }

    public void setHjState(HorseJockeyState state) {
        horseJockeyState = state;
    }

    public void setBrokerState(BrokerState state) {
        brokerState = state;
    }

    public int getAgility(){
        return horseJockeyAgility;
    }

    public int getHj_number() {
        return horseJockeyNumber;
    }

    public void setOdd(int odd) {
        this.odd = odd;
    }
}
