package shared_regions;
import clients.GeneralInformationRepositoryStub;
import entities.*;
import extras.config;
import servers.Aps;

/**
 * This class specifies the methods that will be executed on the Paddock .
 */
public class Paddock implements PaddockInterface {

    /**
     * General Repository Stub
     * @serial repo
     */
    //private GeneralInformationRepository repo;
    private GeneralInformationRepositoryStub repo;
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

    /**
     * Paddock Constructor
     * @param N Number of HorseJockeys
     * @param M Number of Spectators
     */
    public Paddock(int N, int M) {
        this.N = N;
        this.M = M;
        this.repo = new GeneralInformationRepositoryStub();
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
    public synchronized void goCheckHorses2(boolean last){

        ((Aps)Thread.currentThread()).setState((SpectatorState.APPRAISING_THE_HORSES));
        repo.setSpectatorState(SpectatorState.APPRAISING_THE_HORSES,((Aps)Thread.currentThread()).getSpecId());
        repo.reportStatus();

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

    /**
     * Returns current instance of Paddock
     * @return instance of Paddock
     */
    public static Paddock getInstance(){
        if (instance==null)
            instance = new Paddock(config.N, config.M);

        return instance;
    }
}