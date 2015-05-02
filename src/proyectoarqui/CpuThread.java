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
    
    int x;
    
    CpuThread(int[] x){
        this.x=x[0];
    }
    
    public void run(){
        
        //System.out.println("MyThread running");
        System.out.println(++x);
    }
    
}
