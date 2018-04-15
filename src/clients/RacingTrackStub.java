package clients;

import shared_regions.RacingTrackInterface;
import shared_regions.Winners;

public class RacingTrackStub implements RacingTrackInterface {
    @Override
    public void startTheRace(int k) {

    }

    @Override
    public void proceedToStartLine(int hj_number) {

    }

    @Override
    public void makeAMove(int hj_number) {

    }

    @Override
    public boolean hasFinishLineBeenCrossed() {
        return false;
    }

    @Override
    public Winners[] reportResults() {
        return new Winners[0];
    }
}
