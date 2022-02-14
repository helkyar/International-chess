/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.game.pieces;

import java.util.ArrayList;
import java.util.Map;
import onlinechess.views.game.Board;

/**
 *
 * @author admin
 */
public class King extends PiecesChess{
    
    public static boolean allowed(int from, int to, String piece, String target, int w, int h, Board board, ArrayList<Integer> check, Map<Integer, Integer> castling){
        if(!isDiffTeam(piece, target, w, h)){return false;}
    
        //"-1" avoids problems with rigth side
        int row = (from-1) / w * w; 
        boolean inBounds = to > row && to <= row + w;
        boolean uBound = to > row + w && to <= row + w*2;
        boolean dBound = to > row - w && to != row+1;
        
        if((to == from + 1 || to == from - 1) && inBounds){return true;}
        else if(to == from + w || to == from - w ){return true;}
        else if((to == from + w -1 || to == from + w +1) && uBound){return true;}
        else if((to == from - w -1 || to == from - w +1) && dBound){return true;}
        
        //Castling limitation
        if(check.contains(from)){return false;} //king in check
        if(castling.containsKey(from)){return false;} //king or rook moved
        //Castling logical move
        return castlingLogicalMove(from, to, piece, w, h, board);

    }

    private static boolean castlingLogicalMove(int from, int to, String piece,int w, int h, Board board) {
        if(board.getTilePiece(from + 1).equals("-") && board.getTilePiece(from + 2).equals("-") &&
        board.getTilePiece(from + 3).equalsIgnoreCase("R") && to == from + 2){
            if(!isDiffTeam(piece, board.getTilePiece(from + 3), w, h)){return true;}
        }
        
        else if(board.getTilePiece(from - 1).equals("-") && board.getTilePiece(from - 2).equals("-") &&
        board.getTilePiece(from - 3).equalsIgnoreCase("R") && to == from - 2){
            if(!isDiffTeam(piece, board.getTilePiece(from - 3), w, h)){return true;}
        }
                
        return false;
    }
}
