/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import onlinechess.controller.socket.SearchServer;
import onlinechess.controller.socket.utils.NetUtils;
import onlinechess.helpers.ConfigApp;
    
/**
 *
 * @author javier
 */
public class Session extends JDialog{

    public Session(JFrame parent, ConfigApp cnf){
        super(parent, true);
        
        this.cnf = cnf;
        logo = new JLabel(cnf.APP_ICON); 
        close = new JLabel(cnf.CLOSE_ICON);
        paswshow = new JLabel(cnf.EYE_CLOSE);
        
    //PANEL STRUCTURE__________________________________________________________
       auth.setLayout(null); 
       wait.setLayout(new BorderLayout());
       message.setLayout(new BorderLayout());
       JPanel style = new JPanel();
    
    //SET LOCATION AND SIZE ___________________________________________________
        setLocationAndSize(40);
        style.setPreferredSize(new Dimension(0,7));
        wait.setPreferredSize(new Dimension(30,0));
        options.setPreferredSize(new Dimension(0,200));
        msgtxt.setBounds(0,0,w,h-100);
        
    //ADD LISTENERS ___________________________________________________________
        changepanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        close.setCursor(new Cursor(Cursor.HAND_CURSOR));
        paswshow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        paswtxt.setTransferHandler(null); //avoid paste       
        msgtxt.setEditable(false);
        swapChangePanelListener();
        
        sessionbtn.addActionListener((ActionEvent e)->{sessionStart(e);});    
        guesstbtn.addActionListener((ActionEvent e)->{sessionStart(e);});
        localbtn.addActionListener((ActionEvent e)->{sessionStart(e);});
        close.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){localSessionInit(swap);}
        });        
        paswshow.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){showPassword();}
        });
        
    //STYLE OPTIONS ___________________________________________________________
        setIconImage(cnf.APP_ICON.getImage());
        auth.setBackground(cnf.PRIME); 
        paswshow.setBackground(cnf.PRIME);
        userinfo.setForeground(cnf.ALT); 
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
        SimpleAttributeSet align = new SimpleAttributeSet();
        StyleConstants.setAlignment(align, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), align, false);  
        msgtxt.setFont(new Font("arial", 3, 24));
        msgtxt.setBackground(cnf.PRIME);
        msgtxt.setForeground(cnf.ALT);        
        wait.setBackground(cnf.PRIME);
        options.setBackground(cnf.PRIME);
        style.setBackground(cnf.PRIME);
            
    //ADD COMPONENTS __________________________________________________________ 
        auth.add(userinfo);
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
        message.add("North",style);
        message.add("East",wait);
        message.add("Center",msgtxt);        
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
                
    //SEARCH SERVER ___________________________________________________________
        new SearchServer(this);
    }

    private void setLocationAndSize(int i) {
        int start=205, o=30;
        userinfo.setBounds(w/6,5,w/2,20);
        close.setBounds(w-25,5,20,20);
        logo.setBounds(w/2-100,50,200,150);
        userlabel.setBounds(w/4,start,150,30);
        usertxt.setBounds(w/4,start+o,w/2,30);
        paswlabel.setBounds(w/4,start+o*4-i,150,30);
        paswtxt.setBounds(w/4,start+o*5-i,w/2-33,30);
        paswshow.setBounds(w/4+w/2-57,start+o*5-i,w/6,30);
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
                auth.repaint(); auth.validate();
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
        toggle = !toggle;
        if (!toggle) {
            paswshow.setIcon(cnf.EYE_OPEN);
            paswtxt.setEchoChar((char) 0);
        } else {
            paswshow.setIcon(cnf.EYE_CLOSE);
            paswtxt.setEchoChar('\u2022');
        }
    }
    
    private void inputValidation(String ac){
        //GET INPUTS
        //CHECK INPUTS
            //(B)blank
            //(R)secure password
        //SET ERROR MSG
    }

    /**
     * Manages APP START either local, guesst, login or register
     * @param e Action event (button) 
     */
    private void sessionStart(ActionEvent e) {
        String ac = e.getActionCommand();
        inputValidation(ac);
        
        ((CardLayout) masterpanel.getLayout()).next(masterpanel);
        swap = !swap;
        
    //LOCAL CASE ______________________________________________________________
        if(ac.equals("LOCAL")){localSessionInit(swap); return;}
        wait.add("North",close);
        
    //CHECK CONNECTION AND SEARCH SERVER ______________________________________ 
        //(!)INSERT LOADING ICON        
        SearchServer search = new SearchServer(this);
        new Timer(6000, (ActionEvent evt) -> {
            if(connecting){search.getServerIP();}
            else{
                ((Timer)evt.getSource()).stop();
                serverValidation(ac);
            }
        }).start();       
    }
    
    private void localSessionInit(boolean swap) {       
        message.remove(options);
        message.remove(wait);
        if(swap) {((CardLayout) masterpanel.getLayout()).next(masterpanel);} 
        new Timer(1100, (ActionEvent ev)->{dispose();}).start(); 
        msgtxt.setText(cnf.LOCAL);
    }
    
    private void serverValidation(String ac) {
        String msg="";
       
    //GUESST CASE _____________________________________________________________        
        if(ac.equals("GUESST")){
            msgtxt.setText(cnf.GUEST);                  
            new Timer(1500, (ActionEvent ev)->{dispose();}).start();
            return;
        }
    //SERVER VALIDATION _______________________________________________________
        if(ac.equals("REGISTER")){   
            msg = cnf.REGISTER;
        } 
        else if(ac.equals("LOGIN")) {
            msg = cnf.LOGIN;
        }
        //set color red, change to previous panel, (?)set info on previus panel
    //SUCCESS MSG _____________________________________________________________        
        msgtxt.setText(msg);
        new Timer(2000, (ActionEvent ev)->{dispose();}).start();
    }

    public void badConnection() {
        if(!swap){
            msgtxt.setText(cnf.LOST+"Retry: "+Math.random()+"\n\n"); 
            msgtxt.insertIcon(cnf.DESCONNECTED);
            options.add(localbtn);
            message.add("South", options);
        }
        setInfoLabel("DISCONNECTED");
    }
        
    public void serverResponseTimeout() {
        msgtxt.setText(cnf.TIMEOUT);
        msgtxt.insertIcon(cnf.LOAD_ICON);
        options.add(localbtn);
    }
    
    public void setInfoLabel(String msg) {
        if(msg.equals("DISCONNECTED")){        
            userinfo.setText(cnf.LOST);
            userinfo.setIcon(cnf.MINICONNECTED);
        } else if(msg.equals("WAITING")){            
            userinfo.setText(cnf.WAIT);
            userinfo.setIcon(cnf.MINIWAIT);
        } else if(msg.equals("TIMEOUT")){            
            userinfo.setText(cnf.TIMEOUT);
            userinfo.setIcon(cnf.MINITIMEOUT);        
        } else if(msg.equals("SUCCESS")){            
            userinfo.setText(cnf.OK);
            userinfo.setIcon(cnf.MINISUCCESS);
        }
    }    
    
//GETTERS & SETTERS ___________________________________________________________
    public void setConnecting(boolean connecting){this.connecting = connecting;}
    
//VARIABLES____________________________________________________________________
    private ConfigApp cnf;
    
    private final int w = 500;
    private final int h = 700;
         
    private boolean swap = true;        
    private boolean toggle = true;
    private boolean newuser = false;     
    public boolean connecting = true;
    
//SWING COMPONENTS __________________________________________________________________
    private final JPanel masterpanel = new JPanel(new CardLayout());
    private final JPanel message = new JPanel();
    private final JPanel auth = new JPanel(); 
    
    private final JLabel close;
    private final JLabel logo;    
    private final JLabel paswshow;
    private final JLabel userinfo = new JLabel("", JLabel.RIGHT);
    private final JTextField usertxt = new JTextField(); 
    private final JPasswordField paswtxt = new JPasswordField();  
    private final JLabel confpswdlabel = new JLabel("Confirm Password: "); 
    private final JPasswordField confpswdtxt = new JPasswordField(20);     
    private final JLabel emailabel = new JLabel("Email: "); 
    private final JTextField emailtxt = new JTextField(20);
    private final JLabel userlabel= new JLabel("Username: ");
    private final JLabel paswlabel= new JLabel("Password: ");
    private final JButton sessionbtn = new JButton("LOGIN");
    private final JButton guesstbtn = new JButton("GUESST");
    private final JButton localbtn = new JButton("LOCAL");
    private final String reglink = "No tienes cuenta? Crea una aquí...";
    private final String loglink = "Ya tienes cuenta? Inicia sesión...";
    private final JLabel changepanel = new JLabel(reglink);

    public final JTextPane msgtxt = new JTextPane();
    public final StyledDocument doc = msgtxt.getStyledDocument();
    private final JPanel wait = new JPanel();
    private final JPanel options = new JPanel();
}