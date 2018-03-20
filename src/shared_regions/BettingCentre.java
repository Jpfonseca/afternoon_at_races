package shared_regions;

/**
 * This class specifies
 * */
public class BettingCentre{

    void acceptTheBets(int betAmount){

    }

    boolean areThereAnyWinners(int k){
        // devolve a  existencia de winners
        // faz unlock ao Broker
        return false;
    }


    void honourTheBets(int k){
        //Muda o estado ->SETTLING_ACCOUNTS
        //bloqueia em waitForSpectactorCollectsMoney
    }


    void placeABet(){
        //Muda o  estado -> PLACING_A_BET
        //bloqueia em isBetDone (espera pelo acceptbets do broker cada bet é unica)
    }


    boolean haveIWon(){
        // Muda o estado->COLLECTING_THE_GAINS (se ganha alguma coisa)
        //OR
        //        ->WAITING_FOR_A_RACE_TO_START(enquanto existirem corridas)
		//	->CELEBRATING (final state)
        return false;
    }

    void goCollectTheGain(){

        // Muda o estado -> WAITING_FOR_A_RACE_TO_START(enquanto existirem corridas)
    }

}