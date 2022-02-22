/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.session;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import onlinechess.controller.socket.Request;
import onlinechess.helpers.ConfigApp;
import onlinechess.views.Session;

/**
 *
 * @author admin
 */
public class InputValidator {
    private static ConfigApp cnf = new ConfigApp();
    private static String password;
    private static String lgnmsg = "";
    private static String rgtmsg = "";
    private static Session session;
    
    /**
     * Register Input Field Validator, calls the server searching for repeated
     * users and emails. Server will store new user if none found
     * @param nick
     * @param email
     * @param password
     * @param cnfpaswd
     * @param session sets the JDialog reference to set the response msg
     * @return 
     */
    public static String[] validRegister(String nick, String email, 
        String password, String cnfpaswd, Session session) {
        InputValidator.session = session;
        rgtmsg = "";
        //https://emailregex.com/
        String eCheck = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+"
          + "/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23"
          + "-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@("
          + "?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a"
          + "-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?"
          + ":25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x"
          + "01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-"
          + "\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        
    //INPUTFIELD VALIDATION ___________________________________________________
        String blank = nick.trim().equals("") ? "" : nick;
        blank = email.trim().equals("") ? "" : blank;
        blank = password.trim().equals("") ? "" : blank;
        blank = cnfpaswd.trim().equals("") ? "" : blank;
        
        if(blank.equals("")){rgtmsg+= cnf.BLANK;}
        if(email.matches(eCheck)){rgtmsg+= cnf.WRONG_MAIL;}
        if(password.equals(cnfpaswd)){rgtmsg+= cnf.DIFF_PWD;}
        
        if(nick.equals("") && email.equals("") && email.matches(eCheck)){
            return new String[]{"ERROR", rgtmsg};
        }
        
    //REGISTERING _____________________________________________________________
        String pwdcryp="";
        try {pwdcryp = CryptoValidator.generateEncryptation(password);} 
        catch (NoSuchAlgorithmException ex) {}
        catch (InvalidKeySpecException ex) {}
            
        Request.registerUser(nick, pwdcryp, email);
        return new String[]{"WAIT", rgtmsg};
    }
    
    /**
     * Login Input Field Validator, calls the server to get the password
     * @param nick
     * @param pwd
     * @param session sets the JDialog reference to set the response msg
     * @return 
     */
    public static String[] validLogin(String nick, String pwd, Session session){
        InputValidator.session = session;
        InputValidator.password = pwd;
    //INPUTFIELD VALIDATION ___________________________________________________
        if(nick.equals("") || pwd.equals("")){
            return new String[]{"ERROR", cnf.BLANK};
        }
    //LOGIN ___________________________________________________________________
        Request.loginUser(nick);
        return new String[]{"WAIT", ""};
    }
    
    /**
     * Gets encrypted password from server and validates it. If no user exists
     * password is empty and same error message gets displayed.
     * @param storedpwd 
     */
    public static void serverLoginValidator(String storedpwd){
        String[] response;
        
        boolean isCorrect=false;
        try {isCorrect = CryptoValidator.validatePassword(password, storedpwd);} 
        catch (NoSuchAlgorithmException ex) {} 
        catch (InvalidKeySpecException ex) {}
        
        InputValidator.password = ""; //destroy saved password
        if(!isCorrect){lgnmsg += cnf.WRONG_CREDENTIALS;}
        
        if(!lgnmsg.equals("")){response = new String[]{"ERROR", lgnmsg};}
        response = new String[]{"OK", cnf.LOGIN};
        
        boolean denied =  response[0].equals("ERROR");
        String msg = response[1];
        
        session.setValidationMessage(msg, denied);
    }
    
    /**
     * Gets server response and calls (JDialog) Session to set the response.
     * @param servermsg 
     */
    public static void serverRegisterValidator(String servermsg){
        String[] response;
        rgtmsg += servermsg;
        
        if(!rgtmsg.equals("")){response = new String[]{"ERROR", rgtmsg};}
        response = new String[]{"OK", cnf.REGISTER};
        
        boolean denied =  response[0].equals("ERROR");
        String msg = response[1];
        
        session.setValidationMessage(msg, denied);
    }
    
}
