package com.xfinity.poker;



import static org.junit.Assert.assertTrue;
import com.xfinity.poker.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.xfinity.poker.Value.CardValue;

public class HighRankedHandTestsJUnitTest {

	@Test
	public void GetHighHandRankShouldReturnMaximumValueCard() {
		
		List<Card> cardList = new ArrayList<Card>();

		cardList.add(new Card(Suit.getSuit("SPADES"),Value.getValue(CardValue.ACE)));
		cardList.add( new Card(Suit.getSuit("SPADES"),Value.getValue(CardValue.KING)));
		cardList.add( new Card(Suit.getSuit("HEARTS"),Value.getValue(CardValue.TEN)));
		
		HighRankedHand hrd = new HighRankedHand();	
		
		Card highCard = hrd.getHighRankCard(cardList);
		assertTrue(highCard.toString().equals("SPADES:"+CardValue.ACE));
	}

}

