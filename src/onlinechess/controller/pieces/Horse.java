/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.pieces;

import onlinechess.views.Board;

/**
 *
 * @author admin
 */
public class Horse extends PiecesChess{
   
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}
        
        //Board out of bounds
        //"-1" avoids problems with rigth side
        int row = (from-1) / Board.w * Board.w; 
        boolean inBound = (to > row + Board.w && to <= row + Board.w*2) || (to > row - Board.w && to <= row);
        boolean rigthBound = to >= row + Board.w && to <= row + Board.w - Board.w;
        boolean uBound = (to > row + Board.w*2 && to <= row + Board.w*3);
        boolean dBound = (to > row - Board.w*2 && to <= row + Board.w);
        
        //Piece logical moves
        if((to == from + 2 + Board.w || to == from + 2 - Board.w) && inBound){return true;} //Rigth
        else if((to == from - 2 + Board.w || to == from - 2 - Board.w) && inBound){return true;} //Left      
        else if((to == from + 2 * Board.w  + 1|| to == from + 2 * Board.w - 1) && uBound){return true;} //up       
        else if((to == from - 2 * Board.w + 1|| to == from - 2 * Board.w - 1)&& dBound){return true;} //down

        return false;
        //Know if target tile is full (v)
        //Know if target cell is fiend/foe (v)
        //Aboid bumping into other pieces
    }
}
