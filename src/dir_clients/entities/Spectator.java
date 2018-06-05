package entities;

import interfaces.*;
import shared_regions.RMIReply.*;

/**
 * Spectator Entity
 */
public class Spectator extends Thread{

    /**
     * Current Spectator State
     * @serial state
     */
    private SpectatorState state;
    /**
     * Control Centre and Watching Stand Stub
     * @serial ccwsStub
     */
    private ControlCentreInterface ccwsStub;
    /**
     * Paddock Stub
     * @serial pdStub
     */
    private PaddockInterface pdStub;
    /**
     * Betting Centre Stub
     * @serial bcStub
     */
    private BettingCentreInterface bcStub;
    /**
     * General Repository Stub
     * @serial repoStub
     */
    private GeneralInformationRepositoryInterface repoStub;
    /**
     * Current spectator Index
     * @serial specId
     */
    private int specId;
    /**
     * Sum of money own by the current Spectator
     * @serial wallet
     */
    private int wallet;

    /**
     * Spectator constructor
     * @param specId Spectator Id
     * @param wallet Spectator wallet
     */
    public Spectator(int specId, int wallet, ControlCentreInterface ccwsStub, PaddockInterface pdStub, BettingCentreInterface bcStub, GeneralInformationRepositoryInterface repoStub) {
        this.specId=specId;
        this.ccwsStub = ccwsStub;
        this.pdStub = pdStub;
        this.bcStub = bcStub;
        this.repoStub = repoStub;
        this.wallet = wallet;

        try {
            repoStub.setSpectatorMoney(wallet, specId);

            this.state=SpectatorState.WAITING_FOR_A_RACE_TO_START;
            repoStub.setSpectatorState(SpectatorState.WAITING_FOR_A_RACE_TO_START.getShortName(),specId);
            repoStub.reportStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Spectator life cycle
     */
    @Override
    public void run(){

        boolean last;

        try {
            WaitForNextRace temp;
            temp = ccwsStub.waitForNextRace(this.specId);
            while(temp.getWaitForNextRace()){

                last = pdStub.goCheckHorses1();     // Este método verifica o último.
                if (last)
                    ccwsStub.goCheckHorses();    // Acorda o Broker , que dá inicio à corrida
                pdStub.goCheckHorses2(last, specId);          //envia o spectator para o pdStub e diz se é o último

                bcStub.placeABet(specId, wallet);
                ccwsStub.goWatchTheRace(this.specId);

                if(bcStub.haveIWon(specId).getStatus()) {
                    bcStub.goCollectTheGains(specId, wallet);
                }

                temp = ccwsStub.waitForNextRace(this.specId);
            }

        ccwsStub.relaxABit(this.specId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //ccwsStub.shutdown();
        //bcStub.shutdown();
        //pdStub.shutdown();
        //repoStub.shutdown();
    }

    /**
     * This method sets a new Spectator's State.
     * @param state Spectator State
     */
    public void setState(SpectatorState state){
        this.state=state;
    }

    /**
     * This method returns the ID of the current Spectator
     * @return Current Spectator ID
     */
    public int getSpecId() {
        return specId;
    }

    /**
     * This method sets the total amount in wallet
     * @param wallet sum of money in wallet
     */
    public synchronized void setWallet(int wallet) {
        this.wallet = wallet;
        try {
            repoStub.setSpectatorMoney(wallet,specId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns the total amount in wallet
     * @return wallet
     */
    public synchronized int getWallet() {
        return wallet;
    }
}