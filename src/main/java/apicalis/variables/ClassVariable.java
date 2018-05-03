package apicalis.variables;

import de.prob.animator.domainobjects.EvalResult;
import de.prob.statespace.State;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a set as a variable in the B-method.
 * Syntax: varName = {val1, val2}.
 * 
 * @author Aurélien Pepin
 */
public class ClassVariable extends Variable {

    private final Pattern pattern;
    
    public ClassVariable(String identifier, String value) {
        super(identifier, value);
        pattern = Pattern.compile("^\\{\\(1\\|->([0-9]+)\\),\\(2\\|->([0-9]+)\\)\\}$");
    }

    public ClassVariable(String identifier, String value, double weight) {
        super(identifier, value, weight);
        pattern = Pattern.compile("^\\{\\(1\\|->([0-9]+)\\),\\(2\\|->([0-9]+)\\)\\}$");
    }

    @Override
    public float evaluate(State state) {
//        String partU = "card(" + identifier + " /\\ " + value + ")";
//        String partD = "card(" + identifier + " \\/ " + value + ")";
//        
//        int resU = Integer.parseInt(state.eval(partU).toString());
//        int resD = Integer.parseInt(state.eval(partD).toString());
//              
//        return 1 - (resU / (float) resD);
        String partU = "card(" + identifier + " /\\ " + value + ")";
        String partD = "card(" + identifier + " \\/ " + value + ")";
        String partSequence = "[" + partU + "," + partD + "]";
        
        EvalResult result = (EvalResult) state.eval(partSequence);
        Matcher m = pattern.matcher(result.getValue());
        
        m.matches();
        int resU = Integer.parseInt(m.group(1));
        int resD = Integer.parseInt(m.group(2));
        
        return 1 - (resU / (float) resD);
    }
}
