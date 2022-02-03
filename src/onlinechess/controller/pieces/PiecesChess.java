/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.pieces;

import onlinechess.views.Board;

//Board borders
//Other team pieces stops advance (a++)

/**
 *
 * @author Javier Palacios
 */
public class PiecesChess {
    
    public static boolean isDiffTeam(String piece, String target){
        
        boolean pieceTeam = piece.equals(piece.toUpperCase());
        boolean targetTeam = target.equals(target.toUpperCase());
        
        return (pieceTeam != targetTeam)|| target.equals("-");         
    }
    
}

//    private String target;
//    private String piece;
//    private int from;
//    private int to;
//    
//    public King(int from, int to, String piece, String target){
//        this.target = target;
//        this.piece = piece;
//        this.from = from;
//        this.to = to;
//    }