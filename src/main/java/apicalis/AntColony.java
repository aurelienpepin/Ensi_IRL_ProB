package apicalis;

import de.prob.animator.domainobjects.AbstractEvalResult;
import de.prob.animator.domainobjects.IEvalElement;
import de.prob.statespace.State;
import de.prob.statespace.Transition;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Modelling of the colony of Pachycondyla Apicalis.
 * TODO: Description.
 * 
 * @author Aurélien Pepin
 */
public class AntColony {
    
    /**
     * Position of the nest of the colony.
     */
    private State nest;
    
    /**
     * List of hunting ants in the colony.
     */
    private final List<Ant> ants;
    
    /**
     * General amplitude for the colony.
     * Should be in [0; 1].
     */
    private float amplitude;
    
    /**
     * Global best solution.
     * Merging all local solutions from ants.
     */
    private PartialSolution bestSolution;
    
    /**
     * Final searched values.
     * Should be valid according to the B syntax.
     */
    private final Map<String, String> finalValues;
    
    /**
     * Constants for ants parameters.
     */
    private final int LOCAL_PATIENCE = 10;
    private final int LOCAL_AMPLITUDE = 4;
    private final int ANT_MEMORY = 2;
    
    // From the thesis (p. 128)
    private final int GLOBAL_PATIENCE = 2 * (LOCAL_PATIENCE + 1) * ANT_MEMORY;
    private final int GLOBAL_AMPLITUDE = 15;
    
    
    /**
     * CONSTRUCTOR. Initialize a list of n ants.
     * @param n     Number of ants
     * @param root  Root of the state space, initial position of the nest
     * @param finalValues  Set of state values (variables, relations) in B.
     */
    public AntColony(int n, State root, Map<String, String> finalValues) {
        this.nest = root;
        this.ants = new ArrayList<>();
        this.createAnts(n);
        
        this.finalValues = finalValues;
    }
    
    /**
     * CONSTRUCTOR/INITIALIZATION. Initialize ants.
     * @param n     Number of ants
     */
    private void createAnts(int n) {
        if (n < 1)
            throw new IllegalArgumentException("Bad number of ants");
        
        for (int i = 0; i < n; i++) {
            this.ants.add(new Ant(LOCAL_PATIENCE, LOCAL_AMPLITUDE, ANT_MEMORY, this));
        }
    }
    
    
    /**
     * OPERATOR. Neighborhood operator (O_explo).
     * @param s The first point, already visited.    
     * @param amplitude Maximum number of followed transitions. 
     * @return  A new point in the neighborhood of s.
     */
    public static State opExplo(State s, int amplitude) {
        if (s == null || amplitude < 1)
            return null;
        
        // Number of random transitions to follow
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int toFollow = rand.nextInt(amplitude);
        
        State randState = randomStateFrom(s, rand);
        
        // TODO. Check NULL values
        while (toFollow > 0) {
            randState = randomStateFrom(randState, rand);
            toFollow--;
        }
        
        return randState;
    }
    
    private static State randomStateFrom(State s, ThreadLocalRandom rand) {
        List<Transition> transitions = s.getOutTransitions();
        State randState = null;
        
        if (transitions.size() > 0) {
            int randIndex = rand.nextInt(transitions.size());
            randState = transitions.get(randIndex).getDestination();
        }
        
        return randState;
    }
    
    
    /**
     * ALGORITHM. Simulate a colony of Apicalis ants.
     * Ants are well initialized before this method call.
     */
    public void simulate() {
        // The initial site of the nest is the root of the state space.
        int T = 1;
        
        while (T < 1000) {
            // Local behaviour of ants
            for (Ant a : ants) a.search();
            
            // If the nest should be moved
            if (T % GLOBAL_PATIENCE == 0) {
                this.nest = this.getBestSolution();
                System.out.println(">> Mouvement du nid: ");
                System.out.println(">> - Account: " + this.nest.eval("Account"));
                System.out.println(">> - Customer: " + this.nest.eval("Customer"));
                System.out.println(">> - AccountOwner: " + this.nest.eval("AccountOwner"));
                
                for (Ant a : ants)
                    a.emptyMemory();
            }
            
            T++;
        }
        
        // WIP: I should give the trace as a solution, not the only final state.
        System.out.println("End of the algorithm. Best solution: " + this.bestSolution.getScore());
    }
    
    /**
     * EVALUATION FONCTION in [0, 1].
     * Indicates the quality of an hunting site.
     *      - 0: perfect
     *      - 1: very bad
     * 
     * @param state
     * @return The "quality" of the hunting site.
     */
    public float f(State state) {
        if (state == null)
            return 1;
        
        float similarityMean = 0;
        
        // Computing Jaccard indexes for similarity between states
        for (String propertyName : finalValues.keySet()) {            
            String partU = "card(" + propertyName + " /\\ " + finalValues.get(propertyName) + ")";
            String partD = "card(" + propertyName + " \\/ " + finalValues.get(propertyName) + ")";
            
            int resU = Integer.parseInt(state.eval(partU).toString());
            int resD = Integer.parseInt(state.eval(partD).toString());
            
            similarityMean += (1 - (resU / (float) resD));
        }
        
        System.out.println("EVALUATION: " + (similarityMean / (float) finalValues.keySet().size()));
        return similarityMean / (float) finalValues.keySet().size();
    }
    
    /**
     * EVALUATION FONCTION in [0, 1].
     * Shortcut for the state evaluation fonction.
     * 
     * @param hSite
     * @return The "quality" of the hunting site.
     */
    public float f(HuntingSite hSite) {
        return this.f(hSite.getSite());
    }

    public State getNest() {
        return nest;
    }
    
    public State getBestSolution() {
        State bestState = null;
        float bestScore = Float.MAX_VALUE;
        
        for (Ant a : ants) {
            if (a.getBestSolution().getScore() < bestScore) {
                bestScore = a.getBestSolution().getScore();
                bestState = a.getBestSolution().getState();
                
                this.bestSolution = a.getBestSolution();
            }
        }
        
        return bestState;
    }
}
