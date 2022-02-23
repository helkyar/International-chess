/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import packager.Package;
import server.dbconnect.managers.UserManager;

/**
 *
 * @author admin
 */
public class Server extends JFrame implements Runnable{
    private static int guest = 0;
    public static JTextArea txt = new JTextArea();
    private Map<String, String[]> ips = new HashMap<>(); 
    private String ip = "";
    
    Server(){
        Thread lintening = new Thread(this);
        lintening.start();
        new NewUser(); //new thread to avoid overlapping res/req
        
        add(new JScrollPane(txt));
        setSize(400,100);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new Server();
    }    
 
 // ===========================================================================
 //                            REQUEST
 // ===========================================================================
    @Override
    public void run() {
        Package p;
        ObjectInputStream input;
        ServerSocket port = null;

        try {port = new ServerSocket(9999);}
        catch (IOException ex) {ex.printStackTrace();}

        while(true){
            try (Socket request = port.accept()) {
                input = new ObjectInputStream(request.getInputStream());
                p = (Package) input.readObject();
                request.close();
                
        //ROUTES ______________________________________________________________
                switch(p.getStatus()){
                    case "register": setNewRegisterUser(p); break;
                    case "login":    getLoginPassword(p); break;
                }      

            } catch (Exception ex) {ex.printStackTrace();}
        }
    }        
 // ===========================================================================
 //                    CONTROLLERS & MIDDLEWARE
 // ===========================================================================
    private void getLoginPassword(Package p) throws IOException{
        ObjectOutputStream msgpackage;
        String resp;
                
        //Send info to database
        resp = UserManager.checkLogin(p.getNick());
        p.setInfo(resp);
        
//        p.setObj(UserManager.getRegisteredChats(p.getNick()));
        
        //if OK resgister ip as last for this user        
//         if(resp.equals("OK")){DBConnection.setLastIP(p.getNick(), p.getIp());}
        
        Socket sendmsg = new Socket(p.getIp(), 9090);
        msgpackage = new ObjectOutputStream(sendmsg.getOutputStream());
        msgpackage.writeObject(p);
                        
        msgpackage.close(); sendmsg.close();
    }
    
    private void setNewRegisterUser(Package p) throws IOException{
        ObjectOutputStream msgpackage;
        String resp = UserManager.checkRegister(p.getNick(), p.getEmail());
        
        if(!resp.equals("")){p.setInfo(resp);}            
        else {        
            int registersuccess =
            UserManager.registerUser( p.getNick(),p.getPaswd(), p.getEmail());
            p.setInfo(registersuccess > 0 ? "" : "\n\n\n\nDatabase error...");
        }     
        
        //if OK resgister ip as last for this user
//         if(resp.equals("OK")){DBConnection.setLastIP(p.getNick(), p.getIp());}

        Socket sendmsg = new Socket(p.getIp(), 9090);
        msgpackage = new ObjectOutputStream(sendmsg.getOutputStream());
        msgpackage.writeObject(p);
                        
        msgpackage.close(); sendmsg.close();  
    }
 // ===========================================================================
 //                            RESPONSE
 // ===========================================================================
    public void response(Package p, String ip) throws IOException{
        ObjectOutputStream msgpackage;
        
        Socket sendmsg = new Socket(ip, 9090);
        msgpackage = new ObjectOutputStream(sendmsg.getOutputStream());
        msgpackage.writeObject(p);

        msgpackage.close(); sendmsg.close(); 
    }
}

