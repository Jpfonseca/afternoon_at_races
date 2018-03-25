package shared_regions;
import entities.*;

/**
 *This class specifies the methods that will be executed on the Racing Track .
 */
public class RacingTrack{

    private boolean waitForA=true;
    private boolean lastHorseCrossed=false;
    private int currentRace;
    private int[] fifo;
    /**
     *  Total HorseJockeys in RacingTrack (FIFO)
     *  @serialField queueHJ
     */
    private int totalHJ=0;
    private int[] D;
    private int[] HJPos;

    private Winners[] winners;

    public RacingTrack(int K) {
        this.currentRace = 0;
        this.D = new int[K];
        this.fifo = new int[4];
        this.HJPos = new int[4];
        this.winners = new Winners[4];

        for (int i=0; i<K; i++)
            D[i] = (int) (Math.random() * 50 + 50);
        for (int i=0; i<4; i++) {
            HJPos[i] = 0;
            winners[i]=new Winners();
        }
    }

    public synchronized void startTheRace(int k){
        // Mudar o estado -> SUPERVISING_THE_RACE
        // bloquear em waitForRaceToFinish

        ((Broker)Thread.currentThread()).setBrokerState((BrokerState.SUPERVISING_THE_RACE));

        this.currentRace = k;
        this.lastHorseCrossed = false;

        waitForA=false;
        notifyAll();
    }

    public synchronized void proceedToStartLine(int hj_number){
        // sends horse_jockey to the start_Line
        // bloqueia em  waitForA

        // DID WE FORGET TO UPDATE STATE -> AT_THE_START_LINE ???!???!???
        // SHOULD THIS ALSO HAVE 1 METHOD IN PD DUE TO BROKER startTheRace ???!???!??? (Option 1)
        // OR SHOULD BROKER COME HERE TO startTheRace ???!???!??? (* Using Option 2 *)

        ((HorseJockey)Thread.currentThread()).setHjState((HorseJockeyState.AT_THE_START_LINE));


        fifo[totalHJ++] = hj_number;

        while (waitForA || fifo[0]!=hj_number)
            try {
                wait();
            } catch (Exception e) {
                System.out.println("HorseJockey rt.proceedToStartLine() Exception: "+e);
            }

        // SHOULD THIS LOOP ONLY ALLOW 1 HJ TO makeAMove ???!???!???
        /*
        the pair horse / jockey is waken up by the operation startTheRace of the
        broker (the first) or by the operation makeAMove of another horse / jockey
        pair
         */

    }

    private void fifoUpdate(){
        int temp = fifo[0];
        for (int i=0; i<fifo.length-1; i++)
            fifo[i]=fifo[i+1];
        fifo[fifo.length-1]=temp;
    }

    public synchronized void makeAMove(int hj_number) {
        // makeAMove
        //bloqueia a andar e acorda o seguinte se o mesmo não tiver passado linha de corrida

        // TODO
        // MAKING MOVES

        HJPos[hj_number] += (int)(Math.random()*((HorseJockey)Thread.currentThread()).getAgility()+1);
System.out.println("Cavalo:" +hj_number+" Posição:"+HJPos[hj_number]);

        fifoUpdate();
        notifyAll();

        while(fifo[0]!=hj_number)
            try {
                wait();
            } catch (Exception e) {
                System.out.println("HorseJockey rt.makeAMove() Exception: "+e);
            }


    }

    public synchronized boolean hasFinishLineBeenCrossed(){

        if (HJPos[fifo[0]] < D[currentRace-1])
            return false;

        HorseJockey horseJockey=((HorseJockey)Thread.currentThread());
        horseJockey.setHjState((HorseJockeyState.AT_THE_FINNISH_LINE));

        winners[fifo[0]].position=HJPos[fifo[0]];
        // TODO - Make iterations
        winners[fifo[0]].iteration=0;
        winners[fifo[0]].hj_id=horseJockey.getHj_number();

        if (fifo.length>1) {
            int[] temp = new int[fifo.length-1];
            System.arraycopy(fifo, 1, temp, 0, fifo.length - 1);
            fifo = temp;
        }

        totalHJ--;
        if (totalHJ==0) {
            waitForA = true; // variable reset
            lastHorseCrossed=true;
            fifo = new int[4];
            HJPos = new int[4];
        }
        notifyAll();
        return true;
    }

    public Winners[] reportResults(int currentRace){
        return winners;
    }

    public boolean hasLastHorseCrossed() {
        return lastHorseCrossed;
    }


}