package com.xfinity.poker;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;

public class Player implements PlayerRules {
	private final static String NAME_PROP_NAME = "name";
	private ReadOnlyStringWrapper name;
	protected PlayerHand playerHand;
	private DoubleProperty playerChips;
	private int order;
	private boolean isDealer;	
	private boolean allIn;	
        private boolean folded;
        private boolean calledForAllIn;
        
        private String winningHandType;
        
        private List<Card> winningCards;
        
        private List<Card> winningLowCards;

            
        public enum PlayerAction{CALL,RAISE,CHECK,FOLD,ALL_IN};	
	
	public Player(String name,int order){
		this.name = new ReadOnlyStringWrapper(this,NAME_PROP_NAME, name);
		playerHand = new PlayerHand();
		allIn = false;
		playerChips = new SimpleDoubleProperty(PLAYER_INITIAL_CHIPS);
		isDealer = false;
		this.order = order;
                folded = false;
                winningCards = null;
                winningLowCards = null;
	}
	/*
	private List<Chip> createChipsFromMoney(int playerMoney) {
		List<Chip> chips = new ArrayList<Chip>();		
		return chips;
	} */
        
        public void setWinningHandType(String type){
            winningHandType = type;
        }
        
        public String getWinningHandType(){
            return winningHandType;
        }
        
        public ReadOnlyStringProperty nameProperty() {
            return name.getReadOnlyProperty();
        }
        
        public boolean isFolded(){
            return folded;
        }
        
        public void setFolded(boolean value){
            folded = value;
        }

	public void giveCard(Card card){
		playerHand.add(card);
	}
	
	public int getHandSize(){
		return playerHand.size();
	}
	
	public String getName() {
		return name.get();
	}
	public PlayerHand getPlayerHand() {
		return playerHand;
	}

	public boolean isAllIn() {
		return allIn;
	}

	public void setAllIn(boolean allIn) {
		this.allIn = allIn;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isDealer() {
		return isDealer;
	}

	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}
	
	public double getPlayerChips(){
		return playerChips.get();
	}

	public double takeChips(double amount) {
		if(playerChips.get() < amount){
			//TODO throw exception
			return -1;
		}else{
			playerChips.set(playerChips.get()-amount);
			return amount;
		}
			
	}
        
        public DoubleProperty getPlayerChipsProperty(){
            return playerChips;
        }      
        
        public void setWinningCards(List<Card> cards){
            winningCards = cards;
        }
        
        public List<Card> getWinningCards(){
            return winningCards;
        }
        
        public void setWinningLowCards(List<Card> cards){
            winningLowCards = cards;
        }
        
        public List<Card> getWinningLowCards(){
            return winningLowCards;
        }
        
        public void awardChips(double d) {
            playerChips.set(playerChips.get()+d);
        }
        
        public void clearWinningHands() {
            if(winningCards != null)
                winningCards.clear();
            if(winningLowCards != null)
                winningLowCards.clear();
            
            winningCards = null;
            winningLowCards = null;
        }
        
        public boolean isCalledForAllIn(){
            return calledForAllIn;
        }
        
        public void setCalledForAllIn(boolean value){
            calledForAllIn = value;
        }
        
        public void setPlayerChips(double chips){
            playerChips.set(chips);
        }
       
        
	
}
