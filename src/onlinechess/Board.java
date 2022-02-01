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
    
    public Board (int h, int w, String state, boolean isWhite){
        this.h = h;
        this.w = w;
        
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
        // create a reverse board 
        int r = 0;
        if(!isWhite){r=65;}
        
        for(int row = 0, col = 1; row < h; col++){                  
            //Set btn id.            
            String id = String.valueOf(Math.abs((row * w + col) - r));
            JButton btn = new JButton(id);
                
            //Set tiles color pattern (alternated). Int cast for precision
            if (((int)row + col) % 2 == 0){btn.setBackground(Color.blue);}
            else {btn.setBackground(Color.gray);}
            add(btn);
            
            //Reseter to avoid nested loops. 
            if(col == w){col = 0; row += 1;}
        }
    }
    public void setState(String state){
        
        try{
            //Reverse board for whites
            if(isWhite){state = new StringBuilder(state).reverse().toString();}
            //Get buttons as array and set pieces
            String[] icons = state.split("");
            
            for (int i = 0; i < getComponentCount(); i++){
                //Asociate piece enum to image (B: whitebishop.png)
//                ImageIcon icon = new ImageIcon("img/"+icons[i]+".png");
                System.out.println(Cnf.get(icons[i]));
                
                ImageIcon icon = new ImageIcon(new Cnf().map.get(icons[i]).toString());

                JButton btn = (JButton) getComponents()[i];
                btn.setIcon(icon);
            }
            
        }catch(Exception e){
            LogGen.error("Number of pieces and board size don't match");
        }
    }
    
    public void storeState(){
        String board = "";
        //Get buttons as array and store positions
        for (int i = 0; i < getComponentCount(); i++){
            JButton btn = (JButton) getComponents()[i];
            board += btn.getIcon().toString().replaceAll("(img/)|(.png)","");
        }
        //WHY?????????? why i need to do this? comment line below to see shit happen
        if(isWhite){board = new StringBuilder(board).reverse().toString();}
        record.add(board);
    }
    
    public void startBoard(ActionListener e){
       for (int i = 0; i < getComponentCount(); i++){
            JButton btn = (JButton) getComponents()[i];
            btn.addActionListener(e);
        }
    }
    
    public String getTile(int i){
        JButton t = (JButton) getComponent(i);
        return t.getText();
    }
}
