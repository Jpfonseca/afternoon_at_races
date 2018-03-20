package shared_regions;
import entities.*;

public class Paddock{
    /*Horse_Jockey*/
    boolean proceedToThePaddock1(HorseJockey horse){
        //check if it’s the last horse

        return false;
    }
    void proceedToThePaddock2(HorseJockey horse){
        //Muda de estado ->AT_THE_PADDOCK
        // Bloqueia o Horse/Jockey  em waitBeingChecked
    }

    /*Spectator*/

    boolean goCheckHorses(){
        //checks if the horse is the last to enter the paddock

        return false;
    }
    void goCheckHorses2(boolean last){
        //Sends spectator to paddock(sleeps ??)
        //If last spectator :
        // Acordar o Horse/Jockey que estão em waitBeingChecked (levando a que eles se movam para a rt)
    }

}