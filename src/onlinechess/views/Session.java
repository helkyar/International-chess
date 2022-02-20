/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.views;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import onlinechess.helpers.ConfigApp;
    
/**
 *
 * @author javier
 */
public class Session extends JDialog{
    
    public Session(JFrame parent, ConfigApp cnf){
        super(parent, true);
        
        this.cnf = cnf;
        close = new JLabel(cnf.CLOSE_ICON);
        wait = new JLabel(cnf.LOAD_ICON);
        logo = new JLabel(cnf.APP_ICON); 
        
    //PANEL STRUCTURE__________________________________________________________
       auth.setLayout(null);
       message.setLayout(null);
    
    //SET LOCATION AND SIZE ___________________________________________________
        setLocationAndSize(40);
        msgtxt.setBounds(0,0,w,h);
        
    //ADD LISTENERS ___________________________________________________________
        changepanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        close.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        paswtxt.setTransferHandler(null); //avoid paste       
        msgtxt.setEditable(false);
        swapChangePanelListener();
        
        paswshow.addActionListener((ActionEvent e)->{showPassword();});        
        sessionbtn.addActionListener((ActionEvent e)->{sessionStart(e);});    
        guesstbtn.addActionListener((ActionEvent e)->{sessionStart(e);});
        localbtn.addActionListener((ActionEvent e)->{sessionStart(e);});
        close.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){dispose();}
        });
        
    //STYLE OPTIONS ___________________________________________________________
        setIconImage(cnf.APP_ICON.getImage());
        auth.setBackground(cnf.PRIME); 
        paswshow.setBackground(cnf.PRIME);
        userlabel.setForeground(cnf.ALT);        
        paswlabel.setForeground(cnf.ALT);      
        changepanel.setForeground(cnf.ALT);
        emailabel.setForeground(cnf.ALT);
        confpswdlabel.setForeground(cnf.ALT);
        //center text        
        usertxt.setHorizontalAlignment(JTextField.CENTER);
        paswtxt.setHorizontalAlignment(JTextField.CENTER);
        emailtxt.setHorizontalAlignment(JTextField.CENTER);
        confpswdtxt.setHorizontalAlignment(JTextField.CENTER);
        //register-login mesage        
        StyledDocument style = msgtxt.getStyledDocument();
        SimpleAttributeSet align = new SimpleAttributeSet();
        StyleConstants.setAlignment(align, StyleConstants.ALIGN_CENTER);
        style.setParagraphAttributes(0, style.getLength(), align, false);  
        msgtxt.setFont(new Font("arial", 3, 24));
        msgtxt.setBackground(cnf.PRIME);
        msgtxt.setForeground(cnf.ALT);
    
    //ADD COMPONENTS __________________________________________________________ 
        auth.add(close);
        auth.add(logo);
        auth.add(userlabel);
        auth.add(paswlabel);
        auth.add(usertxt);
        auth.add(paswtxt);
        auth.add(paswshow);
        auth.add(sessionbtn);
        auth.add(guesstbtn);
        auth.add(localbtn);
        auth.add(changepanel);
        //session start information
        message.add(wait);
        message.add(msgtxt);
        //card to change between views
        masterpanel.add(auth, "auth");
        masterpanel.add(message, "message");                
        add(masterpanel);
    
    //FRAME STRUCTURE _________________________________________________________ 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(w,h);
        
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }

    private void setLocationAndSize(int i) {
        int start=205, o=30;
        close.setBounds(w-25,5,20,20);
        logo.setBounds(w/2-100,50,200,150);
        userlabel.setBounds(w/4,start,150,30);
        usertxt.setBounds(w/4,start+o,w/2,30);
        paswlabel.setBounds(w/4,start+o*4-i,150,30);
        paswtxt.setBounds(w/4,start+o*5-i,w/2,30);
        paswshow.setBounds(w/4,start+o*5-10,w/2,30);
        sessionbtn.setBounds(w/2-105,start+o*9-i*2,100,30);
        guesstbtn.setBounds(w/2+5,start+o*9-i*2,100,30);
        localbtn.setBounds(w/2-50,start+o*10-i*2+10,100,30);
        changepanel.setBounds(155,start+o*11-i*2+5,200,30);
        
        emailabel.setBounds(w/4,start+o*2,w/2,30);
        emailtxt.setBounds(w/4,start+o*3,w/2,30);
        confpswdlabel.setBounds(w/4,start+o*6,w/2,30);
        confpswdtxt.setBounds(w/4,start+o*7,w/2,30);
    }
    
    private void swapChangePanelListener(){
        changepanel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                changepanel.setText(newuser ? reglink:loglink);
                sessionbtn.setText(newuser ? "LOGIN":"REGISTER");   
                setLocationAndSize(newuser ? 40 : 0);             
                newuser = newuser ? addComp() : removeComp();
                auth.repaint();
            }

            private boolean addComp() {
                auth.remove(emailtxt); 
                auth.remove(confpswdtxt); 
                auth.remove(emailabel);                 
                auth.remove(confpswdlabel);
                auth.add(paswshow);
                return false;
            }

            private boolean removeComp() { 
                auth.add(emailtxt); 
                auth.add(confpswdtxt); 
                auth.add(emailabel);                 
                auth.add(confpswdlabel);                
                auth.remove(paswshow);
                return true;
            }
        });
    }
    
    private void showPassword() {
        if (paswshow.isSelected()) {paswtxt.setEchoChar((char) 0);} 
        else {paswtxt.setEchoChar('\u2022');}
    }

    private void startAsGuesst(ActionEvent e) {
    
    }
       
    /**
     * Manages SESSSION START either login or register
     * @param e Action event (button) 
     */
    private void sessionStart(ActionEvent e) {
        ((CardLayout) masterpanel.getLayout()).next(masterpanel);
        String warning, msg="", enter = "\n\n\n\n\n\n\n\n";
        int timer = 2000;
        
    //LOCAL CASE ______________________________________________________________
        if(e.getActionCommand().equals("LOCAL")){
            warning = "\n\nChats & Boards \nwont be saved!";
            msgtxt.setText(enter+"Started local game..."+warning);
            new Timer(1000, (ActionEvent ev)->{dispose();}).start();
            return;
        }

    /**
     * (!)CHECK INTERNET CONNECTION
     * (!)CALL SERVER
     */
        msgtxt.insertIcon(cnf.LOAD_ICON);
        
    //GUESST CASE _____________________________________________________________        
        if(e.getActionCommand().equals("GUESST")){
            warning = "\n\nChats & Boards \nwont be saved!";
            msgtxt.setText(enter+"Session started as Guesst..."+warning);            
            new Timer(3000, (ActionEvent ev)->{dispose();}).start();
            return;
        }
    //VALIDATION ______________________________________________________________
        if(e.getActionCommand().equals("REGISTER")){   
            msg = enter+"\n\nRegistered successfully!!";
        } else {
             msg = enter+"\n\nAccess granted!!";
        }
        //set color red, change to previous panel, (?)set info on previus panel
    //SUCCESS MSG _____________________________________________________________        
        msgtxt.setText(msg);
        new Timer(timer, (ActionEvent ev)->{dispose();}).start();
    }
//==============================================================================
//VARIABLES 
//==============================================================================
    private final ConfigApp cnf;
    
    private final int w = 500;
    private final int h = 700;
    
    private final JPanel masterpanel = new JPanel(new CardLayout());
    private final JPanel message = new JPanel();
    private final JPanel auth = new JPanel();    
    boolean newuser = false;
    
//COMPONENTS __________________________________________________________________
    private final JLabel close;
    private final JLabel logo;
    private final JTextField usertxt = new JTextField(); 
    private final JPasswordField paswtxt = new JPasswordField();  
    private final JLabel confpswdlabel = new JLabel("Confirm Password: "); 
    private final JPasswordField confpswdtxt = new JPasswordField(20);     
    private final JLabel emailabel = new JLabel("Email: "); 
    private final JTextField emailtxt = new JTextField(20);
    private final JLabel userlabel= new JLabel("Username: ");
    private final JLabel paswlabel= new JLabel("Password: ");    
    private final JCheckBox paswshow = new JCheckBox("Show Password");
    private final JButton sessionbtn = new JButton("LOGIN");
    private final JButton guesstbtn = new JButton("GUESST");
    private final JButton localbtn = new JButton("LOCAL");
    private final String reglink = "No tienes cuenta? Crea una aquí...";
    private final String loglink = "Ya tienes cuenta? Inicia sesión...";
    private final JLabel changepanel = new JLabel(reglink);

    private final JTextPane msgtxt = new JTextPane();
    private final JLabel wait; 
}
//(>)Waiting gif when login on label