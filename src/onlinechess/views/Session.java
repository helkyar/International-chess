/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import onlinechess.controller.session.*;
import onlinechess.controller.socket.SearchServer;
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
        retrybtn.addActionListener((ActionEvent e)->{search.startSearch("");});
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
                
    //INIT SEARCH SERVER ___________________________________________________________
        search = new SearchServer(this);
    }

    private void setLocationAndSize(int i) {
        start=205; o=30; this.i=i;
        userinfo.setBounds(w/6,5,w/2,20);
        close.setBounds(w-25,5,20,20);
        logo.setBounds(w/2-100,50,200,150);
        userlabel.setBounds(w/4,start,150,30);
        usertxt.setBounds(w/4,start+o,w/2,30);
        paswlabel.setBounds(w/4,start+o*4-i,150,30);
        paswtxt.setBounds(w/4,start+o*5-i,(int)(w/2-i*0.825),30);
        paswshow.setBounds(w/4+w/2-57,start+o*5-i,w/6,30);
        sessionbtn.setBounds(w/2-105,start+o*9-i*2,100,30);
        guesstbtn.setBounds(w/2+5,start+o*9-i*2,100,30);
        localbtn.setBounds(w/2-50,start+o*10-i*2+10,100,30);
        changepanel.setBounds(155,start+o*11-i*2+5,200,30);     
        retrybtn.setBounds(w/2-105,start+o*10-i*2+10,100,30);
        
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
    
    /**
     * Manages APP START either local, guesst, login or register
     * @param e Action event (button) 
     */
    private void sessionStart(ActionEvent e) {
        String ac = e.getActionCommand();
        
        ((CardLayout) masterpanel.getLayout()).next(masterpanel);
        swap = !swap;
        
    //LOCAL CASE ______________________________________________________________
        if(ac.equals("LOCAL")){localSessionInit(swap); return;}
        wait.add("North",close);
        
    //CHECK CONNECTION AND SEARCH SERVER ______________________________________ 
        //(!)INSERT LOADING ICON 
        search.startSearch(ac);
    }    
        
    public void startValidations(String ac) {
        if(ac.equals("")){return;}
        boolean denied = true;
        String msg="";
               
    //GUESST CASE _____________________________________________________________        
        if(ac.equals("GUESST")){
            msgtxt.setText(cnf.GUEST);                  
            new Timer(1500, (ActionEvent ev)->{dispose();}).start();
            return;
        }
        
    //GET INPUTS ______________________________________________________________       
        String nick = usertxt.getText();
        String email = emailtxt.getText();
        String password = String.valueOf(paswtxt.getPassword());
        String cnfpaswd = String.valueOf(confpswdtxt.getPassword());
        
        if(ac.equals("REGISTER")){ 
            
            String[] response = 
            InputValidator.validateRegister(nick, email, password, cnfpaswd);
            denied =  response[0].equals("ERROR");                       
            msg = response[1];
            
        } else if(ac.equals("LOGIN")) {  
            
            String[] response = 
            InputValidator.validateLogin(nick, password);
            denied =  response[0].equals("ERROR");
            msg = response[1];
        }
    //SUCCESS MSG _____________________________________________________________        
        msgtxt.setText(msg);
        boolean forfuckssakejavapleasestopthisshit = denied;
        new Timer(2000, (ActionEvent ev)->{            
            if(!forfuckssakejavapleasestopthisshit){dispose();}
            else {
                swap = !swap;
                auth.add(localbtn);
                ((CardLayout) masterpanel.getLayout()).next(masterpanel);
            }
            ((Timer) ev.getSource()).stop();
        }).start();
    }

    public void badConnection() {        
        msgtxt.setText(cnf.LOST+"Retry: "+Math.random()+"\n\n"); 
        msgtxt.insertIcon(cnf.DESCONNECTED);
        if(!swap){options.add(localbtn);}
        message.add("South", options);
        
        setInfoLabel("DISCONNECTED");
    }
        
    public void serverResponseTimeout() { 
        auth.add(retrybtn);
        localbtn.setBounds(w/2+5,start+o*10-i*2+10,100,30);
        retrybtn.setBounds(w/2-105,start+o*10-i*2+10,100,30);
        msgtxt.setText(cnf.TIMEOUT);
        msgtxt.insertIcon(cnf.LOAD_ICON);
        if(!swap){
            options.add(localbtn); options.add(retrybtn);
        }
    }
    
    public void setInfoLabel(String msg) {
        if(msg.equals("DISCONNECTED")){        
            userinfo.setText(cnf.LOST);
            userinfo.setIcon(cnf.MINICONNECTED);
        } else if(msg.equals("WAITING")){            
            userinfo.setText(cnf.WAIT);
            userinfo.setIcon(cnf.MINIWAIT);
        } else if(msg.equals("TIMEOUT")){            
            userinfo.setText(cnf.TIMEOUTMINI);
            userinfo.setIcon(cnf.MINITIMEOUT);        
        } else if(msg.equals("SUCCESS")){            
            userinfo.setText(cnf.OK);
            userinfo.setIcon(cnf.MINISUCCESS);
        }
    }    
        
    private void localSessionInit(boolean swap) {       
        message.remove(options);
        message.remove(wait);
        if(swap) {((CardLayout) masterpanel.getLayout()).next(masterpanel);} 
        new Timer(1100, (ActionEvent ev)->{dispose();}).start(); 
        msgtxt.setText(cnf.LOCAL);
    }
    
//GETTERS & SETTERS ___________________________________________________________
    public void setConnecting(boolean connecting){this.connecting = connecting;}
    
//VARIABLES____________________________________________________________________
    private ConfigApp cnf;
    
    private final int w = 500;
    private final int h = 700;
    //variables for component location
    int start, o, i;
    
    private boolean swap = true;        
    private boolean toggle = true;
    private boolean newuser = false;     
    public boolean connecting = true;
    
    private SearchServer search;
    
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
    private final JButton retrybtn = new JButton("RECONNECT");
    private final String reglink = "No tienes cuenta? Crea una aquí...";
    private final String loglink = "Ya tienes cuenta? Inicia sesión...";
    private final JLabel changepanel = new JLabel(reglink);

    public final JTextPane msgtxt = new JTextPane();
    public final StyledDocument doc = msgtxt.getStyledDocument();
    private final JPanel wait = new JPanel();
    private final JPanel options = new JPanel();
}