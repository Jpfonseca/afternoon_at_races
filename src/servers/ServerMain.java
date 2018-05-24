package servers;

import com.sun.security.ntlm.Server;
import communication.ClientCom;
import extras.config;
import communication.ServerCom;
import interfaces.Register;
import shared_regions.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
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
        String rmiRegHostName = config.RMI_REGISTRY_HOSTNAME;
        int rmiRegPortNumb = config.RMI_REGISTRY_PORT;

        /* instanciação e instalação do gestor de segurança */
        //if (System.getSecurityManager() == null) {
        //    System.setSecurityManager(new SecurityManager());
        //}

        InterfaceServers server = null;

        String nameEntryObject;

        Registry registry = null;
        Register reg = null;

        if (args.length == 1) {

            int service = Integer.parseInt(args[0]);

            switch (config.baseListenPort + service) {
                case config.stableServerPort:           // Stable

                    /* find the remote object by name on the RMI service registry*/
                    Stable st = new Stable(config.N);
                    shared_regions.StableInterface stableStub = null;

                    try {
                        stableStub = (shared_regions.StableInterface) UnicastRemoteObject.exportObject(st, config.RMI_REGISTRY_PORT);
                    } catch (RemoteException e) {
                        System.out.println("Exception while creating the StableStub: " + e.getMessage());
                        System.exit(1);
                    }
                    System.out.println("The StableSub is running!");

                    /* name of the service in the register inside the RMI registry service */
                    nameEntryObject = "STABLE";

                    try {
                        registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
                    } catch (RemoteException e) {
                        System.out.println("Exception while finding the register for the  STABLE on the RMI Registry: " + e.getMessage());
                        System.exit(1);
                    }
                    System.out.println("The StableStub was not found the RMI registry");

                    reg = regLookup(registry);

                    try {
                        reg.bind(nameEntryObject, stableStub);
                    } catch (RemoteException e) {
                        System.out.println("Exception inside the RMI Registry(STABLE): " + e.getMessage());
                        System.exit(1);
                    } catch (AlreadyBoundException e) {
                        System.out.println("There is an instance of the Stable Stub already bounded on the RMI Registry: " + e.getMessage());
                        System.exit(1);
                    }

                    System.out.println("The Stable was successfully registered on the RMI Registry");

                    break;
                case config.controlCentreServerPort:    // ControlCentre portNumb = 22221

                    /* find the remote object by name on the RMI service registry*/
                    ControlCentre ccws = new ControlCentre(config.K, config.M);
                    shared_regions.ControlCentreInterface ccwsStub = null;

                    try {
                        ccwsStub = (shared_regions.ControlCentreInterface) UnicastRemoteObject.exportObject(ccws, config.RMI_REGISTRY_PORT);
                    } catch (RemoteException e) {
                        System.out.println("Exception while creating the ccwsStub: " + e.getMessage());
                        System.exit(1);
                    }
                    System.out.println("The ccwsSub is running!");

                    /* name of the service in the register inside the RMI registry service */

                    nameEntryObject = "CCWS";

                    try {
                        registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
                    } catch (RemoteException e) {
                        System.out.println("Exception while finding the register for the  CCWS on the RMI Registry: " + e.getMessage());
                        System.exit(1);
                    }
                    System.out.println("The StableStub was not found the RMI registry");

                    reg = regLookup(registry);

                    try {
                        reg.bind(nameEntryObject, ccwsStub);
                    } catch (RemoteException e) {
                        System.out.println("Exception inside the RMI Registry(CCWS) : " + e.getMessage());
                        System.exit(1);
                    } catch (AlreadyBoundException e) {
                        System.out.println("There is an instance of the CCWS Stub already bounded on the RMI Registry: " + e.getMessage());
                        System.exit(1);
                    }

                    System.out.println("The CCWS was successfully registered on the RMI Registry");

                    break;
                case config.paddockServerPort:          // Paddock portNumb = 22222
                    /* find the remote object by name on the RMI service registry*/

                    Paddock pd = new Paddock(config.N,config.M);
                    shared_regions.PaddockInterface pdStub=null;

                    try {
                        pdStub=(shared_regions.PaddockInterface) UnicastRemoteObject.exportObject(pd,config.RMI_REGISTRY_PORT);
                    } catch (RemoteException e){
                        System.out.println("Exception while creating the pdStub: " + e.getMessage());
                        System.exit(1);
                    }

                    System.out.println("The pdSub is running!");

                    /* name of the service in the register inside the RMI registry service */

                    nameEntryObject = "PD";

                    try {
                        registry=LocateRegistry.getRegistry(rmiRegHostName,rmiRegPortNumb);
                    } catch (RemoteException e){
                        System.out.println("Exception while finding the register for the  Paddock on the RMI Registry: " + e.getMessage());
                        System.exit(1);
                    }

                    reg = regLookup(registry);

                    try {
                        reg.bind(nameEntryObject,pdStub);
                    }catch (RemoteException e){
                        System.out.println("Exception inside the RMI Registry(PD) : " + e.getMessage());
                        System.exit(1);
                    }catch (AlreadyBoundException e){
                        System.out.println("There is an instance of the Paddock Stub already bounded on the RMI Registry: " + e.getMessage());
                        System.exit(1);
                    }

                    System.out.println("The Paddock was successfully registered on the RMI Registry");
                    break;

                case config.racingTrackServerPort:      // RacingTrack portNumb = 22223
                    /* find the remote object by name on the RMI service registry*/

                    RacingTrack rt = new RacingTrack(config.K, config.N, config.DMin, config.DMax);
                    shared_regions.RacingTrackInterface rtStub=null;

                    try {
                        rtStub = (shared_regions.RacingTrackInterface) UnicastRemoteObject.exportObject(rt,config.RMI_REGISTRY_PORT);
                    }catch (RemoteException e){
                        System.out.println("Exception while creating the rtStub: " + e.getMessage());
                        System.exit(1);
                    }

                    System.out.println("The rtSub is running!");

                    /* name of the service in the register inside the RMI registry service */

                    nameEntryObject = "RT";

                    try {
                        registry=LocateRegistry.getRegistry(rmiRegHostName,rmiRegPortNumb);
                    }catch (RemoteException e){
                        System.out.println("Exception while finding the register for the Racing Track on the RMI Registry: " + e.getMessage());
                        System.exit(1);
                    }

                    reg = regLookup(registry);

                    try {
                        reg.bind(nameEntryObject, rtStub);
                    }catch (RemoteException e){
                        System.out.println("Exception inside the RMI Registry(RT) : " + e.getMessage());
                        System.exit(1);
                    }catch (AlreadyBoundException e){
                        System.out.println("There is an instance of the Racing Track Stub already bounded on the RMI Registry: " + e.getMessage());
                        System.exit(1);
                    }
                    System.out.println("The Racing Track was successfully registered on the RMI Registry");

                    break;
                case config.bettingCentreServerPort:    // BettingCentre portNumb = 22224
                    /* find the remote object by name on the RMI service registry*/

                    BettingCentre bc=new BettingCentre(config.M);
                    shared_regions.BettingCentreInterface bcStub=null;

                    try {
                        bcStub=(shared_regions.BettingCentreInterface) UnicastRemoteObject.exportObject(bc,config.RMI_REGISTRY_PORT);
                    }catch (RemoteException e){
                        System.out.println("Exception while creating the bcStub: " + e.getMessage());
                        System.exit(1);
                    }

                    /* name of the service in the register inside the RMI registry service */
                    nameEntryObject = "BC";

                    try {
                        registry=LocateRegistry.getRegistry(rmiRegHostName,rmiRegPortNumb);
                    }catch (RemoteException e){
                        System.out.println("Exception while finding the register for the Betting Centre on the RMI Registry: " + e.getMessage());
                        System.exit(1);
                    }

                    reg = regLookup(registry);

                    try {
                        reg.bind(nameEntryObject, bcStub);
                    }catch (RemoteException e){
                        System.out.println("Exception inside the RMI Registry(BC) : " + e.getMessage());
                        System.exit(1);
                    }catch (AlreadyBoundException e){
                        System.out.println("There is an instance of the Betting Centr Stub already bounded on the RMI Registry: " + e.getMessage());
                        System.exit(1);
                    }
                    System.out.println("The Betting Centre was successfully registered on the RMI Registry");


                    break;
                case config.repoServerPort:             // Repo portNumb = 22225

                    /* find the remote object by name on the RMI service registry*/
                    GeneralInformationRepository repo = new GeneralInformationRepository(config.logName, config.K, config.N, config.M);
                    shared_regions.GeneralInformationRepositoryInterface repoStub = null;

                    try {
                        repoStub = (shared_regions.GeneralInformationRepositoryInterface) UnicastRemoteObject.exportObject(repo, config.RMI_REGISTRY_PORT);
                    } catch (RemoteException e) {
                        System.out.println("Exception while creating the repoStub: " + e.getMessage());
                        System.exit(1);
                    }

                    /* name of the service in the register inside the RMI registry service */
                    nameEntryObject = "REPO";

                    try {
                        registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
                    } catch (RemoteException e) {
                        System.out.println("Exception while finding the register for the General Information Repository on the RMI Registry:" + e.getMessage());
                        System.exit(1);
                    }

                    reg = regLookup(registry);

                    try {
                        reg.bind(nameEntryObject, repoStub);
                    } catch (RemoteException e) {
                        System.out.println("Exception inside the RMI Registry(REPO) " + e.getMessage());
                        System.exit(1);
                    } catch (AlreadyBoundException e) {
                        System.out.println("There is an instance of the Betting Centr Stub already bounded on the RMI Registry: " + e.getMessage());
                        System.exit(1);
                    }

                    System.out.println("The General Repository was successfully registered on the RMI Registry");

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

    private static Register regLookup(Registry registry) {
        String nameEntryBase = config.RMI_REGISTER_NAME;
        Register reg = null;

        try {
            reg = (Register) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("RegisterRemoteObject lookup created NullPointerException: " + e.getMessage());
            System.exit(1);
        }

        return reg;
    }


}
