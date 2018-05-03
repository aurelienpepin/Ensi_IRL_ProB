package apicalis;

import apicalis.solutions.Observable;
import apicalis.solutions.PartialSolution;
import de.prob.statespace.State;
import de.prob.statespace.Trace;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Modelling of a Pachycondyla Apicalis.
 * TODO: Description.
 * 
 * @author Aurélien Pepin
 */
public class Ant implements Observable {
   
    /**
     * Local patience for the ant.
     * Should be > 0.
     */
    protected int patience;
    
    /**
     * List of hunting sites.
     */
    protected List<HuntingSite> sites;
    
    /**
     * Previous explored site.
     */
    protected HuntingSite previousSite;
    
    /**
     * Memory size for hunting sites.
     * Should be > 0.
     */
    protected final int memorySize;
    
    /**
     * Number of current sites.
     */
    protected int currentSize;
    
    /**
     * The colony of the ants.
     */
    protected AntColony colony;
    
    /**
     * Local amplitude for the ant.
     */
    protected int amplitude;
    
    /**
     * Current best solution.
     */
    protected PartialSolution bestSolution;
    
    
    /**
     * CONSTRUCTOR. Initialize an ant.
     * @param patience      P_locale
     * @param amplitude     A_locale
     * @param memorySize    p
     * @param colony        Colony (nest, etc.)
     */
    public Ant(int patience, int amplitude, int memorySize, AntColony colony) { 
        if (patience < 1 || memorySize < 1 || colony == null)
            throw new IllegalArgumentException("No negative parameters for an ant.");
        
        this.patience = patience;
        this.amplitude = amplitude;
        this.memorySize = memorySize;
        
        this.sites = new ArrayList<>(memorySize);
        this.currentSize = 0;
        this.previousSite = null;   // Nothing explored at the beginning
        
        this.bestSolution = new PartialSolution(colony.getNest(), 1);
        
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
            System.out.println("Ajout d'un site près du nid !");
            
            State huntingState = colony.opExplo(colony.getNest(), colony.getSiteAmplitude());            
            HuntingSite huntingSite = new HuntingSite(huntingState);
            
            this.sites.add(huntingSite);
            currentSize++;
        } else {
            HuntingSite hSite = this.getPreviousSite();
            
            if (hSite.getFails() > 0) {
                hSite = this.getRandomSite();
            }
            
            /**
             * LOCAL EXPLORATION.
             */
            State exploreSite = colony.opExplo(hSite.getSite(), amplitude);
            
            // Evaluation
            float exploreEval = colony.f(exploreSite);
            float currentEval = colony.f(hSite);
            
            if (exploreEval < currentEval) {
                System.out.println("Progression !");
                hSite.setSite(exploreSite);
                hSite.resetFails();
                
                this.bestSolution = new PartialSolution(exploreSite, exploreEval);
            } else {
                System.out.println("Abandon d'un site...");
                hSite.addFail();
                
                // The local patience was overtaken
                if (hSite.shouldBeForgotten(patience)) {
                    this.sites.remove(hSite);
                    currentSize--;
                }
            }
            
            // If the exact searched state was found
            // At the end because the strategy of the colony isn't necessarily an exit
            if (Float.compare(exploreEval, 0) == 0) {
                this.notifyColony(new PartialSolution(exploreSite, exploreEval));
            }
        }
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
    
    /**
     * Empty the memory of the ant (when the nest moves).
     */
    public void emptyMemory() {
        this.sites = new ArrayList<>(memorySize);
        this.currentSize = 0;
        this.previousSite = null;
    }

    /**
     * Get the best solution found by this ant (: best local solution).
     * @return The best local solution
     */
    public PartialSolution getBestSolution() {
        return bestSolution;
    }
    
    @Override
    public void notifyColony(PartialSolution solution) {
        colony.stopStimulation(solution);
    }
}
