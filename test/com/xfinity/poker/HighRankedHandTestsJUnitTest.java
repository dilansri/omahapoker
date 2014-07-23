package com.xfinity.poker;



import static org.junit.Assert.*;
import com.xfinity.poker.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.xfinity.poker.Value.CardValue;
import java.util.Collection;
import java.util.Collections;

public class HighRankedHandTestsJUnitTest {

	@Test
	public void GetHighHandRankShouldReturnMaximumValueCard() {
		
		List<Card> cardList = new ArrayList<Card>();

		cardList.add(new Card(Suit.getSuit("SPADES"),Value.getValue(CardValue.ACE)));
		cardList.add( new Card(Suit.getSuit("SPADES"),Value.getValue(CardValue.KING)));
		cardList.add( new Card(Suit.getSuit("HEARTS"),Value.getValue(CardValue.TEN)));
		
			
		
		Card highCard = HighRankedHand.getHighRankCard(cardList);
		assertTrue(highCard.toString().equals("SPADES:"+CardValue.ACE));
	}
        
        
        @Test
        public void GetTwoPairsShouldReturnTwoCardsAndMaxValueOtherwiseNull(){
            List<Card> cardList = new ArrayList<Card>();

		cardList.add(new Card(Suit.getSuit("SPADES"),Value.getValue(CardValue.ACE)));
		cardList.add( new Card(Suit.getSuit("SPADES"),Value.getValue(CardValue.KING)));
		cardList.add( new Card(Suit.getSuit("HEARTS"),Value.getValue(CardValue.KING)));
                cardList.add( new Card(Suit.getSuit("HEARTS"),Value.getValue(CardValue.ACE)));
                cardList.add( new Card(Suit.getSuit("HEARTS"),Value.getValue(CardValue.TEN)));
		
			
		List<Card> returnList = HighRankedHand.twoPairs(cardList);
		assertEquals(CardValue.KING,returnList.get(0).getValue().getCardValue());
                assertEquals(CardValue.ACE,returnList.get(1).getValue().getCardValue());
                assertEquals(CardValue.TEN, returnList.get(2).getValue().getCardValue());
                
                cardList.clear();
                
                cardList.add(new Card(Suit.getSuit("SPADES"),Value.getValue(CardValue.ACE)));
		cardList.add( new Card(Suit.getSuit("SPADES"),Value.getValue(CardValue.KING)));
		cardList.add( new Card(Suit.getSuit("HEARTS"),Value.getValue(CardValue.QUEEN)));
                cardList.add( new Card(Suit.getSuit("HEARTS"),Value.getValue(CardValue.JACK)));
                cardList.add( new Card(Suit.getSuit("HEARTS"),Value.getValue(CardValue.TEN)));
               returnList = HighRankedHand.twoPairs(cardList);
               assertNull(returnList);
               
        }
        
        
        @Test
        public void GetFullHouseShouldReturnTwoCardsOtherwiseNull(){
            List<Card> cardList = new ArrayList<Card>();

            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.ACE)));
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.KING)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.KING)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.ACE)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.KING)));
            
            List<Card> returnList = HighRankedHand.fullHouse(cardList);
            
            assertEquals(CardValue.KING, returnList.get(0).getValue().getCardValue());
            assertEquals(CardValue.ACE, returnList.get(1).getValue().getCardValue());
        }
        
        
        @Test
        public void threeOfKindShouldReturnACardAndHighestValue(){
            List<Card> cardList = new ArrayList<Card>();

            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.EIGHT)));
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.KING)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.KING)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.ACE)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.KING)));
            
            List<Card> returnList = HighRankedHand.threeOfKind(cardList);
            
            assertEquals(CardValue.KING, returnList.get(0).getValue().getCardValue());
            assertEquals(CardValue.ACE, returnList.get(1).getValue().getCardValue());
        }
        
        
        @Test
        public void GetRoyalFlushShouldReturnTrueOrFalse(){
            List<Card> cardList = new ArrayList<Card>();

            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.TEN)));
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.KING)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.QUEEN)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.ACE)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.JACK)));
            
            boolean result = HighRankedHand.isRoyalFlush(cardList);
            
            assertFalse(result);
            
            cardList.clear();
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.TEN)));
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.KING)));
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.QUEEN)));
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.ACE)));
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.JACK)));
            
            result = HighRankedHand.isRoyalFlush(cardList);
            assertTrue(result);
        }
        
        /*
        @Test
        public void sortingCardList(){
            List<Card> cardList = new ArrayList<Card>();

            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.TEN)));
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.KING)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.QUEEN)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.ACE)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.JACK)));
            
            Collections.sort(cardList);
            
            for(Card card:cardList){
                System.out.println(card);
            }
        } */
        
        @Test
        public void GetStraightFlushShouldReturnHighestCardOrNull(){
            List<Card> cardList = new ArrayList<Card>();

            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.TEN)));
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.KING)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.QUEEN)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.ACE)));
            cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(CardValue.JACK)));
            
            Card highest = HighRankedHand.straightFlush(cardList);
            
            assertNull(highest);
            cardList.clear();
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.TWO)));
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.FOUR)));
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.FIVE)));
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.THREE)));
            cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(CardValue.SIX)));
            
            highest = HighRankedHand.straightFlush(cardList);
            
            assertEquals(CardValue.SIX, highest.getValue().getCardValue());
        }

}

