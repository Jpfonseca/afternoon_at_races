package shared_regions;
import entities.*;

/**
 * This class specifies the methods that will be executed on the Stable .
 */
public class Stable{
    public void summonHorsesToPaddock(int number_race){
        //Muda de estado ->AT_THE_STABLE
        //Bloqueia o Horse/Jockey
    }

    public void proceedToStable(HorseJockey horse_jockey){
        //Muda de estado ->AT_THE_STABLE
        //Bloqueia o Horse/Jockey  em waitForNextRace
    }

    public void proceedToPaddock(HorseJockey horse_jockey){
        //Muda de estado ->AT_THE_PADDOCK
        // Bloqueia o Horse/Jockey  em waitBeingChecked
    }

    public void proceedToStable2(HorseJockey horse_jockey){
        //Muda de estado ->AT_THE_STABLE
        //mata o cavalo ???
    }


}