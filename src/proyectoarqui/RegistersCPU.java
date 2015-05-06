/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoarqui;

/**
 *
 * @author tRasHcAn
 */
public class RegistersCPU {
    
    //default contructor
    
    int[] regs=new int[32];//32 word register 
    int cpuId;//optional wich processor this belongs to
    
    RegistersCPU(int cpuId){
        this.cpuId=cpuId;
    }
    
    public boolean setReg(int index,int value){
        
        boolean res=true;
        
        if(index>0)//reg0 should always be 0
            this.regs[index]=value;
        else
            res=false;
        
        return res;
    } 
    
    public int getReg(int index){
        return regs[index]; 
    }
    
}
