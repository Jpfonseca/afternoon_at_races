package shared_regions;
import com.sun.xml.internal.bind.v2.TODO;
import entities.*;

/**
 *This class specifies the methods that will be executed on the Racing Track .
 */
public class RacingTrack{

    private boolean waitForA=true;
    private boolean lastHorseCrossed=true;
    /**
     *  Total HorseJockeys in RacingTrack (FIFO)
     *  @serialField queueHJ
     */
    private int totalHJ=0;

    public synchronized void startTheRace(){
        // Mudar o estado -> SUPERVISING_THE_RACE
        // bloquear em waitForRaceToFinish

        ((Broker)Thread.currentThread()).setBrokerState((BrokerState.SUPERVISING_THE_RACE));

        waitForA=false;
        notifyAll();
    }

    public synchronized void proceedToStartLine(){
        // sends horse_jockey to the start_Line
        // bloqueia em  waitForA

        // DID WE FORGET TO UPDATE STATE -> AT_THE_START_LINE ???!???!???
        // SHOULD THIS ALSO HAVE 1 METHOD IN PD DUE TO BROKER startTheRace ???!???!??? (Option 1)
        // OR SHOULD BROKER COME HERE TO startTheRace ???!???!??? (* Using Option 2 *)

        ((HorseJockey)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_START_LINE));

        totalHJ++;

        while (waitForA)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("HorseJockey rt.proceedToStartLine() InterruptedException: "+e);
            }

        // SHOULD THIS LOOP ONLY ALLOW 1 HJ TO makeAMove ???!???!???
        /*
        the pair horse / jockey is waken up by the operation startTheRace of the
        broker (the first) or by the operation makeAMove of another horse / jockey
        pair
         */
    }

    public void makeAMove() {
        // makeAMove
        //bloqueia a andar e acorda o seguinte se o mesmo não tiver passado linha de corrida

        /*if (waitForA) {
            waitForA = false;
            notifyAll();
        }*/

        // TODO
        // MAKING MOVES

        try {
            Thread.sleep(10);   // Temporary makeAMove
        } catch (Exception e){
            System.out.println("HorseJockey rt.makeAMove() Exception: "+e);
        }
    }

    public synchronized boolean hasFinishLineBeenCrossed(){
        // Verify line crossed

        // DID WE FORGET TO UPDATE STATE -> AT_THE_FINISH_LINE ???!???!???

        // TODO
        // VERIFY FINISH LINE CROSSED

        ((HorseJockey)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_FINNISH_LINE));

        totalHJ--;
        if (totalHJ==0) {
            waitForA = true; // variable reset

        }
        notifyAll();
        return true;
    }

    public boolean hasLastHorseCrossed() {
        return lastHorseCrossed;
    }
}