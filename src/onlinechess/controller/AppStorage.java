/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller;

import onlinechess.controller.game.Game;

/**
 *
 * @author javier
 */
public class AppStorage {

    public AppStorage(Game game, String msg, String chatId) {
        this.game = game;
        this.msg = msg;
        this.chatId = chatId;
    }    
        
    Game game;
    String msg;
    String chatId;
}
