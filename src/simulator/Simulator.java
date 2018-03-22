package simulator;

import shared_regions.*;
import entities.*;


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
        int K = 5; // K races
        int N = 4; // N competitors per race
        int M = 4; // M Spectators
        int maxBet=100;
        BettingCentre bc = new BettingCentre(M,maxBet);
        ControlCentre ccws = new ControlCentre(K);
        GeneralInformationRepository repo = new GeneralInformationRepository();
        Paddock pd = new Paddock(N, M);
        RacingTrack rt = new RacingTrack(K);
        Stable st = new Stable(N);

        Broker broker;
        broker = new Broker(K, N, ccws, st, bc, pd, rt);

        Spectator [] spectator = new Spectator[M];
        for (int i=0; i<M; i++)
            spectator[i] = new Spectator(i,ccws, pd, bc);


        // Simulation Start
        for (int i=0; i<M; i++){
            spectator[i].start();
            System.out.println("Spectator "+(i+1)+" started");
        }

        broker.start();
        System.out.println("Broker started");


        /* Simulation End */
        for (int i=0; i<M; i++) {
            //while (spectator[i].isAlive()) {
            //    spectator[i].interrupt();
            //    Thread.yield();
            //}
            try {
                spectator[i].join();
            } catch (InterruptedException e) {
                System.out.println("Spectator "+i+" InterruptedException: "+e);
            }
            System.out.println("Spectator "+(i+1)+" ended");
        }

        try {
            broker.join();
        } catch (InterruptedException e) {
            System.out.println("Broker InterruptedException: "+e);
        }

        System.out.println("Broker ended");
    }
}