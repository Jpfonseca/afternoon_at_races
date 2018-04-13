package entities;

import shared_regions.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * HorseJockey entity.<br>
 * The horse Jockey entity is the entity responsible for the horse life cycle.
 */
public class HorseJockey extends Thread{

    /**
     * Current Horse Jockey State
     */
    private HorseJockeyState hjState;
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
     * Paddock - Shared Region
     * @serial pd
     */
    private Paddock pd;
    /**
     * Racing Track- Shared Region
     * @serial rt
     */
    private RacingTrack rt;

    private BettingCentre bc;
    /**
     * HorseJockey agility
     * @serial agility

     */
    private int agility;
    /**
     * Index of the current HorseJockey
     * @serial hj_number
     */
    private int hj_number;

    private int odd;
    /**
     * HorseJockey Constructor
     * @param hj_number HorseJockey index
     * @param ccws Control Centre and Watching Stand - Shared Region
     * @param st Stable - Shared Region
     * @param pd Paddock - Shared Region
     * @param rt Racing Track- Shared Region
     * @param repo General Repository
     */

    public HorseJockey(int hj_number, ControlCentre ccws, Stable st, Paddock pd, RacingTrack rt,BettingCentre bc, GeneralInformationRepository repo) {
        this.hj_number = hj_number;
        this.ccws = ccws;
        this.st = st;
        this.pd = pd;
        this.rt = rt;
        this.bc= bc;
        this.agility = ThreadLocalRandom.current().nextInt(1, 20+1);
        this.hjState=HorseJockeyState.AT_THE_STABLE;
        this.odd=0;

        repo.setIterationStep(hj_number,-1);
        repo.setCurrentPosZero(hj_number);
        repo.setHorseJockeyAgility(agility,hj_number);
        repo.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE,hj_number);
        /*
        each horse / jockey Cnk, with n = 0, 1, ... , N-1 and k = 0, 1, ... , K-1 carries out a single position
        increment per iteration by moving randomly 1 to Pnk length units along its path – the maximum
        value Pnk is specific of a given horse, because they are not all equal, some being more agile and
        faster than others;
         */
    }

    /**
     * HorseJockey main life cycle
     */
    @Override
    public void run(){
        boolean last;

        st.proceedToStable();
        bc.setHorseJockeyOdd();
        last = pd.proceedToPaddock1();     // Este método verifica o último.
        if (last)
            ccws.proceedToPaddock();    // Acorda spectator que está no ccws a espera de ser acordado
        pd.proceedToPaddock2();   //envia para o paddock

        rt.proceedToStartLine(hj_number);
        do{
            rt.makeAMove(hj_number);
        }while(!rt.hasFinishLineBeenCrossed()); //devolve se terminou ou não. Em caso de témino devolve a posição
        ccws.lastHorseCrossedLine();

        st.proceedToStable2();
    }

    /**
     * This method will be responsible for returning the agility of the horse
     * @return agility Agility
     */
    public int getAgility() {
        return agility;
    }
    /**
     * This method will return the horse jockey index number.
     * @return hj_number Index number
     */
    public synchronized int getHj_number() {
        return hj_number;
    }

    /**
     * This method changes the current HorseJockey state
     * @param hjState State
     */
    public void setHjState(HorseJockeyState hjState) {
        this.hjState = hjState;
    }

    public int getOdd() {
        return odd;
    }

    public void setOdd(int odd) {
        this.odd = odd;
    }
}