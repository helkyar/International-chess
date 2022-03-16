/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.socket;

import java.io.IOException;  
import java.io.ObjectOutputStream;
import java.net.Socket;
import onlinechess.helpers.Memory;
import onlinechess.views.ChessApp;
import packager.Packager;

/**
 * Makes all requests to the server (except search server request)
 * @author Javier Palacios
 */
public class Request {    
    public static String server;
    public static String ownip;

    public static void registerUser(String nick, String pwdcryp, String email) {
        try {
            try (Socket socket = new Socket(server,9999)) {
                Packager p = new Packager();
                p.setStatus("register");
                p.setPassword(pwdcryp);
                p.setEmail(email);
                p.setNick(nick);
                p.setIp(ownip);
                
                sendRequest(p, socket);
            }
               
        } catch (IOException ex) {ex.printStackTrace();}
    }

    public static void loginUser(String nick) {
        try {
            try (Socket socket = new Socket(server,9999)) {
                Packager p = new Packager();
                p.setStatus("login");
                p.setNick(nick);
                p.setIp(ownip);
                
                sendRequest(p, socket);
            }               
        } catch (IOException ex) {ex.printStackTrace();}
    }

    public static void searchUsersOnline(String nick, int id) {
        try {
            try (Socket socket = new Socket(server,9999)) {
                Packager p = new Packager();
                p.setStatus("online");
                p.setIp(ownip);
                p.setId(id);
                p.setNick(nick);
                sendRequest(p, socket);
            }               
        } catch (IOException ex) {ex.printStackTrace();}
    }
    
    public static void sendMessage(Memory m, String msg) {
        m.msg = msg;
        try {
            try (Socket socket = new Socket(server,9999)) {
                Packager p = new Packager();
                p.setStatus("message");
                p.setMemory(m);
                p.setIp(ownip); //to compare on the server side
                sendRequest(p, socket);
            }               
        } catch (IOException ex) {ex.printStackTrace();}
    }
    
    public static void sendMove(Memory m) {     
        try {
            try (Socket socket = new Socket(server,9999)) {
                Packager p = new Packager();
                p.setStatus("play");
                p.setMemory(m);
                p.setIp(ownip); //to compare on the server side
                sendRequest(p, socket);
            }               
        } catch (IOException ex) {ex.printStackTrace();}
    }
//=============================================================================
//                        SEND REQUEST
//=============================================================================
    private static void sendRequest(Packager p, Socket s) throws IOException{
        ObjectOutputStream objp = 
        new ObjectOutputStream(s.getOutputStream());
        objp.writeObject(p);
        s.close();
    }
}
