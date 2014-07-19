package com.xfinity.poker;




import com.xfinity.poker.*;
import com.xfinity.poker.Value.CardValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CardDeckTestsJUnitTest {	
	

	@Test
	public void InitializeCardDeckSizeIs52() {
		CardDeck deck = new CardDeck();
		int size = deck.getSize();
		assertEquals(size, 52);
	}
	
	@Test
	public void GettingTheTopCardAfterDeckCreationShouldBeSpadesAce()
	{
		CardDeck deck = new CardDeck();
		Card card = deck.getTopCard();
		
		assertTrue(card.toString().equals("SPADES:"+CardValue.ACE));
		assertEquals(deck.getSize(), 51);
	}
	
	@Test
	public void ShufflingCardDeckShouldChangeTopCard()
	{
		CardDeck deck = new CardDeck();		
		deck.shuffle();
		Card card = deck.getTopCard();
		assertTrue(!card.toString().equals("SPADES:"+CardValue.ACE));
		assertEquals(deck.getSize(), 51);
	}
	
	

}
