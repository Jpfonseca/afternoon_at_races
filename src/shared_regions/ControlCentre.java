package shared_regions;
import entities.*;
/**
 * This class specifies the methods that will be executed on the Control Centre and the Watching Stand .
 */
public class ControlCentre{
    public void summonHorsesToPaddock(int k){
        // Mudar o estado -> ANNOUNCING_NEXT_RACE
        // bloquear em waitForSpectatorEvaluation
    }

    public void startTheRace(int k){
        // Mudar o estado -> SUPERVISING_THE_RACE
        // bloquear em waitForRaceToFinish
    }

    public void reportResults(int k){
        // Reports results
    }

    public void entertainTheGuests(){
        // Waiting for childs to die
    }
    public void proceedToPaddock(int hj_number){
        // Wakes up Spectator that is in CCWS
    }

    public boolean waitForNextRace(){
        // Mudar o estado -> WAITING_FOR_A_RACE_TO_START
        // Block waitForRaceToStart (while(!raceStart && races<K-1))
        return false;
    }
    public boolean goCheckHorses(){
        // Actualiza waitForSpectatorEvaluation
        // Acorda Broker
        return false;
    }
    public void goWatchTheRace(){
        // Muda o estado -> WATCHING_A_RACE
        // Bloquear em waitForResults
    }

    public void relaxABit(){
        // muda o estado -> CELEBRATING
        // Preparar para terminar thread
    }
}