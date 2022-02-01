/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess;

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

//To calculate allowed move initial and final positions are needed
class Pawn extends PiecesChess{
    
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}
        System.out.println(from + "  " + to + "  " + Board.h);
        if(Math.abs(from - to) == Board.w ){return true;} 
        else {return false;}
        //Revert not allowed
        //Consider pieces in path
        //diagonal eat
        //Half board + 16
        //check for out of board
    }
}

class Rook extends PiecesChess{
    
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}
        
        return true;
        //Know if target tile is full (v)
        //Know if target cell is fiend/foe (v)
        //Aboid bumping into other pieces
    }
}
class Horse extends PiecesChess{
   
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}
        
        return true;
        //Know if target tile is full (v)
        //Know if target cell is fiend/foe (v)
        //Aboid bumping into other pieces
    }
}

class Bishop extends PiecesChess{
    
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}
        
        return true;
        
        //Know if target tile is full (v)
        //Know if target cell is fiend/foe (v)
        //Aboid bumping into other pieces
    }
}

class Queen extends PiecesChess{
    
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}
        
        return true;
        //Know if target tile is full (v)
        //Know if target cell is fiend/foe (v)
        //Aboid bumping into other pieces
    }
}

class King extends PiecesChess{
    
    public static boolean allowed(int from, int to, String piece, String target){
        if(!isDiffTeam(piece, target)){return false;}

        return true;
        //Know if target tile is full (v)
        //Know if target cell is fiend/foe (v)
        //Aboid bumping into other pieces
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