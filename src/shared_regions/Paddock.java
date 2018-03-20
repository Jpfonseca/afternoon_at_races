package shared_regions;
import entities.*;

/**
 * This class specifies the methods that will be executed on the Paddock .
 */
public class Paddock{
    /**
     * Horse_Jockey
     * */
    public boolean proceedToPaddock1(HorseJockey horse){
        //check if it’s the last horse

        return false;
    }
    public void proceedToPaddock2(HorseJockey horse){
        //Muda de estado ->AT_THE_PADDOCK
        // Bloqueia o Horse/Jockey  em waitBeingChecked
    }

    /**Spectator
     *
     */

    public boolean goCheckHorses1(){
        //checks if the horse is the last to enter the paddock

        return false;
    }
    public void goCheckHorses2(boolean last){
        //Sends spectator to paddock(sleeps ??)
        //If last spectator :
        // Acordar o Horse/Jockey que estão em waitBeingChecked (levando a que eles se movam para a rt)
    }

}