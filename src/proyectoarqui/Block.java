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
public class Block {
    
     int  [] words= new int[4];//4 words/block, int in java are 4 bytes
     int TAG;//optional!! number of block-TAG in shared memory
     
     Block(int TAG){
         this.TAG=TAG;
     }
     
     Block(){}//explicit default constructor
     
     public void setAllWords(int[] words){
         this.words=words;
     }
     
     public void setOneWord(int index,int word){
         this.words[index]=word;
     }
     
     public int[] getAllWords(){
         return this.words;
     }
     
     public int getOneWord(int index){
         return this.words[index];
     }
}
