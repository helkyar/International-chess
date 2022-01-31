/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess;

import javax.swing.JFrame;

/**
 *
 * @author admin
 */
public class Test extends JFrame{
    public Test(){
        add(new Board(8,8,""));
        setSize(500,500);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Test();
    }
}
