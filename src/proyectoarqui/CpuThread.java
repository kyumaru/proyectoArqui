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
   volatile int pc;
   volatile ArrayList<Integer> iVector;  
   volatile ArrayList<Integer> pcVector;

      
   //constructor should receive the instruction thread to execute
    CpuThread(int cpuId,Block[] sharedMem,Clock clock, ArrayList<Integer> iVector, ArrayList<Integer> pcVector){
        this.cpuId=cpuId;
        this.sharedMem=sharedMem;
        this.cache=new Cache(cpuId);
        this.ir=new Block();
        this.regsCpu=new RegistersCPU(cpuId);
        this.clock=clock;   
        this.pc = 0;
        this.iVector = iVector;
        this.pcVector = pcVector;
    }

    public synchronized void runProgram(){
        //int currentThread = 0;
        System.out.println("Starting run..\n");       
        while(this.pc < this.iVector.size()){
            //Load instruction to IR
                    System.out.println("\npc= "+pc);
            ir.words[0] = this.iVector.get(pc);            
            ir.words[1] = this.iVector.get(pc+1);
            ir.words[2] = this.iVector.get(pc+2);
            ir.words[3] = this.iVector.get(pc+3);
            //Execute current instruction
            this.exec();
        }
        
    }
    
    public synchronized void exec(){
        switch (ir.words[0]) {
            
            case 8://DADDI add inmediate
                /*
                ir Block is used to hold the 4 int MIPS intructions
                ir[0] opcode already used at this point
                ir[1] Rsource operand1
                ir[2] R destiny
                ir[3] inmediate, constant
                */
                     regsCpu.regs[ir.words[2]]=ir.words[1]+ir.words[3];  
                     //System.out.println("DADDI");
                     this.pc += 4;
                     break;
            
            case 32:  //DADD add content of 2 Regs and store into Rsource
                /*
                ir Block is used to hold the 4 int MIPS intructions
                ir[0] opcode already used at this point
                ir[1] Rsource operand1
                ir[2] Rsource operand2
                ir[3] R destiny
                */  
                     regsCpu.regs[ir.words[3]]=regsCpu.regs[ir.words[1]]+regsCpu.regs[ir.words[2]]; 
                     this.pc += 4;
                     break;
            
            case 34:  //DSUB take away content of 1 Reg of another Reg and store into Rsource
                /*
                ir Block is used to hold the 4 int MIPS intructions
                ir[0] opcode already used at this point
                ir[1] Rsource operand1
                ir[2] Rsource operand2
                ir[3] R destiny
                */  
                     regsCpu.regs[ir.words[3]]=regsCpu.regs[ir.words[1]]-regsCpu.regs[ir.words[2]];
                     this.pc += 4;
                     break;
                
            case 35:
                    this.loadWord(ir,cache,sharedMem,regsCpu,clock);
                    System.out.println("LW");  
                    this.pc += 4;
                    break;
            
            case 43:  
                     this.storeWord(ir,cache,sharedMem,regsCpu,clock);
                     System.out.println("SW");  
                     this.pc += 4;
                     break;
            
            case 4:  //beq if ($s == $t) pc += i << 2 ;                
                    System.out.println("BEQ"); 
                    
                    /*if(this.regsCpu.getReg(ir.words[1]) == this.regsCpu.getReg(this.ir.words[2])){
                        int offset = this.ir.words[3];
                        this.pc += (offset * 4);
                    }
                    else{
                        this.pc += 4;
                    }*/
                    this.pc += 4;
                    break;
            
            case 5:  //;
                     this.pc += 4;
                     break;
                
            case 63:  //;
                     System.out.println("fin del hilo");  
                     this.pc += 4;
                     break;
            default :
                    this.pc += 4;
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
    
    public synchronized void storeWord( Block ir,Cache cache,Block []sharedMem,
                                       RegistersCPU regsCpu,Clock clock){
        
        
	int word = 0;//4 words/block, int in java are 4 bytes
        final int BB=16;//bytes per block,  bytes not represented just used for this calc
        final int BW=4;//bytes per word
        
        int memAddress=ir.words[1]+ir.words[3];//word number in shared memory index
        
        //calc bloq#, TAG, W in bloq# word#
        int headByte=memAddress*4;//starting byte of the word in shared memory 
        int TAG=headByte/BB; //block# in shared memory, 16 bytes per block  
        int W=(headByte%BB)/BW;// position word# into this block
        word = regsCpu.regs[ir.words[2]];// registro a guardar en chache
        CacheController myController=new CacheController();
        myController.setWord(TAG, W, cache, sharedMem,clock,word);
        
    }
    
    
    
    
    public void run(){
        System.out.println("Iniciando..\n");
        this.runProgram();
    }
    
}
