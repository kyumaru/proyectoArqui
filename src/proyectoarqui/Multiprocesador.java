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
   
    public void loadThreadFiles(ArrayList<Integer> ivector, ArrayList<Integer> PCvector, ArrayList<String> threadFileList){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
                    int pos = 0;
        for(int i=0; i<threadFileList.size(); ++i){
            String currentFile = threadFileList.get(i);
            try {
                // Apertura del fichero y creacion de BufferedReader para poder
                // hacer una lectura comoda (disponer del metodo readLine()).
                //archivo = new File ("/home/david/1.txt");
                archivo = new File (currentFile);
                fr = new FileReader (archivo);
                br = new BufferedReader(fr);
 
                // Lectura del fichero
                System.out.println(PCvector.size());
                String linea;
                while((linea=br.readLine())!=null){ // cada hilo es una fila del archivo
                  System.out.println(pos + " "+ linea);
                  for (String s : linea.split("\\s")){
                      //se extrae la instrucción y se inserta en el vector de instrucciones
                      this.ivector.add(Integer.parseInt(s));  
                  }
                   // se marca el número de bloque donde inicia el siguiente hilo
                  pos = pos + 4;
                  //PCvector.add(pos);
                }
                                PCvector.add(pos);
                //anula la última posición pues es inválida
                //PCvector.remove(PCvector.size()-1);
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
    }
   
     void loadthreads(ArrayList<String> fileList) {
        this.loadThreadFiles(this.ivector, this.PCvector, fileList);    
     }
   
    //hilo principal
    public static void main(String[] args) {
        // TODO code application logic here
        //global=variables de proceso
       
        Multiprocesador m = new Multiprocesador();  
        //cargar hilos en el vector de instrucciones
        ArrayList<String> threadFileList = new ArrayList<String>();
        threadFileList.add("C:\\hilos\\1.txt");
       threadFileList.add("C:\\hilos\\2.txt");
        threadFileList.add("C:\\hilos\\3.txt");
        threadFileList.add("C:\\hilos\\4.txt");
       
        m.loadthreads(threadFileList);
       
        //crear un reloj nuevo para 1 thread
        //new Clock(n) n must be equal to the # of threads created otherwise error no current thread
        Clock myClock= new Clock(1);
 
       
        //create shared mem
        final int SMEMSIZE=24;//shared mem size shoul be 24    
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
        CpuThread cpu0 = new CpuThread(0,sharedMem,myClock, m.ivector, m.PCvector);
        //cpu0.ir.words=y;//first exect()
        //cpu0.ir.words=y;//first exect()
 
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