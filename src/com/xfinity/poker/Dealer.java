package com.xfinity.poker;

import java.util.List;

public class Dealer implements DealerRules {	
	
	
	CardDeck deck;
	Table table;
	//HighHandCardAnalyser highAnalyser;
	
	private int dealingPlayerOrder;	
	

	public Dealer()
	{
		deck = new CardDeck();
		dealingPlayerOrder = 1;
		//highAnalyser = new HighHandCardAnalyser();
	    
		
	}
        
        public void shuffleDeck(){
            deck.shuffle();
        }
        
        public int getDealingPlayerOrder(){
            return dealingPlayerOrder;
        }
	
	public void setTable(Table table){
		this.table = table;
	}
	
	public void collectBlinds()
	{
		getBigBlind();
		getSmallBlind();
	}
	
	private void getSmallBlind() {
		table.takeSmallBlindFromPlayer(dealingPlayerOrder);
	}

	private void getBigBlind() {
		table.takeBigBlindFromPlayer(dealingPlayerOrder+1);
	}

	public void collectPlayerPots(){
		
	}
	
	private Player findHighHandWinner(){
		return null;
	}
	
	private Player findLowHandWinner(){
		return null;
	}
	
	public void dealPreFlop(){
		
	}
	
	public void dealFlop(){
		
	}
	
	public void dealTurn(){
		
	}
	
	public void dealRiver(){
		
	}
	
	public void dealToPlayers()
	{
		List<Player> players = table.getPlayers();		
		dealToPlayers(players);		
	}
	
	public void dealToPlayers(List<Player> players){		
		
		for(int i=0,j=dealingPlayerOrder; i< players.size()*PLAYER_HAND_SIZE;i++,j++){
			players.get(j%players.size()).giveCard(deck.getTopCard());
		}				
		//dealingPlayerOrder++;
	}
	

	public CardDeck getDeck() {
		return deck;
	}

	public void setDeck(CardDeck deck) {
		this.deck = deck;
	}
	
	private void deal(Player player){
		
	}
	
	public List<Player> getWinners(){
		
		return null;
	}
        
        public Table getTable(){
            return table;
        }
	
	
}
