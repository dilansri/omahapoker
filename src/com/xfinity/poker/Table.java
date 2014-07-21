package com.xfinity.poker;

import static com.xfinity.poker.DealerRules.PLAYER_HAND_SIZE;
import java.util.List;

public class Table implements TableRules {
	
	private TablePot tablePot;
	
	private List<Player> playerList;
	
	private int bigBlind;
	private int smallBlind;
        
        private double highestPotValue;
	
	
	public Table(List<Player> players)
	{
		playerList = players;
		tablePot = new TablePot(playerList);
		bigBlind = INITIAL_BIG_BLIND;
		smallBlind = INITIAL_SMALL_BLIND;
	}
        
        public double getHighestPotValue(){
            return highestPotValue;
        }
        
        public double getRaiseAmount(){
            return highestPotValue + bigBlind;
        }
        
        public TablePot getTablePot(){
            return tablePot;
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

	synchronized public void takeBigBlindFromPlayer(int i) {
		
		playerList.get(i%numberOfPlayers()).takeChips(bigBlind);
                getTablePot().getPlayerPots().get(i).addToPot(bigBlind);
                
                highestPotValue = bigBlind;
	}

	synchronized public void takeSmallBlindFromPlayer(int i) {
		playerList.get(i%numberOfPlayers()).takeChips(smallBlind);
                getTablePot().getPlayerPots().get(i).addToPot(smallBlind);
	}

    public boolean isSamePotValues() {
        return false;
    }

}
