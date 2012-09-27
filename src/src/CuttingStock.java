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
    
    public ArrayList<Var>   vars;
    public IntVar           cost;
        
    IntVar[]    startPos; //Data with starting positions
    IntVar []   endPos; //Data with ending positions of roll
    int         rlsDistNum; //number of distinct types of rolls (different sizes)
    int         rlsNumAll; // number of all rolls
    Store       store;
    int []      m_rlsSizes; //sizes of distincts rolls
    int []      m_rlsAmount; //amount of distinct rolls
    boolean     m_solFound; //found solution?
    int [][]    m_orderedRolls;
    /**
     * Models a CLP problem
     * 
     * @param Max_size Maximum size of master roll
     * @param rlsSizes Sizes of distinct rolls
     * @param rlsAmount Amount of dinstinct rolls
     */
    public void model(int Max_size, int [] rlsSizes, int [] rlsAmount) {
        
        m_solFound = false;
        m_rlsSizes = rlsSizes;
        m_rlsAmount = rlsAmount;
        int M_size = Max_size;  
        int INF = 1000;
        rlsDistNum = rlsSizes.length;
        int [][]rollsData = new int[rlsDistNum][2];
        rlsNumAll = 0;
        int ind; // base index number for moving through tables
        
        for(int i = ind = 0; i < rlsDistNum; i++)
        {
            rollsData[i][0] = rlsSizes[i];
            rollsData[i][1] = rlsAmount[i];
            rlsNumAll += rlsAmount[i];
        }
        store = new Store();
        
//        Create a beginning set of huge amount of master rolls
//        This variable will be minimized
        IntVar numMasterRolls   = new IntVar(store, "numMasterRolls", 1, INF);
        
//        Last possible ending position of roll
        IntVar maxPos    = new IntVar(store, "maxSize", M_size,M_size);
    
//        Starting positions
        startPos       = new IntVar[rlsNumAll];
        
        for(int i = 0; i < rlsDistNum; i++) {
            for( int j = 0; j < rlsAmount[i]; j++){
                startPos[ind+j] = new IntVar(store, "startPos_"+(char)(i+'A')+j, 0, M_size);
            }
            ind += rlsAmount[i];
        }
        
        
        IntVar[] lengths        = new IntVar[rlsNumAll]; //lengths of each roll
        IntVar[] resources      = new IntVar[rlsNumAll]; //always using only 1 Master roll
        endPos                  = new IntVar[rlsNumAll]; //ending position of roll
        
        for (int i = ind = 0; i < rlsDistNum; i++) {            
            for( int j = 0; j < rlsAmount[i]; j++){
                
//		 Converts to FDV
		lengths[ind+j] = new IntVar(store, "length_"+(char)(i+'A')+j, rlsSizes[i], rlsSizes[i]);
//                System.out.println(lengths[ind+j]);
                resources[ind+j] = new IntVar(store, "res_"+(char)(i+'A')+j, 1,1); //every roll uses only one master roll
			
//               Setting constrains
		endPos[ind+j]  = new IntVar(store, "end_"+(char)(i+'A')+j, 0, M_size);
		store.impose(new XplusYeqZ(startPos[ind+j], lengths[ind+j], endPos[ind+j]));
		store.impose(new XlteqY(endPos[ind+j], maxPos));
            }
            ind += rlsAmount[i];
        }
        
        store.impose(new Cumulative(startPos, lengths, resources, numMasterRolls));
        
//        System.out.println("In Cutting stock:\n");
        
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
    
    
    public void searchSpecific() {

        int ind = 0;
        SelectChoicePoint select = new SimpleSelect (vars.toArray(new Var[1]),
                                                     new SmallestDomain(),
                                                     new IndomainMin ());
        
        Search label = new DepthFirstSearch ();
        label.getSolutionListener().searchAll(true);
        label.getSolutionListener().recordSolutions(false);

        boolean result;
        
//        minimize over cost = numMasterRolls
        result = label.labeling(store, select, cost); 

        Var[] variables = label.getSolutionListener().getVariables();
//        for(int i = 0; i < variables.length; i++) {
//            System.out.println("Variable " + i + " " + variables[i]);
//        }

        if(result) {
            m_solFound = true;
            label.printAllSolutions();

            System.out.println("Number of master rolls needed: " + cost);
            
            for(int i = ind = 0; i < rlsDistNum; i++) {
                for( int j = 0; j < m_rlsAmount[i]; j++){
                    System.out.println("Roll_" + (char)(i+'A')+j + ": " + startPos[ind] + " " + endPos[ind]);
                    ind++;
                }
                
            }
        }
    }
    public void orderResults() {
        
    }
}
