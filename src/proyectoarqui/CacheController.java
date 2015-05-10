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
 public class CacheController {
    
    //default constructor
    
    public synchronized int getWord(int TAG, int W, Cache cache,Block []sharedMem,Clock clock){
        final int BB=16;//bytes per block,  bytes not represented just used for this calc
        final int BC=4;//blocks per cache
        //check if block with TAG is present in cache
        int index=TAG%BC;//cache block# to check
        int res=0;//container of result
        int tiqCont=0;
        
        if( this.checkForTag(TAG,index,cache)&& this.checkForStatus(index,cache) ){//cache hit
            res=cache.cacheBlocks[index].cacheblock.words[W];
        }
        else{//cache miss
            //fetch block from shared memory
            //ask for 16 clock tiqs
            tiqCont =this.askTiqs(16,clock);
            Block memBlock=new Block(sharedMem[TAG]);//make a new copy
            res=memBlock.getOneWord(W);
            //TODO save evicted block in sharedMem if status is M
            //cache.cacheBlocks[index].cacheTAG != -1 //do not download invalid cacheblocks
         /*notice how in this implementation a cache block maps to TAG index in memory since it is a Block[]
           in theory the memory is an byte[] so  a cache block maps to cacheTAG*BB headbyte(starting byte) in memory
          */
           ///BUG!!!!! THIS SHOULD GET THE TAG OF INDEX BLOCK IN CACHE AND DOWNLOAD THERE  
	   if( (this.getStatus(index,cache)).equals("M") ){
                tiqCont=this.askTiqs(16,clock);
                sharedMem[(cache.cacheBlocks[index].cacheTAG)]=new Block(cache.cacheBlocks[index].cacheblock);// TAG*BB/4 memory is mapped as int[] index
            }
            //save fetch block in cache
            cache.cacheBlocks[index].cacheblock=memBlock;//cacheblock references new block
            cache.cacheBlocks[index].cacheTAG=TAG;
            cache.cacheBlocks[index].status="S";
        }     
        return res;
    }
    
    //no need to be sync???????
    public int askTiqs(int howMany, Clock clock){
        
        int count=0;
        for(int i=0;i<howMany;++i){
        
            count=clock.getClockTick();
        }
        
        return count;
    }
    public synchronized void setWord(int TAG, int W, Cache cache,Block []sharedMem,Clock clock,int   word ){
        final int BB=16;//bytes per block,  bytes not represented just used for this calc
        final int BC=4;//blocks per cache
        //check if block with TAG is present in cache
        int index=TAG%BC;//cache block# to check
        
        int tiqCont=0;
        
        if( this.checkForTag(TAG,index,cache)&& this.checkForStatus(index,cache) ){//cache hit
            	if( (this.getStatus(index,cache)).equals("M") ){
			cache.cacheBlocks[index].cacheblock.words[W] = word;
		}else{// se encuentra compartido
                         //mensaje de invalidación a los otros caches
			cache.cacheBlocks[index].cacheblock.words[W]= word;
			cache.cacheBlocks[index].status="M";
		}
        }
        else{//cache miss
         
            tiqCont =this.askTiqs(16,clock);
            Block memBlock=new Block(sharedMem[TAG]);//make a new copy
         
            if( (this.getStatus(index,cache)).equals("M") ){
                tiqCont=this.askTiqs(16,clock);
                sharedMem[cache.cacheBlocks[index].cacheTAG]=new Block(cache.cacheBlocks[index].cacheblock);// TAG*BB/4 memory is mapped as int[] index
                //falta escribir el nuevo bloque en cache que contiene word
		cache.cacheBlocks[index].cacheblock= memBlock;
                cache.cacheBlocks[index].cacheblock.words[W] = word;//graba palabra
		cache.cacheBlocks[index].status="M";
		cache.cacheBlocks[index].cacheTAG=TAG;
                
            }
            // sino está uncached o inválida
	     cache.cacheBlocks[index].cacheblock= memBlock;
                cache.cacheBlocks[index].cacheblock.words[W] = word;//graba palabra
		cache.cacheBlocks[index].status="M";
		cache.cacheBlocks[index].cacheTAG=TAG;
	   
        }   
    }
    public synchronized boolean checkForTag(int TAG,int index,Cache cache){
        boolean res=true;
        
        if(cache.cacheBlocks[index].cacheTAG!=TAG)
            res=false;
           
        return res;
    }
    
    
    public synchronized boolean checkForStatus(int index,Cache cache){
        boolean res=true;
        
        if(!(cache.cacheBlocks[index].status).equals("S") ||
           !(cache.cacheBlocks[index].status).equals("M") )
            res=false;
           
        return res;
    }
    
     public synchronized String getStatus(int index,Cache cache){
        return cache.cacheBlocks[index].status;
    }
    
}