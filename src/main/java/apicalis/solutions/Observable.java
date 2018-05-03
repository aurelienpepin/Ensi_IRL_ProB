package apicalis.solutions;

/**
 * Observer pattern: observable.
 * @author Aurélien Pepin
 */
public interface Observable {
    
    /**
     * Notify the colony.
     * @param solution A solution whose score is 0.0
     */
    public void notifyColony(PartialSolution solution);
}
