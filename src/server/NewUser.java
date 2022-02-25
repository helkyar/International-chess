/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import packager.Packager;

/**
 *
 * @author admin
 */
class NewUser implements Runnable{

    NewUser(){
        Thread lintening = new Thread(this);
        lintening.start();
    }

    @Override
    public void run() {
        packager.Packager p;
        ObjectInputStream input;
        ServerSocket port = null;

        try {port = new ServerSocket(7777);}
        catch (IOException ex) {ex.printStackTrace();}

        while(true){
        // REQUEST ________________________________________________________________
            try (Socket request = port.accept()) {
                input = new ObjectInputStream(request.getInputStream());
                p = (packager.Packager) input.readObject();
                //layer of protection
                if(!p.getStatus().equals("online")){return;}
                //Get client ip 
                InetAddress locateip = request.getInetAddress();
                String ip = locateip.getHostAddress();
                p.setStatus("imserver"); 
                p.setIp(ip);
                               
        // RESPONSE _______________________________________________________________
                ObjectOutputStream msgpackage;
        
                Socket sendmsg = new Socket(ip, 7070);
                msgpackage = new ObjectOutputStream(sendmsg.getOutputStream());
                msgpackage.writeObject(p);

                msgpackage.close(); sendmsg.close(); request.close();
                
        //SHOW INFO _______________________________________________________________    
                Server.txt.append("New connection: " + ip); 
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }
}