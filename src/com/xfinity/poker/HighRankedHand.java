package com.xfinity.poker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.xfinity.poker.Suit.SuitType;

public class HighRankedHand {

	public boolean isRoyalFlush(List<Card> cardlist){

		if(!sameSuit(cardlist)){
			return false;
		}

		for(Card card: cardlist){
			/*
		 SuitType suitType = card.getSuit().getSuitType();		 
		 if(suitType == SuitType.CLUBS){

		 }

		 Value cardValue = card.getValue();

		 int intValue = cardValue.getCardValue();

		 if(intValue == CardValue.KING){

		 }
			 */
		}
		return true;

	}

	private boolean sameSuit(List<Card> cardlist) {

		SuitType type = cardlist.get(0).getSuit().getSuitType();

		for(int i=1;i<cardlist.size();i++)
		{
			SuitType cardType =  cardlist.get(i).getSuit().getSuitType();
			if(type != cardType)

			{
				return false;
			}


		}
		return true;
	}   


	public Card getHighRankCard(List<Card> cardList){
		if(cardList == null || cardList.size() == 0)
			return null;
		Card highCard = cardList.get(0);

		for(int i=1;i<cardList.size();i++)
		{
			Card currentCard = cardList.get(i);
			if(highCard.getValue().getCardValue() < currentCard.getValue().getCardValue())
				highCard = currentCard;		
		}	

		return highCard;
	}

	public List<Card> twoPairs(List<Card> cardList){
		if(cardList == null || cardList.size() != Dealer.EVALUATE_HAND_SIZE)
			return null;

		//TODO use a hashmap to count the same value card. if two pairs available return both cards including the highest value card
		// in a list
		return null;

	}
	

	
	public Card flush(List<Card> cardList){
		 if(!sameSuit (cardList)){
			 return null;
		 }
		 Card highCard = cardList.get(0);

			for(int i=1;i<cardList.size();i++)
			{
				Card currentCard = cardList.get(i);
				if(highCard.getValue().getCardValue() < currentCard.getValue().getCardValue())
					highCard = currentCard;		
			}	
			return highCard;

	} 

	public static List<Card> fullHouse(List<Card> cardList){
		if(cardList == null || cardList.size() != Dealer.EVALUATE_HAND_SIZE)
			return null;
		List<Card> fullHouse = new ArrayList<Card>(2);
		int[] cardsCount = getCardsCounts(cardList);
		
		for(int i=Value.CardValue.TWO;i<cardsCount.length;i++){
			if(cardsCount[i] == 3){
				fullHouse.add(0,new Card(null,Value.getValue(i)));
			}else if(cardsCount[i] == 2){
				fullHouse.add(0,new Card(null,Value.getValue(i)));
			}
		}
		
		return fullHouse;
	}
	
	public static List<Card> threeOfKind(List<Card> cardList){
		if(cardList == null || cardList.size() != Dealer.EVALUATE_HAND_SIZE)
			return null;
		List<Card> threeOfKind = new ArrayList<Card>(2);
		int[] cardsCount = getCardsCounts(cardList);
		
		int maxValue = 0;
		boolean found = false;
		
		for(int i=Value.CardValue.TWO;i<cardsCount.length;i++){
			
			if(cardsCount[i] == 3)
			{
				threeOfKind.add(new Card(null,Value.getValue(i)));
				found = true;
			}		
			if(!found && cardsCount[i] > 0)
				maxValue = i;
			
		}
		
		if(!found){
			return null;
		}else{
			threeOfKind.add(new Card(null,Value.getValue(maxValue)));
		}
		
		return threeOfKind;
		

	}

	private static int[] getCardsCounts(List<Card> cardList) {
		
		int[] returnCards = new int[Value.CardValue.ACE+1];
		for(Card card: cardList){
			returnCards[card.getValue().getCardValue()]++;			
		}
		return returnCards;
	}

}




