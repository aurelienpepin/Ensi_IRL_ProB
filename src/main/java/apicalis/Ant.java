package apicalis;

import de.prob.statespace.State;
import java.util.Arrays;
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
    private List<HuntingSite> sites;
    
    /**
     * Memory size for hunting sites.
     * Should be > 0.
     */
    private final int memorySize;
    
    /**
     * Number of current sites.
     */
    private int currentSize;
    
    
    /**
     * CONSTRUCTOR. Initialize an ant.
     * @param patience      P_locale
     * @param memorySize    p
     */
    public Ant(int patience, int memorySize) { 
        if (patience < 1 || memorySize < 1)
            throw new IllegalArgumentException("No negative parameters for an ant.");
        
        this.patience = patience;
        this.memorySize = memorySize;
        
        this.sites = Arrays.asList(new HuntingSite[memorySize]); // Immutable
        this.currentSize = 0;
        
        throw new UnsupportedOperationException("TODO: Ant@constructor (VERIFICATIONS)");
    }
    
    /**
     * ALGORITHM. Local behaviour of the ant.
     */
    public void search() {
        // First, find new hunting sites if needed.
        if (currentSize < memorySize) {
            HuntingSite huntingSite = new HuntingSite(null);
            
            this.sites.set(currentSize, huntingSite);
            currentSize++;
            
            throw new UnsupportedOperationException("TODO: Ant@search (EXPLO)");
        }
        
        throw new UnsupportedOperationException("TODO: Ant@search");    
    }
}
