package shared_regions;
import clients.GeneralInformationRepositoryStub;
import entities.*;
import extras.config;
import servers.Aps;

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
     * General Repository Stub
     * @serial repoStub
     */
    //private GeneralInformationRepository repo;
    private GeneralInformationRepositoryStub repoStub;

    /**
     * Instance of Stable
     * @serialField instance
     */
    private static Stable instance;

    /**
     * Stable Constructor
     * @param N Number of HorseJockeys
     */

    public Stable(int N) {
        this.N = N;
        this.queueHJ = 0;
        this.repoStub = new GeneralInformationRepositoryStub();
        this.totalAgility=0;
    }

    /**
     * Method used by the Broker to summon the horses to Paddock
     * @param k number of current race
     * @param totalAgility Total agility
     */
    @Override
    public synchronized void summonHorsesToPaddock(int k, int totalAgility){
        ((Aps)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        repoStub.setRaceNumber(k);
        repoStub.setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);

        this.totalAgility = totalAgility;

        this.waitForNextRace = false;
        notifyAll();
    }

    /**
     * Method used by the HorseJockeys to proceed to Stable and wait for the next race
     */
    @Override
    public synchronized void proceedToStable(){
        Aps horse = ((Aps)Thread.currentThread());
        //repo.setIterationStep(horse.getHj_number(),-1);
        //repo.setCurrentPosZero(horse.getHj_number());
        repoStub.setHorseJockeyAgility(horse.getAgility(),horse.getHj_number());
        repoStub.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE,horse.getHj_number());

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
        repoStub.setOdd(horse.getHj_number(),odd);

        horse.setHjState((HorseJockeyState.AT_THE_PADDOCK));
        repoStub.setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK,horse.getHj_number());
    }

    /**
     * Method used by the HorseJockeys when they finished running to proceed back to the Stable
     */
    @Override
    public synchronized void proceedToStable2(){
        ((Aps)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_STABLE));
        repoStub.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE,((Aps)Thread.currentThread()).getHj_number());
        //repo.reportStatus();

        repoStub.setIterationStep(((Aps)Thread.currentThread()).getHj_number(),-1);
        repoStub.setCurrentPos(((Aps)Thread.currentThread()).getHj_number(),-1);
        repoStub.setStandingPos(((Aps)Thread.currentThread()).getHj_number(),-1);
        repoStub.setHorseJockeyAgility(0,((Aps)Thread.currentThread()).getHj_number());
        repoStub.setOdd(((Aps)Thread.currentThread()).getHj_number(), -1);
        repoStub.reportStatus();
        repoStub.setHorseJockeyState(null,((Aps)Thread.currentThread()).getHj_number());
    }

    /**
     * Returns current instance of Stable
     * @return instance of Stable
     */
    public static Stable getInstance(){
        if (instance==null)
            instance = new Stable(config.N);

        return instance;
    }

}