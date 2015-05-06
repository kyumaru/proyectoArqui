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
public class DirEntry {
    
    static final int cacheCount=3;//how many caches in multiprocessor
    boolean[]bitVector=new boolean[cacheCount];
    String status="U";//all starts uncached
    int id;// equals block TAG, there is a 1:1 relationship block-DirEntry
    
   
    DirEntry(int id){
        this.id=id;
    }
    
     DirEntry(){}
   
    int getId(){
        return this.id;
    }
    
     
    void setStatus( String status){
        this.status=status;
    }
    
    String getStatus(){
        return this.status;
    }
    
    void setBVat(int index, boolean value){//sets bitVector value at index 
        this.bitVector[index]=value;
    }
   
    boolean getBVat(int index){//gets bitVector value at index
        return this.bitVector[index];
    }
    
}
