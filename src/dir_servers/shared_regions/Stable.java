package shared_regions;

//import clients.GeneralInformationRepositoryStub;
import entities.BrokerState;
import entities.HorseJockeyState;
import extras.config;
import shared_regions.RMIReply.ProceedToStable;
import shared_regions.RMIReply.ProceedToStable2;
import shared_regions.RMIReply.SummonHorsesToPaddock;

//import servers.Aps;

/**
 * This class specifies the methods that will be executed on the Stable .
 */
public class Stable implements StableInterface{

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
    //TODO private GeneralInformationRepositoryStub repoStub;

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
        //TODO this.repoStub = new GeneralInformationRepositoryStub();
        this.totalAgility=0;
    }

    /**
     * Method used by the Broker to summon the horses to Paddock
     * @param k number of current race
     * @param totalAgility Total agility
     */
    @Override
    public synchronized SummonHorsesToPaddock summonHorsesToPaddock(int k, int totalAgility){
        //((Aps)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        //TODO repoStub.setRaceNumber(k);
        //TODO repoStub.setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);

        this.totalAgility = totalAgility;

        this.waitForNextRace = false;
        notifyAll();

        return new SummonHorsesToPaddock(BrokerState.ANNOUNCING_NEXT_RACE);
    }

    /**
     * Method used by the HorseJockeys to proceed to Stable and wait for the next race
     */
    @Override
    public synchronized ProceedToStable proceedToStable(int agility, int hjNumber){
        //Aps horse = ((Aps)Thread.currentThread());
        //repo.setIterationStep(horse.getHj_number(),-1);
        //repo.setCurrentPosZero(horse.getHj_number());
        //TODO repoStub.setHorseJockeyAgility(agility, hjNumber);
        //TODO repoStub.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE, hjNumber);

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
        odd=totalAgility/agility;
        //horse.setOdd(odd);
        //TODO repoStub.setOdd(hjNumber, odd);

        //horse.setHjState((HorseJockeyState.AT_THE_PADDOCK));
        //TODO repoStub.setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK, hjNumber);

        return new ProceedToStable(odd, HorseJockeyState.AT_THE_PADDOCK);
    }

    /**
     * Method used by the HorseJockeys when they finished running to proceed back to the Stable
     */
    @Override
    public synchronized ProceedToStable2 proceedToStable2(int hjNumber){
        //((Aps)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_STABLE));
        //TODO repoStub.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE, hjNumber);
        //repo.reportStatus();

        //TODO repoStub.setIterationStep(hjNumber,-1);
        //TODO repoStub.setCurrentPos(hjNumber,-1);
        //TODO repoStub.setStandingPos(hjNumber,-1);
        //TODO repoStub.setHorseJockeyAgility(0, hjNumber);
        //TODO repoStub.setOdd(hjNumber, -1);
        //TODO repoStub.reportStatus();
        //TODO repoStub.setHorseJockeyState(null, hjNumber);

        return new ProceedToStable2(HorseJockeyState.AT_THE_STABLE);
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