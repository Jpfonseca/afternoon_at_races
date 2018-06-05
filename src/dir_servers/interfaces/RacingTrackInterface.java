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
     */
    StartTheRace startTheRace(int k) throws RemoteException;

    /**
     * Method used by the HorseJockeys to proceed to the start line
     * @param hj_number HorseJockey index number
     */
    ProceedToStartLine proceedToStartLine1(int hj_number) throws RemoteException;

    /**
     * Method used by the HorseJockeys to proceed to the start line
     * @param hj_number HorseJockey index number
     */
    ProceedToStartLine proceedToStartLine2(int hj_number) throws RemoteException;

    /**
     * Method used by every HorseJockey to make a move in the Racing track while running
     * @param hj_number HorseJockey index number
     */
    void makeAMove(int hj_number,int hj_agility) throws RemoteException;

    /**
     * Method used by the HorseJockeys to know if they have crossed the finish line
     * @return <b>true</b> if he has crossed or <b>false</b>, if he has not.
     */
    HasFinishLineBeenCrossed hasFinishLineBeenCrossed() throws RemoteException;

    /**
     * Method used to return an array with all the winning Spectators information
     * @return winners
     */
    Winners[] reportResults() throws RemoteException;
}
