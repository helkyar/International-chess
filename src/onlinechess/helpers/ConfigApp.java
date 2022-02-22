/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.helpers;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author admin
 */
public class ConfigApp {
    public final Color PRIME  = Color.black;
    public static Color ALT = Color.gray;
    public final Color WORD = Color.white;
    public final ImageIcon APP_ICON = new ImageIcon("img/chessicow.png");
    public final ImageIcon LOG_ICON = new ImageIcon("img/chessico.png");
    public final ImageIcon CLOSE_ICON = new ImageIcon("img/cross.png");
    public final ImageIcon EYE_CLOSE = new ImageIcon("img/-eye.png");
    public final ImageIcon EYE_OPEN = new ImageIcon("img/+eye.png");
    public final ImageIcon LOAD_ICON = new ImageIcon("img/load.gif");
    public final ImageIcon DESCONNECTED = new ImageIcon("img/disconnect.png");
    public final ImageIcon MINICONNECTED = new ImageIcon("img/minidesco.png");
    private final String warning = "\n\nChats & Boards \nwont be saved!";
    public final String enter = "\n\n\n\n\n\n\n\n";
    public final String GUEST = enter+"Session started as Guesst..."+warning;     
    public final String LOCAL = enter+"Started local game..."+warning;
    public final String LOGIN = enter+"\n\\nAccess granted!!";
    public final String REGISTER = enter+"\n\nRegistered successfully!!";
    public final String LOST = "\n\n\nYour connection is lost...\n";
    public final String WAIT = "\n\n\nWaiting for server response...\n";
    public final ImageIcon MINIWAIT = new ImageIcon("img/wait.png");
    public final Icon MINISUCCESS = new ImageIcon("img/ok.png");
    public final String OK = "\n\n\nEverything ok, hurry up!\n";;
    public final String TIMEOUT = "Ups! Server crashed...\n";
    public final ImageIcon MINITIMEOUT = new ImageIcon("img/alert.png");
}
