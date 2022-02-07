/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.pieces;

import onlinechess.controller.Game;
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
        
        //Crazy board: on edge -> KILL (to == from + Board.w +1 || to == from - Board.w -1)
        //else if(to == from + Board.w*2 -1 || to == from - Board.w*2 +1){return true;}

        //Castling limitation
        if(Game.checkPos.contains(from)){return false;} //king in check
        if(!Game.castlingAllowed.containsKey(from)){return false;} //king or rook moved
        //Castling logical move
        return castlingLogicalMove(from, to, piece);

    }

    private static boolean castlingLogicalMove(int from, int to, String piece) {
        if(Game.board.getTilePiece(from + 1).equals("-") && Game.board.getTilePiece(from + 2).equals("-") &&
        Game.board.getTilePiece(from + 3).equalsIgnoreCase("R") && to == from + 2){
            if(!isDiffTeam(piece, Game.board.getTilePiece(from + 3))){return true;}
        }
        
        else if(Game.board.getTilePiece(from - 1).equals("-") && Game.board.getTilePiece(from - 2).equals("-") &&
        Game.board.getTilePiece(from - 3).equalsIgnoreCase("R") && to == from - 2){
            if(!isDiffTeam(piece, Game.board.getTilePiece(from - 3))){return true;}
        }
                
        return false;
    }
}
