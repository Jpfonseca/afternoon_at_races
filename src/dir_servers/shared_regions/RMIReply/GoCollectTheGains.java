package shared_regions.RMIReply;

import entities.SpectatorState;

import java.io.Serializable;

public class GoCollectTheGains implements Serializable {
    private SpectatorState state;
    private int setSpecWallet;

    public GoCollectTheGains(int setSpecWallet, SpectatorState state){
        this.setSpecWallet=setSpecWallet;
        this.state=state;
    }
}
