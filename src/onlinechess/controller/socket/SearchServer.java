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
public class SearchServer {
    
    private Session session;
    private boolean loop = true;
    private boolean firstOne = true;
    
    public SearchServer(Session session){
        this.session = session;
        new ResponseServer();
        new Timer(6000, (ActionEvent evt) -> {
            if(session.connecting){getServerIP();}
            else{((Timer)evt.getSource()).stop();}
        }).start(); 
    }
    
    /**
     * Options on timeout:{RETRY, LOCAL}
     * Send SERVERIP to APP
     * Send messages to APP
     */
    
    public void getServerIP() {     
    //CHECK CONNECTION ________________________________________________________
        boolean localNet = NetUtils.getLocalIp().size() < 1;
        if(localNet){session.badConnection(); return;}//Not even net
        if(!NetUtils.isConnected()){session.badConnection(); return;}//Offline
        
    //SEARCH SERVER IP IN CLIENT NET __________________________________________
        String ip = (String) NetUtils.getLocalIp().get(1);
        ip = ip.substring(0, ip.lastIndexOf(".")+1);
        //Check 255 local ips searching for server
        if(!loop){return;}        
        session.msgtxt.setText("\n\n\n\nSearching server\n"); 
        session.setInfoLabel("WAITING");
        
        for(int i = 0; i<=255; i++){
            new Thread(new RequestServer(i, ip)).start();
        }
        
        setUserMessage(20, "\n\nWaiting response...\n");
    
    //SET TIMEOUT RESPONSE ____________________________________________________
        if(!firstOne){ return;}        
        firstOne = false;
        
        new Timer(20000, (ActionEvent e) -> {
    //(!)CHECK IF SESSION STARTED or BADCONNECTION IN THE MEANTIME
            loop = false;
            session.serverResponseTimeout();
            session.setInfoLabel("TIMEOUT");
            ((Timer)e.getSource()).stop();
        }).start();       
    }
        
    class RequestServer implements Runnable {
        int i;
        String ip;

        public RequestServer(int i, String ip){
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

    class ResponseServer implements Runnable{
       
        ResponseServer(){
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
            //(!)CHESS HORSE GIF NEEDEEEEED!!
                    setUserMessage(41,"\n\nTying the horses...");
                    if(!p.getStatus().equals("imserver")){return;}
                    
                    session.setConnecting(false);
                    session.setInfoLabel("SUCCESS");

                } catch(Exception e){}                    
            }
           
        }                     
    }
    
    private void setUserMessage(int i,String msg){        
        try { session.doc.insertString(i, msg, null );} 
        catch (BadLocationException ex) { }
    }
}