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
    private int maxBet;
    private int [] agility;
    private BetAmount[] betAmounts;

    public BettingCentre(int M, int maxBet) {
        this.totalSpectators = M;
        this.maxBet=maxBet;
        this.betAmounts=new BetAmount[totalSpectators];
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

    public boolean areThereAnyWinners(int k){
        // devolve a  existencia de winners
        // faz unlock ao Broker

        // TODO
        return false;
    }


    public void honourTheBets(int k){
        //Muda o estado ->SETTLING_ACCOUNTS
        //bloqueia em waitForSpectactorCollectsMoney
        ((Broker) Thread.currentThread()).setBrokerState((BrokerState.SETTLING_ACCOUNTS));

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
        System.out.print("spec_id:"+spec_id+"\n");
        System.out.print("BetAmounts: "+betAmounts.length+"\n");
        betAmounts[spec_id]=new BetAmount();
        betAmounts[spec_id].horse_id=temp;

        for (int i=0; i<agility.length;i++){
            totalAgility+=agility[i];
        }

        bet=betAmounts[spec_id].bet= ((agility[temp]*maxBet)/totalAgility);
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

        // TODO

        return false;
    }

    public void goCollectTheGains(){

        // Muda o estado -> WAITING_FOR_A_RACE_TO_START(enquanto existirem corridas)
        ((Spectator) Thread.currentThread()).setState((SpectatorState.WAITING_FOR_A_RACE_TO_START));

        // TODO
    }
}