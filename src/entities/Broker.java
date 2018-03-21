package entities;

import shared_regions.*;

/**
 * Broker Entity
 */

public class Broker extends Thread{

    private BrokerState state;

    /**
     * Control Centre & Watching Stand - Shared Region
     * @serialField ccws
     */
    private ControlCentre ccws;
    /**
     * Stable - Shared Region
     * @serialField st
     */
    private Stable st;
    /**
     * Betting Centre - Shared Region
     * @serialField ccws
     */
    private BettingCentre bc;
    /**
     * Paddock - Shared Region
     * @serialField pd
     */
    private Paddock pd;
    /**
     * Racing Track- Shared Region
     * @serialField rt
     */
    private RacingTrack rt;
    /**
     * Total races
     * @serialField K
     */
    private int K;
    /**
     * Total competitors per race
     * @serialField N
     */
    private int N;

    private HorseJockey[] horseJockeys;

    /**
     * Broker Constructor
     * @param K Total races
     * @param ccws Control Centre & Watching Stand - Shared Region
     * @param st Stable - Shared Region
     * @param bc Betting Centre - Shared Region
     */
    public Broker(int K, int N, ControlCentre ccws, Stable st, BettingCentre bc, Paddock pd, RacingTrack rt) {
        this.K = K;
        this.N = N;
        this.ccws = ccws;
        this.st = st;
        this.bc = bc;
        this.pd = pd;
        this.rt = rt;
        this.state=BrokerState.OPENING_THE_EVENT; // set current Broker state to the initial state

        horseJockeys = new HorseJockey[N];
    }

    /**
     * Broker main life cycle
     */
    @Override
    public void run(){

        //K is the number of races
        //k is the current race
        //N competitors per race
        System.out.println("TEST1");
        for(int k=0;k<K;k++) {
            System.out.println("TEST2");

            // HorseJockey Instantiation and start
            for (int j = 0; j < N; j++) {
                horseJockeys[j] = new HorseJockey(k, j, ccws, st, pd, rt);
                horseJockeys[j].start();
                System.out.println("HorseJockey " + j + " started");
            }

            st.summonHorsesToPaddock(k); // primeira parte é invocada no stable a segunda no ccws
            ccws.summonHorsesToPaddock(k);
            bc.acceptTheBets(k);
            ccws.startTheRace(k);
            ccws.reportResults(k);
            if (bc.areThereAnyWinners(k))
                bc.honourTheBets(k);

            // Wait for HorseJockey threads to finish
            for (int j = 0; j < N; j++) {
                while (horseJockeys[j].isAlive()) {
                    horseJockeys[j].interrupt();
                    Thread.yield();
                }
                try {
                    horseJockeys[j].join();
                } catch (InterruptedException e) {
                    System.out.println("HorseJockey "+j+" InterruptedException: "+e);
                }
                System.out.println("HorseJockey " + j + " ended");
            }
        }

        ccws.entertainTheGuests();
    }

    /**
     *
     * @return true if the state changed and false if it is the same
     */
    public boolean setBrokerState(BrokerState state){
        if(this.state!=state){
            this.state=state;
            return true;
        }
        return false;
    }

    /**
     *
     * @return current BrokerState
     */
    public BrokerState getBrokerState(){
        return this.state;
    }
}