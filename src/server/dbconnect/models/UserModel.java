/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.dbconnect.models;

/**
 *
 * @author admin
 */
public class UserModel {
     
    public UserModel(int id){this.userId = id;}

    public UserModel(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.data = new String[]{String.valueOf(userId), username, password};
    }
    
// GETTERS & SETTERS __________________________________________________________    
    public String[] getData() {return data;}
    public int getUserId() {return userId;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}

    public void setUserid(int userId) {this.userId = userId;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
 
// VARIABLES __________________________________________________________________
    private String[] data;
    
    private int userId;
    private String username;
    private String password;
}
