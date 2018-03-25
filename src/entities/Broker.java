package entities;

import shared_regions.*;

/**
 * Broker Entity
 */

public class Broker extends Thread{

    private GeneralInformationRepository repo;
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

    private int [] agility;

    /**
     * Broker Constructor
     * @param K Total races
     * @param ccws Control Centre & Watching Stand - Shared Region
     * @param st Stable - Shared Region
     * @param bc Betting Centre - Shared Region
     */
    public Broker(int K, int N, ControlCentre ccws, Stable st, BettingCentre bc, Paddock pd, RacingTrack rt, GeneralInformationRepository repo) {
        this.K = K;
        this.N = N;
        this.ccws = ccws;
        this.st = st;
        this.bc = bc;
        this.pd = pd;
        this.rt = rt;
        this.repo = repo;

        this.horseJockeys = new HorseJockey[N];
        this.agility= new int[N];

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
            for (int j = 0; j < N; j++) {
                horseJockeys[j] = new HorseJockey(j, ccws, st, pd, rt, repo);
                horseJockeys[j].start();
                agility[j]=horseJockeys[j].getAgility();
                System.out.println("HorseJockey "+(j+1)+" started");
            }

            System.out.println("Race "+k+" Start");
System.out.println("B-1");
            st.summonHorsesToPaddock(k); // primeira parte é invocada no stable a segunda no ccws
System.out.println("B-2");
            ccws.summonHorsesToPaddock(k);
System.out.println("B-3");
            bc.acceptTheBets(k,agility);

System.out.println("B-4");
            rt.startTheRace(k);
System.out.println("B-5");
            ccws.startTheRace(k);

System.out.println("B-6");
            ccws.reportResults(k);
            if (bc.areThereAnyWinners(k))
                bc.honourTheBets(k);

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
}