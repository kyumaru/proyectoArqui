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
public class Directory {
    
    static final int dBlockCount=8;//8 blocks map to this dir dirEntrys
    int id;//which node this directory belongs to
    DirEntry[] dirEntrys=new DirEntry[dBlockCount];
  
    Directory(int id){
        this.id=id;
    }
    
    void setDir(DirEntry[] dirEntrys){//set this dir direntrys
       this.dirEntrys=dirEntrys;
    }
    
     DirEntry[] getDir(){//get this dir direntrys
      return this.dirEntrys;
    }
  
}
