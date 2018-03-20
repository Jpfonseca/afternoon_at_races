package shared_regions;
import entities.*;

/**
 *This class specifies the methods that will be executed on the Racing Track .
 */
public class RacingTrack{
    public void proceedToTheStartLine(HorseJockey horse){
        // sends horse_jockey to the start_Line
        // bloqueia em  waitForA
    }

    public void makeAmove(HorseJockey horse){
        // makeAMove
        //bloqueia a andar e acorda o seguinte se o mesmo não tiver passado linha de corrida
    }

    public boolean hasFinishLineBeenCrossed(HorseJockey horse){
        // Verify line crossed
        return false;
    }

}