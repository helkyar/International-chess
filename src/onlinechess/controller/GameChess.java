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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
        String tile = btn.getText();
        //Test -----------------------------------------------------------------
        System.out.println(board.getPosition(btn));      
        System.out.println(board.getTilePiece(board.getPosition(btn)-1));
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
            prev.setForeground(conf.slct);  
                      
            selected = true;
            board.paint(prev);  
            blackTurn = !blackTurn;
            
        //Move piece if allowed ------------------------------------------------
        } else if(selected && allowedMove(btn, tile)){  
            prev.setIcon(null);
            prev.setText("-");
            btn.setText(piece);
            btn.setIcon(icon);
            
            selected = false;
            board.storeState(); 
            board.unPaint();
            
            isPawnInTheEnd(btn);
            
        //deselect without move ------------------------------------------------   
        } else if (selected) {
            selected = false;
            board.unPaint();
            blackTurn = !blackTurn;
        }
    }    
    
    private boolean allowedMove(JButton btn, String targed){
        int to = board.getPosition(btn);
        int from = board.getPosition(prev);
        
        //Game allowed moves
            //check posibility (but who knows)
            //enroque        
            //win condition
            
        //Pieces allowed moves
        if(piece.equalsIgnoreCase("R"))     {return Rook.allowed(from, to, piece, targed);}            
        else if(piece.equalsIgnoreCase("H")){return Horse.allowed(from, to, piece, targed);}            
        else if(piece.equalsIgnoreCase("B")){return Bishop.allowed(from, to, piece, targed);}            
        else if(piece.equalsIgnoreCase("Q")){return Queen.allowed(from, to, piece, targed);}            
        else if(piece.equalsIgnoreCase("K")){return King.allowed(from, to, piece, targed);}            
        else if(piece.equalsIgnoreCase("P")){return Pawn.allowed(from, to, piece, targed);}
            
        return false;
    }
    
    private void isPawnInTheEnd(JButton btn){
        boolean firstRow = board.getPosition(btn) <= Board.w;
        boolean finalRow = board.getPosition(btn) > Board.w*Board.h-Board.w;
        boolean isPawn = piece.equalsIgnoreCase("P");
        if((firstRow || finalRow) && isPawn){
           System.out.println("Maybe you should write something"); 
        }
    }

    private void test() {
        String choose = JOptionPane.showInputDialog(board, "Set state");
        board.setState(board.record.get(Integer.parseInt(choose)));
        if(Integer.parseInt(choose) % 2 == 0){blackTurn = false;}
    }
}
