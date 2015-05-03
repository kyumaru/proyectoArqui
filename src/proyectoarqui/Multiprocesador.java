/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoarqui;

import java.util.ArrayList;

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
        //global=variables de proceso
        
        final int SMEMSIZE=24;//shared mem size
       
        RegistersCPU regsCpu0=new RegistersCPU(0);//regs cpu0
        RegistersCPU regsCpu1=new RegistersCPU(1);
        RegistersCPU regsCpu2=new RegistersCPU(2);
        
        Block []sharedMem= new Block[SMEMSIZE];//24 blocks shared memory
        
        ArrayList<Integer> ivector = new ArrayList<>();//instruction vector
        
        Block ir0=new Block();//instruction register cpu0
        Block ir1=new Block();//
        Block ir2=new Block();//
        
        Cache cache0=new Cache(0);
        Cache cache1=new Cache(1);
        Cache cache2=new Cache(2);

        
        
        Block myBlock= new Block(0);
                
        CpuThread cpu0 = new CpuThread(myBlock);     
        CpuThread cpu1 = new CpuThread(myBlock);
        cpu0.start();
        cpu1.start();
            
                
        
          
    }
    
}
