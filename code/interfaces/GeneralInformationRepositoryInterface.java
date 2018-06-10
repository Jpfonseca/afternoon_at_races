package interfaces;
import entities.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GeneralInformationRepositoryInterface
 * Used to instantiate GeneralInformationRepository Server with the "public"
 */
public interface GeneralInformationRepositoryInterface extends Remote {

    /**
     * This method is used to print a pair of Log Lines into a file containing the current snapshot of the current simulation status
     * @throws RemoteException Remote Exception
     */
    void reportStatus() throws RemoteException;

    /**
     * Used to set the Broker state
     * @param state Broker state
     * @throws RemoteException Remote Exception
     */

    void setBrokerState(BrokerState state) throws RemoteException;

    /**
     * Used to set the Spectator State
     * @param state state to set
     * @param index index of the Spectator
     * @throws RemoteException Remote Exception
     */
    void setSpectatorState(SpectatorState state, int index) throws RemoteException;

    /**
     * Used to set the Spectator amount of money in the wallet
     * @param money amount of money
     * @param index index of Spectator
     * @throws RemoteException Remote Exception
     */
    void setSpectatorMoney(int money, int index) throws RemoteException;

    /**
     * Used to set the current race number
     * @param raceNumber race number
     * @throws RemoteException Remote Exception
     */
    void setRaceNumber(int raceNumber) throws RemoteException;

    /**
     * Used to set the HorseJockey state
     * @param state state to set
     * @param index HorseJockey's index
     * @throws RemoteException Remote Exception
     */
    void setHorseJockeyState(HorseJockeyState state, int index) throws RemoteException;

    /**
     * Used to set the HorseJockey agility
     * @param agility magility to set
     * @param index HorseJockey's index
     * @throws RemoteException Remote Exception
     */
    void setHorseJockeyAgility(int agility, int index) throws RemoteException;

    /**
     * Used to set all the track distances
     * @param d distance
     * @throws RemoteException Remote Exception
     */
    void setTrackDistance(int[] d) throws RemoteException;

    /**
     * Used to set the spectators bets
     * @param spectatorIndex Spectator's index
     * @param betSelection bet selection index
     * @param betAmount bet amount
     * @throws RemoteException Remote Exception
     */
    void setSpectatorBet(int spectatorIndex, int betSelection, int betAmount) throws RemoteException;

    /**
     * Used to the HorseJockey odd
     * @param horse HorseJockey index
     * @param odd odd
     * @throws RemoteException Remote Exception
     */
    void setOdd(int horse, int odd) throws RemoteException;

    /**
     * Used to set the iteration step
     * @param horse HorseJockey index
     * @param iterationStep iteration number
     * @throws RemoteException Remote Exception
     */
    void setIterationStep(int horse, int iterationStep) throws RemoteException;

    /**
     * Used to set the current HorseJockey position in the race
     * @param horse HorseJockey index
     * @param position position
     * @throws RemoteException Remote Exception
     */
    void setCurrentPos(int horse, int position) throws RemoteException;

    /**
     * Used to set the current HorseJockey standing position
     * @param horse HorseJockey index
     * @param standing standing position
     * @throws RemoteException Remote Exception
     */
    void setStandingPos(int horse, int standing) throws RemoteException;

    /**
     * Method used by several entities to send a shutdown signal to the Shared region server
     * @param clientID Id of the Client who asked the shutdown
     * @throws RemoteException Remote Exception
     */
    void shutdown(int clientID) throws RemoteException;
}
