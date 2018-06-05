package shared_regions.RMIReply;

import entities.SpectatorState;

import java.io.Serializable;

public class PlaceABet implements Serializable {

    private int specWallet;
    private SpectatorState state;

    public PlaceABet(int specWallet, SpectatorState state){
        this.state=state;
        this.specWallet=specWallet;
    }

    public int getSpecWallet() {
        return specWallet;
    }

    public SpectatorState getState() {
        return state;
    }
}
