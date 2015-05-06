/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoarqui;

import java.util.ArrayList;
import java.io.*;
import java.util.concurrent.CyclicBarrier;
/**
 *
 * @author b12422
 */
public class Multiprocesador {

    /**
     * @param args the command line arguments
     */
    // deben ser static para que todas las instancias referencien la misma localidad de memoria 
    static ArrayList<Integer> ivector ;//instruction vector
    static ArrayList<Integer> PCvector;//pc vector

    public Multiprocesador() {
        this.ivector = new ArrayList<>();
        this.PCvector = new ArrayList<>();
    }
    
     void loadthreads() {
      File archivo = null;
      FileReader fr = null;
      BufferedReader br = null;
 
      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File ("C:\\path\\test.txt");
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);
 
         // Lectura del fichero
         int pos = 0;
         PCvector.add(pos);
         String linea;
         while((linea=br.readLine())!=null){ // cada hilo es una fila del archivo
              System.out.println(pos + " "+ linea);
              for (int x=0;x<linea.length();x++){ 
                  //se extrae la instrucción y se inserta en el vector de instrucciones
                  ivector.add((int)linea.charAt(x));
                  
               }
               // se marca el número de bloque donde inicia el siguiente hilo
              pos = pos + linea.length();
              PCvector.add(pos);
         }
         //anula la última posición pues es inválida
         PCvector.remove(PCvector.size()-1);
      }
      
      catch(IOException e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (IOException e2){ 
            e2.printStackTrace();
         }
      }
   }
    
    //hilo principal
    public static void main(String[] args) {
        // TODO code application logic here
        //global=variables de proceso
        
        //Multiprocesador m = new Multiprocesador();   
        //cargar hilos en el vector de instrucciones
        //m.loadthreads();
        
        //crear un reloj nuevo para 1 thread
        //new Clock(n) n must be equal to the # of threads created otherwise error no current thread
        Clock myClock= new Clock(1);

        
        //create shared mem
        final int SMEMSIZE=8;//shared mem size shoul be 24    
        Block []sharedMem= new Block[SMEMSIZE];//24 blocks shared memory
       
        for(int i=0; i<sharedMem.length;i++){//java does not initialize def type array elements
            sharedMem[i]=new Block();
            sharedMem[i].TAG=i;
        }
        
        
        //load something on shared mem
        int []x= sharedMem[0].words;//fill some info in the array
        x[0]=1;
        x[1]=2;
        x[2]=3;
        x[3]=4;
        
        //load some instruccion on ir
        Block ir0=new Block();//instruction register cpu0
        int []y=ir0.words;   
        y[0]=35;//opcode
        y[1]=2;//base
        y[2]=6;//destiny
        y[3]=1;//offset
              
        CpuThread cpu0 = new CpuThread(0,sharedMem,myClock);
        cpu0.ir.words=y;
        cpu0.start();
        
        
        

   
        
        
        /* testing code do not erase
        Block myBlock= new Block(0);
                
        CpuThread cpu0 = new CpuThread(myBlock);     
        CpuThread cpu1 = new CpuThread(myBlock);
        cpu0.start();
        cpu1.start();
        */  
                
        
          
    }
    
}
