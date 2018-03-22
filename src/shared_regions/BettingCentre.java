package shared_regions;

import entities.Broker;
import entities.BrokerState;
import entities.Spectator;
import entities.SpectatorState;

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

    public BettingCentre(int M) {
        this.totalSpectators = M;
    }

    /**
     *
     * @param betAmount it should be an integer and it will range from 0 to Max_bet_value
     */
    public synchronized void acceptTheBets(int betAmount){
        // DID WE FORGET TO UPDATE STATE -> WAITING_FOR_BETS ???!???!???

        ((Broker)Thread.currentThread()).setBrokerState((BrokerState.WAITING_FOR_BETS));

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
        //Muda o  estado -> PLACING_A_BET
        //bloqueia em isBetDone (espera pelo acceptbets do broker cada bet é unica)

        ((Spectator)Thread.currentThread()).setState((SpectatorState.PLACING_A_BET));

        // TODO
        // SPECTATOR BET
        bets++;

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