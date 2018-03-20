package shared_regions;
import entities.*;

public class Stable{
    void summonHorsesToPaddock(int number_race){
        //Muda de estado ->AT_THE_STABLE
        //Bloqueia o Horse/Jockey
    }

    void proceedToTheStable(HorseJockey horse_jockey){
        //Muda de estado ->AT_THE_STABLE
        //Bloqueia o Horse/Jockey  em waitForNextRace
    }

    void proceedToThePaddock(HorseJockey horse_jockey){
        //Muda de estado ->AT_THE_PADDOCK
        // Bloqueia o Horse/Jockey  em waitBeingChecked
    }

    void proceedtoStable2(HorseJockey horse_jockey){
        //Muda de estado ->AT_THE_STABLE
        //mata o cavalo ???
    }


}