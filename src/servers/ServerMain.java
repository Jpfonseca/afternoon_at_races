package servers;

import extras.config;
import communication.ServerCom;
import interfaces.Register;
import shared_regions.Stable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * ServerMain
 * Will instantiate every Shared Region Interfaces for every Server
 */

public class ServerMain {

    /**
     * Port Number to be used
     * @serialField portNumb Port number
     */
    private static final int portNumb = config.baseListenPort;
    private static boolean endService;
    /*
        SD ## = 02
        ssh sftp : sd02##@l040101-ws$$.ua.pt
        Ports : G3= 22220 - 22229
        Machines : ws$$= 01 - 10
     */

    /**
     * main cycle of ServerMain
     * Instantiate every server interface
     * Starts Servers "life cycle"
     * @param args command line arguments
     */
    public static void main(String[] args){
        String rmiRegHostName = config.RMI_REGISTRY_HOSTNAME;;
        int rmiRegPortNumb = config.RMI_REGISTRY_PORT;

        /* instanciação e instalação do gestor de segurança */
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        if (args.length == 1) {

            int service = Integer.parseInt(args[0]);

            switch (config.baseListenPort + service) {
                case config.stableServerPort:           // Stable

                    /* localização por nome do objecto remoto no serviço de registos RMI */
                    Stable st = new Stable(config.N);
                    shared_regions.StableInterface stableInterface = null;

                    try {
                        stableInterface = (shared_regions.StableInterface) UnicastRemoteObject.exportObject(st, "REGISTRY_STABLE");
                    } catch (RemoteException e) {
                        System.out.println("Excepção na geração do stub para o Stable: " + e.getMessage());
                        System.exit(1);
                    }
                    System.out.println("O stub para o Stable foi gerado!");

                    /* seu registo no serviço de registo RMI */
                    String nameEntryBase = config.RMI_REGISTER_NAME;
                    String nameEntryObject = "REGISTRY_STABLE";
                    Registry registry = null;
                    Register reg = null;


                    server = new StableInterface();
                    System.out.println("Server Stable is listening!");
                    break;
                case config.controlCentreServerPort:    // ControlCentre portNumb = 22221
                    server = new ControlCentreInterface();
                    System.out.println("Server ControlCentre is listening!");
                    break;
                case config.paddockServerPort:          // Paddock portNumb = 22222
                    server = new PaddockInterface();
                    System.out.println("Server Paddock is listening!");
                    break;
                case config.racingTrackServerPort:      // RacingTrack portNumb = 22223
                    server = new RacingTrackInterface();
                    System.out.println("Server RacingTrack is listening!");
                    break;
                case config.bettingCentreServerPort:    // BettingCentre portNumb = 22224
                    server = new BettingCentreInterface();
                    System.out.println("Server BettingCentre is listening!");
                    break;
                case config.repoServerPort:             // Repo portNumb = 22225
                    server = new GeneralInformationRepositoryInterface();
                    System.out.println("Server GeneralInformationRepository is listening!");
                    break;
                default:
                    break;
            }

            scon.start();
            /* Requests Processing */
            while (!endService) {
            // while(!serviceEnd)
                //sconi = scon.accept();
                //aps = new Aps(sconi, server);
                //aps.start();

                // 1 mensagem por tipo de client


                try {
                    sconi = scon.accept();
                    aps = new Aps(sconi, server);
                    aps.start();
                } catch (java.net.SocketTimeoutException e){
                    //System.out.println(Integer.toString(Aps.getShutdownCount(service)));
                    //continue;
                }

                if (Aps.getShutdownCount(service) == (1 + config.M))
                    endService = true;

            }

            sconi.close();
        }
    }

}
