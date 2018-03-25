package shared_regions;
import entities.*;

/**
 * This class specifies the methods that will be executed on the Paddock .
 */
public class Paddock{

    private GeneralInformationRepository repo;
    /**
     *  Total HorseJockeys in paddock (FIFO)
     *  @serialField queueHJ
     */
    private int totalHJ=0;
    /**
     *  Total Spectators in paddock (FIFO)
     *  @serialField queueSpec
     */
    private int totalSpec=0;
    /**
     * Total competitors per race
     * @serialField N
     */
    private int N;
    /**
     * Total Spectators
     * @serialField M
     */
    private int M;

    private boolean waitBeingChecked=true;
    private boolean waitForLastHJ=true;

    public Paddock(int N, int M, GeneralInformationRepository repo) {
        this.N = N;
        this.M = M;
        this.repo = repo;
    }

    /**
     * Horse_Jockey
     * */
    public synchronized boolean proceedToPaddock1(){
        //check if it’s the last horse
        totalHJ++;

        return (totalHJ == N);
    }
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

    /**Spectator
     *
     */

    public synchronized boolean goCheckHorses1(){
        //checks if the (horse)SPECTATOR is the last to enter the paddock
        totalSpec++;

        return (totalSpec == M);
    }

    public synchronized void goCheckHorses2(boolean last){

        ((Spectator)Thread.currentThread()).setState((SpectatorState.APPRAISING_THE_HORSES));
        repo.setSpectatorState(SpectatorState.WATCHING_A_RACE,((Spectator)Thread.currentThread()).getspecId());

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
    }

}