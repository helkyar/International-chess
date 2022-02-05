/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.pieces;

/**
 *
 * @author admin
 */
public class Queen extends PiecesChess{
    
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}
        
        if(Rook.allowed(from, to, piece, target)|| Bishop.allowed(from, to, piece, target))
        {return true;}
        
        return false;
        //Know if target tile is full (v)
        //Know if target cell is fiend/foe (v)
        //Aboid bumping into other pieces
    }
}
