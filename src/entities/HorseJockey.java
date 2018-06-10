package entities;

import interfaces.*;
import shared_regions.RMIReply.*;

import java.rmi.RemoteException;
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
    private ControlCentreInterface ccwsStub;
    /**
     * Stable Stub
     * @serial st
     */
    private StableInterface stStub;
    /**
     * Paddock Stub
     * @serial pd
     */
    private PaddockInterface pdStub;
    /**
     * Racing Track Stub
     * @serial rt
     */
    private RacingTrackInterface rtStub;

    /**
     * Betting Centre Stub
     * @serial bc
     */
    private BettingCentreInterface bcStub;

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
    public HorseJockey(int hj_number, int maxAgil, ControlCentreInterface ccwsStub, StableInterface stStub, PaddockInterface pdStub, RacingTrackInterface rtStub, BettingCentreInterface bcStub, GeneralInformationRepositoryInterface repoStub) {
        this.hj_number = hj_number;
        this.ccwsStub = ccwsStub;
        this.stStub = stStub;
        this.pdStub = pdStub;
        this.rtStub = rtStub;
        this.bcStub = bcStub;
        this.agility = ThreadLocalRandom.current().nextInt(1, maxAgil+1);
        this.hjState=HorseJockeyState.AT_THE_STABLE;
        this.odd=0;

        //this.repoStub = repoStub;

        try {
            repoStub.setHorseJockeyAgility(agility,hj_number);
            repoStub.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE,hj_number);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        each horse / jockey Cnk, with n = 0, 1, ... , N-1 and k = 0, 1, ... , K-1 carries out a single position
        increment per iteration by moving randomly 1 to Pnk length units along its path the maximum
        value Pnk is specific of a given horse, because they are not all equal, some being more agile and
        faster than others;
         */
    }

    /**
     * HorseJockey main life cycle
     */
    @Override
    public void run(){
        HasFinishLineBeenCrossed rmiReply1;
        ProceedToStable rmiReply2;
        ProceedToStable2 rmiReply3;

        try {
            boolean last;

            rmiReply2 = stStub.proceedToStable(agility, hj_number);
            this.odd = rmiReply2.getOdd();
            setHjState(rmiReply2.getHjState());

            bcStub.setHorseJockeyOdd(hj_number, odd);
            last = pdStub.proceedToPaddock1();     // Este metodo verifica o ultimo.
            if (last)
                ccwsStub.proceedToPaddock();    // Acorda spectator que esta no ccws a espera de ser acordado
            pdStub.proceedToPaddock2();   //envia para o paddock

            this.setHjState(rtStub.proceedToStartLine1(hj_number).getState());
            this.setHjState(rtStub.proceedToStartLine2(hj_number).getState());
            do{
                rtStub.makeAMove(hj_number, agility);
                rmiReply1 = rtStub.hasFinishLineBeenCrossed();
                this.setHjState(rmiReply1.getState());
            }while(!rmiReply1.getStatus()); //devolve se terminou ou nao. Em caso de termino devolve a posicao
            ccwsStub.lastHorseCrossedLine();

            this.setHjState(stStub.proceedToStable2(hj_number).getHjState());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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