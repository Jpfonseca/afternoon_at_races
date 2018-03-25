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
    private int finishedHJ;
    private boolean waitForNextRace=true;
    private GeneralInformationRepository repo;

    public Stable(int N, GeneralInformationRepository repo) {
        this.N = N;
        this.queueHJ = 0;
        this.finishedHJ = 0;
        this.repo = repo;
    }

    public synchronized void summonHorsesToPaddock(int k){
        //this.raceNumber=number_race;
        //notifyAll();
        // é apenas isto

        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        repo.setRaceNumber(k);
        repo.setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);



        // DO WE NEED k ???!???!???
        this.waitForNextRace = false;
        notifyAll();
    }

    /*Horse*/
    public synchronized void proceedToStable(){
        // Set in constructor
        //((HorseJockey)Thread.currentThread()).setHjState(HorseJockeyState.AT_THE_STABLE);
        //repo.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE,((HorseJockey)Thread.currentThread()).getHj_number());

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
        repo.setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK,((HorseJockey)Thread.currentThread()).getHj_number());
    }

    public synchronized void proceedToStable2(){
        //Muda de estado ->AT_THE_STABLE
        //mata o cavalo ???

        // THIS THREAD WILL IN FACT DIE TO GIVE ALLOW NEXT HORSE/JOCKEY PAIR
        ((HorseJockey)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_STABLE));


        repo.setIterationStep(((HorseJockey)Thread.currentThread()).getHj_number(),-1);
        repo.setCurrentPosNull(((HorseJockey)Thread.currentThread()).getHj_number());
        repo.setStandingPos(((HorseJockey)Thread.currentThread()).getHj_number(),0);
        repo.setHorseJockeyAgility(0,((HorseJockey)Thread.currentThread()).getHj_number());
        repo.setHorseJockeyState(null,((HorseJockey)Thread.currentThread()).getHj_number());
    }
}