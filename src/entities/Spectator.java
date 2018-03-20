package entities;

import shared_regions.BettingCentre;
import shared_regions.ControlCentre;
import shared_regions.Paddock;

/**
 * Spectator Entity
 */
public class Spectator extends Thread{

    /**
     * Control Centre & Watching Stand - Shared Region
     * @serialField ccws
     */
    private ControlCentre ccws;
    /**
     * Paddock - Shared Region
     * @serialField pd
     */
    private Paddock pd;
    /**
     * Betting Centre - Shared Region
     * @serialField ccws
     */
    private BettingCentre bc;

    /**
     * Spectator constructor
     * @param ccws Control Centre & Watching Stand - Shared Region
     * @param pd Paddock - Shared Region
     * @param bc Betting Centre - Shared Region
     */
    public Spectator(ControlCentre ccws, Paddock pd, BettingCentre bc) {
        this.ccws = ccws;
        this.pd = pd;
        this.bc = bc;
    }

    /**
     * Spectator life cycle
     */
    @Override
    public void run(){
        boolean last;

        while(ccws.waitForNextRace()){
            pd.goCheckHorses();
            last = pd.goCheckHorses1();     // Este método verifica o último.
            if (last)
                ccws.goCheckHorses();    // Acorda o Broker , que dá inicio à corrida
            pd.goCheckHorses2(last);          //envia o spectator para o pd e diz se é o último

            bc.placeABet();
            ccws.goWatchTheRace();
            if(bc.haveIWon())
                bc.goCollectTheGains();
        }
        ccws.relaxABit();
    }

}