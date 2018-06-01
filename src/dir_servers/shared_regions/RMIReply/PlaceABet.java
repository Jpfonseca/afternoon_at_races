package shared_regions.RMIReply;

import entities.SpectatorState;

import java.io.Serializable;

public class PlaceABet implements Serializable {

    private int setSpecWallet;
    private SpectatorState state;

    public PlaceABet(int setSpecWallet, SpectatorState state){
        this.state=state;
        this.setSpecWallet=setSpecWallet;
    }
}
