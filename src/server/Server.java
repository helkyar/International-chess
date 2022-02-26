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
import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import packager.Packager;
import server.dbconnect.managers.UserManager;

/**
 *
 * @author admin
 */
public class Server extends JFrame implements Runnable{
    private static int guest = 0;
    public static JTextArea txt = new JTextArea();
    public static Map<String, String> ips = new HashMap<>(); 
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
        Packager p;
        ObjectInputStream input;
        ServerSocket port = null;

        try {port = new ServerSocket(9999);}
        catch (IOException ex) {ex.printStackTrace();}

        while(true){
            try (Socket request = port.accept()) {
                input = new ObjectInputStream(request.getInputStream());
                p = (Packager) input.readObject();
                request.close();
                
        //ROUTES ______________________________________________________________
                switch(p.getStatus()){
                    case "register": setNewRegisterUser(p); break;
                    case "login":    getLoginPassword(p); break;
                    case "online":   getUsersOnline(p); break;
                }      

            } catch (Exception ex) {ex.printStackTrace();}
        }
    }        
 // ===========================================================================
 //                    CONTROLLERS & MIDDLEWARE
 // ===========================================================================
    private void getLoginPassword(Packager p) throws IOException{
        //Send info to database
        Map<Integer, String> resp = UserManager.checkLogin(p.getNick());
        if(resp == null){p.setInfo("");}            
        else{for(int id : resp.keySet()){p.setInfo(resp.get(id)); p.setId(id);}}

//        p.setObj(UserManager.getRegisteredChats(p.getNick()));
        
        //if OK resgister ip as last for this user        
//         if(resp.equals("OK")){DBConnection.setLastIP(p.getNick(), p.getIp());}
        
        response(p, p.getIp());
    }
    
    private void setNewRegisterUser(Packager p) throws IOException{
        String resp = UserManager.checkRegister(p.getNick(), p.getEmail());
        
        if(!resp.equals("")){//user or email found   
            p.setInfo(resp);
        } else {        
            int registersuccess =
            UserManager.registerUser( p.getNick(),p.getPaswd(), p.getEmail());
            p.setInfo(registersuccess > 0 ? "" : "\n\n\n\nDatabase error...");
            //use the same method as login to get the new user_id
            Map<Integer, String> getid = UserManager.checkLogin(p.getNick());
            if(resp != null) {for(int id : getid.keySet()){p.setId(id);}}
            else {p.setId(-1);}   
        }     
        
        //if OK resgister ip as last for this user
//         if(resp.equals("OK")){DBConnection.setLastIP(p.getNick(), p.getIp());}
        response(p, p.getIp());  
    }
    
    private void getUsersOnline(Packager p) throws IOException {            
        //Sets new user on the map and send the actualized version to all users        
        String chatid =  "~u~"+new Date().getTime()+Math.random(); 
        ips.put(p.getIp(), p.getNick());
        
        p.setInfo(chatid);
        p.setIps(ips);
        
        System.out.println("User:"+p.getNick() +"@"+ p.getIp());
        for(String userip : ips.keySet()){response(p, userip);}        
    }
 // ===========================================================================
 //                            RESPONSE
 // ===========================================================================
    public void response(Packager p, String ip) throws IOException{
        ObjectOutputStream msgpackage;
        
        Socket sendmsg = new Socket(ip, 9090);
        msgpackage = new ObjectOutputStream(sendmsg.getOutputStream());
        msgpackage.writeObject(p);

        msgpackage.close(); sendmsg.close(); 
    }
    
 // ===========================================================================
 //                            VARIABLES
 // ===========================================================================    

}

