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
public class CpuThread extends Thread{
    
    volatile Block block;
    volatile Clock clock;
    
    CpuThread(Block block, Clock clock){
        this.block=block;
        this.clock=clock;
    }
    
    public synchronized void test(){
       System.out.println("thread = "+Thread.currentThread().getId()+" tick actual = "+clock.getClockTick());
       System.out.println(++block.words[0]);  
       System.out.println("thread = "+Thread.currentThread().getId()+" tick actual = "+clock.getClockTick());        
    }
    
    public void run(){
        this.test();
        //System.out.println("MyThread running");
    }
    
}
