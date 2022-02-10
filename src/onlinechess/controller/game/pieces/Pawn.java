/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.game.pieces;

import onlinechess.helpers.GameConfig;
import onlinechess.views.Board;

/**
 *
 * @author admin
 */
public class Pawn extends PiecesChess{
    
    private static boolean checkOnlyForward(boolean side, int from, int to, String piece, int w, int h, Board board){
        
        //Pawns can't eat pieces on it's path "(+/-)w" must be aplied to the method
        boolean collisionDown = upDownCollisions(from, to+w, w, h, board);
        boolean collisionUp = upDownCollisions(from, to-w, w, h, board);
        //Out of bounds
        if(from % w == 0 && to - 1 % w == 0){return false;}       
        if(from - 1 % w == 0 && to % w == 0){return false;}
        
        //Player insensitive moves
        if(side){            
            //Eating
            if((from-to == w+1 || from-to == w-1) && !board.isTileEmpty(to)){return true;}
            //Piece logical moves & collisions 
            else if(from - to == w && collisionUp){return true;} //only down
            else if(from-to == w*2 && from > w*(h-2)  && collisionUp){return true;}
            
        } else if(!side){            
            //Eating
            if((from-to == -w+1 || from-to == -w-1) && !board.isTileEmpty(to)){return true;}
            //Piece logical moves & collisions  
            else if(from - to == -w  && collisionDown){return true;} //only up
            else if(from - to == -w*2 && from <= w*2  && collisionDown){return true;}
        }
        
        return false;
    }
    
    public static boolean allowed(int from, int to, String piece, String target, int w, int h, Board board, GameConfig conf){
        if(!isDiffTeam(piece, target, w, h)){return false;}
                
        //Pawn logic
        boolean side = (conf.WHITES.contains(piece) && board.isWhite) || (!conf.WHITES.contains(piece) && !board.isWhite);
        return checkOnlyForward(side, from, to, piece, w, h, board);
    }
}
