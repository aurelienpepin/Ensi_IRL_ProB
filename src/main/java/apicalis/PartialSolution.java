package apicalis;

import de.prob.statespace.State;

/**
 * Represents a partial (local) solution for an ant.
 * A partial solution is the best state found by an ant.
 * It is associated with the evaluation score (f).
 * 
 * @author Aurélien Pepin
 */
public class PartialSolution implements Comparable {
    private final State state;
    private final float score;

    public PartialSolution(State state, float score) {
        this.state = state;
        this.score = score;
    }

    @Override
    public int compareTo(Object t) {
        if (!(t instanceof PartialSolution))
            throw new UnsupportedOperationException("Impossible comparison.");
        
        // Using the score evaluation to compare solutions
        PartialSolution other = (PartialSolution) t;
        
        return (this.score == other.score) ? 0 : ((this.score < other.score) ? -1 : 1);
    }

    public State getState() {
        return state;
    }

    public float getScore() {
        return score;
    }
}
