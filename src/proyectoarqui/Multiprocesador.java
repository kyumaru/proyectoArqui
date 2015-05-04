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
         archivo = new File ("C:\\Users\\raskolnnikov\\Documents\\NetBeansProjects\\proyectoArqui\\build\\classes\\proyectoarqui\\test.txt");
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
        
        final int SMEMSIZE=24;//shared mem size
       
        RegistersCPU regsCpu0=new RegistersCPU(0);//regs cpu0
        RegistersCPU regsCpu1=new RegistersCPU(1);
        RegistersCPU regsCpu2=new RegistersCPU(2);
        
        Block []sharedMem= new Block[SMEMSIZE];//24 blocks shared memory
        
        Multiprocesador m = new Multiprocesador();
        
        //cargar hilos en el vector de instrucciones
        m.loadthreads();
        
        
        Block ir0=new Block();//instruction register cpu0
        Block ir1=new Block();//
        Block ir2=new Block();//
        
        Cache cache0=new Cache(0);
        Cache cache1=new Cache(1);
        Cache cache2=new Cache(2);

        
        
        Block myBlock= new Block(0);
        Clock myClock= new Clock(2);
                
        CpuThread cpu0 = new CpuThread(myBlock, myClock);     
        CpuThread cpu1 = new CpuThread(myBlock, myClock);
        cpu0.start();
        cpu1.start();
            
                
        
          
    }
    
}
