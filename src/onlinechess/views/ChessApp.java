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
        add(new Game()); 
       
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
        
        new Session(this, cnf);
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
    }
    
    private void exit(){        
        int exit = JOptionPane.showConfirmDialog(this, "Tienes partidas en curso. Seguro que quiere salir?", "Salir", 1, 1, chessico);
        if(exit == 0) {System.exit(0);} //yes
        else if(exit == 1) {}//no
    }
        
    // VARIABLE DECLARATION
    private ConfigApp cnf;
    public static ImageIcon chessico;
           
    private JTextField dateText = new JTextField();
    private DateFormat timeFormat = new SimpleDateFormat("  kk:mm:ss  dd/MM/yyyy  ");  
}

