package apicalis.variables;

import de.prob.statespace.State;

/**
 * Represents a boolean as variable in the B-method.
 * Syntax: varName = {TRUE, FALSE}.
 * 
 * @author Aurélien Pepin
 */
public class BooleanVariable extends Variable {

    public BooleanVariable(String identifier, boolean value) {
        super(identifier, value == true ? "TRUE" : "FALSE");
    }
    
    public BooleanVariable(String identifier, boolean value, double weight) {
        super(identifier, value == true ? "TRUE" : "FALSE", weight);
    }

    @Override
    public float evaluate(State state) {
        String res = identifier + " = " + value;
        return "TRUE".equals(state.eval(res).toString()) ? 0 : 1;
    }
}
