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
     * Control Centre and Watching Stand - Shared Region
     * @serial ccws
     */
    private ControlCentreStub ccws;
    /**
     * Paddock - Shared Region
     * @serial pd
     */
    private PaddockStub pd;
    /**
     * Betting Centre - Shared Region
     * @serial bc
     */
    private BettingCentreStub bc;
    /**
     * General Repository
     * @serial repo
     */
    private GeneralInformationRepositoryStub repo;
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
     */
    public Spectator(int specId, int wallet) {
        this.specId=specId;
        this.ccws = new ControlCentreStub();
        this.pd = new PaddockStub();
        this.bc = new BettingCentreStub();
        this.repo = new GeneralInformationRepositoryStub();
        this.wallet = wallet;

        repo.setSpectatorMoney(wallet, specId);

        this.state=SpectatorState.WAITING_FOR_A_RACE_TO_START;
        repo.setSpectatorState(SpectatorState.WAITING_FOR_A_RACE_TO_START,specId);
        repo.reportStatus();
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

            if(bc.haveIWon()) {
                bc.goCollectTheGains();
            }
        }

        ccws.relaxABit();

        ccws.shutdown();
        bc.shutdown();
        pd.shutdown();
        repo.shutdown();
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
        repo.setSpectatorMoney(wallet,specId);
    }

    /**
     * This method returns the total amount in wallet
     * @return wallet
     */
    public synchronized int getWallet() {
        return wallet;
    }
}