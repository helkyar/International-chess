/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import packager.Package;

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
        packager.Package p;
        String nick, move, msg;
        ObjectInputStream input;
        ServerSocket port = null;

        try {port = new ServerSocket(7777);}
        catch (IOException ex) {ex.printStackTrace();}

        while(true){
            try (Socket request = port.accept()) {
                input = new ObjectInputStream(request.getInputStream());
                p = (packager.Package) input.readObject();

                switch(p.getStatus()){
                    case "online": sayHello(request, p); break;
                }      

                request.close();
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    private void sayHello(Socket request, Package p) throws IOException{
        //Get client ip 
        InetAddress locateip = request.getInetAddress();
        String getip = locateip.getHostAddress();
        p.setStatus("imserver"); 

        Response.response(p, getip);  

    //SHOW INFO ___________________________________________________________    
        Comunication.txt.append("New connection: "+getip);
    }

    }