package shared_regions;
import entities.*;

/**
 * This class specifies the methods that will be executed on the Stable .
 */
public class Stable{

    /**
     * Total competitors per race
     * @serial N
     */
    private int N;
    /**
     *  Total HorseJockeys in Stable (FIFO)
     *  @serial queueHJ
     */
    private int queueHJ;
    /**
     * Condition Statement used by the HorseJockey to know when the next race is starting
     * @serial
     */
    private boolean waitForNextRace=true;

    private int agility [];
    private int totalAgility;

    /**
     * General Repository
     * @serial repo
     */
    private GeneralInformationRepository repo;

    /**
     * Stable Constructor
     * @param N Number of HorseJockeys
     * @param repo General Repository
     */

    public Stable(int N, GeneralInformationRepository repo) {
        this.N = N;
        this.queueHJ = 0;
        this.repo = repo;
        this.agility= new int[N];
        this.totalAgility=0;
    }

    /**
     * Method used by the Broker to summon the horses to Paddock
     * @param k number of current race
     */
    public synchronized void summonHorsesToPaddock(int k,int [] agility){

        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        repo.setRaceNumber(k);
        repo.setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);

        this.waitForNextRace = false;
        notifyAll();
        this.agility=agility;

        for (int i=0; i<agility.length;i++){
            totalAgility+=agility[i];
        }
    }

    /**
     * Method used by the HorseJockeys to proceed to Stable and wait for the next race
     */
    public synchronized void  proceedToStable(){
            while(waitForNextRace)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("HorseJockey proceedToStable() InterruptedException: "+e);
            }

        queueHJ++;

        if (queueHJ==N) {
            waitForNextRace = true; // variable reset
            queueHJ=0; // variable reset
        }
        int odd;
        HorseJockey horse= ((HorseJockey)Thread.currentThread());

        odd=totalAgility/horse.getAgility();
        horse.setOdd(odd);
        repo.setOdd(horse.getHj_number(),odd);

        horse.setHjState((HorseJockeyState.AT_THE_PADDOCK));
        repo.setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK,horse.getHj_number());
    }

    /**
     * Method used by the HorseJockeys when they finished running to proceed back to the Stable
     */
    public synchronized void proceedToStable2(){
        ((HorseJockey)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_STABLE));
        repo.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE,((HorseJockey)Thread.currentThread()).getHj_number());


        repo.setIterationStep(((HorseJockey)Thread.currentThread()).getHj_number(),-1);
        repo.setCurrentPosNull(((HorseJockey)Thread.currentThread()).getHj_number());
        repo.setStandingPos(((HorseJockey)Thread.currentThread()).getHj_number(),-1);
        repo.setHorseJockeyAgility(0,((HorseJockey)Thread.currentThread()).getHj_number());
        repo.setHorseJockeyState(null,((HorseJockey)Thread.currentThread()).getHj_number());
    }

}