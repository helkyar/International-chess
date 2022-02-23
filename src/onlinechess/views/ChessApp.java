/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package onlinechess.views;

import onlinechess.helpers.LogGen;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Timer;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

import onlinechess.controller.game.Game;
import onlinechess.controller.socket.SearchServer;
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
        this.cnf = cnf;
        initDate();
        add(new Game());
        add(test);
        //ADD CHAT IF USER ONLINE
        //ADD LATERAL PANELS IF ONLINE
        //LOCAL SETTING HAS ROW OF OPTIONS INSTEAD OF CHAT
       
    // FRAME STRUCTURE ________________________________________________________
        setTitle("Online Chess");
        chessico = cnf.APP_ICON;
        setLayout(new GridLayout(1,2));
        setIconImage(chessico.getImage());
        
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
    // HELPERS
    // =========================================================================
    private void initDate(){
        dateText.setBackground(Color.black);
        dateText.setForeground(Color.green);

        new Timer(1000, (ActionEvent e) -> {
            dateText.setText(timeFormat.format(new Date()));
        }).start();
        System.out.println("HELLO?");
        test.setText(nick);
    }
    
    private void exit(){        
        int exit = JOptionPane.showConfirmDialog(this, "Tienes partidas en curso. Seguro que quiere salir?", "Salir", 1, 1, chessico);
        if(exit == 0) {System.exit(0);} //yes
        else if(exit == 1) {}//no
    }
//GETTERS & SETTERS ___________________________________________________________
    public void setSessionVariables(String nick, int id) {
        ChessApp.nick = nick; 
        ChessApp.userId = id;
        test.setText(nick);
    }
      
// VARIABLE DECLARATION _______________________________________________________
    private ConfigApp cnf;
    public static ImageIcon chessico;
           
    private JTextField dateText = new JTextField();
    private DateFormat timeFormat = new SimpleDateFormat("kk:mm dd/yy");  
    
    //SESSION VARIABLES _______________________________________________________
    private static String nick = "local";
    private static int userId = -1; //guest has id = 0
    JLabel test = new JLabel(nick);
}

