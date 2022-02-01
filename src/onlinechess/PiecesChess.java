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
    private String targed;
    private String piece;
    private int from;
    private int to;
    
    public Pawn(int from, int to, String targed, String piece){
        this.targed = targed;
        this.piece = piece;
        this.from = from;
        this.to = to;
    }
    
    public boolean allowed(){
        if(Math.abs(from - to) % Board.h == 0 ){return true;} 
        else {return false;}
        
        //Know if targed cell is full (v)
        //Know if targed cell is fiend/foe (v)
        //Aboid bumping into other pieces
    }
}
class Tower{}
class Horse{}
class Bishop{}
class Queen{}
class King{}