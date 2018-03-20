package entities;

import shared_regions.ControlCentre;
import shared_regions.Paddock;
import shared_regions.RacingTrack;
import shared_regions.Stable;

/**
 * HorseJockey entity
 */
public class HorseJockey extends Thread{

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
     * Index of HorseJockey
     * @serialField n
     */
    private int n;

    /**
     * HorseJockey Constructor
     * @param n HorseJockey index
     * @param ccws Control Centre & Watching Stand - Shared Region
     * @param st Stable - Shared Region
     * @param pd Paddock - Shared Region
     * @param rt Racing Track- Shared Region
     */
    public HorseJockey(int n, ControlCentre ccws, Stable st, Paddock pd, RacingTrack rt) {
        this.n = n;
        this.ccws = ccws;
        this.st = st;
        this.pd = pd;
        this.rt = rt;

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

        st.proceedToStable(n);

        st.proceedToPaddock(n);           // Spectator à espera no st
        last = pd.proceedToPaddock1(n);     // Este método verifica o último.
        if (last)
            ccws.proceedToPaddock(n);    // Acorda spectator que está no ccws a espera de ser acordado
        pd.proceedToPaddock2(n);   //envia para o paddock

        rt.proceedToStartLine(n);

        do{
            rt.makeAMove(n);
        }while(!rt.hasFinishLineBeenCrossed(n)); //devolve se terminou ou não. Em caso de témino devolve a posição
        st.proceedToStable2();
    }

}