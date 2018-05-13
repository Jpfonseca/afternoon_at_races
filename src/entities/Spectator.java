package entities;

import clients.*;

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
    private ControlCentreStub ccwsStub;
    /**
     * Paddock Stub
     * @serial pdStub
     */
    private PaddockStub pdStub;
    /**
     * Betting Centre Stub
     * @serial bcStub
     */
    private BettingCentreStub bcStub;
    /**
     * General Repository Stub
     * @serial repoStub
     */
    private GeneralInformationRepositoryStub repoStub;
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
    public Spectator(int specId, int wallet) {
        this.specId=specId;
        this.ccwsStub = new ControlCentreStub();
        this.pdStub = new PaddockStub();
        this.bcStub = new BettingCentreStub();
        this.repoStub = new GeneralInformationRepositoryStub();
        this.wallet = wallet;

        repoStub.setSpectatorMoney(wallet, specId);

        this.state=SpectatorState.WAITING_FOR_A_RACE_TO_START;
        repoStub.setSpectatorState(SpectatorState.WAITING_FOR_A_RACE_TO_START,specId);
        repoStub.reportStatus();
    }

    /**
     * Spectator life cycle
     */
    @Override
    public void run(){

        boolean last;

        while(ccwsStub.waitForNextRace()){

            last = pdStub.goCheckHorses1();     // Este método verifica o último.
            if (last)
                ccwsStub.goCheckHorses();    // Acorda o Broker , que dá inicio à corrida
            pdStub.goCheckHorses2(last);          //envia o spectator para o pdStub e diz se é o último

            bcStub.placeABet();
            ccwsStub.goWatchTheRace();

            if(bcStub.haveIWon()) {
                bcStub.goCollectTheGains();
            }
        }

        ccwsStub.relaxABit();

        ccwsStub.shutdown();
        bcStub.shutdown();
        pdStub.shutdown();
        repoStub.shutdown();
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
        repoStub.setSpectatorMoney(wallet,specId);
    }

    /**
     * This method returns the total amount in wallet
     * @return wallet
     */
    public synchronized int getWallet() {
        return wallet;
    }
}