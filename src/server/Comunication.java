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

/**
 *
 * @author admin
 */
public class Comunication extends JFrame implements Runnable{
    private static int guest = 0;
    public static JTextArea txt = new JTextArea();
    private Map<String, String[]> ips = new HashMap<>(); 
    private String ip = "";
    
    Comunication(){
        Thread lintening = new Thread(this);
        lintening.start();
        new NewUser(); //new thread to avoid overlapping res/req
        
        add(new JScrollPane(txt));
        setSize(400,100);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new Comunication();
    }    

    @Override
    public void run() {
        Package p;
        String nick, move, msg;
        ObjectInputStream input;
        ServerSocket port = null;

        try {port = new ServerSocket(9999);}
        catch (IOException ex) {ex.printStackTrace();}

        while(true){
            try (Socket request = port.accept()) {
                input = new ObjectInputStream(request.getInputStream());
                p = (Package) input.readObject();

                switch(p.getStatus()){
                    /**
                     * FILTER REQUESTS
                     */
                }      

                request.close();
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }        
}

