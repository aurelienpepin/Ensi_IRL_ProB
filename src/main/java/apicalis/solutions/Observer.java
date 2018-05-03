package apicalis.solutions;

/**
 * Observer pattern: observer.
 * @author Aurélien Pepin
 */
public interface Observer {
    
    /**
     * If the score 0.0 is found, the colony is notified.
     * @param solution A solution whose score is 0.0
     */
    public void stopStimulation(PartialSolution solution);
}
