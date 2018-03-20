package shared_regions;
import entities.*;

public class ControlCentre{
    void summonHorsesToPaddock(int k){
        // Mudar o estado -> ANNOUNCING_NEXT_RACE
        // bloquear em waitForSpectatorEvaluation
    }

    void startTheRace(int k){
        // Mudar o estado -> SUPERVISING_THE_RACE
        // bloquear em waitForRaceToFinish
    }

    void reportResults(int k){
        // Reports results
    }

    void entertainTheGuests(){
        // Waiting for childs to die
    }

    void proceedToPaddock(HorseJockey horse_jockey){
        // Wakes up Spectator that is in CCWS
    }

    boolean waitForNextRace(){
        // Mudar o estado -> WAITING_FOR_A_RACE_TO_START
        // Block waitForRaceToStart (while(!raceStart && races<K-1))
        return false;
    }
    boolean goCheckHorses(){
        // Actualiza waitForSpectatorEvaluation
        // Acorda Broker
        return false;
    }
    void goWatchtheRace(){
        // Muda o estado -> WATCHING_A_RACE
        // Bloquear em waitForResults
    }

    void relaxAbit(){
        // muda o estado -> CELEBRATING
        // Preparar para terminar thread
    }
}