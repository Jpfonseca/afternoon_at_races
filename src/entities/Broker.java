package entities;

import clients.*;

/**
 * Broker Entity
 * This Entity manages the simulation, and launches Horses/Jockey
 */

public class Broker extends Thread{

    /**
     * General Repository Stub
     * @serial repoStub
     */
    private GeneralInformationRepositoryStub repoStub;
    /**
     * Broker current State
     * @serial state
     */
    private BrokerState state;
    /**
     * Control Centre and Watching Stand Stub
     * @serial ccws
     */
    private ControlCentreStub ccwsStub;
    /**
     * Stable Stub
     * @serial st
     */
    private StableStub stStub;
    /**
     * Betting Centre Stub
     * @serial bc
     */
    private BettingCentreStub bcStub;
    /**
     * Racing Track Stub
     * @serial rtStub
     */
    private RacingTrackStub rtStub;
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
     */
    public Broker(int K, int N, int maxAgil) {
    //public Broker(int K, int N, ControlCentre ccws, Stable st, BettingCentre bc, Paddock pd, int maxAgil, RacingTrack rtStub) {
        this.K = K;
        this.N = N;
        this.ccwsStub = new ControlCentreStub();
        this.stStub = new StableStub();
        this.bcStub  = new BettingCentreStub();
        this.rtStub = new RacingTrackStub();
        this.repoStub = new GeneralInformationRepositoryStub();
        this.maxAgil = maxAgil;
        this.horseJockeys = new HorseJockey[N];

        this.state=BrokerState.OPENING_THE_EVENT; // set current Broker state to the initial state
        repoStub.setBrokerState(this.state);
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
                horseJockeys[j] = new HorseJockey(j, maxAgil);
                horseJockeys[j].start();
                totalAgility += horseJockeys[j].getAgility();
                System.out.println("HorseJockey "+(j+1)+" started");
            }

            System.out.println("Race "+k+" Start");


            stStub.summonHorsesToPaddock(k,totalAgility); // primeira parte é invocada no stable a segunda no ccws
            ccwsStub.summonHorsesToPaddock();
            bcStub.acceptTheBets();
            rtStub.startTheRace(k);
            ccwsStub.startTheRace();

            if (bcStub.areThereAnyWinners(rtStub.reportResults())) {
                ccwsStub.reportResults();
                bcStub.honourTheBets();
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

        ccwsStub.entertainTheGuests();

        stStub.shutdown();
        ccwsStub.shutdown();
        bcStub.shutdown();
        rtStub.shutdown();
        repoStub.shutdown();
    }

    /**
     * Method to change the current State of the Broker.
     * @param state Broker state
     */
    public void setBrokerState(BrokerState state) {
        this.state = state;
    }
}