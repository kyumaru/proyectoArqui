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
public class CacheBlock {
    
    Block cacheblock;
    String status="U";//all starts uncached
    int cacheTAG;//this cacheblock TAG entry
    
    
    void setWordat(int index, int value){//sets word value at index 
        this.cacheblock.words[index]=value;
    }
   
    int getWordat(int index){//gets bitVector value at index
        return this.cacheblock.words[index];
    }
    
    
    void setBlock(Block cacheblock){//sets this cacheblock block 
        this.cacheblock=cacheblock;
    }
   
    Block getBlock(){//gets this cacheblock block 
        return this.cacheblock;
    }
     
}
