package shared_regions;

/**
 * This class specifies the Place where the spectators will make the bets.
 *
 */
public class BettingCentre{
    /**
     *
     * @param betAmount it should be an integer and it will range from 0 to Max_bet_value
     */
    public void acceptTheBets(int betAmount){

    }

    public boolean areThereAnyWinners(int k){
        // devolve a  existencia de winners
        // faz unlock ao Broker
        return false;
    }


    public void honourTheBets(int k){
        //Muda o estado ->SETTLING_ACCOUNTS
        //bloqueia em waitForSpectactorCollectsMoney
    }


    public void placeABet(){
        //Muda o  estado -> PLACING_A_BET
        //bloqueia em isBetDone (espera pelo acceptbets do broker cada bet é unica)
    }


    public boolean haveIWon(){
        // Muda o estado->COLLECTING_THE_GAINS (se ganha alguma coisa)
        //OR
        //        ->WAITING_FOR_A_RACE_TO_START(enquanto existirem corridas)
		//	->CELEBRATING (final state)
        return false;
    }

    public void goCollectTheGain(){

        // Muda o estado -> WAITING_FOR_A_RACE_TO_START(enquanto existirem corridas)
    }

}