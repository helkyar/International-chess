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

    public static Map<String, String> map;
    
    public static String get(String s){
        return map.get(s);
    }
    
    public Cnf (){
        map = new HashMap<String, String>();
        map.put("R", "img/T.png");
        map.put("H", "img/H.png");
        map.put("B", "img/B.png");
        map.put("Q", "img/Q.png");
        map.put("K", "img/K.png");
        map.put("P", "img/P.png");
        map.put("r", "img/Y.png");
        map.put("h", "img/J.png");
        map.put("b", "img/V.png");
        map.put("q", "img/W.png");
        map.put("k", "img/L.png");
        map.put("p", "img/O.png");
        map.put("n", "img/null.png");
    }
}
