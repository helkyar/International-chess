/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller.session;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 *
 * @author admin
 */
public class InputValidator {

    //LOGIN INPUT VALIDATION __________________________________________________
    //LOGIN SERVER VALIDATI____________________________________________________
    public static String[] validateRegister(String nick, String email, 
        String password, String cnfpaswd) {
        //send inputs check them
        //send msg to display in panel
        //indicate error or sucess
        //CHECK INPUTS
        //(B)blank        
        //SET ERROR MSG and CHANGE PANEL [swap=!swap] 
        String pwdcryp;
        try {pwdcryp = CryptoValidator.generateEncryptation(password);} 
        catch (NoSuchAlgorithmException ex) {}
        catch (InvalidKeySpecException ex) {}
        return new String[]{};
    }

    public static String[] validateLogin(String nick, String password) {    
        return new String[]{};
    }
    
}
