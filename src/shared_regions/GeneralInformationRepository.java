package shared_regions;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import genclass.TextFile;
import genclass.GenericIO;

/**
 * General Repository
 */
public class GeneralInformationRepository{
    /**
     * Name of LogFile
     * @serialField logName
     */
    private String logName;
    /**
     * TextFile object to append text
     * @serialField log
     */
    private TextFile log;

    private BrokerState brokerState;
    private SpectatorState[] spectatorState;
    private int[] spectatorMoney;
    private int raceNumber;
    private int K;
    private int[] D;
    private BetAmount[] bet;
    private int[] odd;
    private int[] iterationStep;
    private int[] currentPos;
    private int[] standingPos;
    private HorseJockeyState[] horseJockeyState;
    private int[] hjAgility;
    private int N;
    private int M;

    public GeneralInformationRepository(String logName, int K, int N, int M) {
        log = new TextFile();

        this.N = N;
        this.M = M;
        this.brokerState = BrokerState.OPENING_THE_EVENT;
        this.spectatorState = new SpectatorState[N];
        this.spectatorMoney = new int[N];
        raceNumber = 0;
        this.K = K;
        this.D = new int[K];
        this.bet = new BetAmount[N];
        this.odd = new int[N];
        this.iterationStep = new int[N];
        this.currentPos = new int[N];
        this.standingPos = new int[N];
        this.horseJockeyState = new HorseJockeyState[M];
        this.hjAgility = new int[N];

        for (int i=0; i<N; i++){
            spectatorMoney[i] = 0;
            bet[i] = new BetAmount();
            odd[i] = -1;
            iterationStep[i] = -1;
            currentPos[i] = -1;
            standingPos[i] = 0;
            hjAgility[i]=0;
        }
        for (int i=0; i<K; i++) {
            D[i] = 0;
        }

        this.logName = "Afternoon_At_Races.log";
        if (!logName.equals(""))
            this.logName = logName;

        reportInitial();
    }

    private void reportInitial(){
/**
         AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem
MAN/BRK         SPECTATOR/BETTER              HORSE/JOCKEY PAIR at Race RN
  Stat  St0  Am0 St1  Am1 St2  Am2 St3  Am3 RN St0 Len0 St1 Len1 St2 Len2 St3 Len3
                                        Race RN Status
 RN Dist BS0  BA0 BS1  BA1 BS2  BA2 BS3  BA3  Od0 N0 Ps0 SD0 Od1 N1 Ps1 Sd1 Od2 N2 Ps2 Sd2 Od3 N3 Ps3 St3
  ####  ### #### ### #### ### #### ### ####  # ###  ##  ###  ##  ###  ##  ###  ##
  #  ##   #  ####  #  ####  #  ####  #  #### #### ##  ##  # #### ##  ##  # #### ##  ##  # #### ##  ##  #
*/

/**
 Legend:
 Stat - manager/broker state
 St# - spectator/better state (# - 0 .. 3)
 Am# - spectator/better amount of money she has presently (# - 0 .. 3)
 RN - race number
 St# - horse/jockey pair state in present race (# - 0 .. 3)
 Len# - horse/jockey pair maximum moving length per iteration step in present race (# - 0 .. 3)
 RN - race number
 Dist - race track distance in present race
 BS# - spectator/better bet selection in present race (# - 0 .. 3)
 BA# - spectator/better bet amount in present race (# - 0 .. 3)
 Od# - horse/jockey pair winning probability in present race (# - 0 .. 3)
 N# - horse/jockey pair iteration step number in present race (# - 0 .. 3)
 Ps# - horse/jockey pair track position in present race (# - 0 .. 3)
 SD# - horse/jockey pair standing at the end of present race (# - 0 .. 3)
 */
        if (!log.openForWriting(null,logName)) {
            GenericIO.writelnString("Operation fails on creating the " + logName + " file!");
            System.exit(1);
        }
        log.writelnString("         AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem\n"
                + "MAN/BRK         SPECTATOR/BETTER              HORSE/JOCKEY PAIR at Race RN\n"
                + "  Stat  St0  Am0 St1  Am1 St2  Am2 St3  Am3 RN St0 Len0 St1 Len1 St2 Len2 St3 Len3\n"
                + "                                        Race RN Status\n"
                + " RN Dist BS0  BA0 BS1  BA1 BS2  BA2 BS3  BA3  Od0 N0 Ps0 SD0 Od1 N1 Ps1 Sd1 Od2 N2 Ps2 Sd2 Od3 N3 Ps3 St3\n\n"
        );

        if (!log.close()) {
            GenericIO.writelnString("Operation fails on creating the " + logName + " file!");
            System.exit(1);
        }

        //reportStatus();
    }

    public synchronized void reportStatus(){
/**
         AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem
MAN/BRK         SPECTATOR/BETTER              HORSE/JOCKEY PAIR at Race RN
  Stat  St0  Am0 St1  Am1 St2  Am2 St3  Am3 RN St0 Len0 St1 Len1 St2 Len2 St3 Len3
                                        Race RN Status
 RN Dist BS0  BA0 BS1  BA1 BS2  BA2 BS3  BA3  Od0 N0 Ps0 SD0 Od1 N1 Ps1 Sd1 Od2 N2 Ps2 Sd2 Od3 N3 Ps3 St3
  ####  ### #### ### #### ### #### ### ####  # ###  ##  ###  ##  ###  ##  ###  ##
  #  ##   #  ####  #  ####  #  ####  #  #### #### ##  ##  # #### ##  ##  # #### ##  ##  # #### ##  ##  #
*/


        StringBuilder textToAppend = new StringBuilder(" ");

        if (!log.openForAppending(".", logName)) {
            GenericIO.writelnString("Operation fails on creating the " + logName + " file1!");
            System.exit(1);
        }

        switch (brokerState){
            case OPENING_THE_EVENT:
                textToAppend.append(" OPTE ");
                break;
            case ANNOUNCING_NEXT_RACE:
                textToAppend.append(" ANNR ");
                break;
            case WAITING_FOR_BETS:
                textToAppend.append(" WAFB ");
                break;
            case SUPERVISING_THE_RACE:
                textToAppend.append(" SUTR ");
                break;
            case SETTLING_ACCOUNTS:
                textToAppend.append(" SETA ");
                break;
            case PLAYING_HOST_AT_THE_BAR:
                textToAppend.append(" PHAB ");
                break;
            default:
                textToAppend.append(" ---- ");
                break;
        }

        for (int i=0; i<M; i++){
            if (spectatorState[i]!=null)
                switch (spectatorState[i]){
                    case WAITING_FOR_A_RACE_TO_START:
                        textToAppend.append(" WFR");
                        break;
                    case APPRAISING_THE_HORSES:
                        textToAppend.append(" ATH");
                        break;
                    case PLACING_A_BET:
                        textToAppend.append(" PAB");
                        break;
                    case WATCHING_A_RACE:
                        textToAppend.append(" WAR");
                        break;
                    case COLLECTING_THE_GAINS:
                        textToAppend.append(" CTG");
                        break;
                    case CELEBRATING:
                        textToAppend.append(" CEL");
                        break;
                    default:
                        textToAppend.append(" ---");
                        break;
                }
            else
                textToAppend.append(" ---");

            if(spectatorMoney[i]<10)
                textToAppend.append("    ");
            else if(spectatorMoney[i]<100)
                textToAppend.append("   ");
            else if(spectatorMoney[i]<1000)
                textToAppend.append("  ");
            else
                textToAppend.append(" ");
            textToAppend.append(spectatorMoney[i]);
        }

        textToAppend.append("  ").append(raceNumber);

        for (int i=0; i<N; i++){
            if (horseJockeyState[i]!=null)
                switch (horseJockeyState[i]){
                    case AT_THE_STABLE:
                        textToAppend.append(" ATS ");
                        break;
                    case AT_THE_PADDOCK:
                        textToAppend.append(" ATP ");
                        break;
                    case AT_THE_START_LINE:
                        textToAppend.append(" ASL ");
                        break;
                    case RUNNING:
                        textToAppend.append(" RUN ");
                        break;
                    case AT_THE_FINNISH_LINE:
                        textToAppend.append(" AFL ");
                        break;
                    default:
                        textToAppend.append(" --- ");
                        break;
                }
            else
                textToAppend.append(" --- ");


            if (hjAgility[i]==0)
                textToAppend.append("  - ");
            else if (hjAgility[i]<10)
                textToAppend.append("  ").append(hjAgility[i]).append(" ");
            else if(hjAgility[i]<100)
                textToAppend.append(" ").append(hjAgility[i]).append(" ");
        }

        log.writelnString(textToAppend.toString());

        textToAppend = new StringBuilder();

        textToAppend.append("  ").append(raceNumber);

        if (raceNumber==0)
            textToAppend.append("   - ");
        else if (D[raceNumber-1]<10)
            textToAppend.append("   ").append(D[raceNumber - 1]).append(" ");
        else if (D[raceNumber-1]<100)
            textToAppend.append("  ").append(D[raceNumber - 1]).append(" ");

        for (int i=0; i<M; i++){
            if (bet[i].horse_id < 0)
                textToAppend.append("  -");
            else
                textToAppend.append("  ").append(bet[i].horse_id);

            if (bet[i].bet < 0)
                textToAppend.append("     -");
            else if (bet[i].bet < 10)
                textToAppend.append("     ").append(bet[i].bet);
            else if (bet[i].bet < 100)
                textToAppend.append("    ").append(bet[i].bet);
            else if (bet[i].bet < 1000)
                textToAppend.append("   ").append(bet[i].bet);
            else if (bet[i].bet < 10000)
                textToAppend.append("  ").append(bet[i].bet);
        }

        for (int i=0; i<N; i++){
            /*
            Od# - horse/jockey pair winning probability in present race (# - 0 .. 3)
            N# - horse/jockey pair iteration step number in present race (# - 0 .. 3)
            Ps# - horse/jockey pair track position in present race (# - 0 .. 3)
            SD# - horse/jockey pair standing at the end of present race (# - 0 .. 3)
            " #### ##  ##  # #### ##  ##  # #### ##  ##  # #### ##  ##  #"
            */

            if (odd[i]<0)
                textToAppend.append("    -");
            else if (odd[i]<10)
                textToAppend.append("    ").append(odd[i]);
            else if (odd[i]<100)
                textToAppend.append("   ").append(odd[i]);
            else if (odd[i]<1000)
                textToAppend.append("  ").append(odd[i]);
            else if (odd[i]<10000)
                textToAppend.append(" ").append(odd[i]);

            if (iterationStep[i]==-1)
                textToAppend.append("  -");
            else if (iterationStep[i]<10)
                textToAppend.append("  ").append(iterationStep[i]);
            else if (iterationStep[i]<100)
                textToAppend.append(" ").append(iterationStep[i]);

            if (currentPos[i]==-1)
                textToAppend.append("   -");
            else if (currentPos[i]<10)
                textToAppend.append("   ").append(currentPos[i]);
            else if(currentPos[i]<100)
                textToAppend.append("  ").append(currentPos[i]);
            else if(currentPos[i]<1000)
                textToAppend.append(" ").append(currentPos[i]);


            textToAppend.append("  ").append(standingPos[i]);
        }

        log.writelnString(textToAppend.toString());

        if (!log.close()) {
            GenericIO.writelnString("Operation fails on creating the " + logName + " file!");
            System.exit(1);
        }
    }

    public synchronized void setBrokerState(BrokerState brokerState) {
        this.brokerState = brokerState;
        reportStatus();
    }

    public synchronized void setSpectatorState(SpectatorState state, int index) {
        this.spectatorState[index] = state;
        reportStatus();
    }

    public synchronized void setSpectatorMoney(int money, int index) {
        this.spectatorMoney[index] = money;
    }

    public synchronized void setRaceNumber(int raceNumber) {
        this.raceNumber = raceNumber;
        //reportStatus();
    }

    public synchronized void setHorseJockeyState(HorseJockeyState state, int index) {
        this.horseJockeyState[index] = state;
        reportStatus();
    }

    public synchronized void setHorseJockeyAgility(int agility, int index) {
        this.hjAgility[index] = agility;
    }

    public synchronized void setTrackDistance(int[] d) {
        D = d;
    }

    public synchronized void setSpectatorBet(int spectatorIndex, int betSelection, int betAmmount) {
        this.bet[spectatorIndex].horse_id = betSelection;
        this.bet[spectatorIndex].bet = betAmmount;
        reportStatus();
    }

    public synchronized void setOdd(int horse, int odd) {
        this.odd[horse] = odd;
    }

    public synchronized void setIterationStep(int horse, int iterationStep) {
        this.iterationStep[horse] = iterationStep;
    }

    public void setCurrentPos(int horse, int position) {
        this.currentPos[horse] = position;
        //reportStatus();
    }

    public synchronized void setCurrentPosNull(int horse) {
        this.currentPos[horse] = -1;
    }

    public synchronized void setCurrentPosZero(int horse) {
        this.currentPos[horse] = 0;
    }

    public synchronized void setStandingPos(int horse, int standing) {
        this.standingPos[horse] = standing;
    }
}