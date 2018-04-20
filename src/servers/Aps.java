package servers;

import communication.Message;
import communication.ServerCom;
import entities.BrokerState;
import entities.HorseJockey;
import entities.HorseJockeyState;
import entities.SpectatorState;

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
    private SpectatorState spectatorState;
    private int horseJockeyAgility;
    private int horseJockeyNumber;
    private int odd;
    private int spectatorIndex;
    private int wallet;

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
            case Message.WAIT_FOR_NEXT_RACE:
            case Message.GO_WATCH_THE_RACE:
            case Message.RELAX_A_BIT:
            case Message.HAVE_I_WON:
                spectatorIndex = message.getIndex();
                break;
            case Message.PLACE_A_BET:
            case Message.GO_COLLECT_THE_GAINS:
                spectatorIndex = message.getIndex();
                wallet = message.getWallet();
                break;
            case Message.SET_HORSEJOCKEY_ODD:
                odd = message.getOdd();
                horseJockeyNumber = message.getHorseJockeyNumber();
            default:
                break;
        }

        reply = server.processAndReply(message);
System.out.println("Message Reply = "+ reply.getType());

        switch (reply.getType()){
            case Message.REPLY_START_THE_RACE:
            case Message.REPLY_SUMMON_HORSES_TO_PADDOCK:
            case Message.REPLY_ENTERTAIN_THE_GUESTS:
            case Message.REPLY_ACCEPT_THE_BETS:
            case Message.REPLY_HONOUR_THE_BETS:
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
            case Message.REPLY_WAIT_FOR_NEXT_RACE:
            case Message.REPLY_GO_WATCH_THE_RACE:
            case Message.REPLY_RELAX_A_BIT:
                reply.setSpectatorState(spectatorState);
                break;
            case Message.REPLY_PLACE_A_BET:
            case Message.REPLY_GO_COLLECT_THE_GAINS:
                reply.setSpectatorState(spectatorState);
                reply.setWallet(wallet);
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

    public void setState(SpectatorState state) {
        this.spectatorState = state;
    }

    public int getSpecId() {
        return spectatorIndex;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public int getOdd() {
        return odd;
    }
}
