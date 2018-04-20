package entities;

import clients.*;
import shared_regions.*;

/**
 * Broker Entity
 * This Entity manages the simulation, and launches Horses/Jockey
 */

public class Broker extends Thread{

    /**
     * General Repository
     * @serial repo
     */
    //private GeneralInformationRepository repo;
    private GeneralInformationRepositoryStub repo;
    /**
     * Broker current State
     * @serial state
     */
    private BrokerState state;
    /**
     * Control Centre and Watching Stand - Shared Region
     * @serial ccws
     */
    //private ControlCentre ccws;
    private ControlCentreStub ccws;
    /**
     * Stable - Shared Region
     * @serial st
     */
    //private Stable st;
    private StableStub st;
    /**
     * Betting Centre - Shared Region
     * @serial bc
     */
    //private BettingCentre bc;
    private BettingCentreStub bc;
    /**
     * Paddock - Shared Region
     * @serial pd
     */
    private Paddock pd;
    /**
     * Racing Track- Shared Region
     * @serial rt
     */
    //private RacingTrack rt;
    private RacingTrackStub rt;
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
     * @param pd Paddock -Shared Region
     * @param maxAgil maximum Agility of each HorseJockey
     */
    public Broker(int K, int N, Paddock pd, int maxAgil) {
    //public Broker(int K, int N, ControlCentre ccws, Stable st, BettingCentre bc, Paddock pd, int maxAgil, RacingTrack rt) {
        this.K = K;
        this.N = N;
        //this.ccws = ccws;
        this.ccws = new ControlCentreStub();
        //this.st = st;
        this.st = new StableStub();
        //this.bc = bc;
        this.bc = new BettingCentreStub();
        this.pd = pd;
        //this.rt = rt;
        this.rt = new RacingTrackStub();
        this.repo = new GeneralInformationRepositoryStub();
        this.maxAgil = maxAgil;
        this.horseJockeys = new HorseJockey[N];

        this.state=BrokerState.OPENING_THE_EVENT; // set current Broker state to the initial state
        repo.setBrokerState(this.state);
    }

    /**
     * Broker main life cycle
     */
    @Override
    public void run(){

        //K is the number of races
        //k is the current race
        //N competitors per race

        for(int k=1;k<=K;k++) {
            // HorseJockey Instantiation and start
            totalAgility = 0;
            for (int j = 0; j < N; j++) {
                horseJockeys[j] = new HorseJockey(j, pd, maxAgil);
                horseJockeys[j].start();
                totalAgility += horseJockeys[j].getAgility();
                System.out.println("HorseJockey "+(j+1)+" started");
            }

            System.out.println("Race "+k+" Start");


            st.summonHorsesToPaddock(k,totalAgility); // primeira parte é invocada no stable a segunda no ccws
            ccws.summonHorsesToPaddock();
            bc.acceptTheBets();
            rt.startTheRace(k);
            ccws.startTheRace();

            if (bc.areThereAnyWinners(rt.reportResults())) {
                ccws.reportResults();
                bc.honourTheBets();
            }else
                ccws.reportResults();


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

        ccws.entertainTheGuests();
    }

    /**
     * Method to change the current State of the Broker.
     * @param state Broker state
     */
    public void setBrokerState(BrokerState state) {
        this.state = state;
    }
}