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
    InterfaceServers server;

    /**
     * @serialField sconi Communication channel
     */
    ServerCom sconi;          // Communication channels

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
        Message message=null, reply=null;

        message = (Message) sconi.readObject();
System.out.println("Message = "+ message.getType());

        reply = server.processAndReply(message);
System.out.println("Message Reply = "+ reply.getType());

        sconi.writeObject(reply);
        sconi.close();
    }

}
