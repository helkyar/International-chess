/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.helpers;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JToggleButton;
import onlinechess.controller.game.Game;

/**
 *
 * @author javier
 */
public class Memory {

    public Memory(Game g, String m, String id, 
     HashMap<String, String> u, JToggleButton b){
        this.game = g;
        this.msg = m;
        this.chatId = id;
        this.users = u;
        this.btn = b;
    }    
        
    Game game;
    String msg;
    String chatId;
    JToggleButton btn;
    Map<String, String> users; 
}

//(>)GET USERS ONLINE if not local user
//(>)CREATE USER BUTTONS
//(>)SEND MESSAGES
//(>)SEND MOVES
//(>)STORE MESSAGES AND MOVES
//(>)STORE CHATS
//(>)GET CHATS
//(>)RENDER CHATS
//(>)ORDER CHATS BY LAST SPOKEN