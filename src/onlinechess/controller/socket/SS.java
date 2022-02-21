/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.socket;

import packager.Package;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import onlinechess.controller.socket.utils.NetUtils;
import onlinechess.helpers.ConfigApp;
import onlinechess.views.ChessApp;
import onlinechess.views.Session;

/**
 *
 * @author admin
 */
public class SS {
    
    private boolean loop = true;
    private boolean onlyOne = true;
    
    public SS(){
        new RecieveMsg();
        getServerIP();
    }
    /**
     * Options on timeout:{RETRY, LOCAL}
     * Send SERVERIP to APP
     * Send messages to APP
     */
    
    public void getServerIP() {               
        //Search server ip in the client net
        String ip = (String) NetUtils.getLocalIp().get(1);
        ip = ip.substring(0, ip.lastIndexOf(".")+1);
        //Check 255 local ips searching for server
        if(!loop){return;}        
        Session.msgtxt.setText("\n\n\n\nSearching server\n"); 
        
        for(int i = 0; i<=255; i++){
            new Thread(new SearchServer(i, ip)).start();
        }
        setUserMessage(20, "\n\nWaiting response...\n");
        //DESTROY EVERYTHING AND SET OPTIONS {RETRY-LOCAL}
        if(onlyOne){
            onlyOne = false;
            new Timer(20000, (ActionEvent e) -> {
                loop = false;
                Session.serverResponseTimeout();           
                ((Timer)e.getSource()).stop();
            }).start();
        }
        
    }
        
    class SearchServer implements Runnable {
        int i;
        String ip;

        public SearchServer(int i, String ip){
            this.ip = ip;
            this.i = i;
        }

        @Override
        public void run() {
            ObjectOutputStream objp;
            // Alternatively:  while (!Thread.interrupted()) {}
            try {
                Socket socket = new Socket(ip+i,7777);
                Package p = new Package();
                p.setStatus("online");
                objp = new ObjectOutputStream(socket.getOutputStream());
                objp.writeObject(p);
                socket.close();

                System.out.println("===============================================");
                System.out.println("Server OK: "+ip+i);
                System.out.println("===============================================");
            } catch(IOException ex){System.out.println("Server test: "+ip+i);}
        }
    }

    class RecieveMsg implements Runnable{
       
        RecieveMsg(){
            Thread lintening = new Thread(this);
            lintening.start();
        }
        
        @Override
        public void run() {
            ObjectInputStream input;
            ServerSocket port = null;
            Package p;
            
            try {port = new ServerSocket(7070);}
            catch (Exception e){}

            while(true){
                try (Socket mysocket = port.accept()) {
                    input = new ObjectInputStream(mysocket.getInputStream());
                    p = (Package) input.readObject();
                    
                    setUserMessage(41,"\n\nTying the horses...");
                    if(!p.getStatus().equals("imserver")){return;}
                    
                    Session.setOffline(false);
                    System.out.println(p.getStatus());

                } catch(Exception e){}                    
            }
           
        }                     
    }
    
    private void setUserMessage(int i,String msg){        
        try { Session.doc.insertString(i, msg, null );} 
        catch (BadLocationException ex) { }
    }
}