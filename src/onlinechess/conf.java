/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

//May be highly inneficient

/**
 *
 * @author Javier Palacios Botejara
 */
public class conf {

    public static Dimension context; 
    public static Map<String, Integer> board;
    public static Map<String, String> img;
    public static Map<String, String> start;
    
    //Types of plays:
    private static int choose = 0;
    String std = "rhbkqbhrpppppppp--------------------------------PPPPPPPPRHBQKBHR";
    String pawn = "pppkpppppppppppppppppppp----------------PPPPPPPPPPPPPPPPPPPPKPPP";
    String out = "pppppppprhbkqbhrpppppppp--------------------------------PPPPPPPPRHBQKBHRPPPPPPPP"; 
                                                                                                          
    public conf (){
        context = new Dimension(600,500);
        
        board = new HashMap<>();
        board.put("h", 18);
        board.put("w", 8);
         
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
        start.put("standart", std);
        start.put("pawnbattle", pawn);  
        start.put("outofbounds", out);
    }   
    
    public static String getImg(String s){
        return img.get(s);
    }
    
    public static int[] size(){
       int[] size = {board.get("h"),board.get("w")};
       return size;
    }
    
    public static String init(){
        return start.values().toArray()[choose].toString();
    }
}