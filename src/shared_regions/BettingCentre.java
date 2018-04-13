package shared_regions;

import entities.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class specifies the Place where the spectators will make the bets.
 */
public class BettingCentre{

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
     * General Repository
     * @serial repo
     */
    private GeneralInformationRepository repo;

    /**
     * This is the Betting Centre constructor. <br> It specifies the variables which will be essential to create this shared region.
     * @param M number of spectators
     * @param repo General Information Repository
     */
    public BettingCentre(int M, GeneralInformationRepository repo) {
        this.totalSpectators = M;
        this.betAmounts=new BetAmount[totalSpectators];
        this.odd=new int[totalSpectators];
        this.repo = repo;
        for(int i=0; i<M; i++)
            fifo = new int[totalSpectators];
    }

    /**
     * Method used by the broker to announce he can collect Spectator's bets
     */
    public synchronized void acceptTheBets(){

        ((Broker)Thread.currentThread()).setBrokerState((BrokerState.WAITING_FOR_BETS));
        repo.setBrokerState(BrokerState.WAITING_FOR_BETS);

        spectatorWinners = new int[0];

        while (bets < totalSpectators)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Broker bt.acceptTheBets() InterruptedException: "+e);
            }

        isBetDone = true;
        notifyAll();
    }

    /**
     * This method specifies the existence of winners in the current race.
     * @param winners winners array
     * @return <b>true</b>,if there winners or <b>false</b>, if there aren't any.
     */
    public synchronized boolean areThereAnyWinners(Winners[] winners){

        int winnersCount=0;
        int[] temp;
        for (int i=0; i<winners.length; i++)
            if (winners[i].standing == 1) {
                winnersCount++;

                temp = spectatorWinners;
                spectatorWinners = new int[winnersCount];
                System.arraycopy(temp, 0, spectatorWinners, 0, temp.length);
                spectatorWinners[spectatorWinners.length-1] = i;
            }

        return (winnersCount != 0);
    }

    /**
     * This method specifies the existence of winners in the current race.
     */
    public synchronized void honourTheBets(){

        ((Broker) Thread.currentThread()).setBrokerState((BrokerState.SETTLING_ACCOUNTS));
        repo.setBrokerState(BrokerState.SETTLING_ACCOUNTS);

        waitForWinnerCall = false;
        notifyAll();

        while (waitForSpectatorCollectsMoney || totalWaiting>0)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator bc.honourTheBets() InterruptedException: " + e);
            }

        notifyAll();

    }

    /**
     * This method is used by each spectator to place a bet.
     */
    public synchronized void placeABet(){

        Spectator spec =((Spectator)Thread.currentThread());
        spec.setState((SpectatorState.PLACING_A_BET));
        repo.setSpectatorState(SpectatorState.PLACING_A_BET,spec.getSpecId());

        // SPECTATOR BET
        int temp,bet,spec_id,totalAgility=0;
        spec_id=spec.getSpecId();

        temp= ThreadLocalRandom.current().nextInt(0,odd.length);
        betAmounts[spec_id]=new BetAmount();
        betAmounts[spec_id].horse_id=temp;

        bet = betAmounts[spec_id].bet= ThreadLocalRandom.current().nextInt(0, spec.getWallet())/odd[temp];

        spec.setWallet(spec.getWallet()-bet);
        betAmounts[spec_id].spectator_id=spec_id;

        bets++;

        //System.out.print("Spectator: " + spec.getSpecId()+ " betted [{horse:"+temp+"},{bet:"+bet+"}]\n");
        repo.setSpectatorBet(spec.getSpecId(), temp, bet);
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

    }

    /**
     * This method is used by the spectator to check if it has won something from the bet
     * @return <b>true</b> or <b>false</b> whether the spectator won something or not.
     */
    public synchronized boolean haveIWon(){

        Spectator spec =((Spectator)Thread.currentThread());

        for (int i=0;i<spectatorWinners.length;i++){
            if (spectatorWinners[i]==spec.getSpecId()){
                return true;
            }
        }

        return false;
    }

    /**
     * This method is used by the spectators to collect their gains, if they have won something.
     */
    public synchronized void goCollectTheGains(){

        Spectator spec=((Spectator) Thread.currentThread());
        spec.setState((SpectatorState.COLLECTING_THE_GAINS));
        repo.setSpectatorState(SpectatorState.COLLECTING_THE_GAINS,spec.getSpecId());

        fifo[totalWaiting++]=spec.getSpecId();
        if (totalWaiting == spectatorWinners.length) {
            waitForSpectatorCollectsMoney = false;
            notifyAll();
        }

        while (waitForWinnerCall && fifo[0]!=spec.getSpecId() )
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
            if (betAmounts[i].spectator_id==spec.getSpecId()){
                money_won=betAmounts[i].bet*odd[i];
                money_won=money_won/spectatorWinners.length;
            }
        }

        spec.setWallet(spec.getWallet()+money_won);

        waitForSpectatorCollectsMoney =false;
        notifyAll();
    }

    public void setHorseJockeyOdd() {
        HorseJockey horseJockey = ((HorseJockey)Thread.currentThread());
        int tmp[];
        tmp=odd;
        tmp[horseJockey.getHj_number()]=horseJockey.getOdd();
        this.odd=tmp;
    }
}