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
public class CpuThread extends Thread{
    
   int cpuId;
   volatile RegistersCPU regsCpu;//regs cpu0
   volatile Cache cache;
   volatile Block ir;
   volatile Block[] sharedMem;
   volatile Clock clock;
      
   //constructor should receive the instruction thread to execute
    CpuThread(int cpuId,Block[] sharedMem,Clock clock){
        this.cpuId=cpuId;
        this.sharedMem=sharedMem;
        this.cache=new Cache(cpuId);
        this.ir=new Block();
        this.regsCpu=new RegistersCPU(cpuId);
        this.clock=clock;   
    }
    
    public synchronized void test(){
       //System.out.println(++block.words[0]);
       // this.loadWord(ir,cache,sharedMem,regsCpu,clock);
        
       
    }
    
    public synchronized void exec(){
        
        switch (ir.words[0]) {
            
            case 8:  //;
                     break;
            
            case 32:  //;
                     break;
            
            case 34:  //;
                     break;
                
            case 35:   this.loadWord(ir,cache,sharedMem,regsCpu,clock);
                       break;
            
            case 43:  //;
                     break;
            
            case 4:  //;
                     break;
            
            case 5:  //;
                     break;
                
            case 63:  //;
                     break;
                
        }
    }
    
    
    public synchronized void loadWord( Block ir,Cache cache,Block []sharedMem,
                                       RegistersCPU regsCpu,Clock clock){
        
        /*
            ir Block is used to hold the 4 int MIPS intructions
            ir[0] opcode already used at this point
            ir[1] reg base
            ir[2] reg destiny where the word will load 
            ir[3] offset
        
        */
        final int BB=16;//bytes per block,  bytes not represented just used for this calc
        final int BW=4;//bytes per word
        
        int memAddress=ir.words[1]+ir.words[3];//word number as shared memory index
        //calc bloq#, TAG, W in bloq# word#
        int headByte=memAddress*4;//starting byte of the word in shared memory 
        int TAG=headByte/BB; //block# in shared memory, 16 bytes per block  
        int W=(headByte%BB)/BW;// word# into this block
        
        CacheController myController=new CacheController();
        regsCpu.regs[ir.words[2]]=myController.getWord(TAG, W, cache, sharedMem,clock);
        
    }
    
    
    
    public void run(){
        this.test();
        //System.out.println("MyThread running");
    }
    
}
