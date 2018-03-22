package shared_regions;
import entities.*;

/**
 * This class specifies the methods that will be executed on the Paddock .
 */
public class Paddock{

    /**
     *  Total HorseJockeys in paddock (FIFO)
     *  @serialField queueHJ
     */
    private int queueHJ=0;
    /**
     *  Total Spectators in paddock (FIFO)
     *  @serialField queueSpec
     */
    private int queueSpec=0;
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

    public Paddock(int N, int M) {
        this.N = N;
        this.M = M;
    }

    /**
     * Horse_Jockey
     * */
    public synchronized boolean proceedToPaddock1(){
        //check if it’s the last horse
        queueHJ++;

        return (queueHJ == N);
    }
    public synchronized void proceedToPaddock2(){
        //Muda de estado ->AT_THE_PADDOCK
        // Bloqueia o Horse/Jockey  em waitBeingChecked


        // SHOULD THIS STATE CHANGE BE IN proceedToPaddock1 ???!???!???
        // SINCE IT IS ALREADY IN PADDOCK AND BROKER IS WAKEN UP...
        // NEVERMIND! THIS CHANGE WENT TO st.proceedToStable()

        while(waitBeingChecked)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("HorseJockey pd.proceedToPaddock2() InterruptedException: "+e);
            }

        queueHJ++;

        if (queueHJ==N*2) {
            waitForLastHJ = false;
            waitBeingChecked=true; // variable reset
            queueHJ=0;
            notifyAll();
        }
    }

    /**Spectator
     *
     */

    public synchronized boolean goCheckHorses1(){
        //checks if the (horse)SPECTATOR is the last to enter the paddock
        queueSpec++;

        return (queueSpec == M);
    }

    public synchronized void goCheckHorses2(boolean last){
        //Sends spectator to paddock(sleeps ??)
        //If last spectator :
        // Acordar o Horse/Jockey que estão em waitBeingChecked (levando a que eles se movam para a rt)

        // DID WE FORGET TO UPDATE STATE -> APPRAISING_THE_HORSES ???!???!???
        // DID WE FORGET TO waitForLastHJ ???!???!???

        ((Spectator)Thread.currentThread()).setState((SpectatorState.APPRAISING_THE_HORSES));

        if (last) {
            waitBeingChecked = false;
            notifyAll();
        }

        while(waitForLastHJ){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Spectator pd.goCheckHorses2() InterruptedException: "+e);
            }
        }

        queueSpec--;
        if (queueSpec==0)
            waitForLastHJ=true; // variable reset
    }

}