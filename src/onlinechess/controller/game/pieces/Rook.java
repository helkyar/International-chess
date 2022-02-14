/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.game.pieces;

import onlinechess.views.Board;

/**
 *
 * @author admin
 */
public class Rook extends PiecesChess{
    
    public static boolean allowed(int from, int to, String piece, String target, int w, int h, Board board){
        if(!isDiffTeam(piece, target, w, h)){return false;}
        
        //Aboid board out of bounds (left-rigth)
        int row = (from-1) / w * w;
        
        //Piece logical moves
        if(Math.abs(from - to) % w == 0){  //up & down            
            return upDownCollisions(from, to, w, h, board);
            
        } else if(to > row && to <= row + w){ //left & rigth
            return leftRigthCollisions(from, to, row, w, board);
        }  


        return false;
                                                 
        //Know if target tile is full (v)
        //Know if target cell is fiend/foe (v)
        //Aboid bumping into other pieces
//        else if((to +  w) > row && to <= row + w && to < row ){return true;} //DOUBLE //Strange L

    }
}
