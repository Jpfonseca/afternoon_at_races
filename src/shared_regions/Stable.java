package shared_regions;
import entities.*;

/**
 * This class specifies the methods that will be executed on the Stable .
 */
public class Stable{

    private int raceNumber;
    private boolean waitForNextRace=true;

    public synchronized void summonHorsesToPaddock(int k){
        //this.raceNumber=number_race;
        //notifyAll();
        // é apenas isto
        this.raceNumber = k;
        this.waitForNextRace = false;
        notifyAll();
    }

    /*Horse*/
    public synchronized void proceedToStable(){
        HorseJockey horse =((HorseJockey)Thread.currentThread());
        horse.setHjState(HorseJockeyState.AT_THE_STABLE);

        while(waitForNextRace){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("HorseJockey proceedToStable() InterruptedException: "+e);
            }
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
}