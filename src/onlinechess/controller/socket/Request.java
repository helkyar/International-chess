/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author admin
 */
public class Request {    
    public static String server;
    public static String ownip;

    public static void registerUser(String nick, String pwdcryp, String email) {
        try {
            try (Socket socket = new Socket(server,9999)) {
                packager.Package p = new packager.Package();
                p.setStatus("register");
                p.setPassword(pwdcryp);
                p.setEmail(email);
                p.setNick(nick);
                p.setIp(ownip);
                
                ObjectOutputStream objp = 
                new ObjectOutputStream(socket.getOutputStream());
                objp.writeObject(p);
                socket.close();
            }
               
        } catch (IOException ex) {ex.printStackTrace();}
    }

    public static void loginUser(String nick) {
        try {
            try (Socket socket = new Socket(server,9999)) {
                packager.Package p = new packager.Package();
                p.setStatus("login");
                p.setNick(nick);
                p.setIp(ownip);
                
                ObjectOutputStream objp = 
                new ObjectOutputStream(socket.getOutputStream());
                objp.writeObject(p);
                socket.close();
            }
               
        } catch (IOException ex) {ex.printStackTrace();}
    }
    
}
