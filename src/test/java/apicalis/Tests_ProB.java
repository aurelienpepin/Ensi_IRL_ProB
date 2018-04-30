package apicalis;

import apicalis.variables.SetVariable;
import apicalis.variables.Variable;
import de.prob.Main;
import de.prob.model.classicalb.ClassicalBMachine;
import de.prob.model.classicalb.ClassicalBModel;
import de.prob.scripting.Api;
import de.prob.scripting.ModelTranslationError;
import de.prob.statespace.StateSpace;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests the algorithm on many parameters combinations.
 * @author Aurélien Pepin
 */
public class Tests_ProB {

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
        StateSpace sspace = api.b_load("machines/rbac/RBAC_Model.mch");
        
        ClassicalBModel model = (ClassicalBModel) sspace.getModel();
        ClassicalBMachine machine = model.getMainMachine();
        
        /**
         * APICALIS ALGORITHM.
         */        
        List<Variable> variables = new ArrayList<>();
        variables.add(new SetVariable("Customer", "{Bob,Paul}"));
        variables.add(new SetVariable("Account", "{cpt1,cpt2,cpt3}"));
        variables.add(new SetVariable("AccountOwner", "{(cpt1|->Bob),(cpt2|->Bob),(cpt3|->Paul)}"));
        
        AntColony colony;
        List<Integer> antsNumbers = Arrays.asList(1, 2, 3, 5, 10, 15, 20, 50, 100);

        for (Integer i : antsNumbers) {
            System.out.println("--------------------------------------------");
            System.out.println("----------- Number of ants: " + i + "\t------------");
            
            colony = new AntColony(i, sspace.getRoot(), variables);
            colony.simulate();
        }
        
        // AntColony colony = new AntColony(1, sspace.getRoot(), variables);
        // colony.simulate();
    }
}
