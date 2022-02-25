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
    public static void setUsersOnlineOnScreen(String ip, String nick) {
        //may repat users that connect and desconnect
        //or login as guest and then as user
        String space = "                ";
        //Needs also a checker for public ips        
        boolean own = ip.equals(Request.ownip);
         
       //avoid infinite userbutton adding. Only add it if didn't exist
        if(!own && app.storage.get(ip) == null){ 
            HashMap<String, String> users = new HashMap<>();
            users.put(ip,nick);
            String chatid =  "~u~"+new Date().getTime()+Math.random(); 
            JToggleButton btn = new JToggleButton(nick+space, cnf.APP_ICON);
            btn.addActionListener((ActionEvent e) -> {
                renderAllTheShit(e);
            });
            //Create new AppStorage and store it in "APP"
            //user and user ip, chadid, remove group options, thisbtn
            app.storage.put(ip, new Memory(new Game(), "", chatid, users, btn));
            app.btngrouper.add(btn);
            app.users.add(btn);         
            app.users.repaint(); 
            app.users.validate();
        } else {
            
        }
    }
    
    private static void renderAllTheShit(ActionEvent e) {
    
    }    
    //Function: Inserts buttons into a referenced panel and groupbutton
    //Problem: setting variables adress and chat
}
//(!) APP IS NULL AT SOME POINT