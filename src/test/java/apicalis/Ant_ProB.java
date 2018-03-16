package apicalis;

import de.prob.Main;
import de.prob.model.classicalb.ClassicalBMachine;
import de.prob.model.classicalb.ClassicalBModel;
import de.prob.scripting.Api;
import de.prob.scripting.ModelTranslationError;
import de.prob.statespace.StateSpace;
import java.io.IOException;

/**
 * Combination of ProB Api and Apicalis.
 * @author Aurélien Pepin
 */
public class Ant_ProB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Instance of the API to work with
        Api api = Main.getInjector().getInstance(Api.class);
        System.out.println("LOADING ProB: " + api.getVersion());
        
        // Work with the API
        try {
            apicalisProB(api);
        } catch (IOException | ModelTranslationError ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
        
        System.exit(0);
    }
    
    private static void apicalisProB(Api api) throws IOException, ModelTranslationError {
        // Load the state space
        StateSpace sspace = api.b_load("machines/scheduler.mch");
        
        ClassicalBModel model = (ClassicalBModel) sspace.getModel();
        ClassicalBMachine machine = model.getMainMachine();
        
        /**
         * APICALIS ALGORITHM.
         */
        AntColony colony = new AntColony(4, sspace.getRoot());
        colony.simulate();
    }
}
