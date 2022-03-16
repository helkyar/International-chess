/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.dbconnect.managers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import server.dbconnect.models.UserModel;

/**
 *
 * @author admin
 */
public class UserManager extends Manager{

    public UserManager(){ }

    /**
    * Stores all table data inside ArrayList variable. Each model instance
    * correlates to a table row.
    * @throws SQLException
    * @throws Exception 
    */
    private static void initUsers() {  
        String query = "SELECT * FROM users";
        connect();
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while(rs.next()) {
                users.add(new UserModel(rs.getInt(1), rs.getString(2), 
                       rs.getString(3), rs.getString(4), rs.getInt(5),
                       rs.getInt(6), rs.getInt(7), rs.getDate(8)));
            }
            
        } catch (SQLException e){e.printStackTrace();}
        catch(Exception e){e.printStackTrace();}    
        finally{try{conn.close();}catch(Exception e){e.printStackTrace();}}
    }
    
    public static Map checkLogin(String nick) {
        Map<Integer, String> resp = new HashMap<>();
        System.out.println(resp);
        String query = "SELECT `user_id`, `password` FROM users WHERE "
            + "username = '"+nick+"';";
        
        connect();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            
            if (!rs.next()) {return resp;}
            resp.put(rs.getInt(1), rs.getString(2));
            return resp;
            
        } catch (SQLException e){e.printStackTrace(); return resp;}
        catch(Exception e){e.printStackTrace(); return resp;}    
        finally{try{conn.close();} catch(Exception e){return resp;}}
    }

    public static String checkRegister(String nick, String email) {
        String msg = "";
        String db = "\nDatabase connection faliure\n";
        String deniedNick = "\nUsername already registered\n";
        String deniedEmail = "\nEmail already registered\n";
        String query = "SELECT `username`, `email` FROM users WHERE "
            + "username = '"+nick+"' OR email ='"+email+"'";
            
        connect();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            
            if (!rs.next()) {return "";}
            
            msg = rs.getString(1).equals("") ? "" : deniedNick ;
            msg += rs.getString(2).equals("") ? "" : deniedEmail;
            return msg;            
            
        } catch (SQLException e){e.printStackTrace(); return db;}
        catch(Exception e){e.printStackTrace(); return db;}    
        finally{try{conn.close();} catch(Exception e){return db;}}
    }
    
    public static int registerUser(String nick, String password, String email){
        String query = "INSERT INTO users (`username`, `password`, `email`)"
            + " VALUES ('"+nick+"','"+password+"','"+email+"')";
        connect();
        try{
            ps = conn.prepareStatement(query);      
            ps.executeUpdate();
            return 1;
            
        } catch (SQLException e){e.printStackTrace(); return -1;}
        catch(Exception e){e.printStackTrace(); return -1;}    
        finally{try{conn.close();return -1;} catch(Exception e){return -1;}}
    }
        
    // GETTERS & SETTERS _________________________________________________________
    /**
    * This variable holds the value of each row of the table User 
    * (one instance of User class per row).
    * @return users ArrayList with all the users in the database
    */    
    public static List<UserModel> getAllUsers() {initUsers();  return users;}
  
// VARIABLES _________________________________________________________________
    private static ArrayList<UserModel> users;

}
