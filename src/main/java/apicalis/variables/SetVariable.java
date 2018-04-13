package apicalis.variables;

import de.prob.statespace.State;

/**
 * Represents a set as a variable in the B-method.
 * Syntax: varName = {val1, val2}.
 * 
 * @author Aurélien Pepin
 */
public class SetVariable extends Variable {

    public SetVariable(String identifier, String value) {
        super(identifier, value);
    }

    public SetVariable(String identifier, String value, double weight) {
        super(identifier, value, weight);
    }

    @Override
    public float evaluate(State state) {
        String partU = "card(" + identifier + " /\\ " + value + ")";
        String partD = "card(" + identifier + " \\/ " + value + ")";
            
        int resU = Integer.parseInt(state.eval(partU).toString());
        int resD = Integer.parseInt(state.eval(partD).toString());
            
        return 1 - (resU / (float) resD);
    }
}
