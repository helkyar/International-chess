/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JToggleButton;
import onlinechess.controller.game.Game;
import onlinechess.controller.socket.Request;
import onlinechess.helpers.Memory;
import onlinechess.helpers.ConfigApp;
import onlinechess.views.ChessApp;

/**
 *
 * @author javier
 */
public class ScreenController {

//=============================================================================
//                INSTANTIATE CONTROLLER WITH APP REFERENCE
//=============================================================================
    private static ChessApp app;
    private static ConfigApp cnf;

    public ScreenController(ChessApp app, ConfigApp cnf) {
        this.app = app;
        this.cnf = cnf;
    }
    
//=============================================================================
//                            CONTROLLER METHODS
//=============================================================================
    public static void setUsersOnScreen(String ip, String nick, int id) {
        //(!)Needs user id to know if chat should be stored
        String space = "                ";
        //Needs also a checker for public ips        
        boolean own = ip.equals(Request.ownip);
         
       //avoid infinite userbutton adding. Only add it if didn't exist
        if(own){return;}
        if(app.storage.get(ip)!=null){/*change btn name and user id*/return;}
        
        HashMap<String, String> users = new HashMap<>();
        users.put(ip,nick);
        String chatid =  "~u~"+new Date().getTime()+Math.random(); 
        JToggleButton btn = new JToggleButton(nick+space, cnf.EYE_OPEN);
        btn.addActionListener((ActionEvent e) -> {renderAllTheShit(e);});
        //Create new AppStorage and store it in "APP"
        //user and user ip, chadid, remove group options, thisbtn
        Game g = new Game(0, true);//(!)not all can be whites
        app.storage.put(ip, new Memory(g, "", chatid, ip, id, users, btn));
        app.btngrouper.add(btn);
        app.users.add(btn);         
        app.users.repaint(); 
        app.users.validate();         
    }
    
    private static void renderAllTheShit(ActionEvent e) {
        JToggleButton btn = (JToggleButton) e.getSource();
        for(Memory m : app.storage.values()){
            if(btn != m.btn){continue;}
            app.chatxt.setText(m.msg);
            app.screen.add(m.game);
        }
        //(?)Repaint
        //when message send include chatid, adressip, 
    }    
    //Function: Inserts buttons into a referenced panel and groupbutton
    //Problem: setting variables adress and chat
}
//(!) APP IS NULL AT SOME POINT