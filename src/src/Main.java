/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;
import gui.Window;
import JaCoP.core.*;
import JaCoP.constraints.*;
import JaCoP.search.*;
import JaCoP.set.core.*;
import JaCoP.set.constraints.*;
import JaCoP.set.search.*;


/**
 *
 * @author eave
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Window okno = new Window();
//        okno.setVisible(true);
        
        CuttingStock cutme = new CuttingStock();
        int pieces[] = {2,1};
        int res[]    = {1,2};
        
        cutme.model(3, pieces,res);
        
        cutme.searchSpecific();
        
//        AntCP eatme = new AntCP();
        
       // int[] weights = {5,1,7,9,15,15,4,7,6,3,7,8,9,10,3,3,9,9,8,7};
       /* int[] weights = {1,1,3,3,10,10,6,6,7,7,10,10,5,5,6,6,8,8,9,9};
        
        eatme.Network_definition(weights,0,5);*/
       
        return;
    }
}