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
    private static Color cell;
    private static Icon icon;
    private String init;
    
    private static int i;
    

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        String piece = btn.getIcon().toString().replaceAll("(img/)|(.png)","");
        i++;
        if(selected){
            prev.setIcon(new ImageIcon("img/n.png"));
            prev.setBackground(cell);
            btn.setIcon(icon);
            selected = false;
            Chess.board.storeState();
        }else if(!piece.contains("n")){
            prev = btn;
            selected = true;
            cell = prev.getBackground();
            icon = prev.getIcon();
            prev.setBackground(Color.red);
        }
        
        if(i > 10){
            String choose = JOptionPane.showInputDialog(Chess.board, "mimi");
            Chess.board.setState(Chess.board.plays.get(Integer.parseInt(choose)));
        }
        
    }    
}
