/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import packager.Package;
/**
 *
 * @author admin
 */
public class Response {
    
    public static void res(Package p, String ip) throws IOException{
        ObjectOutputStream msgpackage;
        
        Socket sendmsg = new Socket(ip, 7070);
        msgpackage = new ObjectOutputStream(sendmsg.getOutputStream());
        msgpackage.writeObject(p);

        msgpackage.close(); sendmsg.close(); 
    }
}
