package com.xfinity.poker;


import com.xfinity.poker.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

public class ChipsTestsJUnitTest {

	@Test
	public void TakingSmallAndBigBlindShowReducePlayerChips() {
		Player singlePlayer = new Player("Single Player",0);
		singlePlayer.setDealer(true);
		
		List<Player> computerPlayers = new ArrayList<Player>();
		for(int i=1;i<5;i++)
			computerPlayers.add(new Player("CP"+i,i));
		
		List<Player> allPlayers = new ArrayList<Player>();
		allPlayers.add(singlePlayer);
		allPlayers.addAll(computerPlayers);
		Table table = new Table(allPlayers);
		Dealer dealer = new Dealer();
		dealer.setTable(table);
		dealer.collectBlinds();
		
		double DELTA = 1e-15;
		assertEquals(1000, allPlayers.get(0).getPlayerChips(),DELTA);
		assertEquals(990, allPlayers.get(1).getPlayerChips(),DELTA);
		assertEquals(980, allPlayers.get(2).getPlayerChips(),DELTA);
	}

}
