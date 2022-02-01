/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import onlinechess.helpers.LogGen;


/**
 *
 * @author admin
 */
public class Board extends JPanel{
    
    static int h;
    static int w;
    private String state;
    private boolean isWhite;
    public static ArrayList<String> record;
    
    public Board (int[] size, String state, boolean isWhite){
        this.h = size[0];
        this.w = size[1];
        
        this.isWhite = isWhite;
        record = new ArrayList<String>();
        this.state = state;
        record.add(state);
        
        setLayout(new GridLayout(h, w));
        
        initBoard(h, w);
        setState(state);
        setVisible(true);
    }    
    
    private void initBoard(int h, int w){
        //Reverse board for whites
        if(isWhite){state = new StringBuilder(state).reverse().toString();}
        //Get pieces as array and set pieces
        String[] icons = state.split("");
        
        for(int tile = 0, alt = 0; tile < h*w; tile++){                                     
                //Set pieces through button text
                JButton btn = new JButton();
                try{btn.setText(icons[tile]);}catch(Exception e){}
                finally{btn.setText("-");}
                //Set tiles color pattern (alternated). Int cast for precision
                if (((int)tile + alt) % 2 == 0){btn.setBackground(Color.blue);}
                else {btn.setBackground(Color.gray);}
                add(btn);     

                if(tile % w == 0){alt++;}
        }
    }
    
    public void setState(String state){
        //Reverse board for whites
        if(isWhite){state = new StringBuilder(state).reverse().toString();}
        //Get pieces as array and set pieces
        String[] tile = state.split("");
        
        for (int i = 0; i < getComponentCount(); i++){
            try{         
                JButton btn = (JButton) getComponents()[i];
                
                ImageIcon icon = null;
                boolean full = !tile[i].equals("-");
                if(full){icon = new ImageIcon(conf.getImg(tile[i]));}

                btn.setText(tile[i]);
                btn.setIcon(icon);
                
            }catch(Exception e){}
        }
    }
    
    public void storeState(){
        String board = "";
        //Get buttons as array and store positions
        for (int i = 0; i < getComponentCount(); i++){
            JButton btn = (JButton) getComponents()[i];         
            board += btn.getText();
        }
        //Store same string independent of color played
        if(isWhite){board = new StringBuilder(board).reverse().toString();}
        record.add(board);
    }
    
    public void startBoard(ActionListener e){
       for (int i = 0; i < getComponentCount(); i++){
            JButton btn = (JButton) getComponents()[i];
            btn.addActionListener(e);
        }
    }
    
    public int getPosition(JButton b){
          for (int i = 0; i < getComponentCount(); i++){
            JButton btn = (JButton) getComponents()[i];
            if(b == btn){return i+1;}
          }
          return -1;
    }
}
