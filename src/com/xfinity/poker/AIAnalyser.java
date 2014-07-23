/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xfinity.poker;

import com.xfinity.poker.Player.PlayerAction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
class AIAnalyser {

    

    PlayerAction getPreFlopAction(PlayerHand playerHand,List<PlayerAction> possibleActions, int roundCount) {
        
        int highValue = getHighHandValue(playerHand);
        
        int roundCountAdjustment = (roundCount-1)*2+1;
        
        PlayerAction action = PlayerAction.FOLD;
        
        int rankHandScore = 0;
        
        if(HighRankedHand.hasPair(playerHand.getCards())){
            rankHandScore += 10;
        }
        
        System.out.println("ROUND COUNT ADJUSTMENT:"+roundCountAdjustment);
        
        System.out.println("PLAYER TOTAL:"+(highValue + rankHandScore));
        
        if (highValue + rankHandScore <= (20 + roundCountAdjustment)) {
            action = PlayerAction.FOLD;
        }else if(highValue + rankHandScore < 53 && possibleActions.contains(PlayerAction.CALL)){
            action = PlayerAction.CALL;
        }
        else if (highValue + rankHandScore >= 53 && possibleActions.contains(PlayerAction.RAISE)) {
            action = PlayerAction.RAISE;
        } else if (possibleActions.contains(PlayerAction.CHECK)) {
            action = PlayerAction.CHECK;
        }
            
            return action;
    }
    
    public int getHighHandValue(PlayerHand playerHand) {
        int value = 0;
        
        for(Card card:playerHand.getCards()){
            value += card.getValue().getCardValue();
        }
        System.out.println("HIGH HAND VALUE:"+value);
        return value;
        
    }

    PlayerAction getFlopAction(PlayerHand playerHand,List<Card> communityCards, List<PlayerAction> possibleActions, int roundCount) {
        
        int roundCountAdjustment = (roundCount-1)*2+1;
        
        int highHandScore = 0;
        List<Card> playerCards = playerHand.getCards();
        List<Card> analyseCards = new ArrayList<>();
        for(int i=0;i<playerCards.size();i++){
            for(int j=i+1;j<playerCards.size();j++){
                for(int p=0;p<communityCards.size();p++){
                    for(int q=p+1;q<communityCards.size();q++){
                        for(int r=q+1;r<communityCards.size();r++){
                            analyseCards.clear();
                            analyseCards.add(playerCards.get(i));
                            analyseCards.add(playerCards.get(j));
                            analyseCards.add(communityCards.get(p));
                            analyseCards.add(communityCards.get(q));
                            analyseCards.add(communityCards.get(r));
                            
                            if(HighRankedHand.isRoyalFlush(analyseCards)){
                                highHandScore += 25;
                            }
                            
                            if(HighRankedHand.straightFlush(analyseCards) != null)
                                highHandScore += 20;
                            
                            if(HighRankedHand.fourOfaKind(analyseCards) != null)
                                highHandScore += 17;
                            
                            if(HighRankedHand.fullHouse(analyseCards) != null)
                                highHandScore += 15;
                            
                            if(HighRankedHand.flush(analyseCards) != null)
                                highHandScore += 10;
                            
                            if(HighRankedHand.straight(analyseCards)!= null)
                                highHandScore += 9;
                            
                            if(HighRankedHand.threeOfKind(analyseCards) != null)
                                highHandScore += 8;
                            
                            if(HighRankedHand.twoPairs(analyseCards) != null)
                                highHandScore += 5;
                            
                            if(HighRankedHand.hasPair(analyseCards))
                                highHandScore += 2;
                        }
                    }
                }
            }
        }
        System.out.println(highHandScore);
        
        PlayerAction action = PlayerAction.FOLD;
        if(highHandScore <= 0)
            action = PlayerAction.FOLD;
        else if(highHandScore < 10 && possibleActions.contains(PlayerAction.CHECK)){
            action = PlayerAction.CHECK;
        }else if(highHandScore < 40 && possibleActions.contains(PlayerAction.CALL)){
            action = PlayerAction.CALL;
        }else if(highHandScore < 100 && possibleActions.contains(PlayerAction.RAISE)){
            action = PlayerAction.RAISE;
        }else if(highHandScore >= 100 && possibleActions.contains(PlayerAction.ALL_IN)){
            action = PlayerAction.ALL_IN;
        }
        return action;
    }
    
}
