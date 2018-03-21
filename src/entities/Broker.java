package entities;

import shared_regions.ControlCentre;
import shared_regions.Stable;
import shared_regions.BettingCentre;
import entities.BrokerState;

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
     * Total races
     * @serialField K
     */
    private int K;
    /**
     * Total competitors per race
     * @serialField N
     */
    private int N;

    /**
     * Broker Constructor
     * @param K Total races
     * @param ccws Control Centre & Watching Stand - Shared Region
     * @param st Stable - Shared Region
     * @param bc Betting Centre - Shared Region
     */
    public Broker(int K, int N, ControlCentre ccws, Stable st, BettingCentre bc) {
        this.K = K;
        this.ccws = ccws;
        this.st = st;
        this.bc = bc;
        this.state=BrokerState.OPENING_THE_EVENT; // set current Broker state to the initial state
    }

    /**
     * Broker main life cycle
     */
    @Override
    public void run(){

        //K is the number of races
        //k is the current race
        //N competitors per race

        for(int k=0;k<K;k++){
            st.summonHorsesToPaddock(k); // primeira parte é invocada no stable a segunda no ccws
            ccws.summonHorsesToPaddock(k);
            bc.acceptTheBets(k);
            ccws.startTheRace(k);
            ccws.reportResults(k);
            if(bc.areThereAnyWinners(k))
                bc.honourTheBets(k);
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