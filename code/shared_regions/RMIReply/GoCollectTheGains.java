package shared_regions.RMIReply;

import entities.SpectatorState;

import java.io.Serializable;

/**
 * GoCollectTheGain Class to return RMIReply
 */
public class GoCollectTheGains implements Serializable {
    /**
     * @serial state Spectator State
     */
    private SpectatorState state;

    /**
     * @serial specWallet Spectator Wallet
     */
    private int specWallet;

    /**
     * GoCollectTheGains RMIReply
     * @param specWallet Spectator Wallet
     * @param state Spectator State
     */
    public GoCollectTheGains(int specWallet, SpectatorState state){
        this.specWallet = specWallet;
        this.state = state;
    }

    /**
     * Get Spectator State
     * @return SpectatorState RMIReply
     */
    public SpectatorState getState() {
        return state;
    }

    /**
     * Get Spectator Wallet
     * @return int Spectator Wallet RMIReply
     */
    public int getSpecWallet() {
        return specWallet;
    }
}
