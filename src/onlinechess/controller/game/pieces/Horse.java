/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.game.pieces;

/**
 *
 * @author admin
 */
public class Horse extends PiecesChess{
   
    public static boolean allowed(int from, int to, String piece, String target, int w, int h){
        if(!isDiffTeam(piece, target, w, h)){return false;}
        
        //Board out of bounds
        //"-1" avoids problems with rigth side
        int row = (from-1) / w * w; 
        boolean inBound = (to > row + w && to <= row + w*2) || (to > row - w && to <= row);
        boolean rigthBound = to >= row + w && to <= row + w - w;
        boolean uBound = (to > row + w*2 && to <= row + w*3);
        boolean dBound = (to > row - w*2 && to <= row + w);
        
        //Piece logical moves
        if((to == from + 2 + w || to == from + 2 - w) && inBound){return true;} //Rigth
        else if((to == from - 2 + w || to == from - 2 - w) && inBound){return true;} //Left      
        else if((to == from + 2 * w  + 1|| to == from + 2 * w - 1) && uBound){return true;} //up       
        else if((to == from - 2 * w + 1|| to == from - 2 * w - 1)&& dBound){return true;} //down

        return false;
        //Know if target tile is full (v)
        //Know if target cell is fiend/foe (v)
        //Aboid bumping into other pieces
    }
}
