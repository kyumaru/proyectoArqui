/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoarqui;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.BrokenBarrierException;
/**
 *
 * @author raskolnnikov
 */
/*
* Reloj que se encarga de sincronizar los hilos del CPU
*/
public class Clock {
    AtomicInteger currentCycle = new AtomicInteger(0);
    
    /*
    * Se encarga de incrementar el numero de ciclo una vez que todos los hilos han llegado a la barrera ciclica.
    * De esta manera se garantiza que el valor actualiza unicamente una vez por ciclo para todos los hilos.
    */
    class barrierAction implements Runnable{
        private final AtomicInteger currentCyle;
        
        public barrierAction(AtomicInteger currentCyle){
            this.currentCyle = currentCycle;
        };
        
        public void run(){
            this.currentCyle.getAndIncrement();
        }
    } 
    Runnable incrementCycleNumber = new barrierAction(this.currentCycle);
    
    CyclicBarrier barrier;
    
    public Clock(int numThreads){ 
        this.barrier = new CyclicBarrier(numThreads, incrementCycleNumber);
    }
    
    /*
    * Una vez que todos los hilos han completado sus tareas del ciclo actual, se aumenta el contador currentCycle y se les notifica del
    * numero de ciclo nuevo.
    */
    public int getClockTick(){
        int currentTick = this.currentCycle.get();
         try {
           this.barrier.await();
         } 
            catch (BrokenBarrierException ex) {
              return -1;
            }
            catch (InterruptedException ex) {
               return -2;
            } 
        return currentTick;        
    }
}

