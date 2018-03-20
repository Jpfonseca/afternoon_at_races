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
    }

    /**
     * HorseJockey main life cycle
     */
    @Override
    public void run(){
        boolean last;

        st.proceedtothestable(n);

        st.proceedtothepaddock(n);           // Spectator à espera no st
        last = pd.proceedtothepaddock1(n);     // Este método verifica o último.
        if (last)
            ccws.proceedtothepaddock(n);    // Acorda spectator que está no ccws a espera de ser acordado
        pd.proceedtothepaddock2(n);   //envia para o paddock

        rt.proceedtothestartLine(n);

        do{
            rt.makeAmove(n);
        }while(!rt.hasFinishLineBeenCrossed(n)); //devolve se terminou ou não. Em caso de témino devolve a posição
        st.proceedtoStable2();
    }

}