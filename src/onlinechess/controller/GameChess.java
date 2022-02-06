/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller;

import onlinechess.controller.pieces.King;
import onlinechess.controller.pieces.Rook;
import onlinechess.controller.pieces.Queen;
import onlinechess.controller.pieces.Pawn;
import onlinechess.controller.pieces.Bishop;
import onlinechess.controller.pieces.Horse;
 
import onlinechess.views.Board;
import onlinechess.helpers.conf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import onlinechess.views.Chess;

/**
 *
 * @author Javier Palacios Botejara
 */
public class GameChess extends JPanel implements ActionListener{
    private static JButton prev;
    private static boolean selected;
    private static String piece;
    private static Icon icon;
    private static Color bg;
    
    public static Board board;
    private static boolean blackTurn;
    
    public GameChess(boolean isWhite){
        board = new Board(conf.size(), conf.init(), isWhite);
        board.startBoard(this);
        
        JScrollPane border = new JScrollPane(board);
        border.setPreferredSize(conf.context);
        add(border);
        
    //test ------------------------------------
        JButton btn = new JButton("T");
        btn.addActionListener(this);  
        add(btn);
    //----------------------------------------
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        String tile = btn.getName();
        //Test -----------------------------------------------------------------
//        System.out.println(board.getPosition(btn));      
//        System.out.println(board.getTilePiece(board.getPosition(btn)));
        if(e.getActionCommand().equals("T")){test();return;}
        
        //Turn checker ---------------------------------------------------------
        boolean correctTurn = (
            (conf.WHITES.contains(tile) && !blackTurn)||
            (conf.BLACKS.contains(tile) && blackTurn)
        );
        
        //Select tile ----------------------------------------------------------
        if(!selected && !tile.equals("-") && correctTurn){ 
            prev = btn;
            piece = tile;
            icon = btn.getIcon();
            prev.setBackground(conf.slct);    
                      
            selected = true;
            board.paint(prev);  
            blackTurn = !blackTurn;
            
        //Move piece if allowed ------------------------------------------------
        } else if(selected && allowedMove(btn, tile)){  
            prev.setIcon(null);
            prev.setName("-");
            btn.setName(piece);
            btn.setIcon(icon);
            
            selected = false;
            board.storeState(); 
            board.unPaint();
            
            isThereaWinner();
            isPawnInTheEnd(btn);
            isCheck();
            
        //deselect without move ------------------------------------------------   
        } else if (selected) {
            selected = false;
            board.unPaint();
            blackTurn = !blackTurn;
        }
    }    
    
    /**
     * Returns true if the move is allowed in the game 
     * @param btn source button clicked
     * @param target tile to move to.
     * @return 
     */
    private boolean allowedMove(JButton btn, String target){
        int to = board.getPosition(btn);
        int from = board.getPosition(prev);

            //enroque        
            //win condition
            
        //Pieces allowed moves
        if(piece.equalsIgnoreCase("R"))     {return Rook.allowed(from, to, piece, target);}            
        else if(piece.equalsIgnoreCase("H")){return Horse.allowed(from, to, piece, target);}            
        else if(piece.equalsIgnoreCase("B")){return Bishop.allowed(from, to, piece, target);}            
        else if(piece.equalsIgnoreCase("Q")){return Queen.allowed(from, to, piece, target);}            
        else if(piece.equalsIgnoreCase("K")){return King.allowed(from, to, piece, target);}            
        else if(piece.equalsIgnoreCase("P")){return Pawn.allowed(from, to, piece, target);}
            
        return false;
    }
    
    private void isCheck(){
        
        boolean check = false;
        
        Map<Integer, String> king = new HashMap<>();
        for(int i = 1; i <= Board.w*Board.h; i++){            
            String tile = board.getTilePiece(i);            
            if(tile.equalsIgnoreCase("K")) {king.put(i,tile);}
        }
        
        for(int pos : king.keySet()){
            
            for(int i = 1; i <= Board.w*Board.h; i++){
                String tile = board.getTilePiece(i);

                if(!GameChess.board.isTileEmpty(i) && 
                    conf.WHITES.contains(tile) && king.get(pos).equals("K"))
                {         
                    if(tile.equalsIgnoreCase("R"))     {check = Rook.allowed(i, pos, tile, king.get(pos)) ? true : check;}            
                    else if(tile.equalsIgnoreCase("H")){check = Horse.allowed(i, pos, tile, king.get(pos)) ? true : check;}            
                    else if(tile.equalsIgnoreCase("B")){check = Bishop.allowed(i, pos, tile, king.get(pos)) ? true : check;}            
                    else if(tile.equalsIgnoreCase("Q")){check = Queen.allowed(i, pos, tile, king.get(pos)) ? true : check;}            
                    else if(tile.equalsIgnoreCase("K")){check = King.allowed(i, pos, tile, king.get(pos)) ? true : check;}            
                    else if(tile.equalsIgnoreCase("P")){check = Pawn.allowed(i, pos, tile, king.get(pos)) ? true : check;}
                    if(check){board.paintCheck(pos);}
                    check = false;
                }  
                
                 if(!GameChess.board.isTileEmpty(i) && 
                    conf.BLACKS.contains(tile) && king.get(pos).equals("k"))
                {        
                    if(tile.equalsIgnoreCase("R"))     {check = Rook.allowed(i, pos, tile, king.get(pos)) ? true : check;}            
                    else if(tile.equalsIgnoreCase("H")){check = Horse.allowed(i, pos, tile, king.get(pos)) ? true : check;}            
                    else if(tile.equalsIgnoreCase("B")){check = Bishop.allowed(i, pos, tile, king.get(pos)) ? true : check;}            
                    else if(tile.equalsIgnoreCase("Q")){check = Queen.allowed(i, pos, tile, king.get(pos)) ? true : check;}            
                    else if(tile.equalsIgnoreCase("K")){check = King.allowed(i, pos, tile, king.get(pos)) ? true : check;}            
                    else if(tile.equalsIgnoreCase("P")){check = Pawn.allowed(i, pos, tile, king.get(pos)) ? true : check;} 
                    if(check){board.paintCheck(pos);}
                    check = false;
                } 
            } 
        }
    }
    
    private void isPawnInTheEnd(JButton btn){
        boolean firstRow = board.getPosition(btn) <= Board.w;
        boolean finalRow = board.getPosition(btn) > Board.w*Board.h-Board.w;
        boolean isPawn = piece.equalsIgnoreCase("P");
        if((firstRow || finalRow) && isPawn){  
           JOptionPane.showOptionDialog(this, new Transformer(piece), "Select a piece", 1, 1, Chess.chessico, new Object[]{},null);
           String select = Transformer.getSelectedPiece();
           btn.setName(select);
           btn.setIcon(new ImageIcon(conf.getImg(select)));
        }
    }

    private void test() {
        String choose = JOptionPane.showInputDialog(board, "Set state");
        board.setState(board.record.get(Integer.parseInt(choose)));
        if(Integer.parseInt(choose) % 2 == 0){blackTurn = false;}
    }

    private void isThereaWinner() {
        if(!board.getState().contains("k")){JOptionPane.showMessageDialog(this, "Black Wins!");reset();}        
        if(!board.getState().contains("K")){JOptionPane.showMessageDialog(this, "White Wins!");reset();}
        
        System.out.println(board.getState());
    }
    private void reset(){
        board.setState(board.record.get(0));
        blackTurn = false;
    }
}
