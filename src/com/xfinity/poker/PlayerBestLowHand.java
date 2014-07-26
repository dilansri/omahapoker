/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xfinity.poker;

/**
 *
 * @author Administrator
 */
public class PlayerBestLowHand {
    private int playerPosition; 
    
    private int playerBestLowHand;
    
    public PlayerBestLowHand(int pos){
        playerPosition = pos;
        playerBestLowHand = -5;
    }
    
    public int getPlayerPosition(){
        return playerPosition;
    }
    
    public void setPlayerBestLowHand(int hand){
        playerBestLowHand = hand;
    }
    
    public int getPlayerBestLowHand(){
        return playerBestLowHand;
    }
}
