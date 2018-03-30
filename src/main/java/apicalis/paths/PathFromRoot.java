package apicalis.paths;

import de.prob.statespace.State;
import de.prob.statespace.Transition;
import java.util.Map;

/**
 * Creates a path of transitions from the root state to another.
 * @author Aurélien Pepin
 */
public class PathFromRoot extends Path {

    public PathFromRoot(State dest, Map<State, Transition> transitions) {
        super(null, dest, transitions);
    }
    
}
