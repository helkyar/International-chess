/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.dbconnect.managers;

import java.sql.*;
/**
 *
 * @author javip
 */
public class Manager {
    public Manager(){        
        try{ Class.forName(driver);}
        catch(ClassNotFoundException e){e.printStackTrace();} 
        
        try {conn = DriverManager.getConnection(url, user, pwd);}
        catch (SQLException e){e.printStackTrace();}
    }
    
    public static void connect(){
        try{ Class.forName(driver);}
        catch(ClassNotFoundException e){e.printStackTrace();} 
        
        try {conn = DriverManager.getConnection(url, user, pwd);}
        catch (SQLException e){e.printStackTrace();}
    }
    
// VARIABLES __________________________________________________________________
//    private static String driver = "com.mysql.jdbc.Driver";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/chess";
    private static String user = "root";
    private static String pwd = "";    
    
    protected static PreparedStatement ps = null;
    protected static Connection conn = null;
    protected static Statement st = null;
    protected static ResultSet rs = null;
    
}
