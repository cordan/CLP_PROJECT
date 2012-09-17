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
        
        System.out.println("Hello world! !\n");
        System.out.println("Dodane w netbeans!\n");
        System.out.println("Push me in my netbeans!\n");
        
        Window okno = new Window();
        okno.setVisible(true);
        
        CuttingStock cutme = new CuttingStock();
        int pieces[] = {2,1};
        int res[]    = {1,1};
        
        cutme.model(3, pieces,res);
        
        cutme.searchSpecific();
        
        AntCP eatme = new AntCP();
        
        int[] weights = {5,7,15,4,6,7,9,3,9,8};
        
        eatme.Network_definition(weights);
        eatme.searchSpecific();
        
    }
}