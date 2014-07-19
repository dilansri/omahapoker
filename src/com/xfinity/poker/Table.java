package com.xfinity.poker;

import java.util.List;

public class Table implements TableRules {
	
	private TablePot tablePot;
	
	private List<Player> playerList;
	
	private int bigBlind;
	private int smallBlind;
	
	
	public Table(List<Player> players)
	{
		playerList = players;
		tablePot = new TablePot(playerList);
		bigBlind = INITIAL_BIG_BLIND;
		smallBlind = INITIAL_SMALL_BLIND;
	}
	
	private double getTableChips()
	{
		return tablePot.getTablePotChips();
	}
	
	public List<Player> getPlayers(){
		return playerList;
	}
	
	public int numberOfPlayers(){
		return (playerList == null)? 0 : playerList.size();
	}

	public void takeBigBlindFromPlayer(int i) {
		
		playerList.get(i%numberOfPlayers()).takeChips(bigBlind);
	}

	public void takeSmallBlindFromPlayer(int i) {
		playerList.get(i%numberOfPlayers()).takeChips(smallBlind);	
	}

}
