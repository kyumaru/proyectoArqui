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
     int  [] words= new int[4];//4 words/block
     int TAG;//optional!! number of block-TAG in shared memory
     
     Block(int TAG){
         this.TAG=TAG;
     }
     
     public void setWords(int[] words){
         this.words=words;
     }
     
     public int[] getWords(){
         return this.words;
     }
}
