package shared_regions;

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
     */
    SummonHorsesToPaddock summonHorsesToPaddock(int k, int totalAgility) throws RemoteException;

    /**
     * Method used by the HorseJockeys to proceed to Stable and wait for the next race
     */
    ProceedToStable proceedToStable(int agility, int hjNumber) throws RemoteException;

    /**
     * Method used by the HorseJockeys when they finished running to proceed back to the Stable
     */
    ProceedToStable2 proceedToStable2(int hjNumber) throws RemoteException;
}
