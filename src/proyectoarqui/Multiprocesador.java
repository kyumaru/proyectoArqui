/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoarqui;

/**
 *
 * @author b12422
 */
public class Multiprocesador {

    /**
     * @param args the command line arguments
     */
    
    
    
    
    //hilo principal
    public static void main(String[] args) {
        // TODO code application logic here
        //global=varialbes de proceso
        
        int [] x= {0};
                
        CpuThread cpu0 = new CpuThread(x);
        cpu0.start();
        
        
        CpuThread cpu1 = new CpuThread(x);
        cpu1.start();
        
          
    }
    
}
