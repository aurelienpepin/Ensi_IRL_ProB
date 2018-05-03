package basics;

import apicalis.AntColony;
import apicalis.variables.BooleanVariable;
import apicalis.variables.IntegerVariable;
import apicalis.variables.Variable;
import de.prob.Main;
import de.prob.model.classicalb.ClassicalBMachine;
import de.prob.model.classicalb.ClassicalBModel;
import de.prob.scripting.Api;
import de.prob.scripting.ModelTranslationError;
import de.prob.statespace.StateSpace;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manipulation of booleans within ProB.
 * @author Aurélien Pepin
 */
public class TestBoolean_ProB {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Instance of the API to work with
        Api api = Main.getInjector().getInstance(Api.class);
        System.out.println("LOADING ProB: " + api.getVersion());
        
        // Work with the API
        try {
            testProB(api);
        } catch (IOException | ModelTranslationError ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
        
        System.exit(0);
    }
    
    protected static void testProB(Api api) throws IOException, ModelTranslationError {
        // Load the state space
        StateSpace sspace = api.b_load("machines/RussianPostalPuzzle.mch");
        
        ClassicalBModel model = (ClassicalBModel) sspace.getModel();
        ClassicalBMachine machine = model.getMainMachine();
        
        /**
         * APICALIS ALGORITHM.
         */        
        List<Variable> variables = new ArrayList<>();
        variables.add(new BooleanVariable("box_contains_gem", false));
        
        AntColony colony = new AntColony(10, sspace.getRoot(), variables);
        colony.simulate();
    }
}
