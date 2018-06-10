package shared_regions.RMIReply;

import java.io.Serializable;

/**
 * AreThereAnyWinners Class to return RMIReply
 */
public class AreThereAnyWinners implements Serializable {
    /**
     * @serial winner_status Status of Winner
     */
    private boolean winner_status;

    /**
     * AreThereAnyWinners RMIReply
     * @param winner_status Status of Winner
     */
    public AreThereAnyWinners(boolean winner_status){
        this.winner_status=winner_status;
    }

    /**
     * Get winner Status
     * @return boolean Winner Status
     */
    public boolean getWinnerStatus() {
        return winner_status;
    }

    /**
     * Get Winner
     * @return boolean Winner
     */
    public boolean getWinner() {
        return winner_status;
    }
}
