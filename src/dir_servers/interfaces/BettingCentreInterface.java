package interfaces;


import shared_regions.RMIReply.*;
import shared_regions.Winners;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * BettingCentreInterface
 * Used to Interface the BettingCentre Server with the "public"
 */
public interface BettingCentreInterface extends Remote {
    /**
     * Method used by the broker to announce he can collect Spectator's bets
     */
    AcceptTheBets acceptTheBets() throws RemoteException;

    /**
     * This method specifies the existence of winners in the current race.
     * @param winners winners array
     * @return <b>true</b>,if there winners or <b>false</b>, if there aren't any.
     */
    AreThereAnyWinners areThereAnyWinners(Winners[] winners)throws RemoteException;

    /**
     * This method specifies the existence of winners in the current race.
     */
    HonourTheBets honourTheBets()throws RemoteException;

    /**
     * This method is used by each spectator to place a bet.
     */
    PlaceABet placeABet(int specId, int specWallet)throws RemoteException;

    /**
     * This method is used by the spectator to check if it has won something from the bet
     * @return <b>true</b> or <b>false</b> whether the spectator won something or not.
     */
    HaveIWon haveIWon(int specId)throws RemoteException;

    /**
     * This method is used by the spectators to collect their gains, if they have won something.
     */
    GoCollectTheGains goCollectTheGains(int horseId,int specWallet)throws RemoteException;

    /**
     * This method sets each HorseJockey odd in the BettingCentre
     */
    void setHorseJockeyOdd(int horseId,int horseOdd)throws RemoteException;
}
