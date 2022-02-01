/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess;

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
 * @author admin
 */
public class GameChess extends JPanel implements ActionListener{
    private static JButton prev;
    private static boolean selected;
    private static String piece;
    private static Icon icon;
    private static Color bg;
    
    private static Board board;
    
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
                
        if(e.getActionCommand().equals("T")){test();}
        
        //Select tile    
        else if(!selected && !tile.equals("-")){ 
            prev = btn;
            selected = true;
            piece = tile;
            icon = btn.getIcon();
            bg = prev.getBackground();
            prev.setBackground(Color.red);
        //Move piece if allowed
        } else if(selected && allowedMove(btn, tile)){
            prev.setBackground(bg);
            prev.setIcon(null);
            prev.setText("-");
            btn.setText(piece);
            btn.setIcon(icon);
            
            selected = false;
            board.storeState(); 
        //deselect without move    
        } else if (selected) {
            selected = false;
            prev.setBackground(bg);
        }
    }    
    
    private boolean allowedMove(JButton btn, String targed){
        int to = board.getPosition(btn);
        int from = board.getPosition(prev);
        System.out.println(Pawn.allowed(from, to, piece, targed));
        //Pieces allowed moves
        if(piece.equalsIgnoreCase("R"))     {return Rook.allowed(from, to, piece, targed);}            
        else if(piece.equalsIgnoreCase("H")){return Horse.allowed(from, to, piece, targed);}            
        else if(piece.equalsIgnoreCase("B")){return Bishop.allowed(from, to, piece, targed);}            
        else if(piece.equalsIgnoreCase("Q")){return Queen.allowed(from, to, piece, targed);}            
        else if(piece.equalsIgnoreCase("K")){return King.allowed(from, to, piece, targed);}            
        else if(piece.equalsIgnoreCase("P")){return Pawn.allowed(from, to, piece, targed);}
            
        //Game allowed moves
            //check posibility (but who knows)
            //enroque
        return false;
    }

    private void test() {
        String choose = JOptionPane.showInputDialog(board, "Set state");
        board.setState(board.record.get(Integer.parseInt(choose)));
    }
}
