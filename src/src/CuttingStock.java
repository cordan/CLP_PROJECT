/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import JaCoP.constraints.Cumulative;
import JaCoP.constraints.Sum;
import JaCoP.constraints.XlteqY;
import JaCoP.constraints.XplusYeqZ;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.core.Var;
import JaCoP.search.*;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author eave
 */
public class CuttingStock {
    
    public ArrayList<Var> vars;
    public IntVar cost;
        
    IntVar[] startPos;
    IntVar [] endPos;
    int rollsNumAll;
    Store store;
    
    public void model(int Max_size, int [] rlsSizes, int [] rsrcs) {
        
        int M_size = Max_size;  
        int INF = 100000;
        int rlsNum = rlsSizes.length;
    
        rollsNumAll = rlsNum;
        store = new Store();
        
        IntVar numMasterRolls   = new IntVar(store, "numMasterRolls", 2, INF); // will be minimized
        IntVar maxSize    = new IntVar(store, "maxSize", M_size,M_size);
    
        //Start positions
        startPos       = new IntVar[rollsNumAll];
        
        for(int i = 0; i < rollsNumAll; i++) {
            startPos[i] = new IntVar(store, "startPos_"+i, 0, M_size); 
        }
        
        //IntVar sumStartPos = new IntVar(store, "SumStartPos", 0, 1000);

        //store.impose(new Sum(startPos, sumStartPos));
        
        
        IntVar[] lengths        = new IntVar[rollsNumAll]; //lengths of each roll
        IntVar[] resources      = new IntVar[rollsNumAll]; //always using only 1 Master roll
        endPos                  = new IntVar[rollsNumAll]; //ending position of roll
        
        for (int i = 0; i < rlsNum; i++) {
			// converts to FDV
			lengths[i] = new IntVar(store, "length_"+i, rlsSizes[i], rlsSizes[i]);
			// converts to FDV
			resources[i] = new IntVar(store, "res_"+i, rsrcs[i],rsrcs[i]);

			
			endPos[i]  = new IntVar(store, "end_"+i, 0, M_size);
			store.impose(new XplusYeqZ(startPos[i], lengths[i], endPos[i]));
			store.impose(new XlteqY(endPos[i], maxSize));
			
        }
        
        store.impose(new Cumulative(startPos, lengths, resources, numMasterRolls));
        
        System.out.println("In Cutting stock:\n");
        
        vars = new ArrayList<Var>();
        
        if( vars.addAll(Arrays.asList(startPos)) != true ) {
            System.out.println("Error while appending variables to the list\n");
            // exit(1);
        }
        
        if( vars.addAll(Arrays.asList(endPos)) != true ) {
            System.out.println("Error while appending variables to the list\n");
            // exit(1);
        }
        
	    vars.add(numMasterRolls);     
	    
	    cost = numMasterRolls;
    
    }
    
    
    //copied from example not really working
    public void searchSpecific() {

        SelectChoicePoint select = new SimpleSelect (vars.toArray(new Var[1]),
                                                     new SmallestDomain(),
                                                     new IndomainMin ());
        
        Search label = new DepthFirstSearch ();
        label.getSolutionListener().searchAll(true);
        label.getSolutionListener().recordSolutions(true);

        boolean result;
        
        // minimize over numMasterRolls
        result = label.labeling(store, select, cost); 

        Var[] variables = label.getSolutionListener().getVariables();
        for(int i = 0; i < variables.length; i++) {
            System.out.println("Variable " + i + " " + variables[i]);
        }

        if(result) {
            
            label.printAllSolutions();

            System.out.println("\nNumber of rolls needed: " + cost.value());
            
            for(int i = 0; i < rollsNumAll; i++) {
                System.out.println("Roll_" + i + ": " + startPos[i].value() + ".." + endPos[i].value() + "\n");
            }                                          
        }
    }
}
