/*package com.xfinity.poker;

import java.util.ArrayList;
import java.util.List;

public class HighHandCardAnalyser {
	public Player getHighHandWinner(List<Player> players, List<Card> holeCards){
		for(Player player:players){
			List<Card> list = player.getPlayerHand().getCards();
			
			//List<Card> holeCards
			
			for(int i=0;i<4;i++){
				List<Card> temp = new ArrayList<Card>();
				temp.add(list.get(i));
				for(int j=i+1;j<4;j++){
					temp.add(list.get(j));
					
				}
			}
			
			
			
			
		}
	}
}
*/