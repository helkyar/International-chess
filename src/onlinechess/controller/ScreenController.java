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
        String space = "                ";
        //Needs also a checker for public ips        
        boolean own = ip.equals(Request.ownip);
         
       //avoid infinite userbutton adding. Only add it if didn't exist
        if(own){return;}
        if(app.storage.get(ip)!=null){            
            Memory m = app.storage.get(ip);
            JToggleButton btn = m.btn;
            btn.setText(nick);
            //clean user map
            m.users.clear();
            m.users.put(new String[]{ip, nick}, id);
            m.users.put(new String[]{Request.ownip, app.nick}, app.userId);                     
            return;
        }
        
        String chatid =  "~u~"+new Date().getTime()+Math.random(); 
        JToggleButton btn = new JToggleButton(nick+space, cnf.EYE_OPEN);
        btn.addActionListener((ActionEvent e) -> {renderAllTheShit(e);});  
        
        HashMap<String[], Integer> users = new HashMap<>();
        users.put(new String[]{ip, nick}, id); 
        users.put(new String[]{Request.ownip, app.nick}, app.userId);
        
        Game g = new Game(0, true);//(!)not all can be whites
        app.storage.put(ip, new Memory(g, "", chatid, ip, users, btn));
        app.btngrouper.add(btn);
        app.users.add(btn);         
        app.users.repaint(); 
        app.users.validate();         
    }
    
    private static void renderAllTheShit(ActionEvent e) {
        JToggleButton btn = (JToggleButton) e.getSource();
        for(Memory m : app.storage.values()){
            if(btn != m.btn){continue;}
            app.adress = m.address;
            app.chatxt.setText(m.msg);            
            app.screen.remove(0);
            app.screen.add(m.game,0);
        }
        app.screen.repaint();
        app.screen.validate();
        //when message send include chatid, adressip, 
    }    
    //Function: Inserts buttons into a referenced panel and groupbutton
    //Problem: setting variables adress and chat
}
//(!) APP IS NULL AT SOME POINT