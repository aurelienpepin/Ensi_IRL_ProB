package basics;

import de.prob.Main;
import de.prob.model.classicalb.ClassicalBMachine;
import de.prob.model.classicalb.ClassicalBModel;
import de.prob.scripting.Api;
import de.prob.scripting.ModelTranslationError;
import de.prob.statespace.State;
import de.prob.statespace.StateSpace;
import de.prob.statespace.Transition;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Infinite manipulation of Pro B with a stack.
 * @author Aurélien Pepin
 */
public class Stack_ProB {

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
    
    public static void testProB(Api api) throws IOException, ModelTranslationError {
        // Load the state space
        StateSpace sspace = api.b_load("machines/ACounter.mch");
        
        // There's a one-to-one relationship between the StateSpace and the
        // model, so we can get the model from the StaceSpace
        ClassicalBModel model = (ClassicalBModel) sspace.getModel();
        ClassicalBMachine machine = model.getMainMachine();
        
        /**
         * PROCESS.
         */
        LinkedList<Transition> remainingTransitions = new LinkedList<>();
        remainingTransitions.addAll(sspace.getRoot().getOutTransitions(true));
        
        while (!remainingTransitions.isEmpty()) {
            Transition trans = remainingTransitions.removeFirst();
            
            // Associated destination
            State dest = trans.getDestination();
            System.out.println(dest.getValues());
            
            remainingTransitions.addAll(dest.getOutTransitions(true));
        }
    }    
}
