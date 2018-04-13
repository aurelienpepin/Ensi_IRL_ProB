package apicalis.variables;

import de.prob.statespace.State;

/**
 * Represents a variable for the final search result.
 * This class is abstract. Its specializations handle specific data type.
 * 
 * @author Aurélien Pepin
 */
abstract public class Variable {
    
    /**
     * Identifier of the variable.
     * Should exist in the machine states.
     */ 
    protected final String identifier;
    
    /**
     * Searched value as a result.
     */
    protected final String value;
    
    /**
     * The bigger the weight, the more important the variable in the evaluation.
     * Examples:
     *      (weight = 1) => default weight
     *      (weight = 0) => unused variable in the evaluation
     *      (weight = 2) => twice as important as another weight
     */
    private double weight;
    
    
    public Variable(String identifier, String value) {
        if (identifier == null || value == null)
            throw new NullPointerException("Wrong variable initialization (identifier, value).");
        
        this.identifier = identifier;
        this.value = value;
        this.weight = 1.0;
    }
    
    public Variable(String identifier, String value, double weight) {
        this(identifier, value);
        
        if (weight < 0)
            throw new UnsupportedOperationException("Wrong variable initialization (weight).");
        
        this.weight = weight;
    }

    /**
     * EVALUATION. Should return a value between 0 and 1.
     * Independent from the weight.
     * 
     * @param state
     * @return 
     */
    abstract public float evaluate(State state);
    
    @Override
    public String toString() {
        return identifier + ": " + value;
    }
    
    public String getIdentifier() {
        return identifier;
    }

    public String getValue() {
        return value;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight < 0)
            throw new UnsupportedOperationException("Wrong variable update (weight).");
        
        this.weight = weight;
    }
}
