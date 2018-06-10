package shared_regions;

//import clients.GeneralInformationRepositoryStub;
import entities.BrokerState;
import entities.HorseJockeyState;
import interfaces.GeneralInformationRepositoryInterface;
import interfaces.StableInterface;
import shared_regions.RMIReply.ProceedToStable;
import shared_regions.RMIReply.ProceedToStable2;
import shared_regions.RMIReply.SummonHorsesToPaddock;

import java.rmi.RemoteException;

//import servers.Aps;

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
     * Variable to store the remaining shutdowns of the servers
     */
    private boolean shutdown=false;

    /**
     * General Repository Stub
     * @serial repoStub
     */
    //private GeneralInformationRepository repo;
    private GeneralInformationRepositoryInterface repoStub;

    /**
     * Stable Constructor
     * @param N Number of HorseJockeys
     * @param repoStub Repository Stub
     */

    public Stable(int N, GeneralInformationRepositoryInterface repoStub) {
        this.N = N;
        this.queueHJ = 0;
        this.repoStub = repoStub ;
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
        try {
            repoStub.setRaceNumber(k);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            repoStub.setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

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

        try {
            repoStub.setHorseJockeyAgility(agility, hjNumber);
            repoStub.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE, hjNumber);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

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
        try {
            repoStub.setOdd(hjNumber, odd);
            //horse.setHjState((HorseJockeyState.AT_THE_PADDOCK));
            repoStub.setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK, hjNumber);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return new ProceedToStable(odd, HorseJockeyState.AT_THE_PADDOCK);
    }

    /**
     * Method used by the HorseJockeys when they finished running to proceed back to the Stable
     */
    @Override
    public synchronized ProceedToStable2 proceedToStable2(int hjNumber){
        //((Aps)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_STABLE));
        try {
            repoStub.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE, hjNumber);
            //repo.reportStatus();

            repoStub.setIterationStep(hjNumber,-1);
            repoStub.setCurrentPos(hjNumber,-1);
            repoStub.setStandingPos(hjNumber,-1);
            repoStub.setHorseJockeyAgility(0, hjNumber);
            repoStub.setOdd(hjNumber, -1);
            repoStub.reportStatus();
            repoStub.setHorseJockeyState(null, hjNumber);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return new ProceedToStable2(HorseJockeyState.AT_THE_STABLE);
    }

    /**
     * Method used to shutdown server
     * @param clientID Client ID
     */
    @Override
    public synchronized void shutdown(int clientID){
        this.shutdown=true;
        notifyAll();
    }

    /**
     * Method used to know if server can shutdown
     * @return boolean with true or false for server shutdown
     */
    public synchronized boolean isShutdown() {
        while (!shutdown)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("ST InterruptedException: "+e);
            }
        return shutdown;
    }


}