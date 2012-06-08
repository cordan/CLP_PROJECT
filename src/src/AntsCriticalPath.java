/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;


import JaCoP.constraints.netflow.NetworkBuilder;
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
      
      int connections = 9;
      int nodes=6;
      //IntVar[] weights = new IntVar[connections]; 
      IntVar cost = new IntVar(store, "cost", 0, 50); 
   
      NetworkBuilder net = new NetworkBuilder(cost); 
      
      Node one = net.addNode("one", 0); 
      Node two = net.addNode("two", 0); 
      Node three = net.addNode("three", 0); 
      Node four = net.addNode("four", 0); 
      Node five = net.addNode("five", 0); 
      Node six = net.addNode("six", 0); 
              
      Arc one_two = net.addArc(one, two, 5); 
      Arc one_four= net.addArc(one,four,15);
      Arc two_three = net.addArc(two, three, 2); 
      Arc two_four= net.addArc(two,four,1);
      Arc two_five = net.addArc(two,five, 9); 
      Arc three_five= net.addArc(three,five,2);
      Arc four_five = net.addArc(four, five, 5); 
      Arc four_six= net.addArc(four,six,10);
      Arc five_six= net.addArc(five,six,7);
      
      
 
      // IntVar cost = new IntVar(store, "cost", 0, 50); 
      
      
       //net.setCostVariable (cost);
        
 
      //store.impose(new NetworkFlow(net));
  
    
    
}
