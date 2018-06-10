package shared_regions.RMIReply;

import entities.SpectatorState;

import java.io.Serializable;

/**
 * PlaceABet Class to return RMIReply
 */
public class PlaceABet implements Serializable {

    /**
     * @serial specWallet Spectator Wallet
     */
    private int specWallet;

    /**
     * @serial state Spectator State
     */
    private SpectatorState state;

    /**
     * PlaceABet RMIReply
     * @param specWallet Spectator Wallet
     * @param state Spectator State
     */
    public PlaceABet(int specWallet, SpectatorState state){
        this.state=state;
        this.specWallet=specWallet;
    }

    /**
     * Get Spectator Wallet RMIReply
     * @return int Spectator Wallet amount RMIReply
     */
    public int getSpecWallet() {
        return specWallet;
    }

    /**
     * Get Spectator State RMIReply
     * @return SpectatorState RMIReply
     */
    public SpectatorState getState() {
        return state;
    }
}
