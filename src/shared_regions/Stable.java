package shared_regions;
import entities.*;

/**
 * This class specifies the methods that will be executed on the Stable .
 */
public class Stable implements StableInterface {

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
     * @serial waitForNextRace
     */
    private boolean waitForNextRace=true;
    /**
     * Total Agility of the horses. Used to calculate the odd.
     * @serial totalAgility
     */
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
        this.totalAgility=0;
    }

    /**
     * Method used by the Broker to summon the horses to Paddock
     * @param k number of current race
     */
    @Override
    public synchronized void summonHorsesToPaddock(int k, int totalAgility){

        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        repo.setRaceNumber(k);
        repo.setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);

        this.totalAgility = totalAgility;

        this.waitForNextRace = false;
        notifyAll();
    }

    /**
     * Method used by the HorseJockeys to proceed to Stable and wait for the next race
     */
    @Override
    public synchronized void proceedToStable(){
        HorseJockey horse = ((HorseJockey)Thread.currentThread());
        //repo.setIterationStep(horse.getHj_number(),-1);
        //repo.setCurrentPosZero(horse.getHj_number());
        repo.setHorseJockeyAgility(horse.getAgility(),horse.getHj_number());
        repo.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE,horse.getHj_number());

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
        odd=totalAgility/horse.getAgility();
        horse.setOdd(odd);
        repo.setOdd(horse.getHj_number(),odd);

        horse.setHjState((HorseJockeyState.AT_THE_PADDOCK));
        repo.setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK,horse.getHj_number());
    }

    /**
     * Method used by the HorseJockeys when they finished running to proceed back to the Stable
     */
    @Override
    public synchronized void proceedToStable2(){
        ((HorseJockey)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_STABLE));
        repo.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE,((HorseJockey)Thread.currentThread()).getHj_number());
        //repo.reportStatus();

        repo.setIterationStep(((HorseJockey)Thread.currentThread()).getHj_number(),-1);
        repo.setCurrentPosNull(((HorseJockey)Thread.currentThread()).getHj_number());
        repo.setStandingPos(((HorseJockey)Thread.currentThread()).getHj_number(),-1);
        repo.setHorseJockeyAgility(0,((HorseJockey)Thread.currentThread()).getHj_number());
        repo.setOdd(((HorseJockey)Thread.currentThread()).getHj_number(), -1);
        repo.reportStatus();
        repo.setHorseJockeyState(null,((HorseJockey)Thread.currentThread()).getHj_number());
    }

}