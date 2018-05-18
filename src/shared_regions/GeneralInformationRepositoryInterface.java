package shared_regions;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;

import java.rmi.Remote;

/**
 * GeneralInformationRepositoryInterface
 * Used to instantiate GeneralInformationRepository Server with the "public"
 */
public interface GeneralInformationRepositoryInterface extends Remote {

    /**
     * This method is used to print a pair of Log Lines into a file containing the current snapshot of the current simulation status
     */
    void reportStatus();

    /**
     * Used to set the Broker state
     * @param brokerState Broker state
     */
    void setBrokerState(BrokerState brokerState);

    /**
     * Used to set the Spectator State
     * @param state state to set
     * @param index index of the Spectator
     */
    void setSpectatorState(SpectatorState state, int index);

    /**
     * Used to set the Spectator amount of money in the wallet
     * @param money amount of money
     * @param index index of Spectator
     */
    void setSpectatorMoney(int money, int index);

    /**
     * Used to set the current race number
     * @param raceNumber race number
     */
    void setRaceNumber(int raceNumber);

    /**
     * Used to set the HorseJockey state
     * @param state state to set
     * @param index HorseJockey's index
     */
    void setHorseJockeyState(HorseJockeyState state, int index);

    /**
     * Used to set the HorseJockey agility
     * @param agility magility to set
     * @param index HorseJockey's index
     */
    void setHorseJockeyAgility(int agility, int index);

    /**
     * Used to set all the track distances
     * @param d distance
     */
    void setTrackDistance(int[] d);

    /**
     * Used to set the spectators bets
     * @param spectatorIndex Spectator's index
     * @param betSelection bet selection index
     * @param betAmount bet amount
     */
    void setSpectatorBet(int spectatorIndex, int betSelection, int betAmount);

    /**
     * Used to the HorseJockey odd
     * @param horse HorseJockey index
     * @param odd odd
     */
    void setOdd(int horse, int odd);

    /**
     * Used to set the iteration step
     * @param horse HorseJockey index
     * @param iterationStep iteration number
     */
    void setIterationStep(int horse, int iterationStep);

    /**
     * Used to set the current HorseJockey position in the race
     * @param horse HorseJockey index
     * @param position position
     */
    void setCurrentPos(int horse, int position);

    /**
     * Used to set the current HorseJockey standing position
     * @param horse HorseJockey index
     * @param standing standing position
     */
    void setStandingPos(int horse, int standing);
}
