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
     * @return AcceptTheBets
     * @throws RemoteException Remote Exception
     */
    AcceptTheBets acceptTheBets() throws RemoteException;

    /**
     * This method specifies the existence of winners in the current race.
     * @param winners winners array
     * @return AreThereAnyWinners
     * @throws RemoteException Remote Exception
     */
    AreThereAnyWinners areThereAnyWinners(Winners[] winners)throws RemoteException;

    /**
     * This method specifies the existence of winners in the current race.
     * @return HonourTheBets
     * @throws RemoteException Remote Exception
     */
    HonourTheBets honourTheBets()throws RemoteException;

    /**
     * This method is used by each spectator to place a bet.
     * @param specId Spectator ID
     * @param specWallet Spectator wallet
     * @return PlaceABet
     * @throws RemoteException Remote Exception
     */
    PlaceABet placeABet(int specId, int specWallet)throws RemoteException;

    /**
     * This method is used by the spectator to check if it has won something from the bet
     * @param specId Spectator ID
     * @return HaveIWon
     * @throws RemoteException Remote Exception
     */
    HaveIWon haveIWon(int specId)throws RemoteException;

    /**
     * This method is used by the spectators to collect their gains, if they have won something.
     * @param specWallet Spectator Wallet
     * @param horseId Horse ID
     * @return GoCollectTheGains
     * @throws RemoteException Remote Exception
     */
    GoCollectTheGains goCollectTheGains(int horseId,int specWallet)throws RemoteException;

    /**
     * This method sets each HorseJockey odd in the BettingCentre
     * @param horseId Horse ID
     * @param horseOdd Horse Odd
     * @throws RemoteException Remote Exception
     */
    void setHorseJockeyOdd(int horseId,int horseOdd)throws RemoteException;

    /**
     * Method used by several entities to send a shutdown signal to the Shared region server
     * @param clientID Id of the Client who asked the shutdown
     * @throws RemoteException Remote Exception
     */
    void shutdown(int clientID) throws RemoteException;
}

