package shared_regions;

import entities.*;

import java.sql.Array;

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
    private int maxBet;
    private int [] agility;
    private int []odd;
    private boolean waitForSpectactorCollectsMoney=true;
    private BetAmount[] betAmounts;
    private int[] horseWinners,spectatorWinners;
    private boolean waitForWinnerInfo=true;

    public BettingCentre(int M, int maxBet) {
        this.totalSpectators = M;
        this.maxBet=maxBet;
        this.betAmounts=new BetAmount[totalSpectators];
        this.odd=new int[totalSpectators];

    }

    /**
     *
     * @param race_number Current race number
     * @param agility Ids of the horses running in the race
     */
    public synchronized void acceptTheBets(int race_number,int [] agility){

        // DID WE FORGET TO UPDATE STATE -> WAITING_FOR_BETS ???!???!???

        ((Broker)Thread.currentThread()).setBrokerState((BrokerState.WAITING_FOR_BETS));
        this.agility=new int[agility.length];
        this.agility=agility;

        while (bets < totalSpectators)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator bc.placeABet() InterruptedException: "+e);
            }
        // TODO

        isBetDone = true;
        notifyAll();
    }

    public boolean areThereAnyWinners(int k, Winners[] winners){
        // devolve a  existencia de winners
        // faz unlock ao Broker
        Winners winner_s=new Winners();
        for (int i=0;i<winners.length-1;i++){
            for (int j=i; j<winners.length;j++){
                if (winners[i].iteration<=winners[j].iteration){
                    winner_s=winners[i];
                    winners[i]=winners[j];
                    winners[j]=winner_s;
                    if(winners[i].iteration==winners[j].iteration&&winners[i].position<=winners[j].position){
                        winner_s=winners[i];
                        winners[i]=winners[j];
                        winners[j]=winner_s;
                    }
                }
            }
        }
        int winnersCount;
        if (winners[0].iteration!=winners[1].iteration){
            winnersCount=1;
        }
        else {
            winnersCount=1;
            for(int i =1; i< winners.length;i++){
                if(winners[0].iteration==winners[i].iteration){
                    if (winners[0].position>winners[i].position){
                        break;
                    }
                    winnersCount++;
                }
                else break;
            }
        }

        int [] temp=new int[winnersCount];
        for (int i=0;i<winnersCount;i++){
            temp[i]=winners[i].hj_id;
        }

        this.horseWinners=new int[winnersCount];
        this.horseWinners=temp;

        waitForWinnerInfo=false;
        notifyAll();

        return temp.length > 1;
    }


    public void honourTheBets(int k){
        //Muda o estado ->SETTLING_ACCOUNTS
        //bloqueia em waitForSpectactorCollectsMoney
        int [] temp=new int[horseWinners.length];

        for(int i=0;i<betAmounts.length;i++){
            for (int j=0; j<horseWinners.length;j++){
                if (horseWinners[j]==betAmounts[i].horse_id){
                    temp[j]=betAmounts[i].spectator_id;
                    System.out.print("Spectator id :"+temp[j]);
                }
            }

        }

        this.spectatorWinners=new int[horseWinners.length];
        this.spectatorWinners=temp;
        System.out.println("SpectatorWinners: "+temp[0]);
        ((Broker) Thread.currentThread()).setBrokerState((BrokerState.SETTLING_ACCOUNTS));
        while (waitForSpectactorCollectsMoney){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator bc.honourTheBets() InterruptedException: "+e);
            }
        }
        notifyAll();
        // TODO
    }


    public synchronized void placeABet(){
        System.out.print("PlaceABet\n");
        //Muda o  estado -> PLACING_A_BET
        //bloqueia em isBetDone (espera pelo acceptbets do broker cada bet é unica)

        Spectator spec =((Spectator)Thread.currentThread());
        spec.setState((SpectatorState.PLACING_A_BET));

        // TODO
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
        bet=betAmounts[spec_id].bet= maxBet/odd[spec_id];

        spec.setWallet(spec.getWallet()-bet);
        betAmounts[spec_id].spectator_id=spec_id;

        bets++;
        System.out.print("Spectator: " + spec.getspecId()+ " betted [{horse:"+temp+"},{bet:"+bet+"}]\n");
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


    public boolean haveIWon(){
        // Muda o estado->COLLECTING_THE_GAINS (se ganha alguma coisa)
        //OR
        //        ->WAITING_FOR_A_RACE_TO_START(enquanto existirem corridas)
		//	->CELEBRATING (final state)

        Spectator spec =((Spectator)Thread.currentThread());

        while (waitForWinnerInfo)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator bc.haveIwon() InterruptedException: "+e);
            }

        for (int i=0;i<spectatorWinners.length;i++){
            if (spectatorWinners[i]==spec.getspecId()){
                return true;
            }
        }
        // TODO


        return false;
    }

    public void goCollectTheGains(){

        Spectator spec=((Spectator) Thread.currentThread());
        spec.setState((SpectatorState.COLLECTING_THE_GAINS));
        int money_won=0;
        for (int i=0;i<betAmounts.length;i++){
            if (betAmounts[i].spectator_id==spec.getspecId()){
                money_won=betAmounts[i].bet*odd[i];
                money_won=money_won/horseWinners.length;
            }
        }
        spec.setWallet(money_won);
        waitForSpectactorCollectsMoney=false;
        System.out.println("Spectator "+spec.getspecId()+" Wallet "+spec.getWallet());
        // TODO
    }
}