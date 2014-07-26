package com.xfinity.poker;

import static com.xfinity.poker.DealerRules.PLAYER_HAND_SIZE;
import java.util.ArrayList;
import java.util.List;

public class Table implements TableRules {
	
	private TablePot tablePot;
	
	private List<Player> playerList;
	
	private int bigBlind;
	private int smallBlind;
        
        private double highestPotValue;
        
        private List<Card> communityCards;
	
	
	public Table(List<Player> players)
	{
		playerList = players;
		tablePot = new TablePot(playerList);
		bigBlind = INITIAL_BIG_BLIND;
		smallBlind = INITIAL_SMALL_BLIND;
                 communityCards = new ArrayList<>(5);
	}
        
        public double getHighestPotValue(){
            return highestPotValue;
        }
        
        public List<Card> getCommunityCards(){
            return communityCards;
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
        List<PlayerPot> pots = tablePot.getPlayerPots();
        
        int startPosition=0;
        
        for(int i=0;i<pots.size();i++){
            Player player = getPlayers().get(i);
            if(player.isFolded() )
                continue;
            startPosition = i;
            break;
        }
        
        double potCheckValue = tablePot.getPlayerPots().get(startPosition).getPlayerContribution();
        
        for(int i=startPosition+1;i<startPosition+pots.size();i++){
            if(potCheckValue != tablePot.getPlayerPots().get(i%pots.size()).getPlayerContribution() 
                    && !getPlayers().get(i%pots.size()).isFolded() )
                return false;
        }
        return true;
    }
    
    public void setHighestPotValue(double value){
        highestPotValue = value;
    }

    public void clearTablePot() {
        tablePot.clear();
    }

}
