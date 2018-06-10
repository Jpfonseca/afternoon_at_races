package simulator;

//import extras.config;

import entities.Broker;
import entities.Spectator;
import interfaces.*;
import extras.config;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static extras.config.*;


/**
 * Main Program
 */
public class Simulator{
    /**
     * Main Program - Simulator
     * Entity instantiations and launch threads
     * @param args Arguments
     */
    public static void main (String [] args) {
        /*
        Assume there are five races, each having four competitors and that the number of spectators is also four.
         */

        //GeneralInformationRepository repo = new GeneralInformationRepository(logName,K,N,M);

        //BettingCentre bc = new BettingCentre(M);
        //ControlCentre ccws = new ControlCentre(K, M);
        //Paddock pd = new Paddock(N, M);
        //RacingTrack rt = new RacingTrack(K, N, DMin, DMax);
        //Stable st = new Stable(N);

        ControlCentreInterface ccwsStub = null;
        StableInterface stStub = null;
        BettingCentreInterface bcStub = null;
        RacingTrackInterface rtStub = null;
        GeneralInformationRepositoryInterface repoStub = null;
        PaddockInterface pdStub = null;

        String rmiRegHostName = config.RMI_REGISTRY_HOSTNAME;
        int rmiRegPortNumb = config.RMI_REGISTRY_PORT;

        Registry registry = null;
        String nameEntryObject;

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);

            nameEntryObject = "REPO";
            repoStub = (GeneralInformationRepositoryInterface) registry.lookup (nameEntryObject);
            nameEntryObject = "STABLE";
            stStub = (StableInterface) registry.lookup (nameEntryObject);
            nameEntryObject = "CCWS";
            ccwsStub = (ControlCentreInterface) registry.lookup (nameEntryObject);
            nameEntryObject = "PD";
            pdStub = (PaddockInterface) registry.lookup (nameEntryObject);
            nameEntryObject = "RT";
            rtStub = (RacingTrackInterface) registry.lookup (nameEntryObject);
            nameEntryObject = "BC";
            bcStub = (BettingCentreInterface) registry.lookup (nameEntryObject);

        } catch (RemoteException e) {
            System.out.println("Exception while finding the register for the General Information Repository on the RMI Registry:" + e.getMessage());
            e.printStackTrace ();
            System.exit(1);
        }catch (NotBoundException e){
            e.printStackTrace ();
            System.exit(1);
        }
        System.out.println("The Repo Stub was found the RMI registry");


        if (args.length == 1) {

            int client = Integer.parseInt(args[0]);

            switch (client){
                case 0: // Broker

                    Broker broker;
                    broker = new Broker(K, N, maxAgility, ccwsStub, stStub, bcStub,rtStub, repoStub, pdStub);

                    broker.start();
                    System.out.println("Broker started");

                    try {
                        broker.join();
                    } catch (InterruptedException e) {
                        System.out.println("Broker InterruptedException: " + e);
                    }

                    System.out.println("Broker ended");

                    break;
                case 1: // Spectators

                    Spectator[] spectator = new Spectator[M];
                    for (int i = 0; i < M; i++)
                        spectator[i] = new Spectator(i, wallet, ccwsStub, pdStub, bcStub, repoStub);

                    // Simulation Start
                    for (int i = 0; i < M; i++) {
                        spectator[i].start();
                        System.out.println("Spectator " + (i + 1) + " started");
                    }

                    /* Simulation End */
                    for (int i = 0; i < M; i++) {
                        try {
                            spectator[i].join();
                        } catch (InterruptedException e) {
                            System.out.println("Spectator " + i + " InterruptedException: " + e);
                        }
                        System.out.println("Spectator " + (i + 1) + " ended");
                        //repo.setSpectatorState(null, i);
                    }

                    break;
                default:
                    break;
            }
        }
    }
}