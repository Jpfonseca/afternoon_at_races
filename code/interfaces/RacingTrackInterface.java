package interfaces;


import shared_regions.Winners;

import shared_regions.RMIReply.StartTheRace;
import shared_regions.RMIReply.ProceedToStartLine;
import shared_regions.RMIReply.HasFinishLineBeenCrossed;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RacingTrackInterface
 * Used to Interface the RacingTrack Server with the "public"
 */
public interface RacingTrackInterface extends Remote {

    /**
     * Method used by the Broker to start the race
     * @param k current race number
     * @return StartTheRace
     * @throws RemoteException Remote Exception
     */
    StartTheRace startTheRace(int k) throws RemoteException;

    /**
     * Method used by the HorseJockeys to proceed to the start line
     * @param hj_number HorseJockey index number
     * @return ProceedToStartLine
     * @throws RemoteException Remote Exception
     */
    ProceedToStartLine proceedToStartLine1(int hj_number) throws RemoteException;

    /**
     * Method used by the HorseJockeys to proceed to the start line
     * @param hj_number HorseJockey index number
     * @return ProceedToStartLine
     * @throws RemoteException Remote Exception
     */
    ProceedToStartLine proceedToStartLine2(int hj_number) throws RemoteException;

    /**
     * Method used by every HorseJockey to make a move in the Racing track while running
     * @param hj_number HorseJockey index number
     * @param hj_agility HorseJockey Agility
     * @throws RemoteException Remote Exception
     */
    void makeAMove(int hj_number,int hj_agility) throws RemoteException;

    /**
     * Method used by the HorseJockeys to know if they have crossed the finish line
     * @return HasFinishLineBeenCrossed
     * @throws RemoteException Remote Exception
     */
    HasFinishLineBeenCrossed hasFinishLineBeenCrossed() throws RemoteException;

    /**
     * Method used to return an array with all the winning Spectators information
     * @return Winners[]
     * @throws RemoteException Remote Exception
     */
    Winners[] reportResults() throws RemoteException;

    /**
     * Method used by several entities to send a shutdown signal to the Shared region server
     * @param clientID Id of the Client who asked the shutdown
     * @throws RemoteException Remote Exception
     */
    void shutdown(int clientID) throws RemoteException;

}
