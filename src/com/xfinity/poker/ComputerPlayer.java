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
public class ComputerPlayer extends Player {
    
    public ComputerPlayer(String name,int order){
        super(name,order);
    }
    
    @Override
    public PlayerAction getAction(){
        return PlayerAction.FOLD;
    }
    
}
