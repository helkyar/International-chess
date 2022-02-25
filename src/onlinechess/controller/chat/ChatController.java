/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import onlinechess.views.ChessApp;

/**
 *
 * @author javier
 */
public class ChatController implements ActionListener{
    ChessApp app;    
    private DateFormat df;
    
    public ChatController(ChessApp app){
        this.app = app;
        this.df = app.timeFormat;
    }
    @Override
    public void actionPerformed(ActionEvent ev) {        
        String txt = app.userinput.getText();
        if(ev.getSource() == app.sendbtn && !txt.equals("")){
            sendMessageToChat(txt);
        }
    }

    public void userKeyInput(KeyEvent pressed) {        
        String txt = app.userinput.getText();
        if(pressed.getKeyCode() == KeyEvent.VK_ENTER && !txt.equals("")){
            sendMessageToChat(txt);
        }
    }
    
    private void sendMessageToChat(String txt){        
        String msg = df.format(new Date())+"["+app.nick+"]:\n"+txt + "\n";
        app.userinput.setText("");
        app.chatxt.append(msg);

        String message = app.chatxt.getText() + "\n";
                
//        chatstorage.put(chatID, txt);
//        Send.message( adress, msg, nick, "messaging", chatID);
   }
}
