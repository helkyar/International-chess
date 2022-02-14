/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.views.session;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import onlinechess.helpers.AppConfig;
    
/**
 *
 * @author javier
 */
public class Session extends JFrame{
    private AppConfig cnf;
    private JPanel login = new JPanel();    
    private JPanel register = new JPanel();
    
    public Session(AppConfig cnfiguration){
        this.cnf = cnfiguration;
    //PANEL STRUCTURE__________________________________________________________
        login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));
        register.setLayout(new BoxLayout(register, BoxLayout.Y_AXIS));
        login.setBackground(cnf.PRIME);        
        register.setBackground(cnf.PRIME);
    
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
