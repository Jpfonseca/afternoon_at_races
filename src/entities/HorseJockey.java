package entities;

import clients.*;
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
     * Paddock Stub
     * @serial pd
     */
    private PaddockStub pdStub;
    /**
     * Racing Track Stub
     * @serial rt
     */
    private RacingTrackStub rtStub;

    /**
     * Betting Centre Stub
     * @serial bc
     */
    private BettingCentreStub bcStub;

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

    /**
     * HorseJockey Odd
     * @serial odd
     */
    private int odd;

    /**
     * HorseJockey Constructor
     * @param hj_number HorseJockey index
     * @param maxAgil Maximum agility of HorseJockey
     */
    public HorseJockey(int hj_number, int maxAgil) {
        this.hj_number = hj_number;
        this.ccwsStub = new ControlCentreStub();
        this.stStub = new StableStub();
        this.pdStub = new PaddockStub();
        this.rtStub = new RacingTrackStub();
        this.bcStub = new BettingCentreStub();
        this.agility = ThreadLocalRandom.current().nextInt(1, maxAgil+1);
        this.hjState=HorseJockeyState.AT_THE_STABLE;
        this.odd=0;

        GeneralInformationRepositoryStub repoStub = new GeneralInformationRepositoryStub();

        repoStub.setHorseJockeyAgility(agility,hj_number);
        repoStub.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE,hj_number);
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

        stStub.proceedToStable();
        bcStub.setHorseJockeyOdd();
        last = pdStub.proceedToPaddock1();     // Este método verifica o último.
        if (last)
            ccwsStub.proceedToPaddock();    // Acorda spectator que está no ccws a espera de ser acordado
        pdStub.proceedToPaddock2();   //envia para o paddock

        rtStub.proceedToStartLine(hj_number);
        do{
            rtStub.makeAMove(hj_number);
        }while(!rtStub.hasFinishLineBeenCrossed()); //devolve se terminou ou não. Em caso de témino devolve a posição
        ccwsStub.lastHorseCrossedLine();

        stStub.proceedToStable2();
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

    /**
     * This method returns the HorseJockey odd
     * @return odd Odd
     */
    public int getOdd() {
        return odd;
    }

    /**
     * This method sets the HorseJockey odd
     * @param odd Odd
     */
    public void setOdd(int odd) {
        this.odd = odd;
    }
}