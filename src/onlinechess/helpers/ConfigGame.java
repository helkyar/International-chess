/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.helpers;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

//May be highly inneficient

/**
 *
 * @author Javier Palacios Botejara
 */
public class ConfigGame {

    public static Dimension context;
    private final int rows = 0; //extra-minus rows
    private final int cols = 0; //extra-minus columns; 
    //tile color
    public static final Color WTILE = Color.gray;
    public static final Color BTILE = Color.darkGray; 
    public static final Color SLCT = Color.red;     
    public static final Color ALLW = Color.cyan; 
    public static final Color CHECK = Color.magenta; 
    
    //piece team reference
    public static final String WHITES = "pqkbhr";
    public static final String BLACKS = "PQKBHR";
    
    protected Map<String, Integer> board;
    protected Map<String, String> img;
    protected Map<String, String> start;
    
    //Types of plays:
    private int choose = 0;
    String std = "rhbkqbhrpppppppp--------------------------------PPPPPPPPRHBQKBHR";
    String p = "rhbkqbh-pppppppk-----------r-------R------------KPPPPPPP-HBQKBHR";
    String pawn = "pppkpppppppppppppppppppp----------------PPPPPPPPPPPPPPPPPPPPKPPP";
    String cstl = "r--kk--rk--rr--kR--KK--RK--RR--Kr--kk--rk--rr--kR--KK--RK--RR--K";
    String out = "pppppppprhbkqbhrpppppppp--------------------------------PPPPPPPPRHBQKBHRPPPPPPPP"; 
                                                                                                         
    public ConfigGame (int chooseBoard){
        this.choose = chooseBoard;
        
        context = new Dimension(700,500);
                                     
        img = new HashMap<>();
        img.put("R", "img/T.png");
        img.put("H", "img/H.png");
        img.put("B", "img/B.png");
        img.put("Q", "img/Q.png");
        img.put("K", "img/K.png");
        img.put("P", "img/P.png");
        img.put("r", "img/Y.png");
        img.put("h", "img/J.png");
        img.put("b", "img/V.png");
        img.put("q", "img/W.png");
        img.put("k", "img/L.png");
        img.put("p", "img/O.png");
        img.put("n", "null");
        
        start = new HashMap<>();
        start.put(std, "8,8");
        start.put(p,"8,8");
        start.put(pawn, "8,8");  
        start.put(cstl, "8,8");
        start.put(out, "10,8");
        
        String[] s = start.values().toArray()[choose].toString().split(",");
        int defaultH = Integer.parseInt(s[0]);
        int defaultW = Integer.parseInt(s[1]);
        
        board = new HashMap<>();
        board.put("h", defaultH+rows);
        board.put("w", defaultW+cols);
    }   
    
    public String getImg(String s){
        return img.get(s);
    }
    
    public int[] size(){
       int[] size = {board.get("h"),board.get("w")};
       return size;
    }
    
    public String init(){
        return start.keySet().toArray()[choose].toString();
    }
}