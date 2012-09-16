/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;


import JaCoP.constraints.netflow.NetworkBuilder;
import JaCoP.constraints.netflow.NetworkFlow;
import JaCoP.constraints.netflow.simplex.Arc;
import JaCoP.constraints.netflow.simplex.Node;
import JaCoP.constraints.netflow.simplex.NetworkSimplex;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Roberto Marroquin
 */
public class AntCP {
    
    Store store = new Store();
    
    NetworkBuilder net = new NetworkBuilder();
    
    int num_arcs = 10;                       // this should be displayed in the GUI
    
    IntVar [] x = new IntVar[num_arcs];             // 10 is the number of paths
    
    public void Network_definition(int [] weights) {
        
        System.out.println("Ant Critical Path:\n");
        List<Node> list_nodes = new ArrayList<>();
        List<Arc> list_arcs = new ArrayList<>();

        x[0] = new IntVar(store,"source->A",0,15);
        x[1] = new IntVar(store,"source->B",0,15);
        x[2] = new IntVar(store,"source->C",0,15);
        x[3] = new IntVar(store,"A->B",0,15);
        x[4] = new IntVar(store,"A->C",0,15);
        x[5] = new IntVar(store,"A->D",0,15);
        x[6] = new IntVar(store,"B->D",0,15);
        x[7] = new IntVar(store,"C->D",0,15);
        x[8] = new IntVar(store,"C->sink",0,15);
        x[9] = new IntVar(store,"D->sink",0,15);
        
        list_nodes.add(0,net.addNode("source",5)); 
        list_nodes.add(1,net.addNode("A", 0));  
        list_nodes.add(2,net.addNode("B", 0)); 
        list_nodes.add(3,net.addNode("C", 0));  
        list_nodes.add(4,net.addNode("D", 0)); 
        list_nodes.add(5,net.addNode("sink", -5)); 
        
       
        list_arcs.add(0,net.addArc(list_nodes.get(0), list_nodes.get(1), weights[0],x[0]));
        list_arcs.add(1,net.addArc(list_nodes.get(0),list_nodes.get(2), weights[1],x[1]));
        list_arcs.add(2,net.addArc(list_nodes.get(0),list_nodes.get(3), weights[2],x[2]));
        list_arcs.add(3,net.addArc(list_nodes.get(1), list_nodes.get(2), weights[3],x[3])); 
        list_arcs.add(4,net.addArc(list_nodes.get(1),list_nodes.get(3), weights[4],x[4]));
        list_arcs.add(5,net.addArc(list_nodes.get(1),list_nodes.get(4), weights[5],x[5])); 
        list_arcs.add(6,net.addArc(list_nodes.get(2),list_nodes.get(4), weights[6],x[6]));
        list_arcs.add(7,net.addArc(list_nodes.get(3),list_nodes.get(4), weights[7],x[7])); 
        list_arcs.add(8,net.addArc(list_nodes.get(3),list_nodes.get(5), weights[8],x[8]));
        list_arcs.add(9,net.addArc(list_nodes.get(4),list_nodes.get(5), weights[9],x[9]));
        
        IntVar cost = new IntVar(store, "cost", 0, 100);
        net.setCostVariable(cost);
        
        NetworkFlow net_flow = new NetworkFlow(net);
        
        net_flow.impose(store);                         // impose the Constraints of the Network to the store
        
        
        NetworkSimplex result = new NetworkSimplex(list_nodes, list_arcs);
        result.print();
        
    }
    
    public void search_solution(List list_arcs,List list_nodes){
        
        
    }
    
    
}
