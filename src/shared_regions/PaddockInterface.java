package shared_regions;

import java.rmi.Remote;

/**
 * PaddockInterface
 * Used to Interface the Paddock Server with the "public"
 */
public interface PaddockInterface extends Remote {

    /**
     * Method used for HorseJockey to know if he is the last one to proceed to paddock
     * @return <b>true</b> if he is the last or <b>false</b>, if he is not.
     * */
    boolean proceedToPaddock1();

    /**
     * Method used for HorseJockey to wait in the Paddock
     * */
    void proceedToPaddock2();

    /**
     * Method used by the Spectator to know if he is the last one to appraise the horses in the Paddock
     * @return <b>true</b> if he is the last or <b>false</b>, if he is not.
     */
    boolean goCheckHorses1();

    /**
     * Method used by the Spectator to wait while appraising the horses in the Paddock
     * @param last last Spectator
     */
    void goCheckHorses2(boolean last);
}
