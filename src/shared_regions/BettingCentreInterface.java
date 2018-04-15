package shared_regions;

public interface BettingCentreInterface {
    void acceptTheBets();

    boolean areThereAnyWinners(Winners[] winners);

    void honourTheBets();

    void placeABet();

    boolean haveIWon();

    void goCollectTheGains();

    void setHorseJockeyOdd();
}
