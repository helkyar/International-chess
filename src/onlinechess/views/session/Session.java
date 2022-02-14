/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.views.session;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
    
/**
 *
 * @author javier
 */
public class Session extends JFrame{
    private JPanel login = new JPanel();    
    private JPanel register = new JPanel();
    
    public Session(){
    //PANEL STRUCTURE__________________________________________________________
        login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));
        register.setLayout(new BoxLayout(register, BoxLayout.Y_AXIS));
        login.setBackground(Color.black);        
        register.setBackground(Color.black);
    
    //ADD COMPONENTS __________________________________________________________
        add(login);
        add(register);
    
    //FRAME STRUCTURE _________________________________________________________  
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500,700);
        
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }
    
}
