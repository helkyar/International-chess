/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.dbconnect.managers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import server.dbconnect.models.UserModel;

/**
 *
 * @author admin
 */
public class UserManager extends Manager{
     public UserManager(String s){}
    
    public UserManager(){        
        super();//call to parent to access database and set connection

        users = new ArrayList<>();

        try{initUsers();} 
        catch (SQLException e){e.printStackTrace();}
        catch(Exception e){e.printStackTrace();}    
        finally{try{conn.close();}catch(Exception e){e.printStackTrace();}}
    }

    /**
    * Stores all table data inside ArrayList variable. Each model instance
    * correlates to a table row.
    * @throws SQLException
    * @throws Exception 
    */
    private void initUsers() throws SQLException, Exception{ 
        String query = "SELECT `user_id`, `username`, `password` "
            + "FROM users";

        st = conn.createStatement();
        rs = st.executeQuery(query);

        while(rs.next()) {
            users.add(new UserModel(rs.getInt(1), 
                rs.getString(2), rs.getString(3)));
        }
    }
    
    public UserModel loginUser(String username, String password) {
        UserModel user = new UserModel(-1);
        String query = "SELECT `user_id` FROM users WHERE "
            + "username = '"+username+"' AND password = '"+password+"'";
            
        connect();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            
            if (rs.next()) {
                user.setUsername(username);
                user.setUserid(rs.getInt(1));
                return user;
            }
            return user;
            
        } catch (SQLException e){e.printStackTrace(); return user;}
        catch(Exception e){e.printStackTrace(); return user;}    
        finally{try{conn.close();} catch(Exception e){return user;}}
    }

    public int registerUser(String username, String password) {
        String query = "INSERT INTO users (`username`, `password`, `money`)"
            + " VALUES ('"+username+"','"+password+"','1000')";
        
        connect();
        try{
            ps = conn.prepareStatement(query);      
            ps.executeUpdate();
            return 1;
            
        } catch (SQLException e){e.printStackTrace(); return -1;}
        catch(Exception e){e.printStackTrace(); return -1;}    
        finally{try{conn.close();} catch(Exception e){return -1;}}
    }
    
    public void setUserAmount(int id, int amount) {
        String money = "SELECT money FROM users WHERE  user_id='"+id+"'";
        //Pass user_id

        connect();
        try{
            st = conn.createStatement();
            rs = st.executeQuery(money);
            
            if (rs.next()) {amount += rs.getInt(1);}
            
            String resolve = "UPDATE users SET money = '"+amount+"'+ "
                + "WHERE user_id='"+id+"'";
            
            ps = conn.prepareStatement(resolve);      
            ps.executeUpdate();

        } catch (SQLException e){e.printStackTrace();}
        catch(Exception e){e.printStackTrace();}    
        finally{try{conn.close();} catch(Exception e){}}
    }
    
    // GETTERS & SETTERS _________________________________________________________
    /**
    * This variable holds the value of each row of the table User 
    * (one instance of User class per row).
    * @return users ArrayList with all the users in the database
    */
    public List<UserModel> getUsers() {return users;}
  
// VARIABLES _________________________________________________________________
    private ArrayList<UserModel> users;

}
