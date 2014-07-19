package com.xfinity.poker;

import java.util.ArrayList;
import java.util.List;

public class TablePot {
	
	private List<PlayerPot> playerPots;

	public TablePot(List<Player> players) {
		playerPots = new ArrayList<PlayerPot>();
		
		initializeTablePot(players);
	}	
	
	private void initializeTablePot(List<Player> players){
		for(Player player:players)
			playerPots.add(new PlayerPot(player));			
	}	 
	
	
	public double getTablePotChips(){
		double chips =0;
		for(PlayerPot pot:playerPots)
			chips += pot.getPlayerContribution();
		
		return chips;
		
	}

}
