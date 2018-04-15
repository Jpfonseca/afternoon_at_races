package servers;

import extras.config;
import communication.ServerCom;

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
        InterfaceServers server = null;         // Server
        ServerCom scon, sconi;          // Communication channels
        Aps aps;
        int port = -1;


        //if (args.length == 1) {

            //int service = Integer.parseInt(args[0]);
            int service = 5;

            if (service >= 0 && service < 6)
                port = portNumb + service;

            /* Service Establishment */
            scon = new ServerCom(port);

            switch (config.baseListenPort + service) {
                case config.stableServerPort:           // Stable portNumb = 22220
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
            while (true) {
                sconi = scon.accept();
                aps = new Aps(sconi, server);
                aps.start();

                //try {
                //    aps.join();
                //}catch (InterruptedException ex) {}
            }
        //}
    }
}
