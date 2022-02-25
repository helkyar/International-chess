/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.socket;

import packager.Packager;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import onlinechess.controller.socket.utils.NetUtils;
import onlinechess.views.Session;

/**
 *
 * @author admin
 */
public class SearchServer {
    
    private Session session;
    private boolean loop = true;
    private boolean firstOne = true;
    private boolean badconnection = false;
    
    public SearchServer(Session session){
        this.session = session;
        new ResponseServer();
        startSearch("");
        //init response listener
        new Response();
    }
    
    public void startSearch(String ac){
        loop = true; firstOne = true;
        new Timer(6000, (ActionEvent evt) -> {
            if(session.connecting){getServerIP();}
            else{
                session.startValidations(ac);
                ((Timer)evt.getSource()).stop();}
        }).start(); 
    }
    
    private void getServerIP() {     
    //CHECK CONNECTION ________________________________________________________
        boolean localNet = NetUtils.getLocalIp().size() > 0;
        if(!localNet || !NetUtils.isConnected()){
            session.badConnection(); 
            System.out.println("Local: " + localNet + "Internet:" +!NetUtils.isConnected());
            badconnection = true; 
            return;
        }
        badconnection = false;
    //SEARCH SERVER IP IN CLIENT NET __________________________________________
        //(!)CAREFULL VIRTUAL IPS AND ARRAY OUT OF BOUNDS
        String ip = (String) NetUtils.getLocalIp().get(2);        
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
            loop = false;
            ((Timer)e.getSource()).stop();
            if(!session.connecting || badconnection){return;}
            
            session.serverResponseTimeout();
            session.setInfoLabel("TIMEOUT");
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
                Packager p = new Packager();
                p.setStatus("online");
                objp = new ObjectOutputStream(socket.getOutputStream());
                objp.writeObject(p);
                socket.close();

                System.out.println("=========================================");
                System.out.println("Server OK: "+ip+i);
                System.out.println("=========================================");
            } catch(IOException ex){}
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
            Packager p;
            
            try {port = new ServerSocket(7070);}
            catch (Exception e){}

            while(true){
                try (Socket response = port.accept()) {
                    input = new ObjectInputStream(response.getInputStream());
                    p = (Packager) input.readObject();
            //(!)CHESS HORSE GIF NEEDEEEEED!!
                    setUserMessage(41,"\n\nTying the horses...");
                    if(!p.getStatus().equals("imserver")){return;}
                    
                    InetAddress locateip = response.getInetAddress();
                    String ip = locateip.getHostAddress();
                    
                    session.setConnecting(false);
                    session.setInfoLabel("SUCCESS");
                    Request.ownip = p.getIp();//(!)why error??
                    Request.server = ip;
                    
                    response.close();
                } catch(Exception e){}                    
            }
           
        }                     
    }
    
    private void setUserMessage(int i,String msg){        
        try { session.doc.insertString(i, msg, null );} 
        catch (BadLocationException ex) { }
    }
}