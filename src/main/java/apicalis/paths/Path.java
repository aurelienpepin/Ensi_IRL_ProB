package apicalis.paths;

import de.prob.statespace.State;
import de.prob.statespace.Transition;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Creates a path of origins from one state to another.
 * @author Aurélien Pepin
 */
public class Path {

    /**
     * Source state, beginning of the path.
     * If null, indicates the root of the statespace.
     */
    private final State source;
    
    /**
     * Destination state, end of the path.
     * Shouldn't be null.
     */
    private final State dest;
    
    /**
     * Transitions' map (as in the colony).
     * Shouldn't be null.
     */
    private final Map<State, Transition> origins;
    
    /**
     * Ordered list of transitions from "source" to "dest".
     * Computed in the constructor.
     */
    private final List<Transition> transitions;
    
    /**
     * CONSTRUCTOR.
     * @param source
     * @param dest
     * @param origins 
     */
    public Path(State source, State dest, Map<State, Transition> origins) {
        if (dest == null || origins == null)
            throw new NullPointerException("Can't build the path for null states");
        
        this.source = source;
        this.dest = dest;
        this.origins = origins;
        
        this.transitions = new LinkedList<>();
        
        // Find origins from "source" to "dest".
        this.compute();
    }
    
    /**
     * First computed in the constructor.
     */
    private void compute() {
        State currState = this.dest;
        Transition currTransition = this.origins.get(currState);
        
        while (computeCondition(currState, currTransition)) {
            this.transitions.add(0, currTransition);
            
            currState = currTransition.getSource();
            currTransition = this.origins.get(currState);
        }
    }
    
    /**
     * If the source is null, transitions are added until the end.
     * Otherwise, we look if the source equals the current state.
     * 
     * @param currState
     * @param currTransition
     * @return 
     */
    private boolean computeCondition(State currState, Transition currTransition) {
        if (!origins.containsKey(currState))
            throw new UnsupportedOperationException("Can't build the path with missing states");
        
        if (source == null)
            return currTransition != null;
        
        return !source.equals(currState);
    }

    public List<Transition> getTransitions() {
        return transitions;
    }
}
