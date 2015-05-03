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
public class Cache {
    
    static final int CBcount=4;//count of cache blocks
    CacheBlock[] cacheBlocks=new CacheBlock[CBcount];
    int id;//this cache identifier
    
    
    Cache(int id){
        this.id=id;
    }
    
    public CacheBlock[] getCache(){
        return this.cacheBlocks;
    }
}
