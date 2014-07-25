/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xfinity.poker;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrator
 */
public class LowRankHandJUnitTest {
    
    public LowRankHandJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void LowHandShouldReturnTheLowScore() {
         List<Card> cardList = new ArrayList<Card>();

         cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(Value.CardValue.ACE)));
         cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(Value.CardValue.KING)));
         cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(Value.CardValue.KING)));
         cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(Value.CardValue.ACE)));
         cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(Value.CardValue.TEN)));
         
         System.out.println(LowRankedHand.getLowHandScore(cardList));         
         assertEquals(35, LowRankedHand.getLowHandScore(cardList));
         
         cardList.clear();
         cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(Value.CardValue.SEVEN)));
         cardList.add(new Card(Suit.getSuit("SPADES"), Value.getValue(Value.CardValue.FIVE)));
         cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(Value.CardValue.EIGHT)));
         cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(Value.CardValue.ACE)));
         cardList.add(new Card(Suit.getSuit("HEARTS"), Value.getValue(Value.CardValue.TWO)));
         System.out.println(LowRankedHand.getLowHandScore(cardList));
         assertEquals(23, LowRankedHand.getLowHandScore(cardList));
     }
}
