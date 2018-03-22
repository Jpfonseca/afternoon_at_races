package shared_regions;
import entities.*;

/**
 * This class specifies the methods that will be executed on the Stable .
 */
public class Stable{

    /**
     * Total competitors per race
     * @serialField N
     */
    private int N;
    /**
     *  Total HorseJockeys in Stable (FIFO)
     *  @serialField queueHJ
     */
    private int queueHJ;
    private int raceNumber;
    private boolean waitForNextRace=true;

    public Stable(int N) {
        this.N = N;
        this.queueHJ=0;
    }

    public synchronized void summonHorsesToPaddock(int k){
        //this.raceNumber=number_race;
        //notifyAll();
        // é apenas isto

        // DO WE NEED k ???!???!???
        this.raceNumber = k;
        this.waitForNextRace = false;
        notifyAll();
    }

    /*Horse*/
    public synchronized void proceedToStable(){
        HorseJockey horse =((HorseJockey)Thread.currentThread());
        horse.setHjState(HorseJockeyState.AT_THE_STABLE);

        while(waitForNextRace)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("HorseJockey proceedToStable() InterruptedException: "+e);
            }

        //Muda de estado ->AT_THE_STABLE
        //Bloqueia o Horse/Jockey  em waitForNextRace


        // OLD st.proceedToPaddock()
        //Muda de estado ->AT_THE_PADDOCK
        // Bloqueia o Horse/Jockey  em waitBeingChecked
        queueHJ++;

        if (queueHJ==N) {
            waitForNextRace = true; // variable reset
            queueHJ=0; // variable reset
        }

        ((HorseJockey)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_PADDOCK));
    }

    public void proceedToStable2(){
        //Muda de estado ->AT_THE_STABLE
        //mata o cavalo ???

        ((HorseJockey)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_STABLE));

        // THIS THREAD WILL IN FACT DIE TO GIVE ALLOW NEXT HORSE/JOCKEY PAIR
    }
}