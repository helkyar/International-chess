/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import onlinechess.helpers.conf;

/**
 *
 * @author admin
 */
public class Transformer extends JPanel implements ActionListener{
    private String pawn;
    private static String selectedPiece;
    
    public Transformer (String pawn){
        this.pawn = pawn;
        initButtons();
    }
    
    private void initButtons(){
        if(conf.WHITES.contains(pawn)){
            for(String piece : conf.WHITES.split("")){
                if(!piece.equals("k")){
                    JButton btn = new JButton(new ImageIcon(conf.getImg(piece)));
                    btn.setName(piece);
                    btn.addActionListener(this);
                    add(btn);
                }
            }
        }
        if(conf.BLACKS.contains(pawn)){
            for(String piece : conf.BLACKS.split("")){
                if(!piece.equals("K")){
                    JButton btn = new JButton(new ImageIcon(conf.getImg(piece)));
                    btn.setName(piece);
                    btn.addActionListener(this);
                    add(btn);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        selectedPiece = btn.getName();
       Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    if (window instanceof JDialog) {
                        JDialog dialog = (JDialog) window;
                        if (dialog.getContentPane().getComponentCount() == 1
                            && dialog.getContentPane().getComponent(0) instanceof JOptionPane){
                            dialog.dispose();
                        }
                    }
                }
    }
    
    public static String getSelectedPiece(){
        return selectedPiece;
    }
}
