package entities;

import clients.GeneralInformationRepositoryStub;
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
    private ControlCentre ccws;
    /**
     * Stable - Shared Region
     * @serial st
     */
    private Stable st;
    /**
     * Betting Centre - Shared Region
     * @serial bc
     */
    private BettingCentre bc;
    /**
     * Paddock - Shared Region
     * @serial pd
     */
    private Paddock pd;
    /**
     * Racing Track- Shared Region
     * @serial rt
     */
    private RacingTrack rt;
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
     * @param ccws Control Centre and Watching Stand - Shared Region
     * @param st Stable - Shared Region
     * @param bc Betting Centre - Shared Region
     * @param pd Paddock -Shared Region
     * @param rt Racing Track -Shared Region
     * @param maxAgil maximum Agility of each HorseJockey
     */
    public Broker(int K, int N, ControlCentre ccws, Stable st, BettingCentre bc, Paddock pd, RacingTrack rt, int maxAgil) {
        this.K = K;
        this.N = N;
        this.ccws = ccws;
        this.st = st;
        this.bc = bc;
        this.pd = pd;
        this.rt = rt;
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
                horseJockeys[j] = new HorseJockey(j, ccws, st, pd, rt, bc, maxAgil);
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