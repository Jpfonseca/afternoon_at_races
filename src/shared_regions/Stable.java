package shared_regions;
import entities.*;

/**
 * This class specifies the methods that will be executed on the Stable .
 */
public class Stable{
    /*Broker*/

    public void summonHorsesToPaddock(int number_race){
        //this.raceNumber=number_race;
        //notifyAll();
        // é apenas isto
    }

    /*Horse*/
    public void proceedToStable(){
        HorseJockey horse =((HorseJockey)Thread.currentThread());
                horse.setHjState(HorseJockeyState.AT_THE_STABLE);
        while(waitForNextRace){

        }
        //Muda de estado ->AT_THE_STABLE
        //Bloqueia o Horse/Jockey  em waitForNextRace
    }

    public void proceedToPaddock(){
        //Muda de estado ->AT_THE_PADDOCK
        // Bloqueia o Horse/Jockey  em waitBeingChecked
    }

    public void proceedToStable2(){
        //Muda de estado ->AT_THE_STABLE
        //mata o cavalo ???
    }

    private int raceNumber;

    private boolean waitForNextRace=true;
}