package shared_regions;

import java.io.Serializable;

/**
 * This class specifies the attributes of a Bet
 */
public class BetAmount implements Serializable {
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
}
