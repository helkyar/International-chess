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

/**
 *
 * @author admin
 */
public class GameChess implements ActionListener{
    private static JButton prev;
    private static boolean selected;
    private static Color background;
    private static String piece;
    private static Icon icon;
    
    private static int i;
    
    public GameChess(){
        Chess.board.startBoard(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        Icon cell = btn.getIcon(); //to check for empty cells
        int to = Integer.parseInt(btn.getText());
               
        i++;
        
        if(selected && allowedMove(to, !piece.equals(null))){
            prev.setBackground(background);
            prev.setIcon(null);
            btn.setIcon(icon);
            selected = false;
            Chess.board.storeState(); 
            
        }else if(!selected && !cell.equals(null)){
            prev = btn;
            icon = cell;
            selected = true;
            background = prev.getBackground();
            piece = cell.toString().replaceAll("(img/)|(.png)","");
            prev.setBackground(Color.red);
        }
        
        if(i == 10){
            i = 0;
            String choose = JOptionPane.showInputDialog(Chess.board, "mimi");
            Chess.board.setState(Chess.board.plays.get(Integer.parseInt(choose)));
        }
        
    }    
    
    private boolean allowedMove(int to, boolean targed){
        
        int from = Integer.parseInt(prev.getText());
        if(piece.equals("T")||piece.equals("Y")){return true;}
        else if(piece.equals("H")||piece.equals("J")){return true;}
        else if(piece.equals("B")||piece.equals("V")){return true;}
        else if(piece.equals("Q")||piece.equals("W")){return true;}
        else if(piece.equals("K")||piece.equals("L")){return true;}
        else if(piece.equals("P")||piece.equals("O")){return new Pawn(from, to, targed).allowed();}
        
        return false;
    }
}
