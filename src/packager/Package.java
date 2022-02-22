/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packager;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Javier Palacios
 */
public class Package implements Serializable{
    
    private String nick, ip, info, msg, status, email, pwdcryp;
    private Map<String, String> ips, users;

    public void setIp(String ip){this.ip = ip;}
    public void setMsg(String msg){this.msg = msg;}
    public void setNick(String nick){this.nick = nick;}
    public void setInfo(String info){this.info = info;}
    public void setStatus(String status){this.status = status;}
    public void setIps(Map<String, String> ips){this.ips = ips;}
    public void setUsers(Map<String, String> users){this.users = users;}
    
    public String getNick(){return nick;}
    public String getIp(){return ip;}
    public String getInfo(){return info;}
    public String getMsg(){return msg;}
    public String getStatus(){return status;}
    public Map<String,String> getIps(){return ips;}
    public Map<String, String> getUsers() {return users;}

    public void setEmail(String email) {this.email = email;}
    public void setPassword(String pwdcryp) {this.pwdcryp = pwdcryp;}
    public String getEmail(){return email;}
    public String getPaswd(){return pwdcryp;}
}
