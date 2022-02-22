/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.dbconnect.models;

import java.util.Date;
import java.util.List;
import server.dbconnect.managers.UserManager;

/**
 *
 * @author admin
 */
public class UserModel {
     
    public UserModel(int id){this.userId = id;}

    public UserModel(int userId, String username, String password, String email,
        int wonGames, int lostGames, int drawGames, Date createdAt) {
        
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.wonGames = wonGames;
        this.lostGames = lostGames;
        this.drawGames = drawGames;
        this.createdAt = createdAt;
        
        this.data = new String[]{String.valueOf(userId), username, password, 
            email, String.valueOf(wonGames), String.valueOf(lostGames), 
            String.valueOf(drawGames), String.valueOf(createdAt)};
    }

    @Override
    public String toString() {
        return "UserModel{" + "userId=" + userId + ", username=" + username 
            + ", password=" + password + ", email=" + email + ", wonGames=" 
            + wonGames + ", lostGames=" + lostGames + ", drawGames=" + 
            drawGames + ", createdAt=" + createdAt + '}';
    }

// GETTERS & SETTERS __________________________________________________________ 
    public List<UserModel> getUsers(){return users;}
    public String[] getData() {return data;}
    public int getUserId() {return userId;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}
    public int getWonGames() {return wonGames;}
    public int getLostGames() {return lostGames;}
    public int getDrawGames() {return drawGames;}
    public Date getCreatedAt() {return createdAt;}

    public static void setUsers () {users = UserManager.getAllUsers();}
    public void setUserId (int userId) {this.userId = userId;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setEmail(String email) {this.email = email;}
    public void setWonGames(int wonGames) {this.wonGames = wonGames;}
    public void setLostGames(int lostGames) {this.lostGames = lostGames;}
    public void setDrawGames(int drawGames) {this.drawGames = drawGames;}
    public void setCreatedAt(Date createdAt) {this.createdAt = createdAt;}
    
// VARIABLES __________________________________________________________________
    private static List<UserModel> users;            
    private String[] data;
    
    private int userId;
    private String username;
    private String password;
    private String email;
    private int wonGames;
    private int lostGames;
    private int drawGames;
    private Date createdAt;
}
