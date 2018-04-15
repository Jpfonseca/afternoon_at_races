package shared_regions;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;

public interface GeneralInformationRepositoryInterface {
    void reportStatus();

    void setBrokerState(BrokerState brokerState);

    void setSpectatorState(SpectatorState state, int index);

    void setSpectatorMoney(int money, int index);

    void setRaceNumber(int raceNumber);

    void setHorseJockeyState(HorseJockeyState state, int index);

    void setHorseJockeyAgility(int agility, int index);

    void setTrackDistance(int[] d);

    void setSpectatorBet(int spectatorIndex, int betSelection, int betAmount);

    void setOdd(int horse, int odd);

    void setIterationStep(int horse, int iterationStep);

    void setCurrentPos(int horse, int position);

    void setStandingPos(int horse, int standing);
}
