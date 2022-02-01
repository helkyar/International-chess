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
    private static Color background;
    private static String piece;
    private static Icon icon;
    
    private static Board board;
    
    //Future ENUM
    private String init = "rhbkqbhrppppppppnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnPPPPPPPPRHBQKBHR";
    private int height = 8;
    private int width = 8;
    
    public GameChess(boolean isWhite){
        board = new Board(height,width, init, isWhite);
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
        String tile = btn.getIcon().toString().replaceAll("(img/)|(.png)","");
        
        if(e.getActionCommand().equals("T")){test();}
        
        else if(selected && allowedMove(btn, tile)){
            prev.setIcon(new ImageIcon("img/n.png"));
            prev.setBackground(background);
            btn.setIcon(icon);
            selected = false;
            board.storeState(); 
            
        }else if(!selected && !tile.equals("n")){
            prev = btn;
            selected = true;
            piece = tile;
            icon = btn.getIcon();
            background = prev.getBackground();
            prev.setBackground(Color.red);
        }        
    }    
    
    private boolean allowedMove(JButton btn, String targed){
        int to = Integer.parseInt(btn.getText());
        int from = Integer.parseInt(prev.getText());
        
        //Pieces allowed moves
        if(piece.equals("T")||piece.equals("Y"))
            {return true;}
        else if(piece.equals("H")||piece.equals("J"))
            {return true;}
        else if(piece.equals("B")||piece.equals("V"))
            {return true;}
        else if(piece.equals("Q")||piece.equals("W"))
            {return true;}
        else if(piece.equals("K")||piece.equals("L"))
            {return true;}
        else if(piece.equals("P")||piece.equals("O"))
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
