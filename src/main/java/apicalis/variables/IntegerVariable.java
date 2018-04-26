package apicalis.variables;

import de.prob.statespace.State;

/**
 * Represents an integer as variable in the B-method.
 * Syntax: varName = INTEGER.
 * 
 * 
 * 
 * @author Aurélien Pepin
 */
public class IntegerVariable extends Variable {

    private int lowerBound;
    private int upperBound;
    
    public IntegerVariable(String identifier, String value, int lowerBound, int upperBound) {
        super(identifier, value);
        
        if (lowerBound >= upperBound)
            throw new UnsupportedOperationException("Wrong variable initialization (range).");
        
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public IntegerVariable(String identifier, String value, double weight, int lowerBound, int upperBound) {
        super(identifier, value, weight);
        
        if (lowerBound >= upperBound)
            throw new UnsupportedOperationException("Wrong variable initialization (range).");
        
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public IntegerVariable(String identifier, String value) {
        super(identifier, value);
        
        this.lowerBound = Integer.MIN_VALUE;
        this.upperBound = Integer.MAX_VALUE;
    }

    public IntegerVariable(String identifier, String value, double weight) {
        super(identifier, value, weight);
        
        this.lowerBound = Integer.MIN_VALUE;
        this.upperBound = Integer.MAX_VALUE;
    }
    

    @Override
    public float evaluate(State state) {
        // 1 - |state.eval(identifier) - value|/|lowerBound - upperBound|
        // Pas de valeur absolue dans la syntaxe B, le faire en Java.
        throw new UnsupportedOperationException("Not supported yet."); // OVR
    }
    
    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        // TODO. Verifications sur la borne ici aussi.
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        // TODO. Verifications sur la borne ici aussi.
        this.upperBound = upperBound;
    }
}
