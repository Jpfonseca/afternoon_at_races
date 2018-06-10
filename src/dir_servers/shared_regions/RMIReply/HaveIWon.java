package shared_regions.RMIReply;

import java.io.Serializable;

public class HaveIWon implements Serializable {
    private boolean status;
    public HaveIWon(boolean status){
        this.status=status;
    }

    public boolean getStatus() {
        return status;
    }

}
