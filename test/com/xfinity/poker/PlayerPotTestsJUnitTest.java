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
public class PlayerPotTestsJUnitTest {
    
    public PlayerPotTestsJUnitTest() {
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
    public void TakingBlindsShouldIncreasePlayerPotValues() {
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
		assertEquals(0, table.getTablePot().getPlayerPots().get(0).getPlayerContribution(),DELTA);
		assertEquals(10, table.getTablePot().getPlayerPots().get(1).getPlayerContribution(),DELTA);
		assertEquals(20, table.getTablePot().getPlayerPots().get(2).getPlayerContribution(),DELTA);
    }
}
