package apicalis;

import java.util.concurrent.ThreadLocalRandom;

/**
 * (NOT EFFICIENT NOW). Modelling of a Pachycondyla Apicalis whose parameters can slightly
 * differ from the parameters of the colony.
 * 
 * @author Aurélien Pepin
 */
public class MixedAnt extends Ant {
    
    /**
     * CONSTRUCTOR. Initialize a mixed ant.
     * @param patience
     * @param amplitude
     * @param memorySize
     * @param colony 
     */
    public MixedAnt(int patience, int amplitude, int memorySize, AntColony colony) {
        super(patience, amplitude, memorySize, colony);
        
        // The ant has slightly changed parameters.
        this.varyParameters();
    }
    
    /**
     * Change parameters of the ant.
     * Eligible parameters for change: local amplitude, global amplitude.
     * 
     * TODO: a more clever use of random?
     */
    private void varyParameters() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        this.amplitude += rand.nextInt(amplitude) * (rand.nextInt(2) == 1 ? 1 : -1);
    }
}
