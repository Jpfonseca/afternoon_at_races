package simulator;

//import extras.config;
import shared_regions.*;
import entities.*;

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

        BettingCentre bc = new BettingCentre(M);
        ControlCentre ccws = new ControlCentre(K, M);
        Paddock pd = new Paddock(N, M);
        //RacingTrack rt = new RacingTrack(K, N, DMin, DMax);
        Stable st = new Stable(N);

        Broker broker;
        broker = new Broker(K, N, ccws, st, bc, pd, maxAgility);

        Spectator [] spectator = new Spectator[M];
        for (int i=0; i<M; i++)
            spectator[i] = new Spectator(i,ccws, pd, bc, wallet);


        // Simulation Start
        for (int i=0; i<M; i++){
            spectator[i].start();
            System.out.println("Spectator "+(i+1)+" started");
        }

        broker.start();
        System.out.println("Broker started");


        /* Simulation End */
        for (int i=0; i<M; i++) {
            try {
                spectator[i].join();
            } catch (InterruptedException e) {
                System.out.println("Spectator "+i+" InterruptedException: "+e);
            }
            System.out.println("Spectator "+(i+1)+" ended");
            //repo.setSpectatorState(null, i);
        }

        try {
            broker.join();
        } catch (InterruptedException e) {
            System.out.println("Broker InterruptedException: "+e);
        }

        System.out.println("Broker ended");
    }
}