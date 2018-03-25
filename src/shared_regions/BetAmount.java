package shared_regions;

/**
 * This class specifies the attributes of a Bet
 */
public class BetAmount {
    /**
     * Amount to bet
     * @serial bet
     */
    int bet = -1;
    /**
     * Horse to Bet in
     * @serial horse_id
     */
    int horse_id = -1;
    /**
     * Index of Spectator that bet
     * @serial spectator_id
     */
    int spectator_id = -1;
}
