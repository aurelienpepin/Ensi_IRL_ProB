package apicalis.variables;

import de.prob.statespace.State;

/**
 * Represents an integer as variable in the B-method.
 * Syntax: varName = INTEGER.
 * 
 * @author Aurélien Pepin
 */
public class IntegerVariable extends Variable {

    private int lowerBound;
    private int upperBound;
    
    public IntegerVariable(String identifier, int value, int lowerBound, int upperBound) {
        super(identifier, Integer.toString(value));
        
        if (lowerBound >= upperBound)
            throw new UnsupportedOperationException("Wrong variable initialization (range).");
        
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public IntegerVariable(String identifier, int value, double weight, int lowerBound, int upperBound) {
        super(identifier, Integer.toString(value), weight);
        
        if (lowerBound >= upperBound)
            throw new UnsupportedOperationException("Wrong variable initialization (range).");
        
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public IntegerVariable(String identifier, int value) {
        super(identifier, Integer.toString(value));
        
        this.lowerBound = Integer.MIN_VALUE;
        this.upperBound = Integer.MAX_VALUE;
    }

    public IntegerVariable(String identifier, String value, double weight) {
        super(identifier, value, weight);
        
        this.lowerBound = Integer.MIN_VALUE;
        this.upperBound = Integer.MAX_VALUE;
    }
    

    /**
     * Evaluation computes: |identifier - value|/|lowerBound - upperBound|.
     * 
     * @param state
     * @return 
     */
    @Override
    public float evaluate(State state) {
        String partU = identifier + " - " + value;
        
        int resU = Math.abs(Integer.parseInt(state.eval(partU).toString().replace('−', '-')));
        int resD = Math.abs(lowerBound - upperBound);

        return resU / (float) resD;
    }
    
    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        if (lowerBound >= upperBound)
            throw new UnsupportedOperationException("Wrong variable settings (range).");
        
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        if (lowerBound >= upperBound)
            throw new UnsupportedOperationException("Wrong variable settings (range).");
        
        this.upperBound = upperBound;
    }
}
