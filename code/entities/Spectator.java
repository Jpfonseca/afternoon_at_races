package entities;

import interfaces.*;
import shared_regions.RMIReply.*;
import shared_regions.RMIReply.GoCollectTheGains;
import shared_regions.RMIReply.PlaceABet;

import java.rmi.RemoteException;

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
     * @param repoStub Repository Stub
     * @param bcStub Betting Centre Stub
     * @param ccwsStub CCWS Stub
     * @param pdStub Paddock Stub
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
            repoStub.setSpectatorState(SpectatorState.WAITING_FOR_A_RACE_TO_START,specId);
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
        PlaceABet rmiReply1;
        GoCollectTheGains rmiReply2;
        WaitForNextRace rmiReply3;

        try {
            rmiReply3 = ccwsStub.waitForNextRace(specId);
            this.setState(rmiReply3.getSpectatorState());
            while(rmiReply3.getWaitForNextRace()){

                last = pdStub.goCheckHorses1();     // Este metodo verifica o ultimo.
                if (last)
                    ccwsStub.goCheckHorses();    // Acorda o Broker , que da inicio a corrida
                this.setState(pdStub.goCheckHorses2(last, specId).getState());          //envia o spectator para o pdStub e diz se e o ultimo

                rmiReply1 = bcStub.placeABet(specId, wallet);
                this.setWallet(rmiReply1.getSpecWallet());
                this.setState(rmiReply1.getState());

                this.setState(ccwsStub.goWatchTheRace(specId).getSpectatorState());

                if(bcStub.haveIWon(specId).getStatus()) {
                    rmiReply2 = bcStub.goCollectTheGains(specId, wallet);
                    this.setWallet(rmiReply2.getSpecWallet());
                    this.setState(rmiReply2.getState());
                }

                rmiReply3 = ccwsStub.waitForNextRace(specId);
                this.setState(rmiReply3.getSpectatorState());
            }
        this.setState(ccwsStub.relaxABit(specId).getSpectatorState());

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ccwsStub.shutdown(1);
            bcStub.shutdown(1);
            pdStub.shutdown(1);
            repoStub.shutdown(1);
        } catch (RemoteException e) {
            System.out.println("A exception has occurred on the Spectator while shutting down the servers : "+ e.getMessage());
            e.printStackTrace();
        }

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