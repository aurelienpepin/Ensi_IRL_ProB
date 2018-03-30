package apicalis.solutions;

import de.prob.statespace.State;
import de.prob.statespace.Transition;

/**
 *
 * @author Aurélien Pepin
 */
public class StateAndTransition {
    private final State state;
    private final Transition transition;
    
    /**
     * CONSTRUCTOR.
     * @param state
     * @param transition 
     */
    public StateAndTransition(State state, Transition transition) {
        this.state = state;
        this.transition = transition;
    }

    public State getState() {
        return state;
    }

    public Transition getTransition() {
        return transition;
    }

    @Override
    public String toString() {
        return "--(" + transition.getName() + ")-->[" + state.getId() + "]";
    }
}
