package shared_regions;

import entities.*;

/**
 * This class specifies the Place where the spectators will make the bets.
 *
 */
public class BettingCentre{
    /**
     *  Total HorseJockeys in RacingTrack (FIFO)
     *  @serialField queueHJ
     */
    private int bets;
    private boolean isBetDone=false;
    private int totalSpectators;
    private int [] agility;
    private int []odd;
    private boolean waitForSpectatorCollectsMoney =true;
    private BetAmount[] betAmounts;
    private int[] spectatorWinners;
    private int[] fifo;
    private int totalWaiting=0;
    private boolean waitForWinnerCall = true;
    private GeneralInformationRepository repo;

    public BettingCentre(int M, GeneralInformationRepository repo) {
        this.totalSpectators = M;
        this.betAmounts=new BetAmount[totalSpectators];
        this.odd=new int[totalSpectators];
        this.repo = repo;
        for(int i=0; i<M; i++)
            fifo = new int[totalSpectators];
    }

    /**
     *
     * @param agility Ids of the horses running in the race
     */
    public synchronized void acceptTheBets(int [] agility){

        ((Broker)Thread.currentThread()).setBrokerState((BrokerState.WAITING_FOR_BETS));
        repo.setBrokerState(BrokerState.WAITING_FOR_BETS);

        this.agility=new int[agility.length];
        this.agility=agility;

        spectatorWinners = new int[0];

        while (bets < totalSpectators)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator bc.placeABet() InterruptedException: "+e);
            }

        isBetDone = true;
        notifyAll();
    }

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


    public synchronized void honourTheBets(int k){

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


    public synchronized void placeABet(){

        Spectator spec =((Spectator)Thread.currentThread());
        spec.setState((SpectatorState.PLACING_A_BET));
        repo.setSpectatorState(SpectatorState.PLACING_A_BET,spec.getspecId());

        // SPECTATOR BET
        int temp,bet,spec_id,totalAgility=0;
        spec_id=spec.getspecId();

        temp=(int)(Math.random()*agility.length);
        betAmounts[spec_id]=new BetAmount();
        betAmounts[spec_id].horse_id=temp;

        for (int i=0; i<agility.length;i++){
            totalAgility+=agility[i];
        }
        odd[spec_id]=totalAgility/agility[temp];
        bet=betAmounts[spec_id].bet= (int)(Math.random()*spec.getWallet())/odd[spec_id];

        spec.setWallet(spec.getWallet()-bet);
        betAmounts[spec_id].spectator_id=spec_id;

        bets++;

        //System.out.print("Spectator: " + spec.getspecId()+ " betted [{horse:"+temp+"},{bet:"+bet+"}]\n");
        repo.setOdd(spec_id, odd[spec_id]);
        repo.setSpectatorBet(spec.getspecId(), temp, bet);
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


    public synchronized boolean haveIWon(){

        Spectator spec =((Spectator)Thread.currentThread());

        for (int i=0;i<spectatorWinners.length;i++){
            if (spectatorWinners[i]==spec.getspecId()){
                return true;
            }
        }

        return false;
    }

    public synchronized void goCollectTheGains(){

        Spectator spec=((Spectator) Thread.currentThread());
        spec.setState((SpectatorState.COLLECTING_THE_GAINS));
        repo.setSpectatorState(SpectatorState.COLLECTING_THE_GAINS,spec.getspecId());

        fifo[totalWaiting++]=spec.getspecId();
        if (totalWaiting == spectatorWinners.length) {
            waitForSpectatorCollectsMoney = false;
            notifyAll();
        }

        while (waitForWinnerCall && fifo[0]!=spec.getspecId() )
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator bc.haveIwon() InterruptedException: "+e);
            }

        if (totalWaiting>0) {
            int temp = fifo[0];
            System.arraycopy(fifo, 1, fifo, 0, fifo.length - 1);
            fifo[fifo.length-1]=temp;
        }

        totalWaiting--;
        int money_won=0;
        for (int i=0;i<betAmounts.length;i++){
            if (betAmounts[i].spectator_id==spec.getspecId()){
                money_won=betAmounts[i].bet*odd[i];
                money_won=money_won/spectatorWinners.length;
            }
        }

        spec.setWallet(spec.getWallet()+money_won);

        waitForSpectatorCollectsMoney =false;
        notifyAll();
    }
}