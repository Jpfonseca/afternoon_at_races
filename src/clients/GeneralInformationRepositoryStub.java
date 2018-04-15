package clients;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import shared_regions.GeneralInformationRepositoryInterface;

public class GeneralInformationRepositoryStub implements GeneralInformationRepositoryInterface {

    @Override
    public void reportStatus() {

    }

    @Override
    public void setBrokerState(BrokerState brokerState) {

    }

    @Override
    public void setSpectatorState(SpectatorState state, int index) {

    }

    @Override
    public void setSpectatorMoney(int money, int index) {

    }

    @Override
    public void setRaceNumber(int raceNumber) {

    }

    @Override
    public void setHorseJockeyState(HorseJockeyState state, int index) {

    }

    @Override
    public void setHorseJockeyAgility(int agility, int index) {

    }

    @Override
    public void setTrackDistance(int[] d) {

    }

    @Override
    public void setSpectatorBet(int spectatorIndex, int betSelection, int betAmount) {

    }

    @Override
    public void setOdd(int horse, int odd) {

    }

    @Override
    public void setIterationStep(int horse, int iterationStep) {

    }

    @Override
    public void setCurrentPos(int horse, int position) {

    }

    @Override
    public void setStandingPos(int horse, int standing) {

    }
}
