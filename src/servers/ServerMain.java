package servers;

import com.sun.security.ntlm.Server;
import communication.ClientCom;
import extras.config;
import communication.ServerCom;
import interfaces.Register;
import shared_regions.ControlCentre;
import shared_regions.ControlCentreInterface;
import shared_regions.GeneralInformationRepository;
import shared_regions.Stable;

import java.rmi.AlreadyBoundException;
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
        //if (System.getSecurityManager() == null) {
        //    System.setSecurityManager(new SecurityManager());
        //}

        InterfaceServers server = null;

        String nameEntryBase = config.RMI_REGISTER_NAME;
        String nameEntryObject = null;

        Registry registry = null;
        Register reg = null;
        try {
            reg = (Register) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            System.exit(1);
        }

        if (args.length == 1) {

            int service = Integer.parseInt(args[0]);

            switch (config.baseListenPort + service) {
                case config.stableServerPort:           // Stable

                    /* localização por nome do objecto remoto no serviço de registos RMI */
                    Stable st = new Stable(config.N);
                    shared_regions.StableInterface stableStub = null;

                    try {
                        stableStub = (shared_regions.StableInterface) UnicastRemoteObject.exportObject(st, config.RMI_REGISTRY_PORT);
                    } catch (RemoteException e) {
                        System.out.println("Excepção na geração do stub para o Stable: " + e.getMessage());
                        System.exit(1);
                    }
                    System.out.println("O stub para o Stable foi gerado!");

                    /* seu registo no serviço de registo RMI */
                    nameEntryObject = "STABLE";

                    try {
                        registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
                    } catch (RemoteException e) {
                        System.out.println("Excepção na criação do registo RMI: " + e.getMessage());
                        System.exit(1);
                    }
                    System.out.println("O registo RMI foi criado!");

                    try {
                        reg.bind(nameEntryObject, stableStub);
                    } catch (RemoteException e) {
                        System.out.println("Excepção no registo do Stable: " + e.getMessage());
                        System.exit(1);
                    } catch (AlreadyBoundException e) {
                        System.out.println("O Stable já está bound: " + e.getMessage());
                        System.exit(1);
                    }

                    System.out.println("O Stable foi registado!");

                    break;
                case config.controlCentreServerPort:    // ControlCentre portNumb = 22221

                    /* localização por nome do objecto remoto no serviço de registos RMI */
                    ControlCentre ccws = new ControlCentre(config.K, config.M);;
                    shared_regions.ControlCentreInterface ccwsStub = null;

                    try {
                        ccwsStub = (shared_regions.ControlCentreInterface) UnicastRemoteObject.exportObject(ccws, config.RMI_REGISTRY_PORT);
                    } catch (RemoteException e) {
                        System.out.println("Excepção na geração do stub para o CCWS: " + e.getMessage());
                        System.exit(1);
                    }
                    System.out.println("O stub para o CCWS foi gerado!");

                    /* seu registo no serviço de registo RMI */

                    nameEntryObject = "CCWS";

                    try {
                        registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
                    } catch (RemoteException e) {
                        System.out.println("Excepção na criação do registo RMI: " + e.getMessage());
                        System.exit(1);
                    }
                    System.out.println("O registo RMI foi criado!");

                    try {
                        reg.bind(nameEntryObject, ccwsStub);
                    } catch (RemoteException e) {
                        System.out.println("Excepção no registo do CCWS: " + e.getMessage());
                        System.exit(1);
                    } catch (AlreadyBoundException e) {
                        System.out.println("O CCWS já está bound: " + e.getMessage());
                        System.exit(1);
                    }

                    System.out.println("O CCWS foi registado!");

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

                    /* localização por nome do objecto remoto no serviço de registos RMI */
                    GeneralInformationRepository repo = new GeneralInformationRepository(config.logName, config.K, config.N, config.M);
                    shared_regions.GeneralInformationRepositoryInterface repoStub = null;

                    try {
                        repoStub = (shared_regions.GeneralInformationRepositoryInterface) UnicastRemoteObject.exportObject(repo, config.RMI_REGISTRY_PORT);
                    } catch (RemoteException e) {
                        System.out.println("Excepção na geração do stub para o REPO: " + e.getMessage());
                        System.exit(1);
                    }
                    System.out.println("O stub para o REPO foi gerado!");

                    /* seu registo no serviço de registo RMI */
                    nameEntryObject = "REPO";

                    try {
                        registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
                    } catch (RemoteException e) {
                        System.out.println("Excepção na criação do registo RMI: " + e.getMessage());
                        System.exit(1);
                    }
                    System.out.println("O registo RMI foi criado!");

                    try {
                        reg.bind(nameEntryObject, repoStub);
                    } catch (RemoteException e) {
                        System.out.println("Excepção no registo do REPO: " + e.getMessage());
                        System.exit(1);
                    } catch (AlreadyBoundException e) {
                        System.out.println("O REPO já está bound: " + e.getMessage());
                        System.exit(1);
                    }

                    System.out.println("O REPO foi registado!");

                    break;
                default:
                    break;
            }

        }

        // TODO
        // SYSTEM CALL PARA SHUTDOWN

        // 1 - Unbind
        //reg.unbind(nameEntryObject, engineStub)
        // 2 - unexportObject
        //engineStub = (Compute) UnicastRemoteObject.unexportObject()

    }

}
