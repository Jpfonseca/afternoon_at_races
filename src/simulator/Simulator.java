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

        GeneralInformationRepository repo = new GeneralInformationRepository("",K,N,M);

        BettingCentre bc = new BettingCentre(M, repo);
        ControlCentre ccws = new ControlCentre(K, M, repo);
        Paddock pd = new Paddock(N, M, repo);
        RacingTrack rt = new RacingTrack(K, N, repo);
        Stable st = new Stable(N, repo);

        Broker broker;
        broker = new Broker(K, N, ccws, st, bc, pd, rt, repo);

        Spectator [] spectator = new Spectator[M];
        for (int i=0; i<M; i++)
            spectator[i] = new Spectator(i,ccws, pd, bc, repo);


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
            repo.setSpectatorState(null, i);
        }

        try {
            broker.join();
        } catch (InterruptedException e) {
            System.out.println("Broker InterruptedException: "+e);
        }

        System.out.println("Broker ended");
    }
}