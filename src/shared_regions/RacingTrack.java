package shared_regions;
import entities.*;

/**
 *This class specifies the methods that will be executed on the Racing Track .
 */
public class RacingTrack{
    public void proceedToStartLine(int hj_number){
        // sends horse_jockey to the start_Line
        // bloqueia em  waitForA
    }

    public void makeAMove(int hj_number){
        // makeAMove
        //bloqueia a andar e acorda o seguinte se o mesmo não tiver passado linha de corrida
    }

    public boolean hasFinishLineBeenCrossed(int hj_number){
        // Verify line crossed
        return false;
    }

}