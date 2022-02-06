/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.views;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import onlinechess.controller.pieces.Bishop;
import onlinechess.controller.pieces.Horse;
import onlinechess.controller.pieces.King;
import onlinechess.controller.pieces.Pawn;
import onlinechess.controller.pieces.Queen;
import onlinechess.controller.pieces.Rook;
import onlinechess.helpers.conf;
import onlinechess.helpers.LogGen;


/**
 *
 * @author admin
 */
public class Board extends JPanel{
    
    public static int h;
    public static int w;
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
        
        for(int tile = 1, alt = 0; tile <= h*w; tile++){                                     
                //Set pieces through button text
                JButton btn = new JButton();
                try{btn.setName(icons[tile]);}
                catch(Exception e){btn.setName("-");}                 
                
                //Set tiles color pattern (alternated). Int cast for precision
                if (((int)tile + alt) % 2 == 0){btn.setBackground(conf.bTile);}
                else {btn.setBackground(conf.wTile);}
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

                btn.setIcon(icon);     
                btn.setName(tile[i]);                           
                
            }catch(Exception e){}
        }
    }
    
    public void storeState(){
        String board = "";
        //Get buttons as array and store positions
        for (int i = 0; i < getComponentCount(); i++){
            JButton btn = (JButton) getComponents()[i];         
            board += btn.getName();
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
  
    /**
     * Resource eater! bunch of "if" statements bound togheder by some dark magic,
     * checking for possible tiles to hold the source piece and marking them with
     * blood, cyan blood. 
     * Is ugly as fuck but gets the job done.
     * @param source the button clicked with the piece info
     */
    public void paint(JButton source){
        int from = getPosition(source);
        String piece = source.getName();
        for (int i = 0; i < getComponentCount(); i++){
            JButton btn = (JButton) getComponents()[i];
            if(piece.equalsIgnoreCase("R"))     {if(Rook.allowed(from, i+1, piece, btn.getName())){btn.setBackground(conf.allw);}}            
            else if(piece.equalsIgnoreCase("H")){if(Horse.allowed(from, i+1, piece, btn.getName())){btn.setBackground(conf.allw);}}            
            else if(piece.equalsIgnoreCase("B")){if(Bishop.allowed(from, i+1, piece, btn.getName())){btn.setBackground(conf.allw);}} 
            else if(piece.equalsIgnoreCase("Q")){if(Queen.allowed(from, i+1, piece, btn.getName())){btn.setBackground(conf.allw);}} 
            else if(piece.equalsIgnoreCase("K")){if(King.allowed(from, i+1, piece, btn.getName())){btn.setBackground(conf.allw);}} 
            else if(piece.equalsIgnoreCase("P")){if(Pawn.allowed(from, i+1, piece, btn.getName())){btn.setBackground(conf.allw);}} 
           
        }
    }
    
    public void unPaint(){ 
        for (int i = 1, alt = 0; i <= getComponentCount(); i++){
            JButton btn = (JButton) getComponents()[i-1];
            //Set tiles color pattern (alternated). Int cast for precision
            if (((int)i + alt) % 2 == 0){
                btn.setBackground(conf.bTile);
                btn.setForeground(conf.bTile);
            } else {
                btn.setBackground(conf.wTile);
                btn.setForeground(conf.wTile);
            }

            if(i % w == 0){alt++;}
        }
    }
    
      
    public int getPosition(JButton b){
          for (int i = 0; i < getComponentCount(); i++){
            JButton btn = (JButton) getComponents()[i];
            if(b == btn){return i+1;}
          }
          return -1;
    }
    
    public boolean isTileEmpty(int tile){
        try{
            JButton btn = (JButton) getComponents()[tile-1];
            return btn.getName().equals("-");
        }catch (Exception e){return true;}
    }
    
    public String getTilePiece(int tile){
        try{
            JButton btn = (JButton) getComponents()[tile-1];
            return btn.getName();
        }catch(Exception e){return "?";}
    }
}
