package entities;

/**
 * This enumerate specifies the different HorseJockey states.
 */
public enum HorseJockeyState {

    /** blocking state
    * the pair horse / jockey is waken up by one of the following operations of the
    * broker: summonHorsesToPaddock, during the races, or entertainTheGuests, at
    * the end */
    AT_THE_STABLE("ATS"),

    /** blocking state
    * the pair horse / jockey is waken up by the operation goCheckHorses of the last
    * spectator to reach the paddock */
    AT_THE_PADDOCK("ATP"),

    /** blocking state
    * the pair horse / jockey is waken up by the operation startTheRace of the
    * broker (the first) or by the operation makeAMove of another horse / jockey
    * pair */
    AT_THE_START_LINE("ASL"),

    /** blocking state with transition
    * the pair horse / jockey blocks after carrying out a position increment, unless he crosses
    * the finishing line; he always wakes up the next pair horse / jockey that has not completed
    * the race yet */
    RUNNING("RUN"),

    /** transition state */
    AT_THE_FINNISH_LINE("ATFL");

    private String shortName;

    HorseJockeyState(String s){
        this.shortName=s;
    }
    public String getShortName() {
        return shortName;
    }

    public static HorseJockeyState longName(String shortName) {
        for(HorseJockeyState e : HorseJockeyState.values())
            if(shortName.equals(e.getShortName()))
                return e;

        return null;
    }
}
