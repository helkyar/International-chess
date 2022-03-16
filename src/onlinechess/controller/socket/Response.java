/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import onlinechess.controller.ScreenCtrl;
import onlinechess.controller.session.InputValidator;
import onlinechess.views.Session;
import packager.Packager;

/**
 * Processes and routes all server responses.
 * @author Javier Palacios
 */
class Response implements Runnable{
       
    Response(){
        Thread lintening = new Thread(this);
        lintening.start();
    }

    @Override
    public void run() {
        packager.Packager p;
        String nick, move, msg;
        ObjectInputStream input;
        ServerSocket port = null;

        try {port = new ServerSocket(9090);}
        catch (IOException ex) {ex.printStackTrace();}

        while(true){
            try (Socket request = port.accept()) {
                input = new ObjectInputStream(request.getInputStream());
                p = (packager.Packager) input.readObject();
                request.close();

                switch(p.getStatus()){
                    case "login":    setLoginMessage(p); break;
                    case "register": setRegisterMessage(p); break;                     
                    case "online":   setUsersOnline(p); break;                        
//                        case "getusers": setUsersOnline(p); break;
//                        case "message":  sendMessage(p); break;
//                        case "managegroup": serverMembersResponse(p); break;
//                        case "groupusers":  informChatUsers(p); break;                       
//                        case "changeusers": refreshGroups(p); break;                
                }
            } catch(Exception e){e.printStackTrace();}                    
        }
    }    
    //Gets server response about login/register and sends it accordingly

    private void setLoginMessage(Packager p) {
        InputValidator.serverLoginValidator(p.getInfo(),p.getNick(), p.getId());
    }

    private void setRegisterMessage(Packager p) {
        InputValidator.svrRegisterValidator(p.getInfo(), p.getNick(), p.getId());
    }

    private void setUsersOnline(Packager p) {
        //Recieves a map ip -> user
        //ScreenCtrl.setChats("test", "test", 0,"ww");        
        //ScreenCtrl.setChats("test2", "test2", 0,"ww2");
        for(String ip : p.getIps().keySet()){
            System.out.println("User:"+p.getIps().get(ip) +"@"+ ip);
            ScreenCtrl.setChats(ip, p.getIps().get(ip), p.getId(), p.getInfo());
        }  
    }
}
    
