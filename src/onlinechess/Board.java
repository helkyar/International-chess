/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 *
 * @author admin
 */
public class Board extends JPanel{
    
    private int h;
    private int w;
    private String state;
    public static ArrayList<String> plays;
    
    public Board (int h, int w, String state){
        this.h = h;
        this.w = w;
        this.state = state;
        plays = new ArrayList<String>();
        plays.add(state);
        
        setLayout(new GridLayout(h, w));
        
        initBoard(h, w);
        setState(state);
        setVisible(true);
    }    
    
    private void initBoard(int h, int w){

        for(int row = 0, col = 1; row < h; col++){                
            //Set btn id.
            JButton btn = new JButton(String.valueOf(row*w+col));
                
            //Set cells color pattern (alterned).
            if (((int)row+col) % 2 == 0){btn.setBackground(Color.blue);}
            else {btn.setBackground(Color.gray);}
            btn.addActionListener(new GameChess());
            add(btn);
            
            //Reseter to avoid nested loops. 
            if(col == w){col = 0; row += 1;}
        }
    }
    public void setState(String state){
        //Get buttons as array and set pieces
        String[] icons = state.split("");
        for (int i = 0; i < getComponentCount(); i++){
            JButton btn = (JButton) getComponents()[i];
            btn.setIcon(new ImageIcon("img/"+icons[i]+".png"));
        }
    }
    
    public void storeState(){
        String board = "";
        //Get buttons as array and set pieces
        for (int i = 0; i < getComponentCount(); i++){
            JButton btn = (JButton) getComponents()[i];
            String imgName = btn.getIcon().toString();
            board += imgName.replaceAll("(img/)|(.png)","");
        }
        plays.add(board);
    }
}
