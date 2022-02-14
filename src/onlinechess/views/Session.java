/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.views;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
       auth.setLayout(null);
    
    //SET LOCATION AND SIZE ___________________________________________________
        setLocationAndSize(60);
        
    //ADD LISTENERS ___________________________________________________________
        changepanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        close.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        close.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){dispose();}
        });
        swapChangePanelListener();
        sessionbtn.addActionListener((ActionEvent e)->{
            //send info
        });
        
    //STYLE OPTIONS ___________________________________________________________
       auth.setBackground(cnf.PRIME);    
        userlabel.setForeground(cnf.ALT);        
        paswlabel.setForeground(cnf.ALT);      
        changepanel.setForeground(cnf.ALT);
        //center text        
        usertxt.setHorizontalAlignment(JTextField.CENTER);
        paswtxt.setHorizontalAlignment(JTextField.CENTER);
    
    //ADD COMPONENTS __________________________________________________________   
        auth.add(close);
        auth.add(logo);
        auth.add(userlabel);
        auth.add(paswlabel);
        auth.add(usertxt);
        auth.add(paswtxt);
        auth.add(changepanel);
        auth.add(sessionbtn);        
        add(auth);
    
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
        
        email.setBounds(155,450+off,200,30);
    }
    
    private void swapChangePanelListener(){
        changepanel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                changepanel.setText(newuser ? loglink:reglink);
                sessionbtn.setText(newuser ? "REGISTER":"LOGIN");   
                setLocationAndSize(newuser ? 20 : 60);             
                newuser = newuser ? addComp() : removeComp();
                auth.setVisible(false); auth.setVisible(true);
            }

            private boolean addComp() {
                auth.remove(email); 
                return false;
            }

            private boolean removeComp() {                
                auth.add(email);
                return true;
            }
        });
    }
    
//VARIABLES ___________________________________________________________________
    private ConfigApp cnf;
    
    private int w = 500;
    private int h = 700;
    
    private JPanel auth = new JPanel();    
    boolean newuser = false;
    
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
