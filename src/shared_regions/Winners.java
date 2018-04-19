package shared_regions;

import java.io.Serializable;

/**
 * This class specified all the attributes related to the HorseJockey Winners of the current race
 */
public class Winners implements Serializable {
    /**
     * Standing position after finishing the race
     * @serial standing
     */
    int standing=0;
    /**
     * Position in the race track
     * @serial position
     */
    int position=0;
    /**
     * Amount of iterations needed to cross the finish line
     * @serial iteration
     */
    int iteration=0;
}
