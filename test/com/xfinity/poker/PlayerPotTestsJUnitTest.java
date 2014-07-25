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
        Player singlePlayer = new HumanPlayer("Single Player",0);
		singlePlayer.setDealer(true);
		
		List<Player> computerPlayers = new ArrayList<Player>();
		for(int i=1;i<5;i++)
			computerPlayers.add(new ComputerPlayer("CP"+i,i));
		
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
    
    /*
    
    @Test
    public void getValuesOfHighHands(){
        for(int j=0;j<500;j++){
            List<Player> computerPlayers = new ArrayList<Player>();
            for(int i=0;i<5;i++)
                            computerPlayers.add(new ComputerPlayer("CP"+i,i));
            Table table = new Table(computerPlayers);
            Dealer dealer = new Dealer();
            dealer.shuffleDeck();
            dealer.setTable(table);
            dealer.dealToPlayers();
            for(int i=0;i<5;i++)
            {
                ComputerPlayer player = (ComputerPlayer)table.getPlayers().get(i);
                //System.out.println(player.getHighHandValue());
            }
        }
        
        assertTrue(true);
    } */
    
    @Test
    public void FlopRoundPlayerValues(){
        for(int j=0;j<500;j++){
            List<Player> computerPlayers = new ArrayList<Player>();
            for(int i=0;i<5;i++)
                            computerPlayers.add(new ComputerPlayer("CP"+i,i));
            Table table = new Table(computerPlayers);
            Dealer dealer = new Dealer();
            dealer.shuffleDeck();
            dealer.setTable(table);
            dealer.dealToPlayers();
            
            dealer.setRound(Dealer.Round.FLOP);
            dealer.dealFlop();
            
            List<Player.PlayerAction> possibleActions = new ArrayList<>();
            possibleActions.add(Player.PlayerAction.CALL);
            possibleActions.add(Player.PlayerAction.ALL_IN);
            possibleActions.add(Player.PlayerAction.RAISE);
            possibleActions.add(Player.PlayerAction.CHECK);
            for(int i=0;i<5;i++)
            {
                ComputerPlayer player = (ComputerPlayer)table.getPlayers().get(i);
                player.getAction(possibleActions, table.getCommunityCards(), Dealer.Round.FLOP, i);
                //System.out.println(player.getPlayerHand().getCards());
            }
            
            //System.out.println("-----------------");
            //System.out.println(table.getCommunityCards());
            //System.out.println("-----------------");
            /*
            
            for(Card card:table.getPlayers().get(0).getPlayerHand().getCards()){
                System.out.println(card);
            }
            
            System.out.println("-------------");
            
            for(Card card:table.getCommunityCards()){
                System.out.println(card);
            }
            
            */
        }
        
        assertTrue(true);
    }
    
    
    
    /*
    @Test
    public void FlopRoundPlayerValues(){
        for(int j=0;j<1;j++){
            List<Player> computerPlayers = new ArrayList<Player>();
            for(int i=0;i<5;i++)
                            computerPlayers.add(new ComputerPlayer("CP"+i,i));
            Table table = new Table(computerPlayers);
            Dealer dealer = new Dealer();
            dealer.shuffleDeck();
            dealer.setTable(table);
            dealer.dealToPlayers();
            
            dealer.setRound(Dealer.Round.TURN);
            dealer.dealFlop();
            dealer.dealTurn();
            
            List<Player.PlayerAction> possibleActions = new ArrayList<>();
            possibleActions.add(Player.PlayerAction.CALL);
            possibleActions.add(Player.PlayerAction.ALL_IN);
            possibleActions.add(Player.PlayerAction.RAISE);
            possibleActions.add(Player.PlayerAction.CHECK);
            for(int i=0;i<1;i++)
            {
                ComputerPlayer player = (ComputerPlayer)table.getPlayers().get(i);
                player.getAction(possibleActions, table.getCommunityCards(), Dealer.Round.TURN, 1);                
            }
            for(Card card:table.getPlayers().get(0).getPlayerHand().getCards()){
                System.out.println(card);
            }
            
            System.out.println("-------------");
            
            for(Card card:table.getCommunityCards()){
                System.out.println(card);
            }
        } 
        
        assertTrue(true);
    }
    
    */
}
