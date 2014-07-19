package com.xfinity.poker;

/**
 * Represents the Suit of a card.
 * 
 * Bugs: none
 * 
 * @author Dilan
 * @version 1.0
 * @since 2014-06-15
 *
 */
public class Suit {
	
	private SuitType suitType;
	
	public enum SuitType{CLUBS,DIAMONDS,HEARTS,SPADES};
	
	
	public static Suit getSuit(String suit)
	{
		return new Suit(suit.toUpperCase());
	}
	
	public Suit(String suit)
	{
		switch(suit){
		case "CLUBS":
			setSuitType(SuitType.CLUBS);
			break;
		case "DIAMONDS":
			setSuitType(SuitType.DIAMONDS);
			break;
		case "HEARTS" :
			setSuitType(SuitType.HEARTS);
			break;
		case "SPADES" :
			setSuitType(SuitType.SPADES);
			break;
		default:
				
		}
	}

	public Suit(SuitType type) {		
		this.setSuitType(type);
	}

	public SuitType getSuitType() {
		return suitType;
	}

	private void setSuitType(SuitType mySuitType) {
		this.suitType = mySuitType;
	}	
	
}
