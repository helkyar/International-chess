/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import onlinechess.helpers.ConfigApp;
    
/**
 *
 * @author javier
 */
public class Session extends JFrame{
    
    public Session(ConfigApp cnfiguration){
        
        this.cnf = cnfiguration;
        close = new JLabel(cnf.CLOSE_ICON);
        logo = new JLabel(cnf.APP_ICON);   
        
    //PANEL STRUCTURE__________________________________________________________
        login.setLayout(null);
        register.setLayout(null);
    
    //SET LOCATION AND SIZE ___________________________________________________
        setLocationAndSize(60);
        
    //ADD LISTENERS ___________________________________________________________
        changepanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        close.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        close.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){dispose();}
        });
        changepanel.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                changepanel.setText(loglink);
                sessionbtn.setText("REGISTER");
                setLocationAndSize(20);
            }
        });
        sessionbtn.addActionListener((ActionEvent e)->{
            
        });
        
    //STYLE OPTIONS ___________________________________________________________
        login.setBackground(cnf.PRIME);        
        register.setBackground(cnf.PRIME);
        userlabel.setForeground(cnf.ALT);        
        paswlabel.setForeground(cnf.ALT);      
        changepanel.setForeground(cnf.ALT);
        //center text        
        usertxt.setHorizontalAlignment(JTextField.CENTER);
        paswtxt.setHorizontalAlignment(JTextField.CENTER);
    
    //ADD COMPONENTS __________________________________________________________   
        login.add(close);
        login.add(logo);
        login.add(userlabel);
        login.add(paswlabel);
        login.add(usertxt);
        login.add(paswtxt);
        login.add(changepanel);
        login.add(sessionbtn);        
        add(login);
    
    //FRAME STRUCTURE _________________________________________________________  
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(500,700);
        
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }

    private void setLocationAndSize(int off) {
        close.setBounds(w-25,5,20,20);
        logo.setBounds(w/2-100,50,200,150);
        userlabel.setBounds(w/4,145+off,150,30);
        usertxt.setBounds(w/4,175+off,w/2,30);
        paswlabel.setBounds(w/4,245+off,150,30);
        paswtxt.setBounds(w/4,275+off,w/2,30);
        sessionbtn.setBounds(w/2-50,350+off,100,30);
        changepanel.setBounds(155,400+off,200,30);
    }
    
//VARIABLES ___________________________________________________________________
    private ConfigApp cnf;
    
    private int w = 500;
    private int h = 700;
    
    private JPanel login = new JPanel();    
    private JPanel register = new JPanel();
    
//COMPONENTS __________________________________________________________________
    private final JLabel close;
    private final JLabel logo;
    private final JTextField usertxt = new JTextField(); 
    private final JPasswordField paswtxt = new JPasswordField();  
    private final JTextField confpswd = new JTextField(20);    
    private final JTextField email = new JTextField(20);
    private final JLabel userlabel= new JLabel("Username: ");
    private final JLabel paswlabel= new JLabel("Password: ");
    private final JButton sessionbtn = new JButton("LOGIN");
    private String reglink = "No tienes cuenta? Crea una aquí...";
    private String loglink = "Ya tienes cuenta? Inicia sesión...";
    private final JLabel changepanel = new JLabel(reglink);

       
}
