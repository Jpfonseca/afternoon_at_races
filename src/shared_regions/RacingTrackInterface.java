package shared_regions;

public interface RacingTrackInterface {

    void startTheRace(int k);

    void proceedToStartLine(int hj_number);

    void makeAMove(int hj_number);

    boolean hasFinishLineBeenCrossed();

    Winners[] reportResults();
}
