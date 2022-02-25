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

    public Memory(Game game, String message, String chatid, String ip, 
     int userid, HashMap<String, String> useripmap, JToggleButton button){
        this.game = game;
        this.msg = message;
        this.chatId = chatid;
        this.users = useripmap;
        this.userid = userid;
        this.btn = button;
    }    
        
    public Game game;
    public String msg;
    public int userid;
    public String chatId;
    public JToggleButton btn;
    public Map<String, String> users; 
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