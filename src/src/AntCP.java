/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;


import JaCoP.constraints.netflow.NetworkBuilder;
import JaCoP.constraints.netflow.NetworkFlow;
import JaCoP.constraints.netflow.simplex.Arc;
import JaCoP.constraints.netflow.simplex.Node;

import JaCoP.core.IntVar;
import JaCoP.core.Store;

import JaCoP.search.*;

/**
 *
 * @author Roberto Marroquin
 */
public class AntCP {
    
    
    private String result = "";
    private String cost_vl = "";
    public Store store = new Store();
    public int num_arcs = 20;                       // this should be displayed in the GUI
    
    public void Network_definition(int [] weights,int start_node,int end_node) {
        Search<IntVar> search = null;
        SelectChoicePoint<IntVar> select = null;
        NetworkBuilder net = new NetworkBuilder();
        
        System.out.println("Critical Path:\n");
        
        Node[] nodes = new Node[6];
        Arc[] arcs = new Arc[num_arcs + 2];
        

        nodes[0] = net.addNode("0", 0); 
        nodes[1] = net.addNode("1", 0); 
        nodes[2] = net.addNode("2", 0); 
        nodes[3] = net.addNode("3", 0); 
        nodes[4] = net.addNode("4", 0); 
        nodes[5] = net.addNode("5", 0); 
        
        Node source = net.addNode("source",1);
        Node sink = net.addNode("sink",-1);
        
        IntVar [] x = new IntVar[num_arcs + 2];
        int lower = 0, upper = 1;    //  if the arc is taken it will have '1' if not '0'
        
        x[0] = new IntVar(store,"source->"+start_node,lower,upper);
        x[1] = new IntVar(store,"0->1",lower,upper);
        x[2] = new IntVar(store,"1->0",lower,upper);
        x[3] = new IntVar(store,"0->2",lower,upper);
        x[4] = new IntVar(store,"2->0",lower,upper);
        x[5] = new IntVar(store,"0->3",lower,upper);
        x[6] = new IntVar(store,"3->0",lower,upper);
        x[7] = new IntVar(store,"1->2",lower,upper);
        x[8] = new IntVar(store,"2->1",lower,upper);
        x[9] = new IntVar(store,"1->3",lower,upper);
        x[10] = new IntVar(store,"3->1",lower,upper);
        x[11] = new IntVar(store,"1->4",lower,upper);
        x[12] = new IntVar(store,"4->1",lower,upper);
        x[13] = new IntVar(store,"2->4",lower,upper);
        x[14] = new IntVar(store,"4->2",lower,upper);
        x[15] = new IntVar(store,"3->4",lower,upper);
        x[16] = new IntVar(store,"4->3",lower,upper);
        x[17] = new IntVar(store,"3->5",lower,upper);
        x[18] = new IntVar(store,"5->3",lower,upper);
        x[19] = new IntVar(store,"4->5",lower,upper);
        x[20] = new IntVar(store,"5->4",lower,upper);
        x[21] = new IntVar(store,"sink->"+end_node,lower,upper);
        
        /* adding connection between nodes(arcs) */

        arcs[0] = net.addArc(source, nodes[start_node],0,x[0]);
        arcs[1] = net.addArc(nodes[0],nodes[1], weights[0],x[1]);
        arcs[2] = net.addArc(nodes[1],nodes[0], weights[1],x[2]);
        arcs[3] = net.addArc(nodes[0], nodes[2], weights[2],x[3]); 
        arcs[4] = net.addArc(nodes[2],nodes[0], weights[3],x[4]);
        arcs[5] = net.addArc(nodes[0],nodes[3], weights[4],x[5]); 
        arcs[6] = net.addArc(nodes[3],nodes[0], weights[5],x[6]);
        arcs[7] = net.addArc(nodes[1],nodes[2], weights[6],x[7]); 
        arcs[8] = net.addArc(nodes[2],nodes[1], weights[7],x[8]);
        arcs[9] = net.addArc(nodes[1],nodes[3], weights[8],x[9]);
        arcs[10] = net.addArc(nodes[3], nodes[1], weights[9],x[10]);
        arcs[11] = net.addArc(nodes[1],nodes[4], weights[10],x[11]);
        arcs[12] = net.addArc(nodes[4],nodes[1], weights[11],x[12]);
        arcs[13] = net.addArc(nodes[2], nodes[4], weights[12],x[13]); 
        arcs[14] = net.addArc(nodes[4],nodes[2], weights[13],x[14]);
        arcs[15] = net.addArc(nodes[3],nodes[4], weights[14],x[15]); 
        arcs[16] = net.addArc(nodes[4],nodes[3], weights[15],x[16]);
        arcs[17] = net.addArc(nodes[3],nodes[5], weights[16],x[17]); 
        arcs[18] = net.addArc(nodes[5],nodes[3], weights[17],x[18]);
        arcs[19] = net.addArc(nodes[4],nodes[5], weights[18],x[19]);
        arcs[20] = net.addArc(nodes[5],nodes[4], weights[19],x[20]); 
        arcs[21] = net.addArc(nodes[end_node],sink, 0,x[21]);
        
        
        IntVar cost = new IntVar(store, "cost", 0, 1000);
        net.setCostVariable(cost);
        net.build();
        
        store.impose(new NetworkFlow(net));                          // impose the Constraints of the Network to the store
     
        search = new DepthFirstSearch<>();
        select = new InputOrderSelect<>(store, x, new IndomainMedian<>());
        
        boolean result_search = search.labeling(store, select, cost);

        if(result_search){ 
            cost_vl += search.getCostValue();

                int counter = 0;
                for(int i = 0; i < x.length; i++){
                    if(x[i].value() == 1){
                        if(i == 0 || i == 21){ counter++; }                 //omitiendo imprimir el source y sink
                        else{   
                            System.out.println("Arc[" + i + "] => " + arcs[i].name()+" => cost: "+weights[i-1]);
                            counter++;
                        }
                    }
            }
            System.out.println("\nTOTAL COST: "+cost_vl);
            // path in order
            int cnt = 0;
            int index = 0;
            result += arcs[0].head.name + "->";                             // start node

            while(cnt < counter-2){
                for(int i = 0; i < x.length; i++){
                    if(arcs[i].name().substring(0, arcs[i].name().indexOf("-")).contains(arcs[index].head.name) && x[i].value() == 1){
                        index = i;   
                        break;
                    }     
                }
                result += arcs[index].head.name + "->";
                cnt++; 
            }

            result = result.substring(0, result.length()-2);
            System.out.println("PATH: "+result);
            
        }
        else System.out.println("No solution was found");

}

    public String get_cost_vl(){
        return cost_vl;
    }

    public String get_result(){
        return result;
    }


}


