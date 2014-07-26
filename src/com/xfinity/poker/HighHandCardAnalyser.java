package com.xfinity.poker;

import static com.xfinity.poker.DealerRules.PLAYER_HAND_SIZE;
import java.util.ArrayList;
import java.util.List;


public class HighHandCardAnalyser implements RankHandsRules {
    
    
    
    public PlayerBestHighHand getHighHandWinner(List<Player> players, List<Card> communityCards) {
        
        List<PlayerBestHighHand> playersBestHands = new ArrayList<>();
        
        for(int player=0;player<players.size();player++ ){
            
            Player currentPlayer = players.get(player);
            if(currentPlayer.isFolded())
                continue;
            
            PlayerBestHighHand bestHand = new PlayerBestHighHand(player);
            
            List<Card> analyseCards = new ArrayList<>();
            
            List<Card> playerCards = players.get(player).getPlayerHand().getCards();
            
            for(int i=0;i<PLAYER_HAND_SIZE;i++){
                for(int j=i+1;j<PLAYER_HAND_SIZE;j++){
                    for(int p=0;p<communityCards.size();p++){
                        for(int q=p+1;q<communityCards.size();q++){
                            for(int r=q+1;r<communityCards.size();r++){
                                analyseCards.clear();
                                analyseCards.add(playerCards.get(i));
                                analyseCards.add(playerCards.get(j));
                                analyseCards.add(communityCards.get(p));
                                analyseCards.add(communityCards.get(q));
                                analyseCards.add(communityCards.get(r));
                                
                                List<Card> fourOfKind = HighRankedHand.fourOfaKind(analyseCards);
                                List<Card> fullHouse = HighRankedHand.fullHouse(analyseCards);
                                List<Card> threeOfKind = HighRankedHand.threeOfKind(analyseCards);
                                List<Card> twoPairs = HighRankedHand.twoPairs(analyseCards);
                                Card straightFlush = HighRankedHand.straightFlush(analyseCards);
                                Card flush = HighRankedHand.flush(analyseCards);
                                Card straight = HighRankedHand.straight(analyseCards);
                                Card pair = HighRankedHand.getPair(analyseCards);
                                
                                if (HighRankedHand.isRoyalFlush(analyseCards)) {
                                    bestHand.setPlayerBestHand(ROYAL_FLUSH);
                                }                            
                                else if (straightFlush != null) {
                                    List<Card> winningCards = new ArrayList<>();
                                    winningCards.add(straightFlush);
                                    if(bestHand.getPlayerBestHand() < STRAIGHT_FLUSH){
                                        bestHand.setPlayerBestHand(STRAIGHT_FLUSH);                                        
                                        bestHand.setWinningCards(winningCards);
                                    }                                    
                                    else if(bestHand.getPlayerBestHand() == STRAIGHT_FLUSH){
                                        Card tieCard = bestHand.getWinningCards().get(0);
                                        if(tieCard.getValue().getCardValue() < straightFlush.getValue().getCardValue()){
                                             bestHand.setWinningCards(winningCards);
                                        }
                                    }
                                }
                                else if (fourOfKind != null && !fourOfKind.isEmpty()) {
                                   if(bestHand.getPlayerBestHand() < FOUR_OF_KIND){
                                       bestHand.setPlayerBestHand(FOUR_OF_KIND);
                                       bestHand.setWinningCards(fourOfKind);
                                   }else if(bestHand.getPlayerBestHand() == FOUR_OF_KIND){
                                       Card tieCard = bestHand.getWinningCards().get(0);                                       
                                       if(tieCard.getValue().getCardValue() < fourOfKind.get(0).getValue().getCardValue()){
                                           bestHand.setWinningCards(fourOfKind);
                                       }
                                   }
                                }
                                else if (fullHouse != null && fullHouse.size() == 2) {
                                    if(bestHand.getPlayerBestHand() < FULL_HOUSE){
                                       bestHand.setPlayerBestHand(FULL_HOUSE);
                                       bestHand.setWinningCards(fullHouse);
                                    }else if(bestHand.getPlayerBestHand() == FULL_HOUSE){
                                        Card tieCard = bestHand.getWinningCards().get(0);
                                        if(tieCard.getValue().getCardValue() < fullHouse.get(0).getValue().getCardValue()){
                                            bestHand.setWinningCards(fullHouse);
                                        }
                                    }
                                }
                                else if (flush != null) {
                                    List<Card> winningCards = new ArrayList<>();
                                    winningCards.add(flush);
                                    if(bestHand.getPlayerBestHand() < FLUSH){
                                        bestHand.setPlayerBestHand(FLUSH);
                                       bestHand.setWinningCards(winningCards);
                                    }else if(bestHand.getPlayerBestHand() == FLUSH){
                                        Card tieCard = bestHand.getWinningCards().get(0);
                                        if(tieCard.getValue().getCardValue() < flush.getValue().getCardValue()){
                                             bestHand.setWinningCards(winningCards);
                                        }
                                    }
                                }
                                else if (straight != null) {
                                    List<Card> winningCards = new ArrayList<>();
                                    winningCards.add(straight);
                                    if(bestHand.getPlayerBestHand() < STRAIGHT){
                                        bestHand.setPlayerBestHand(STRAIGHT);
                                       bestHand.setWinningCards(winningCards);
                                    }else if(bestHand.getPlayerBestHand() == STRAIGHT){
                                        Card tieCard = bestHand.getWinningCards().get(0);
                                        if(tieCard.getValue().getCardValue() < straight.getValue().getCardValue()){
                                             bestHand.setWinningCards(winningCards);
                                        }
                                    }
                                }
                                else if (threeOfKind != null && !threeOfKind.isEmpty()) {
                                    if(bestHand.getPlayerBestHand() < THREE_OF_KIND){
                                       bestHand.setPlayerBestHand(THREE_OF_KIND);
                                       bestHand.setWinningCards(threeOfKind);
                                    }else if(bestHand.getPlayerBestHand() == THREE_OF_KIND){
                                        Card tieCard = bestHand.getWinningCards().get(0);
                                        if(tieCard.getValue().getCardValue() < threeOfKind.get(0).getValue().getCardValue()){
                                            bestHand.setWinningCards(threeOfKind);
                                        }
                                    }
                                }
                                else if (twoPairs != null && !twoPairs.isEmpty()) {
                                    if(bestHand.getPlayerBestHand() < TWO_PAIRS){
                                       bestHand.setPlayerBestHand(TWO_PAIRS);
                                       bestHand.setWinningCards(twoPairs);
                                    }else if(bestHand.getPlayerBestHand() == TWO_PAIRS){
                                        Card tieCard = bestHand.getWinningCards().get(0);
                                        if(tieCard.getValue().getCardValue() < twoPairs.get(0).getValue().getCardValue()){
                                            bestHand.setWinningCards(twoPairs);
                                        }else if(tieCard.getValue().getCardValue() == twoPairs.get(0).getValue().getCardValue()){
                                            tieCard = bestHand.getWinningCards().get(1);
                                            if(tieCard.getValue().getCardValue() < twoPairs.get(1).getValue().getCardValue()){
                                                bestHand.setWinningCards(twoPairs);
                                            }else if(tieCard.getValue().getCardValue() == twoPairs.get(1).getValue().getCardValue()){
                                                tieCard = bestHand.getWinningCards().get(2);
                                                if(tieCard.getValue().getCardValue() < twoPairs.get(2).getValue().getCardValue()){
                                                    bestHand.setWinningCards(twoPairs);
                                                }
                                            }
                                        }
                                    }
                                }
                                else if(pair != null){
                                    List<Card> winningCards = new ArrayList<>();
                                    winningCards.add(pair);
                                    if(bestHand.getPlayerBestHand() < PAIR){
                                        bestHand.setPlayerBestHand(PAIR);
                                       bestHand.setWinningCards(winningCards);
                                    }else if(bestHand.getPlayerBestHand() == PAIR){
                                        Card tieCard = bestHand.getWinningCards().get(0);
                                        if(tieCard.getValue().getCardValue() < pair.getValue().getCardValue()){
                                             bestHand.setWinningCards(winningCards);
                                        }
                                    }
                                }else {
                                    Card highCard = HighRankedHand.getHighRankCard(analyseCards);
                                    bestHand.setPlayerBestHand(HIGH_CARD);
                                    List<Card> winningCards = new ArrayList<>();
                                    winningCards.add(highCard);
                                    bestHand.setWinningCards(winningCards);
                                }
                                
                            }
                        }
                    }
                }
            }
            
            playersBestHands.add(bestHand);
            
        }
        int winnerPos = getWinnerPos(playersBestHands);
        return playersBestHands.get(winnerPos);
    }

    private int getWinnerPos(List<PlayerBestHighHand> playersBestHands) {
        
        int currentBest = playersBestHands.get(0).getPlayerBestHand();
        
        int currentBestPlayer = 0;
        
        for(int i=1;i<playersBestHands.size();i++){
            if(currentBest < playersBestHands.get(i).getPlayerBestHand()){
                currentBest=playersBestHands.get(i).getPlayerBestHand();
                currentBestPlayer = i;
            }else if(currentBest == playersBestHands.get(i).getPlayerBestHand()){
                if(currentBest == STRAIGHT_FLUSH){
                    Card tieCard = playersBestHands.get(currentBestPlayer).getWinningCards().get(0);
                    if (tieCard.getValue().getCardValue() < playersBestHands.get(i).getWinningCards().get(0).getValue().getCardValue()) {
                        currentBestPlayer = i;
                    }
                }else if(currentBest == FOUR_OF_KIND){
                    Card tieCard = playersBestHands.get(currentBestPlayer).getWinningCards().get(0);
                    if (tieCard.getValue().getCardValue() < playersBestHands.get(i).getWinningCards().get(0).getValue().getCardValue()) {
                        currentBestPlayer = i;
                    }
                }else if(currentBest == FULL_HOUSE){
                    Card tieCard = playersBestHands.get(currentBestPlayer).getWinningCards().get(0);
                    if (tieCard.getValue().getCardValue() < playersBestHands.get(i).getWinningCards().get(0).getValue().getCardValue()) {
                        currentBestPlayer = i;
                    }
                }else if(currentBest == FLUSH){
                    Card tieCard = playersBestHands.get(currentBestPlayer).getWinningCards().get(0);
                    if (tieCard.getValue().getCardValue() < playersBestHands.get(i).getWinningCards().get(0).getValue().getCardValue()) {
                        currentBestPlayer = i;
                    }
                }else if(currentBest == STRAIGHT){
                    Card tieCard = playersBestHands.get(currentBestPlayer).getWinningCards().get(0);
                    if (tieCard.getValue().getCardValue() < playersBestHands.get(i).getWinningCards().get(0).getValue().getCardValue()) {
                        currentBestPlayer = i;
                    }
                }else if(currentBest == THREE_OF_KIND){
                    Card tieCard = playersBestHands.get(currentBestPlayer).getWinningCards().get(0);
                    if (tieCard.getValue().getCardValue() < playersBestHands.get(i).getWinningCards().get(0).getValue().getCardValue()) {
                        currentBestPlayer = i;
                    }
                }else if(currentBest == TWO_PAIRS){
                    Card tieCard = playersBestHands.get(currentBestPlayer).getWinningCards().get(0);
                    if (tieCard.getValue().getCardValue() < playersBestHands.get(i).getWinningCards().get(0).getValue().getCardValue()) {
                        currentBestPlayer = i;
                    } else if (tieCard.getValue().getCardValue() == playersBestHands.get(i).getWinningCards().get(0).getValue().getCardValue()) {
                        tieCard = playersBestHands.get(currentBestPlayer).getWinningCards().get(1);
                        if (tieCard.getValue().getCardValue() < playersBestHands.get(i).getWinningCards().get(1).getValue().getCardValue()) {
                            currentBestPlayer = i;
                        } else if (tieCard.getValue().getCardValue() == playersBestHands.get(i).getWinningCards().get(1).getValue().getCardValue()) {
                            tieCard = playersBestHands.get(currentBestPlayer).getWinningCards().get(2);
                            if (tieCard.getValue().getCardValue() < playersBestHands.get(i).getWinningCards().get(2).getValue().getCardValue()) {
                                currentBestPlayer = i;
                            }
                        }
                    }
                }else if(currentBest == PAIR){
                    Card tieCard = playersBestHands.get(currentBestPlayer).getWinningCards().get(0);
                    if (tieCard.getValue().getCardValue() <playersBestHands.get(i).getWinningCards().get(0).getValue().getCardValue()) {
                        currentBestPlayer = i;
                    }
                }else if(currentBest == HIGH_CARD){
                    
                }
            }
        }
        
        return currentBestPlayer;
    }
    
    
}
