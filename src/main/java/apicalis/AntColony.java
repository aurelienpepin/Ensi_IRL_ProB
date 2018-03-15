package apicalis;

import de.prob.statespace.State;
import java.util.List;

/**
 * Modelling of the colony of Pachycondyla Apicalis.
 * TODO: Description.
 * 
 * @author Aurélien Pepin
 */
public class AntColony {
    
    /**
     * List of hunting ants in the colony.
     */
    private List<Ant> ants;
    
    /**
     * General amplitude for the colony.
     * Should be in [0; 1].
     */
    private float amplitude;
    
    
    /**
     * CONSTRUCTOR. Initialize a list of n ants.
     * @param n     Number of ants
     */
    public AntColony(int n) {
        throw new UnsupportedOperationException("TODO: AntColony@constructor");
    }
    
    
    /**
     * OPERATOR. Random operator (O_rand).
     * @return A random point s of the search space S.
     */
    public State opRand() {
        // Random can be constructed through the <anyEvent> method!
        // How deep should we go?
        throw new UnsupportedOperationException("TODO: AntColony@opRand");
    }
    
    /**
     * OPERATOR. Neighborhood operator (O_explo).
     * @param s The first point, already visited.    
     * @return  A new point in the neighborhood of s.
     */
    public State opExplo(State s) {
        throw new UnsupportedOperationException("TODO: AntColony@opExplo");
    }
}
