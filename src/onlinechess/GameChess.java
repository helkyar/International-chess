/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
        board = new Board(Cnf.size(), Cnf.init(), isWhite);
        board.startBoard(this);
        add(board);
        
    //test ------------------------------------
        JButton btn = new JButton("T", new ImageIcon("m"));
        btn.addActionListener(this);  
        add(btn);
    //----------------------------------------
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        String tile = e.getActionCommand();
        
        System.out.println(board.getPosition(btn));//nice
        
        if(e.getActionCommand().equals("T")){test();}
        
        //Move piece system
        else if(selected && allowedMove(btn, tile)){
            prev.setBackground(bg);
            prev.setIcon(null);
            prev.setText("n");
            btn.setText(piece);
            btn.setIcon(icon);
            
            selected = false;
            board.storeState(); 
            
        }else if(!selected && !tile.equals("n")){
            prev = btn;
            selected = true;
            piece = tile;
            icon = btn.getIcon();
            bg = prev.getBackground();
            prev.setBackground(Color.red);
        }        
    }    
    
    private boolean allowedMove(JButton btn, String targed){
        int to = board.getPosition(btn);
        int from = board.getPosition(prev);
        
        //Pieces allowed moves
        if(piece.equalsIgnoreCase("R"))
            {return true;}
        else if(piece.equalsIgnoreCase("H"))
            {return true;}
        else if(piece.equalsIgnoreCase("B"))
            {return true;}
        else if(piece.equalsIgnoreCase("Q"))
            {return true;}
        else if(piece.equalsIgnoreCase("K"))
            {return true;}
        else if(piece.equalsIgnoreCase("P"))
        {return new Pawn(from, to, piece, targed).allowed();}
            
        //Game allowed moves
            //path closed by other pieces
            //check posibility (but who knows)
            //enroque
        return false;
    }

    private void test() {
        String choose = JOptionPane.showInputDialog(board, "Set state");
        board.setState(board.record.get(Integer.parseInt(choose)));
    }
}
