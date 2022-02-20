/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.socket;

import Package.Package;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TimerTask;
import javax.swing.Timer;
import onlinechess.controller.socket.utils.GetIP;

/**
 *
 * @author admin
 */
public class SS {
    /**
     * Check if user connceted to internet
     * Only one loop each time
     * Wait for response
     * Options on timeout:{RETRY, LOCAL}
     * Send SERVERIP to APP
     * Send messages to APP
     * Close all processes 
     */
    
    /**
     * 
     */
    private void getServerIP() {
        //Search server ip in the client net
        String ip = (String) GetIP.getLocalIp().get(1);
        ip = ip.substring(0, ip.lastIndexOf(".")+1);
        //Check last 255 local ips searching for server
        for(int i = 0; i<=255; i++){
            Thread t = new Thread(new SearchServer(i, ip));

            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new KillSearchThread(t, timer), 100);
            t.start();
        }
        //DESTROY EVERYTHING AND SET OPTIONS {RETRY-LOCAL}
        new Timer(15000, (ActionEvent e) -> { }).start();
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
        while (!Thread.interrupted()) {
            try {
                try (Socket socket = new Socket(ip+i,7777)) {
                    Package p = new Package();
                    p.setStatus("online");
                    ObjectOutputStream objp = new ObjectOutputStream(socket.getOutputStream());
                    objp.writeObject(p);
                    socket.close();
                } 
            } catch (IOException ex) {System.out.println("Server tested: "+ip+i);}
        }
    }
}
class KillSearchThread extends TimerTask {
    private Thread t;
    private java.util.Timer timer;

    public KillSearchThread(Thread t, java.util.Timer timer){
        this.t = t;
        this.timer = timer;
    }

    public void run() {
        if (t != null && t.isAlive()) {
            t.interrupt();
            timer.cancel();
        }
    }
}
class RecieveMsg implements Runnable{
       
        RecieveMsg(){
            Thread lintening = new Thread(this);
            lintening.start();
        }
        
        @Override
        public void run() {
            try { 
                ServerSocket port = new ServerSocket(7070);
                String nick, ip, move, msg;
                Package p;

                while(true){
                    try (Socket mysocket = port.accept()) {
                        ObjectInputStream entrada = new ObjectInputStream(mysocket.getInputStream());
                        p = (Package) entrada.readObject();
                        
                        if(p.getStatus().equals("imserver")){initSession();}                      
                    } catch(Exception e){}                    
                }
            } catch (Exception e){}
        }                     
        private String initSession(){
            /**
             * SET APP IP
             * SET INFO MESSAGES OF EXIT
             */
            return "";
        }
        
    }
    