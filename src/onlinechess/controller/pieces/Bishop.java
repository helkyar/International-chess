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
public class Bishop extends PiecesChess{
    
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}
        int a = 0, b = 0, c = 0, d = 0;
        for(int i = 1, prev=1; i < 35; i++){            
            //Aboid board out of bounds
            if(((from + (Board.w + 1)*i)-1) % Board.w == 0){a++;}
            if((from + (Board.w - 1)*i) % Board.w == 0){b++;}
            if((from - (Board.w + 1)*i) % Board.w == 0){c++;}
            if(((from - (Board.w - 1)*i)-1) % Board.w == 0){d++;}
            //Piece logical moves
            if(to == from + (Board.w + 1)*i && a < 1){return true;} 
            else if(to == from + (Board.w - 1)*i && b < 1){return true;}
            else if(to == from - (Board.w + 1)*i && c < 1){return true;} 
            else if(to == from - (Board.w - 1)*i && d < 1){return true;}
        }
        return false;
    }
        //Know if target tile is full (v)
        //Know if target cell is fiend/foe (v)
        //Aboid bumping into other pieces
}

