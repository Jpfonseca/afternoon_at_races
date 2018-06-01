package shared_regions;

//import clients.GeneralInformationRepositoryStub;

import entities.BrokerState;
import entities.SpectatorState;
import extras.config;
//import servers.Aps;


import shared_regions.RMIReply.AcceptTheBets;
import shared_regions.RMIReply.AreThereAnyWinners;
import shared_regions.RMIReply.HonourTheBets;
import shared_regions.RMIReply.PlaceABet;
import shared_regions.RMIReply.HaveIWon;
import shared_regions.RMIReply.GoCollectTheGains;

import java.rmi.RemoteException;
import java.util.concurrent.ThreadLocalRandom;


/**
 * This class specifies the Place where the spectators will make the bets.
 */
public class BettingCentre implements BettingCentreInterface {

    /**
     * Total spectators that already bet
     * @serial bets
     */
    private int bets;
    /**
     * Condition statement to know if all spectators have bet
     * @serial isBetDone
     */
    private boolean isBetDone=false;
    /**
     * Total spectators in the simulation
     * @serial totalSpectators
     */
    private int totalSpectators;
    /**
     * Array with HorseJockey's odd used to calculate the gains
     * @serial odd
     */
    private int[] odd;
    /**
     * Condition Statement used to know if a Spectator has collect its gains
     * @serial waitForSpectatorCollectsMoney
     */
    private boolean waitForSpectatorCollectsMoney =true;
    /**
     * BetAmount Array used to keep track of all the bets made by every Spectator
     * @serial betAmounts
     */
    private BetAmount[] betAmounts;
    /**
     * Array that keeps the index of the victorious Spectators in the current race
     * @serial spectatorWinners
     */
    private int[] spectatorWinners;
    /**
     * Array that keeps Spectator's indexes by arrival order
     * @serial fifo
     */
    private int[] fifo;
    /**
     * Variable used to keep track of how many Spectators are waiting to receive gains
     * @serial totalWaiting
     */
    private int totalWaiting=0;
    /**
     * Condition Statement used by the Broker to decide whether a Spectator can already collect its gain
     * @serial waitForWinnerCall
     */
    private boolean waitForWinnerCall = true;
    /**
     * General Repository Stub
     * @serial repo
     */
    private final GeneralInformationRepositoryInterface repoStub;
    //private GeneralInformationRepositoryStub repoStub;

    /**
     * Instance of BettingCentre
     * @serialField instance
     */
    private static BettingCentre instance;

    /**import RMIReply.AreThereAnyWinners;
import RMIReply.HonourTheBets;
     * This is the Betting Centre constructor. <br> It specifies the variables which will be essential to create this shared region.
     * @param M number of spectators
     */
    public BettingCentre(int M, GeneralInformationRepositoryInterface repoStub) {
        this.totalSpectators = M;
        this.betAmounts=new BetAmount[totalSpectators];
        this.odd=new int[totalSpectators];
        this.repoStub = repoStub;
        for(int i=0; i<M; i++)
            fifo = new int[totalSpectators];
    }

    /**
     * Method used by the broker to announce he can collect Spectator's bets
     */
    @Override
    public synchronized AcceptTheBets acceptTheBets(){

        try {
            repoStub.setBrokerState(BrokerState.WAITING_FOR_BETS);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        spectatorWinners = new int[0];

        while (bets < totalSpectators)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Broker bt.acceptTheBets() InterruptedException: "+e);
            }

        isBetDone = true;
        notifyAll();

        return new AcceptTheBets(BrokerState.WAITING_FOR_BETS);
    }

    /**
     * This method specifies the existence of winners in the current race.
     * @param winners winners array
     * @return <b>true</b>,if there winners or <b>false</b>, if there aren't any.
     */
    @Override
    public synchronized AreThereAnyWinners areThereAnyWinners(Winners[] winners){

        int winnersCount=0;
        int[] temp;

        for (int i=0; i<winners.length; i++)
            for (int j=0; j<betAmounts.length; j++)
                if (winners[i].standing == 1 && betAmounts[j].horse_id==i) {
                    winnersCount++;

                    temp = spectatorWinners;
                    spectatorWinners = new int[winnersCount];
                    System.arraycopy(temp, 0, spectatorWinners, 0, temp.length);
                    spectatorWinners[spectatorWinners.length-1] = j;
                }

        return new AreThereAnyWinners((winnersCount != 0));
    }

    /**
     * This method specifies the existence of winners in the current race.
     */
    @Override
    public synchronized HonourTheBets honourTheBets(){


        try {
            repoStub.setBrokerState(BrokerState.SETTLING_ACCOUNTS);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        waitForWinnerCall = false;
        notifyAll();

        while (waitForSpectatorCollectsMoney || totalWaiting>0)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator bc.honourTheBets() InterruptedException: " + e);
            }

        notifyAll();
        return new HonourTheBets(BrokerState.SETTLING_ACCOUNTS);
    }

    /**
     * This method is used by each spectator to place a bet.
     */

    @Override
    public synchronized PlaceABet placeABet(int specId, int specWallet){
    //TODO make spectator send its Id and wallet

        // SPECTATOR BET
        int temp,bet,spec_id;

        temp= ThreadLocalRandom.current().nextInt(0,odd.length);
        betAmounts[specId]=new BetAmount();
        betAmounts[specId].horse_id=temp;

        bet = betAmounts[specId].bet= ThreadLocalRandom.current().nextInt(0, specWallet)/odd[temp];


        bets++;

        try {
            repoStub.setSpectatorState(SpectatorState.PLACING_A_BET,specId);
            repoStub.setSpectatorBet(specId, temp, bet);
            repoStub.reportStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        notifyAll();

        while (!isBetDone)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator bc.placeABet() InterruptedException: "+e);
            }

        bets--; // reset the variable
        if (bets==0)
            isBetDone=false; // variable reset
        return new PlaceABet(specWallet-bet, SpectatorState.PLACING_A_BET);

    }

    /**
     * This method is used by the spectator to check if it has won something from the bet
     * @return <b>true</b> or <b>false</b> whether the spectator won something or not.
     */
    @Override
    public synchronized HaveIWon haveIWon(int specId){
        //TODO make spectator send its Id

        for (int i=0;i<spectatorWinners.length;i++){
            if (spectatorWinners[i]==specId){
                return new HaveIWon(true);
            }
        }

        return new HaveIWon(false);
    }

    /**
     * This method is used by the spectators to collect their gains, if they have won something.
     */
    @Override
    public synchronized GoCollectTheGains goCollectTheGains(int specId,int specWallet){
        //TODO make spectator send its Id and Wallet

        try {
            repoStub.setSpectatorState(SpectatorState.COLLECTING_THE_GAINS,specId);
            repoStub.reportStatus();
        } catch (RemoteException e) {
            e.printStackTrace();

        }


        fifo[totalWaiting++]=specId;
        if (totalWaiting == spectatorWinners.length) {
            waitForSpectatorCollectsMoney = false;
            notifyAll();
        }

        while (waitForWinnerCall && fifo[0]!=specId )
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator bc.haveIwon() InterruptedException: "+e);
            }

        if (totalWaiting>0) {
            int temp = fifo[0];
            System.arraycopy(fifo, 1, fifo, 0, fifo.length - 1);
            fifo[fifo.length-1]=temp;
        }else
            waitForWinnerCall = true;

        totalWaiting--;
        int money_won=0;
        for (int i=0;i<betAmounts.length;i++){
            if (i == specId){
                money_won = betAmounts[i].bet * odd[betAmounts[i].horse_id];
                money_won = money_won/spectatorWinners.length;
            }
        }

        waitForSpectatorCollectsMoney =false;
        notifyAll();
        return new GoCollectTheGains(specWallet+money_won,SpectatorState.COLLECTING_THE_GAINS);
    }

    /**
     * This method sets each HorseJockey odd in the BettingCentre
     */
    @Override
    public void setHorseJockeyOdd(int horseId,int horseOdd) {
        this.odd[horseId] = horseOdd;
    }

    /**
     * Returns current instance of BettingCentre
     * @return instance of BettingCentre
     */
//    public static BettingCentre getInstance(){
//        if (instance==null)
//            instance = new BettingCentre(config.M);
//
//        return instance;
//    }
}