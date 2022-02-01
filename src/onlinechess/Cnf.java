/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Javier Palacios Botejara
 */
public class Cnf {

    public static Map<String, Integer> board;
    public static Map<String, String> img;
    public static String[] start = new String[1];
        
    public Cnf (){
        board = new HashMap<String, Integer>() {};
        board.put("h",8);
        board.put("w",8);
        
        start[0] = (
            "rhbkqbhr"+"pppppppp"+
            "nnnnnnnn"+"nnnnnnnn"+
            "nnnnnnnn"+"nnnnnnnn"+
            "PPPPPPPP"+"RHBQKBHR"
        );
                
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
    }   
    
    public static String getImg(String s){
        return img.get(s);
    }
    
    public static int[] size(){
       int[] size = {board.get("h"),board.get("w")};
       return size;
    }
    
    public static String init(){
        return start[0];
    }
}