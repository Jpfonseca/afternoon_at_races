package shared_regions.RMIReply;

import entities.SpectatorState;

import java.io.Serializable;

public class GoCollectTheGains implements Serializable {
    private SpectatorState state;
    private int specWallet;

    public GoCollectTheGains(int specWallet, SpectatorState state){
        this.specWallet = specWallet;
        this.state = state;
    }

    public SpectatorState getState() {
        return state;
    }

    public int getSpecWallet() {
        return specWallet;
    }
}
