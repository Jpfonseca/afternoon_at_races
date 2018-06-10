package entities;

import interfaces.*;
import shared_regions.*;
import shared_regions.RMIReply.*;
import extras.*;

import java.rmi.RemoteException;

/**
 * Broker Entity
 * This Entity manages the simulation, and launches Horses/Jockey
 */

public class Broker extends Thread{

    /**
     * General Repository Stub
     * @serial repoStub
     */
    private GeneralInformationRepositoryInterface repoStub;
    /**
     * Broker current State
     * @serial state
     */
    private BrokerState state;
    /**
     * Control Centre and Watching Stand Stub
     * @serial ccws
     */
    private ControlCentreInterface ccwsStub;
    /**
     * Stable Stub
     * @serial st
     */
    private StableInterface stStub;
    /**
     * Betting Centre Stub
     * @serial bc
     */
    private BettingCentreInterface bcStub;
    /**
     * Racing Track Stub
     * @serial rtStub
     */
    private RacingTrackInterface rtStub;

    private PaddockInterface pdStub;
    /**
     * Total races
     * @serial K
     */
    private int K;
    /**
     * Total competitors per race
     * @serial N
     */
    private int N;
    /**
     * Array of Horse/Jockeys used to instantiate and launch the horses in the current simulation
     * @serial horseJockeys
     */
    private HorseJockey[] horseJockeys;
    /**
     * Maximum Agility of the horses. Used to instantiate the horses.
     * @serial maxAgil
     */
    private int maxAgil;
    /**
     * Total Agility of the horses. Used to calculate the odd.
     * @serial totalAgility
     */
    private int totalAgility;

    /**
     *
     * Broker Constructor
     *
     * @param K Total races
     * @param N Number of Horses in each race
     * @param maxAgil maximum Agility of each HorseJockey
     * @param repoStub Repository Stub
     * @param bcStub Betting Centre Stub
     * @param ccwsStub CCWS Stub
     * @param pdStub Paddock Stub
     * @param rtStub Racing Track Stub
     * @param stStub Stable Stub
     *
     */
    public Broker(int K, int N, int maxAgil, ControlCentreInterface ccwsStub, StableInterface stStub, BettingCentreInterface bcStub, RacingTrackInterface rtStub, GeneralInformationRepositoryInterface repoStub, PaddockInterface pdStub) {
    //public Broker(int K, int N, ControlCentre ccws, Stable st, BettingCentre bc, Paddock pd, int maxAgil, RacingTrack rtStub) {
        this.K = K;
        this.N = N;
        this.ccwsStub = ccwsStub;
        this.stStub = stStub;
        this.bcStub  = bcStub;
        this.rtStub = rtStub;
        this.repoStub = repoStub;
        this.maxAgil = maxAgil;
        this.horseJockeys = new HorseJockey[N];

        this.state=BrokerState.OPENING_THE_EVENT; // set current Broker state to the initial state
        try {
            repoStub.setBrokerState(this.state);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.pdStub = pdStub;
    }

    /**
     * Broker main life cycle
     */
    @Override
    public void run(){


        try {
            //K is the number of races
            //k is the current race
            //N competitors per race

            for(int k=1;k<=K;k++) {
                // HorseJockey Instantiation and start
                totalAgility = 0;
                for (int j = 0; j < N; j++) {
                    horseJockeys[j] = new HorseJockey(j, maxAgil, ccwsStub, stStub, pdStub, rtStub, bcStub, repoStub);
                    horseJockeys[j].start();
                    totalAgility += horseJockeys[j].getAgility();
                    System.out.println("HorseJockey "+(j+1)+" started");
                }

                System.out.println("Race "+k+" Start");


                this.setBrokerState(stStub.summonHorsesToPaddock(k,totalAgility).getState()); // primeira parte invocada no stable a segunda no ccws
                ccwsStub.summonHorsesToPaddock();
                this.setBrokerState(bcStub.acceptTheBets().getState());
                this.setBrokerState(rtStub.startTheRace(k).getState());
                ccwsStub.startTheRace();

                if (bcStub.areThereAnyWinners(rtStub.reportResults()).getWinner()) {
                    ccwsStub.reportResults();
                    this.setBrokerState(bcStub.honourTheBets().getState());
                }else
                    ccwsStub.reportResults();


                System.out.println("Race "+k+" End");

                // Wait for HorseJockey threads to finish
                for (int j = 0; j < N; j++) {
                    try {
                        horseJockeys[j].join();
                    } catch (InterruptedException e) {
                        System.out.println("HorseJockey "+j+" InterruptedException: "+e);
                    }
                    System.out.println("HorseJockey "+(j+1)+" ended");
                }

            }
            this.setBrokerState(ccwsStub.entertainTheGuests().getState());

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            bcStub.shutdown(0);
            rtStub.shutdown(0);
            stStub.shutdown(0);
            ccwsStub.shutdown(0);
            repoStub.shutdown(0);
        } catch (RemoteException e) {
            System.out.println("A exception has occurred on the Broker while shutting down the servers : "+ e.getMessage());
            e.printStackTrace();
        }



    }

    /**
     * Method to change the current State of the Broker.
     * @param state Broker state
     */
    public void setBrokerState(BrokerState state) {
        this.state = state;
    }
}