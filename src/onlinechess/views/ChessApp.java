/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package onlinechess.views;

import onlinechess.helpers.LogGen;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Timer;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import onlinechess.controller.Game;
import onlinechess.helpers.Conf;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

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
                    new ChessApp().setVisible(true);
                } catch(Throwable e){LogGen.error(e.getMessage());}
            }
        });
    }
    
    public ChessApp(){
        
        setLayout(new GridLayout(1,2));
        
        add(new Game());
        add(new Game()); //piece selector must be in game
        
        setTitle("Online Chess");
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
        
        initDate();
        
        LogGen.info("Session started succesfully");
    }
    // =========================================================================
    // HELPERS
    // =========================================================================
    private void initDate(){
        dateText.setBackground(Color.black);
        dateText.setForeground(Color.green);

        Timer timer = new Timer(1000, (ActionEvent e) -> {
            dateText.setText(timeFormat.format(new Date()));
        });
        timer.setInitialDelay(0);
        timer.start(); 
    }
    
    private void exit(){        
        int exit = JOptionPane.showConfirmDialog(this, "Tienes partidas en curso. Seguro que quiere salir?", "Salir", 1, 1, chessico);
        if(exit == 0) {System.exit(0);} //yes
        else if(exit == 1) {}//no
    }
    
    // VARIABLE DECLARATION
    public static ImageIcon chessico = new ImageIcon("img/chessico.png");
    
    public static boolean isWhite;
    
    private JTextField dateText = new JTextField();
    private DateFormat timeFormat = new SimpleDateFormat("  kk:mm:ss  dd/MM/yyyy  ");  

}
