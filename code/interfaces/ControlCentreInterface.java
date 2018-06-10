package interfaces;

import shared_regions.RMIReply.EntertainTheGuests;
import shared_regions.RMIReply.GoWatchTheRace;
import shared_regions.RMIReply.RelaxABit;
import shared_regions.RMIReply.WaitForNextRace;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ControlCentreInterface
 * Used to instantiate ControlCentre Server with the "public"
 */
public interface ControlCentreInterface extends Remote {

    /**
     * This method is used by the Broker to summon the Horses to the Paddock
     * @throws RemoteException Remote Exception
     */
    void summonHorsesToPaddock() throws RemoteException;

    /**
     * This method is used to start the race.<br>
     * It is invoked by the Broker to star the race.
     * @throws RemoteException Remote Exception
     */
    void startTheRace() throws RemoteException;

    /**
     * This method is used to by the broker to report the results
     * @throws RemoteException Remote Exception
     */
    void reportResults() throws RemoteException;

    /**
     * This method is used by the winner to entertain the guests
     * @return EntertainTheGuests
     * @throws RemoteException Remote Exception
     */
    EntertainTheGuests entertainTheGuests() throws RemoteException;

    /**
     * This method is used to wake up the spectator after all horses have reached the paddock
     * @throws RemoteException Remote Exception
     */
    void proceedToPaddock() throws RemoteException;

    /**
     * This method is used to know the current state of the Spectators, which will be waiting to start a race
     * @param specId Spectator ID
     * @return WaitForNextRace
     * @throws RemoteException Remote Exception
     */
    WaitForNextRace waitForNextRace(int specId) throws RemoteException;

    /**
     * This method will be used by the Spectators to wake up the broker after they have finished evaluating the horses.
     * @throws RemoteException Remote Exception
     */
    void goCheckHorses() throws RemoteException;

    /**
     * This method will be used by the Spectator to start watching a race.
     * @param specId Spectator ID
     * @return GoWatchTheRace
     * @throws RemoteException Remote Exception
     */
    GoWatchTheRace goWatchTheRace(int specId) throws RemoteException;

    /**
     * This method will be used by the Spectator to relax after all the races are finished
     * @param specId Spectator ID
     * @return RelaxABit
     * @throws RemoteException Remote Exception
     */
    RelaxABit relaxABit(int specId) throws RemoteException;

    /**
     * This method will tell whether last horse has already crossed the finishing line.
     * @throws RemoteException Remote Exception
     */
    void lastHorseCrossedLine() throws RemoteException;

    /**
     * Method used by several entities to send a shutdown signal to the Shared region server
     * @param clientID Id of the Client who asked the shutdown
     * @throws RemoteException Remote Exception
     */
    void shutdown(int clientID) throws RemoteException;
}
