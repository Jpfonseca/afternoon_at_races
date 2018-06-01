package shared_regions.RMIReply;

import java.io.Serializable;

public class AreThereAnyWinners implements Serializable {
    private boolean winner_status;
    public AreThereAnyWinners(boolean winner_status){
        this.winner_status=winner_status;
    }
}
