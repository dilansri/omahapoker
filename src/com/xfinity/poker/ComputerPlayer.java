/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xfinity.poker;

import com.xfinity.poker.Dealer.Round;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ComputerPlayer extends Player {
    
    public ComputerPlayer(String name,int order){
        super(name,order);
    }
    
    
    public PlayerAction getAction(List<PlayerAction> possibleActions,Round currentRound,final int roundCount){
        
        PlayerAction action = PlayerAction.FOLD;
        
        int highValue = getHighHandValue();
        
        int roundCountAdjustment = (roundCount-1)*2+1;
        
        if(currentRound == Round.PRE_FLOP){
            action = PlayerAction.CALL;
            if(highValue <= (25 + roundCountAdjustment))
                action = PlayerAction.FOLD;
            else if(highValue >= 53 && possibleActions.contains(PlayerAction.RAISE))
                action = PlayerAction.RAISE;
            else if(possibleActions.contains(PlayerAction.CHECK))
                action = PlayerAction.CHECK;
                
        }
            
        return action;
    }

    public int getHighHandValue() {
        int value = 0;
        
        for(Card card:playerHand.getCards()){
            value += card.getValue().getCardValue();
        }
        System.out.println(value);
        return value;
        
    }
    
}
