/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.pieces;

import onlinechess.controller.Game;
import onlinechess.helpers.conf;
import onlinechess.views.Board;
import onlinechess.views.ChessApp;

/**
 *
 * @author admin
 */
public class Pawn extends PiecesChess{
    
    private static boolean checkOnlyForward(boolean side, int from, int to, String piece){
        
        //Pawns can't eat pieces on it's path "(+/-)Board.w" must be aplied to the method
        boolean collisionDown = upDownCollisions(from, to+Board.w);
        boolean collisionUp = upDownCollisions(from, to-Board.w);
        //Out of bounds
        if(from % Board.w == 0 && to - 1 % Board.w == 0){return false;}       
        if(from - 1 % Board.w == 0 && to % Board.w == 0){return false;}
        
        //Player insensitive moves
        if(side){            
            //Eating
            if((from-to == Board.w+1 || from-to == Board.w-1) && !Game.board.isTileEmpty(to)){return true;}
            //Piece logical moves & collisions 
            else if(from - to == Board.w && collisionUp){return true;} //only down
            else if(from-to == Board.w*2 && from > Board.w*(Board.h-2)  && collisionUp){return true;}
            
        } else if(!side){            
            //Eating
            if((from-to == -Board.w+1 || from-to == -Board.w-1) && !Game.board.isTileEmpty(to)){return true;}
            //Piece logical moves & collisions  
            else if(from - to == -Board.w  && collisionDown){return true;} //only up
            else if(from - to == -Board.w*2 && from <= Board.w*2  && collisionDown){return true;}
        }
        
        return false;
    }
    
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}
                
        //Pawn logic
        boolean side = conf.WHITES.contains(piece);
        return checkOnlyForward(side, from, to, piece);
    }
}
