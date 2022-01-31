/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess;

/**
 *
 * @author admin
 */
public class PiecesChess {}

//To calculate allowed move initial and final positions are needed
class Pawn{
    private boolean targed;
    private int from;
    private int to;
    
    public Pawn(int from, int to, boolean targed){
        this.targed = targed;
        this.from = from;
        this.to = to;
    }
    
    public boolean allowed(){
        if(from - to % 8 == 0 ){return true;}        
        if(-1*(from - to) % 8 == 0 ){return true;}
        else {return false;}
        
        //Know if targed cell is full
        
    }
}
class Tower{}
class Horse{}
class Bishop{}
class Queen{}
class King{}