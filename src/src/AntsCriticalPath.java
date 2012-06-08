/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;


import JaCoP.constraints.netflow.NetworkBuilder;
import JaCoP.constraints.netflow.NetworkFlow;
import JaCoP.constraints.netflow.simplex.Node;
import JaCoP.constraints.netflow.simplex.Arc;
import JaCoP.core.IntVar;
import JaCoP.core.Store;




/**
 *
 * @author Roberto Marroquin
 */
public class AntsCriticalPath {
    
      Store store = new Store();
      
      int connections = 10;
      int nodes=6;
      int[] weights = {5,15,3,1,2,1,9,5,10,7}; 
      
      IntVar cost = new IntVar(store, "cost", 0, 50); 
   
      
      NetworkBuilder net = new NetworkBuilder(cost); 
      
      Node one = net.addNode("one", 0); 
      Node two = net.addNode("two", 0); 
      Node three = net.addNode("three", 0); 
      Node four = net.addNode("four", 0); 
      Node five = net.addNode("five", 0); 
      Node six = net.addNode("six", 0); 
              
      Arc one_two = net.addArc(one, two, weights[0] ,0,1); 
      Arc one_four= net.addArc(one,four, weights[1] ,0,1);
      Arc one_three= net.addArc(one,three, weights[2] ,0,1);
      Arc two_three = net.addArc(two, three, weights[3] ,0,1); 
      Arc two_four= net.addArc(two,four, weights[4] ,0,1);
      Arc two_five = net.addArc(two,five, weights[5] ,0,1); 
      Arc three_five= net.addArc(three,five, weights[6] ,0,1);
      Arc four_five = net.addArc(four, five, weights[7] ,0,1); 
      Arc four_six= net.addArc(four,six, weights[8] ,0,1);
      Arc five_six= net.addArc(five,six, weights[9] ,0,1);
      
      
 
      // IntVar cost = new IntVar(store, "cost", 0, 50); 
      
      
       //net.setCostVariable (cost);
        
 
      store.impose( NetworkFlow const = new NetworkFlow(net));
  
    //hey
    
}
