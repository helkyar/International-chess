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
public class King extends PiecesChess{
    
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}
    
        //"-1" avoids problems with rigth side
        int row = (from-1) / Board.w * Board.w; 
        boolean inBounds = to > row && to <= row + Board.w;
        boolean uBound = to > row + Board.w && to <= row + Board.w*2;
        boolean dBound = to > row - Board.w && to != row+1;
        
        if((to == from + 1 || to == from - 1) && inBounds){return true;}
        else if(to == from + Board.w || to == from - Board.w ){return true;}
        else if((to == from + Board.w -1 || to == from + Board.w +1) && uBound){return true;}
        else if((to == from - Board.w -1 || to == from - Board.w +1) && dBound){return true;}
        
        //Crazy board(on edge KILL: [to == from + Board.w +1 || to == from - Board.w -1])
        //else if(to == from + Board.w*2 -1 || to == from - Board.w*2 +1){return true;}
        
        //Board out of bounds
        
        return false;
        //Know if target tile is full (v)
        //Know if target cell is fiend/foe (v)
        //Aboid bumping into other pieces
    }
}
