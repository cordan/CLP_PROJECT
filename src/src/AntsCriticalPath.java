package src;

import JaCoP.constraints.netflow.NetworkBuilder;
import JaCoP.constraints.netflow.NetworkFlow;
import JaCoP.constraints.netflow.simplex.Arc;
import JaCoP.constraints.netflow.simplex.Node;
import JaCoP.constraints.netflow.simplex.NetworkSimplex;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.core.Var;
import java.util.List;
import java.util.ArrayList;
import JaCoP.search.*;




/**
 *
 * @author Roberto Marroquin
 */

public class AntsCriticalPath {
    
    Store store = new Store();

    int num_arcs = 10;
    int num_nodes=6;
    int[] weights = {5,7,15,4,6,7,9,3,9,8}; 



    NetworkBuilder net = new NetworkBuilder(); 


    public void Network_definition() {
        
        Node nodes[] =new Node[num_nodes];  
        Arc arcs[] =new Arc[num_arcs];
        
        /* adding nodes to the network */
        
        nodes[0] = net.addNode("cero", 0); 
        nodes[1] = net.addNode("one", 0); 
        nodes[2] = net.addNode("two", 0); 
        nodes[3] = net.addNode("three", 0); 
        nodes[4] = net.addNode("four", 0); 
        nodes[5] = net.addNode("five", 0); 
        
        /* adding connection between nodes(arcs) */

        arcs[0] = net.addArc(nodes[0], nodes[1], weights[0]);
        arcs[1] = net.addArc(nodes[0],nodes[2], weights[1]);
        arcs[2] = net.addArc(nodes[0],nodes[3], weights[2]);
        arcs[3] = net.addArc(nodes[1], nodes[2], weights[3]); 
        arcs[4] = net.addArc(nodes[1],nodes[3], weights[4]);
        arcs[5] = net.addArc(nodes[1],nodes[4], weights[5]); 
        arcs[6] = net.addArc(nodes[2],nodes[4], weights[6]);
        arcs[7] = net.addArc(nodes[3],nodes[4], weights[7]); 
        arcs[8] = net.addArc(nodes[3],nodes[5], weights[8]);
        arcs[9] = net.addArc(nodes[4],nodes[5], weights[9]);


        IntVar cost = new IntVar(store, "cost", 0, 50);     
        net.setCostVariable(cost);  
            
        store.impose(net.build());  // net.build == new NetworkFlow(net)
         
        
    }

    public void search_solution(){

        //SelectChoicePoint select = new SimpleSelect();
       // SimpleSelect select = new SimpleSelect(store,);

      }
      
      
  
      
    
}