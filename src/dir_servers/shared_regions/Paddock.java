package shared_regions;


import interfaces.GeneralInformationRepositoryInterface;
import interfaces.PaddockInterface;
import shared_regions.RMIReply.GoCheckHorses2;
import entities.SpectatorState;

import java.rmi.RemoteException;

/**
 * This class specifies the methods that will be executed on the Paddock .
 */
public class Paddock implements PaddockInterface {

    /**
     * General Repository Stub
     * @serial repoStub
     */
    private GeneralInformationRepositoryInterface repoStub;
    /**
     *  Total HorseJockeys in paddock (FIFO)
     *  @serial queueHJ
     */
    private int totalHJ=0;
    /**
     *  Total Spectators in paddock (FIFO)
     *  @serial queueSpec
     */
    private int totalSpec=0;
    /**
     * Total competitors per race
     * @serial N
     */
    private int N;
    /**
     * Total Spectators
     * @serial M
     */
    private int M;
    /**
     * Condition Statement to know when HorseJockeys will proceed to the start line
     * @serial waitBeingChecked
     */
    private boolean waitBeingChecked=true;
    /**
     * Condition Statement to know when the last HorseJockey has left the Paddock
     * @serial waitForLastHJ
     */
    private boolean waitForLastHJ=true;

    /**
     * Instance of Paddock
     * @serialField instance
     */
    private static Paddock instance;

    private int shutdownRequest;
    private boolean shutdown;


    /**
     * Paddock Constructor
     * @param N Number of HorseJockeys
     * @param M Number of Spectators
     */
    public Paddock(int N, int M, GeneralInformationRepositoryInterface repoStub) {
        this.N = N;
        this.M = M;
        this.repoStub = repoStub;
        this.shutdownRequest=4;
    }

    /**
     * Method used for HorseJockey to know if he is the last one to proceed to paddock
     * @return <b>true</b> if he is the last or <b>false</b>, if he is not.
     * */
    @Override
    public synchronized boolean proceedToPaddock1(){
        //check if it’s the last horse
        totalHJ++;

        return (totalHJ == N);
    }

    /**
     * Method used for HorseJockey to wait in the Paddock
     * */
    @Override
    public synchronized void proceedToPaddock2(){

        while(waitBeingChecked)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("HorseJockey pd.proceedToPaddock2() InterruptedException: "+e);
            }

        totalHJ++;

        if (totalHJ==N*2) {
            waitForLastHJ = false;
            waitBeingChecked=true; // variable reset
            totalHJ=0;
            notifyAll();
        }
    }

    /**
     * Method used by the Spectator to know if he is the last one to appraise the horses in the Paddock
     * @return <b>true</b> if he is the last or <b>false</b>, if he is not.
     */
    @Override
    public synchronized boolean goCheckHorses1(){
        //checks if the (horse)SPECTATOR is the last to enter the paddock
        totalSpec++;

        return (totalSpec == M);
    }

    /**
     * Method used by the Spectator to wait while appraising the horses in the Paddock
     * @param last last Spectator
     */
    @Override
    public synchronized GoCheckHorses2 goCheckHorses2(boolean last, int specId){
        //TODO send spectator id

        try {
            repoStub.setSpectatorState(SpectatorState.APPRAISING_THE_HORSES,specId);
            repoStub.reportStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        if (last) {
            waitBeingChecked = false;
            notifyAll();
        }

        while(waitForLastHJ)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator pd.goCheckHorses2() InterruptedException: "+e);
            }

        totalSpec--;
        if (totalSpec==0)
            waitForLastHJ=true; // variable reset
        return new GoCheckHorses2(SpectatorState.APPRAISING_THE_HORSES);
    }

    @Override
    public synchronized void shutdown(int clientID){
        if (shutdownRequest==0){
            shutdownRequest=0;
            this.shutdown=true;
            notifyAll();
        }else{
            shutdownRequest--;
        }
    }

    public synchronized boolean isShutdown() {
        while (!shutdown)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Paddock InterruptedException: "+e);
            }
        return shutdown;
    }

}