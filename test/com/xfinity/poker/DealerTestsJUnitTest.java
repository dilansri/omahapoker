package com.xfinity.poker;



import static org.junit.Assert.*;
import com.xfinity.poker.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DealerTestsJUnitTest {

	@Test
	public void dealerShouldHaveACardDeckInitially() {
		Dealer dealer = new Dealer();		
		assertNotNull(dealer.getDeck());
	}
	
	@Test
	public void afterDealingHoleCardsEachPlayerShouldHave4Cards()
	{
		List<Player> players = new ArrayList<Player>();
		
		for(int i=0;i<5;i++){
			players.add(new Player("Guest"+i,i+1));
		}
		
		Dealer dealer = new Dealer();		
		dealer.dealToPlayers(players);
		
		for(int i=0;i<5;i++)
			assertEquals(4, players.get(i).getHandSize());
	}
	
	@Test
	public void afterPreFlopDeckSizeShouldBeCorrect()
	{
		assertTrue(true);
	}

}
