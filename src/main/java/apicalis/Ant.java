package apicalis;

import de.prob.statespace.State;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelling of a Pachycondyla Apicalis.
 * TODO: Description.
 * 
 * @author Aurélien Pepin
 */
public class Ant {

    /**
     * Local patience for the ant.
     * Should be > 0.
     */
    private int patience;
    
    /**
     * List of hunting sites.
     */
    private List<State> sites;
    
    /**
     * Memory size for hunting sites.
     * Should be > 0.
     */
    private int memorySize;
    
    
    /**
     * CONSTRUCTOR. Initialize an ant.
     * @param patience      P_locale
     * @param memorySize    p
     */
    public Ant(int patience, int memorySize) {
        throw new UnsupportedOperationException("TODO: Ant@constructor (VERIFICATIONS)");
        
        // this.patience = patience;
        // this.memorySize = memorySize;
        // this.sites = new ArrayList<>(memorySize);
    }
}
