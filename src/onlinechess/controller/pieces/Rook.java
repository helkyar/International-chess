/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.pieces;

import static onlinechess.controller.pieces.PiecesChess.isDiffTeam;
import onlinechess.views.Board;

/**
 *
 * @author admin
 */
public class Rook extends PiecesChess{
    
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}
        
        //Aboid board out of bounds
        int row = (from-1) / Board.w * Board.w;
        System.out.println(row);
        //Piece logical moves
        if(Math.abs(from - to) % Board.w == 0 ){return true;}
        else if(to > row && to <= row + Board.w){return true;}  //STRANGE RIGTH

//        else if((to +  Board.w) > row && to <= row + Board.w && to < row ){return true;} //DOUBLE //Strange L

        return false;
                                                 
        //Know if target tile is full (v)
        //Know if target cell is fiend/foe (v)
        //Aboid bumping into other pieces
    }
}
