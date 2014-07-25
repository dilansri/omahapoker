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
        
        
        
        int highHandScore = 0;
        List<Card> playerCards = playerHand.getCards();
        List<Card> analyseCards = new ArrayList<>();
        
        
        //PAIR SCORE
        
        if(HighRankedHand.hasPair(playerCards)){
            highHandScore += 5;
        }
        
        // TWO PAIRS SCORE
        
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
                            
                            if(HighRankedHand.straight(analyseCards)!= null){
                                highHandScore += 20;                                
                            }
                            
                            if(HighRankedHand.flush(analyseCards) != null){
                                highHandScore += 25;
                            }
                            
                            List<Card> fullHouse = HighRankedHand.fullHouse(analyseCards);
                            
                            if(fullHouse != null && fullHouse.size() == 2){
                                highHandScore += 30;
                                //System.out.println("FULL HOUSE");
                            }
                            
                            if(HighRankedHand.straightFlush(analyseCards) != null){
                                highHandScore += 40;
                                //System.out.println("STRAIGHT FLUSH");
                            }                            
                            if(HighRankedHand.isRoyalFlush(analyseCards)){
                                highHandScore += 50;
                                //System.out.println("ROYAL FLUSH");
                            }
                            
                        }
                        
                        analyseCards.clear();
                        analyseCards.add(playerCards.get(i));
                        analyseCards.add(playerCards.get(j));
                        analyseCards.add(communityCards.get(p));
                        analyseCards.add(communityCards.get(q));
                        
                        List<Card> twoPairs = HighRankedHand.twoPairs(analyseCards);
                        if(twoPairs != null && !twoPairs.isEmpty()){
                            highHandScore += 10;
                        }
                        
                        List<Card> fourOfKind = HighRankedHand.fourOfaKind(analyseCards);

                        if (fourOfKind != null && !fourOfKind.isEmpty()) {
                            highHandScore += 35;
                            //System.out.println("FOUR OF KIND");
                        }
                        

                        
                    }
                    analyseCards.clear();
                    analyseCards.add(playerCards.get(i));
                    analyseCards.add(playerCards.get(j));
                    analyseCards.add(communityCards.get(p));
                    
                    List<Card> threeOfKind = HighRankedHand.threeOfKind(analyseCards);

                    if (threeOfKind != null && !threeOfKind.isEmpty()) {
                        highHandScore += 15;                        
                    }
                    
                }
            }
        }
        
        int roundCountAdjustment = (roundCount-1)*3+1;
        System.out.println(highHandScore);
        //System.out.println("round score:"+roundCountAdjustment);
        PlayerAction action = PlayerAction.FOLD;
        if(highHandScore <= 0 +roundCountAdjustment && possibleActions.contains(PlayerAction.CHECK) )
            action = PlayerAction.CHECK;
        else if(highHandScore <= 0 +roundCountAdjustment )
            action = PlayerAction.FOLD;
        else if(highHandScore < (9+(roundCountAdjustment+((roundCount-1))*5*roundCount)) && possibleActions.contains(PlayerAction.CHECK)){
            action = PlayerAction.CHECK;
        }else if(highHandScore < (39+(roundCountAdjustment+((roundCount-1))*10*roundCount)) && possibleActions.contains(PlayerAction.CALL)){
            action = PlayerAction.CALL;
        }else if(highHandScore < (99+roundCountAdjustment) && possibleActions.contains(PlayerAction.RAISE)){
            action = PlayerAction.RAISE;
        }else if(highHandScore >= 100 && possibleActions.contains(PlayerAction.ALL_IN) ){
            action = PlayerAction.ALL_IN;
        }
        return action;
    }

    PlayerAction getTurnAction(PlayerHand playerHand, List<Card> communityCards, List<PlayerAction> possibleActions, int roundCount) {
        int roundCountAdjustment = (roundCount-1)*5+1;
        
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
                                System.out.println("ROYAL FLUSH");
                            }
                            
                            if(HighRankedHand.straightFlush(analyseCards) != null){
                                highHandScore += 20;
                                System.out.println("STRAIGHT FLUSH");
                            }
                            
                            List<Card> fourOfKind = HighRankedHand.fourOfaKind(analyseCards);
                            
                            if( fourOfKind != null && !fourOfKind.isEmpty()){
                                highHandScore += 17;
                                System.out.println("FOUR OF KIND");
                            }
                            
                            List<Card> fullHouse = HighRankedHand.fullHouse(analyseCards);
                            
                            if(fullHouse != null && fullHouse.size() == 2){
                                highHandScore += 15;
                                System.out.println("FULL HOUSE");
                            }
                            
                            if(HighRankedHand.flush(analyseCards) != null){
                                highHandScore += 10;
                                System.out.println("FLUSH");
                            }
                            
                            if(HighRankedHand.straight(analyseCards)!= null){
                                highHandScore += 9;
                                System.out.println("STRAIGHT");
                            }
                            
                            List<Card> threeOfKind = HighRankedHand.threeOfKind(analyseCards);
                            
                            if(threeOfKind != null && !threeOfKind.isEmpty()){
                                highHandScore += 8;
                                System.out.println("THREE OF KIND");
                            }
                            
                            List<Card> twoPairs = HighRankedHand.twoPairs(analyseCards);
                            
                            if(twoPairs != null && !twoPairs.isEmpty()){
                                highHandScore += 5;
                                System.out.println("TWO PAIRS");
                            }
                            
                            if(HighRankedHand.hasPair(analyseCards)){
                                highHandScore += 2;
                                System.out.println("PAIR");
                            }
                        }
                    }
                }
            }
        }
        System.out.println(highHandScore);
        //System.out.println("round score:"+roundCountAdjustment);
        PlayerAction action = PlayerAction.FOLD;
        if(highHandScore <= 0 +roundCountAdjustment )
            action = PlayerAction.FOLD;
        else if(highHandScore < (9+(roundCountAdjustment+((roundCount-1))*6*roundCount)) && possibleActions.contains(PlayerAction.CHECK)){
            action = PlayerAction.CHECK;
        }else if(highHandScore < (39+(roundCountAdjustment+((roundCount-1))*10*roundCount)) && possibleActions.contains(PlayerAction.CALL)){
            action = PlayerAction.CALL;
        }else if(highHandScore < (99+roundCountAdjustment) && possibleActions.contains(PlayerAction.RAISE)){
            action = PlayerAction.RAISE;
        }else if(highHandScore >= 100 && possibleActions.contains(PlayerAction.ALL_IN)){
            action = PlayerAction.ALL_IN;
        }
        return action;
    }
    
}
