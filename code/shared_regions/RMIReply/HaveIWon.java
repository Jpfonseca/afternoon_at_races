package shared_regions.RMIReply;

import java.io.Serializable;

/**
 * HaveIWon Class to return RMIReply
 */
public class HaveIWon implements Serializable {

    /**
     * @serial status HaveIWon status
     */
    private boolean status;

    /**
     * HaveIWon RMIReply
     * @param status boolean status
     */
    public HaveIWon(boolean status){
        this.status=status;
    }

    /**
     * Get Status RMIReply
     * @return boolean Get Status
     */
    public boolean getStatus() {
        return status;
    }

}
