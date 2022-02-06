/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.pieces;

import onlinechess.controller.GameChess;
import onlinechess.helpers.conf;
import onlinechess.views.Board;
import onlinechess.views.Chess;

/**
 *
 * @author admin
 */
public class Pawn extends PiecesChess{
    
    private static boolean checkOnlyForward(boolean side, int from, int to, String piece){
        
        //Pawns can't eat pieces on it's path "(+/-)Board.w" must be aplied to the method
        boolean collisionDown = upDownCollisions(from, to+Board.w);
        boolean collisionUp = upDownCollisions(from, to-Board.w);
        
        if(side && collisionUp){
            if((from-to == Board.w+1 || from-to == Board.w-1) && !GameChess.board.isTileEmpty(to)){return true;}
            if(from-to == Board.w*2 && from > Board.w*(Board.h-2)){return true;}
            else if(from - to == Board.w){return true;} //only down
            
        } else if(!side && collisionDown){            
            if((from-to == -Board.w+1 || from-to == -Board.w-1) && !GameChess.board.isTileEmpty(to)){return true;}
            if(from - to == -Board.w*2 && from <= Board.w*2){return true;}
            else if(from - to == -Board.w){return true;} //only up
        }
        
        return false;
    }
    
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}
                
        //Collision check
        //Out of bounds while eating
        //Pawn logic
        boolean side = conf.WHITES.contains(piece);
        if(Chess.isWhite){return checkOnlyForward(side, from, to, piece);}
        else             {return checkOnlyForward(side, from, to, piece);}

        //Consider pieces in path
        //diagonal eat
        //check for out of board

    }
}
