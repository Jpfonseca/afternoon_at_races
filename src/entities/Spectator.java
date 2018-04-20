package entities;

import clients.BettingCentreStub;
import clients.ControlCentreStub;
import clients.GeneralInformationRepositoryStub;
import shared_regions.BettingCentre;
import shared_regions.ControlCentre;
import shared_regions.GeneralInformationRepository;
import shared_regions.Paddock;
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
    //private ControlCentre ccws;
    private ControlCentreStub ccws;
    /**
     * Paddock - Shared Region
     * @serial pd
     */
    private Paddock pd;
    /**
     * Betting Centre - Shared Region
     * @serial bc
     */
    //private BettingCentre bc;
    private BettingCentreStub bc;
    /**
     * General Repository
     * @serial repo
     */
    //private GeneralInformationRepository repo;
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
     * @param pd Paddock - Shared Region
     */
    public Spectator(int specId, Paddock pd, int wallet) {
        this.specId=specId;
        //this.ccws = ccws;
        this.ccws = new ControlCentreStub();
        this.pd = pd;
        //this.bc = bc;
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
System.out.println("1");
            if(bc.haveIWon()) {
                System.out.println("2");
                bc.goCollectTheGains();
            }
System.out.println("3");
        }

        ccws.relaxABit();
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