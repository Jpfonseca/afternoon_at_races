package clients;

import shared_regions.BettingCentreInterface;
import shared_regions.Winners;

public class BettingCentreStub implements BettingCentreInterface {
    @Override
    public void acceptTheBets() {

    }

    @Override
    public boolean areThereAnyWinners(Winners[] winners) {
        return false;
    }

    @Override
    public void honourTheBets() {

    }

    @Override
    public void placeABet() {

    }

    @Override
    public boolean haveIWon() {
        return false;
    }

    @Override
    public void goCollectTheGains() {

    }

    @Override
    public void setHorseJockeyOdd() {

    }
}
