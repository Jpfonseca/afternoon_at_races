package interfaces;

import shared_regions.RMIReply.ProceedToStable;
import shared_regions.RMIReply.ProceedToStable2;
import shared_regions.RMIReply.SummonHorsesToPaddock;

import java.rmi.Remote;
import java.rmi.RemoteException;
//import shared_regions.RMIReply.*;

/**
 * StableInterface
 * Used to Interface the Stable Server with the "public"
 */
public interface StableInterface extends Remote {

    /**
     * Method used by the Broker to summon the horses to Paddock
     * @param k number of current race
     * @param totalAgility Total agility
     * @return SummonHorsesToPaddock
     * @throws RemoteException Remote Exception
     */
    SummonHorsesToPaddock summonHorsesToPaddock(int k, int totalAgility) throws RemoteException;

    /**
     * Method used by the HorseJockeys to proceed to Stable and wait for the next race
     * @param agility Agility
     * @param hjNumber HorseJockey Number
     * @return ProceedToStable
     * @throws RemoteException Remote Exception
     */
    ProceedToStable proceedToStable(int agility, int hjNumber) throws RemoteException;

    /**
     * Method used by the HorseJockeys when they finished running to proceed back to the Stable
     * @param hjNumber HorseJockey Number
     * @return ProceedToStable2
     * @throws RemoteException Remote Exception
     */
    ProceedToStable2 proceedToStable2(int hjNumber) throws RemoteException;


    /**
     * Method used by several entities to send a shutdown signal to the Shared region server
     * @param clientID Id of the Client who asked the shutdown
     * @throws RemoteException Remote Exception
     */
    void shutdown(int clientID) throws RemoteException;
}
