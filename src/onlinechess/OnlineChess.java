/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package onlinechess;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author Javier Palacios Botejara
 */
public class OnlineChess extends JFrame{

    OnlineChess(){
        setLayout(new GridLayout(8,8));
        
        initBoard();

        setTitle("Online Chess");
        setIconImage(chessico.getImage());
        setSize(500,500);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
//        setUndecorated(true);
        setVisible(true);
        setResizable(false);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
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
    
    private void initBoard(){
        boolean cell = true;
        for(int i=1,j=0;i<65;i++){
            ++j;
            ImageIcon icon = new ImageIcon("img/n.png");
            if(j==1||j==8){icon = new ImageIcon("img/torre.png");}
            else if(j==2||j==7){icon = new ImageIcon("img/caballo.png");}
            else if(j==3||j==6){icon = new ImageIcon("img/alfil.png");}
            else if(j==4){icon = new ImageIcon("img/reina.png");}
            else if(j==5){icon = new ImageIcon("img/rei.png");}
            else if(j>8 && j<17){icon = new ImageIcon("img/peon.png");}
            
            else if(j==1+56||j==8+56){icon = new ImageIcon("img/torre.png");}
            else if(j==2+56||j==7+56){icon = new ImageIcon("img/caballo.png");}
            else if(j==3+56||j==6+56){icon = new ImageIcon("img/alfil.png");}
            else if(j==4+56){icon = new ImageIcon("img/reina.png");}
            else if(j==5+56){icon = new ImageIcon("img/rei.png");}
            else if(j>8+40 && j<17+40){icon = new ImageIcon("img/peon.png");}
            
            JButton btn = new JButton(String.valueOf(j),icon);
            
            if((i%2==0 && cell)||(i%2!=0 && !cell)){btn.setBackground(Color.blue);}
            else{btn.setBackground(Color.gray);}
            if(i%8==0){cell=!cell;}
            
            add(btn);
        }
    }
    
    private void exit(){        
        int exit = JOptionPane.showConfirmDialog(this, "Tienes partidas en curso. Seguro que quiere salir?", "Salir", 1, 1, chessico);
        if(exit == 0) {System.exit(0);} //no
        else if(exit == 1) {}//yes
               
        //        if(parkedTicket){
        //            int exit = JOptionPane.showConfirmDialog(this, "Tienes partidas en curso. Seguro que quiere salir?", "Salir", 1, 1, chessico);
        //            if(exit == 1) {System.exit(0);} //no
        //            else if(exit == 0) {//yes
        //                if(recupTicket() == 0){
        //                    ticketGen();
        //                    return;
        //                }
        //            }
        //        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    new OnlineChess().setVisible(true);
                } catch(Throwable e){LogGen.error(e.getMessage());}
            }
        });
    }
    
    // VARIABLE DECLARATION
    ImageIcon chessico = new ImageIcon("img/chessico.png");
    
    private JTextField dateText = new JTextField();
    private DateFormat timeFormat = new SimpleDateFormat("  kk:mm:ss  dd/MM/yyyy  ");  

}
