/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.pieces;

import onlinechess.controller.GameChess;
import static onlinechess.controller.pieces.PiecesChess.isDiffTeam;
import onlinechess.helpers.conf;
import onlinechess.views.Board;
import onlinechess.views.Chess;

/**
 *
 * @author admin
 */
public class Pawn extends PiecesChess{
    
    private static boolean checkOnlyForward(boolean side, int from, int to, String piece){
        
        if(side){
            if(from-to == Board.w*2 && from > Board.w*(Board.h-2)){return true;}
            else if(from - to == Board.w){return true;} //only forward
            
        } else if(!side){
            if(from - to == -Board.w*2 && from <= Board.w*2){return true;}
            else if(from - to == -Board.w){return true;} //only backwards
        }
        
        return false;
    }
    
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}
                
        boolean side = conf.WHITES.contains(piece);
        if(Chess.isWhite){return checkOnlyForward(side, from, to, piece);}
        else             {return checkOnlyForward(side, from, to, piece);}

        //Revert not allowed
        //Consider pieces in path
        //diagonal eat
        //Half board + 16
        //check for out of board
        //only forward
        //transform at the end
    }
}
