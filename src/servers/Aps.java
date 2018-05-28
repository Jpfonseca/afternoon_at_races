package servers;

import communication.Message;
import communication.ServerCom;


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
    private InterfaceServers server;

    /**
     * @serialField sconi Communication channel
     */
    private ServerCom sconi;          // Communication channels

    /**
     * Broker State
     * @serial brokerState
     */
    private BrokerState brokerState;
    /**
     * HorseJockey State
     * @serial horseJockeyState
     */
    private HorseJockeyState horseJockeyState;
    /**
     * Spectator State
     * @serial spectatorState
     */
    private SpectatorState spectatorState;
    /**
     * HorseJockey Agiloty
     * @serial horseJockeyAgiloity
     */
    private int horseJockeyAgility;
    /**
     * HorseJockey index
     * @serial horseJockeyNumber
     */
    private int horseJockeyNumber;
    /**
     * HorseJockey Odd
     * @serial odd
     */
    private int odd;
    /**
     * Spectator Index
     * @serial spectatorIndex
     */
    private int spectatorIndex;
    /**
     * Wallet amount
     * @serial wallet
     */
    private int wallet;

    /**
     * Shutdown counter. Initialized with some positive values if some entities do not use certain servers.
     * @serial shutdownCount
     */
    private static int[] shutdownCount = new int[] {4,0,1,4,0,0};

    /**
     * Service Provider Agent constructor
     *
     * @param sconi communication channel
     * @param server interface server
     */
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
            case Message.GO_CHECK_HORSES2:
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
                break;
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
            case Message.REPLY_GO_CHECK_HORSES2:
                reply.setSpectatorState(spectatorState);
                break;
            case Message.REPLY_PLACE_A_BET:
            case Message.REPLY_GO_COLLECT_THE_GAINS:
                reply.setSpectatorState(spectatorState);
                reply.setWallet(wallet);
                break;
            default:
                break;
        }

        sconi.writeObject(reply);
        sconi.close();

        if (message.getType() == Message.SHUTDOWN)
            shutdownCount[message.getServer()]++;

    }

    /**
     * This method returns the total shutdown messages that each server received.
     * @param server Server id
     * @return Total count
     */
    public static int getShutdownCount(int server) {
        return shutdownCount[server];
    }

    /**
     * Used to set the HorseJockey state
     * @param state state to set
     */
    public void setHjState(HorseJockeyState state) {
        horseJockeyState = state;
    }

    /**
     * Used to set the Broker state
     * @param state Broker state
     */
    public void setBrokerState(BrokerState state) {
        brokerState = state;
    }

    /**
     * This method will be responsible for returning the agility of the horse
     * @return agility Agility
     */
    public int getAgility(){
        return horseJockeyAgility;
    }

    /**
     * This method will return the horse jockey index number.
     * @return hj_number Index number
     */
    public int getHj_number() {
        return horseJockeyNumber;
    }

    /**
     * This method sets the HorseJockey odd
     * @param odd Odd
     */
    public void setOdd(int odd) {
        this.odd = odd;
    }

    /**
     * Used to set the Spectator State
     * @param state state to set
     */
    public void setState(SpectatorState state) {
        this.spectatorState = state;
    }

    /**
     * This method returns the ID of the current Spectator
     * @return Current Spectator ID
     */
    public int getSpecId() {
        return spectatorIndex;
    }

    /**
     * This method returns the total amount in wallet
     * @return wallet
     */
    public int getWallet() {
        return wallet;
    }

    /**
     * This method sets the total amount in wallet
     * @param wallet sum of money in wallet
     */
    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    /**
     * This method returns the HorseJockey odd
     * @return odd Odd
     */
    public int getOdd() {
        return odd;
    }
}
