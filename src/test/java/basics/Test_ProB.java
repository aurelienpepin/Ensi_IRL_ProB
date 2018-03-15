package basics;

import de.prob.Main;
import de.prob.model.classicalb.ClassicalBMachine;
import de.prob.model.classicalb.ClassicalBModel;
import de.prob.model.classicalb.Operation;
import de.prob.scripting.Api;
import de.prob.scripting.ModelTranslationError;
import de.prob.statespace.State;
import de.prob.statespace.StateSpace;
import de.prob.statespace.Transition;
import java.io.IOException;

/**
 * First manipulation of the ProB API.
 * @author Aurélien Pepin
 */
public class Test_ProB {

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
    
    /**
     * Tests the features of ProB.
     * @param api
     * @throws IOException
     * @throws ModelTranslationError 
     */
    protected static void testProB(Api api) throws IOException, ModelTranslationError {
        // Load the state space
        StateSpace sspace = api.b_load("machines/ACounter.mch");
        
        // There's a one-to-one relationship between the StateSpace and the
        // model, so we can get the model from the StaceSpace
        ClassicalBModel model = (ClassicalBModel) sspace.getModel();
        ClassicalBMachine machine = model.getMainMachine();
        
        for (Operation o : machine.getOperations()) {
            System.out.println("Operation: " + o.getName());
        }
        
        // Racine du model-checker
        Transition firstTransition = sspace.getRoot().getOutTransitions(true).get(0);
        State destination = firstTransition.getDestination();
        
        destination.explore();

        System.out.println("---");        
        System.out.println(sspace.printOps(sspace.getRoot()));
        System.out.println(sspace.printState(sspace.getRoot()));
        
        System.out.println("---");
        System.out.println(sspace.printOps(destination));
        System.out.println(sspace.printState(destination));
        
        Transition secondTransition = destination.getOutTransitions(true).get(0);
        State destination2 = secondTransition.getDestination();
        
        System.out.println("---");
        System.out.println(sspace.printOps(destination2));
        System.out.println(sspace.printState(destination2));
        
        System.out.println(destination2.getOutTransitions(true).get(0));
        System.out.println(destination2.getValues());
    }
}
