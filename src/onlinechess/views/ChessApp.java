/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package onlinechess.views;

import java.awt.BorderLayout;
import onlinechess.helpers.LogGen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Timer;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

import onlinechess.controller.ScreenCtrl;
import onlinechess.controller.chat.ChatController;

import onlinechess.controller.game.Game;
import onlinechess.controller.socket.Request;
import onlinechess.controller.socket.SearchServer;
import onlinechess.helpers.Memory;
import onlinechess.helpers.ConfigApp;

/**
 *
 * @author Javier Palacios Botejara
 */
public class ChessApp extends JFrame{
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    ConfigApp cnf = new ConfigApp();                    
                    new ChessApp(cnf); //App start  
                } catch(Exception e){e.printStackTrace();}
            }
        });
    }
    
    public ChessApp(ConfigApp cnf){ 
        ChatController chatCtrl = new ChatController(this);
    //INIT COMPONENTS _________________________________________________________
        initDate();
        this.cnf = cnf;
        masterpanel.setLayout(new BorderLayout(1,2));
        masterpanel.setBackground(cnf.PRIME);
    //INIT CHAT COMPONENTS ____________________________________________________
        initChat(chatCtrl);
    //INIT GAME ______________________________________________________________
        screen.add(new Game(0, true), 0);
    // FRAME STRUCTURE ________________________________________________________
        setTitle("Online Chess");
        appicon = cnf.APP_ICON;
        setIconImage(appicon.getImage());
        
        setSize(800,800);    
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setVisible(true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LogGen.info("Session ended");
                exit();
            }
        });
        
        new Session(this, cnf).setVisible(true);
        LogGen.info("Session started succesfully");
    }
// =========================================================================
//                      CONSTRUCTORS
// =========================================================================
    private void initDate(){
        dateText.setBackground(Color.black);
        dateText.setForeground(Color.green);

        new Timer(1000, (ActionEvent e) -> {
            dateText.setText(timeFormat.format(new Date()));
        }).start();
    }
    
    private void initChat(ChatController eventlistener){
        //Dimension and Layout 
        connect.setLayout(new GridLayout(1,1)); //future implementation(2,1)
        scrollUsers = new JScrollPane(users, 22,31); 
        scrollGroups = new JScrollPane(groups, 22,31);
        screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS));
        users.setLayout(new BoxLayout(users, BoxLayout.Y_AXIS));
        groups.setLayout(new BoxLayout(groups, BoxLayout.Y_AXIS));
        scrollUsers.setPreferredSize(new Dimension(115,210));
        scrollGroups.setPreferredSize(new Dimension(115,210));
        chat.setLayout(new BorderLayout(10,10));
        input.setLayout(new FlowLayout());
        JPanel style = new JPanel();
        style.setPreferredSize(new Dimension(2,2));
        //Add them___________________________________________________________
        connect.add(scrollUsers);
        //connect.add(scrollGroups); //future implementation  
        input.add(sendbtn);
        input.add(userinput);
        input.add(nicklabel);
        screen.add(new JScrollPane(chatxt));
        chat.add("Center",screen);
        chat.add("South",input); 
        options.add(login);
        options.add(register);        
        // masterpanel.add("North", options); //future implementation
        masterpanel.add("Center", chat);
        masterpanel.add("West", connect);
        masterpanel.add("East", style); //Just for style, future game moves
        add(masterpanel);
        //LISTENERS ____________________________________________________
        chatxt.setEditable(false);
        sendbtn.addActionListener(eventlistener);     
        login.addActionListener(eventlistener);    
        register.addActionListener(eventlistener);    
        userinput.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent pressed){
                eventlistener.userKeyInput(pressed);
            }            
        });
    }

//HELPERS _____________________________________________________________________
    private void exit(){        
        int exit = JOptionPane.showConfirmDialog(this, 
            "Seguro que quiere salir?", "Salir", 1, 1, appicon);
        
        if(exit == 0) {System.exit(0);} //yes
        else if(exit == 1) {}//no
    }
    
//GETTERS & SETTERS ___________________________________________________________
    /**
     * Gets called when session starts. Is the user entre point to the app, 
     * and comunication with session class.
     * @param nick user nick
     * @param id user id -1:local; 0:guest.
     */
    public void setSessionVariables(String nick, int id) {
        ChessApp.nick = nick; 
        ChessApp.userId = id;
        nicklabel.setText(nick);
        new ScreenCtrl(this, cnf);
        ScreenCtrl.setChats("", "local", 0,""); //init local board 
        ScreenCtrl.setChats(".", "juan", 0,"."); //init local board 
        if(id > -1){Request.searchUsersOnline(nick, id);} 
    }
      
// VARIABLE DECLARATION _______________________________________________________
    private ConfigApp cnf;
    public static ImageIcon appicon;
           
    private JTextField dateText = new JTextField();
    private final JPanel masterpanel = new JPanel();    
    public DateFormat timeFormat = new SimpleDateFormat("kk:mm dd/yy");
    
    //SESSION VARIABLES _______________________________________________________
    public static String nick = "local";
    public static int userId = -1; //guest has id = 0
    public String chatId;
    private JLabel nicklabel = new JLabel(nick);
    public Map<String, Memory> storage = new HashMap<>();
    
    //CHAT COMPONENTS _________________________________________________________
    private JPanel chat = new JPanel();
    public final JPanel screen = new JPanel(); //text chat and game
    private JPanel options = new JPanel();
    private JPanel input = new JPanel(); //facilitate adding componnents
    private JPanel connect = new JPanel();
    public JPanel users = new JPanel();
    private JPanel groups = new JPanel();
    private JScrollPane scrollUsers;
    private JScrollPane scrollGroups; 
    
    public ButtonGroup btngrouper = new ButtonGroup();
    public JButton sendbtn = new JButton(new ImageIcon("img/send.png"));
    private JButton login = new JButton("Login");
    private JButton register = new JButton("Register");
    
    public JTextField userinput = new JTextField(38);
    public JTextArea chatxt = new JTextArea(10,50);
}
//(>)Send messages
//(>)Send moves
//(>)Select color
//(>)Change turn
//(>)End of game reset
//(*>)IF LOCAL HIDE CHAT
//(*>)OPTION PANEL ALLOCATES OPTIONS LIKE: MAKE PUBLIC; DDRAW; FORFEIT
//(*>)SIDE INFOPANEL
//(*>)GROUP BROADCAST
//(*>)ABOUT SECTION
//(*>)LOGIN AND REGISTER OPTIONS
//(*>)RECONNCET -> RETRY
//(*>)STYLE
