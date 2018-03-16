package apicalis;

import de.prob.statespace.State;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
     * Previous explored site.
     */
    private HuntingSite previousSite;
    
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
     * The colony of the ants.
     */
    private AntColony colony;
    
    
    /**
     * CONSTRUCTOR. Initialize an ant.
     * @param patience      P_locale
     * @param memorySize    p
     * @param colony        Colony (nest, etc.)
     */
    public Ant(int patience, int memorySize, AntColony colony) { 
        if (patience < 1 || memorySize < 1 || colony == null)
            throw new IllegalArgumentException("No negative parameters for an ant.");
        
        this.patience = patience;
        this.memorySize = memorySize;
        
        this.sites = Arrays.asList(new HuntingSite[memorySize]); // Immutable
        this.currentSize = 0;
        this.previousSite = null;   // Nothing explored at the beginning
        
        // We keep a reference of the colony
        // so it's easier to get the current position of the nest.
        this.colony = colony;
    }
    
    /**
     * ALGORITHM. Local behaviour of the ant.
     */
    public void search() {
        // First, find new hunting sites if needed.
        if (currentSize < memorySize) {
            State huntingState = AntColony.opExplo(colony.getNest());
            HuntingSite huntingSite = new HuntingSite(huntingState);
            
            this.sites.set(currentSize, huntingSite);
            currentSize++;
        } else {
            HuntingSite hSite = this.getPreviousSite();
            
            if (hSite.getFails() > 0) {
                hSite = this.getRandomSite();
            }
            
            /**
             * LOCAL EXPLORATION.
             */
            // TODO
        }
        
        throw new UnsupportedOperationException("TODO: Ant@search");    
    }
    
    /**
     * Get the previous site or draw it randomly.
     * @return  The previous site.
     */
    private HuntingSite getPreviousSite() {
        // Get a random site if it is the first exploration.
        if (previousSite == null)
            previousSite = this.getRandomSite();
        
        // Return either the previous explored site or the random one.
        return previousSite;
    }
    
    /**
     * Get a random site from the hunting sites.
     * @return  The chosen random site.
     */
    private HuntingSite getRandomSite() {
        return sites.get((new Random()).nextInt(sites.size()));
    }
}
