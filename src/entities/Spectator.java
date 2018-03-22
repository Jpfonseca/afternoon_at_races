package entities;

import shared_regions.BettingCentre;
import shared_regions.ControlCentre;
import shared_regions.Paddock;
/**
 * Spectator Entity
 */
public class Spectator extends Thread{

    private SpectatorState state;

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
        this.state=SpectatorState.WAITING_FOR_A_RACE_TO_START;
    }

    /**
     * Spectator life cycle
     */
    @Override
    public void run(){
        boolean last;

        while(ccws.waitForNextRace()){

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
    /**
     *
     * @return true if the state changed and false if it is the same
     */
    public boolean setState(SpectatorState state){
        if(this.state!=state){
            this.state=state;
            return true;
        }
        return false;
    }
    /**
     *
     * @return return Current Spectator State
     */
    public SpectatorState getSpectatorState(){
        return this.state;
    }
}