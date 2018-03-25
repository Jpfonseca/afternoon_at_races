package entities;

import shared_regions.*;

/**
 * HorseJockey entity
 */
public class HorseJockey extends Thread{
    /**
     * Current Horse Jockey State
     */
    private HorseJockeyState hjState;

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
     * HorseJockey agility
     * @serialField agility
     */
    private int agility;

    private int hj_number;

    /**
     * HorseJockey Constructor
     * @param hj_number HorseJockey index
     * @param ccws Control Centre & Watching Stand - Shared Region
     * @param st Stable - Shared Region
     * @param pd Paddock - Shared Region
     * @param rt Racing Track- Shared Region
     */
    public HorseJockey(int hj_number, ControlCentre ccws, Stable st, Paddock pd, RacingTrack rt, GeneralInformationRepository repo) {
        this.hj_number = hj_number;
        this.ccws = ccws;
        this.st = st;
        this.pd = pd;
        this.rt = rt;
        this.agility = (int)(Math.random()*20+1);
        this.hjState=HorseJockeyState.AT_THE_STABLE;

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

    public int getAgility() {
        return agility;
    }

    public synchronized int getHj_number() {
        return hj_number;
    }

    public void setHjState(HorseJockeyState hjState) {
        this.hjState = hjState;
    }
}