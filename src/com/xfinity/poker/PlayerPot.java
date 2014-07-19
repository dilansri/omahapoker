package com.xfinity.poker;

import java.util.ArrayList;
import java.util.List;

public class PlayerPot {
	
	private Player player;
	
	double playerContribution;

	public PlayerPot(Player player) {
		this.player = player;
		playerContribution = 0;
	}
	
	public void addToPot(double chips){
		playerContribution += chips;
	}
	
	public void clearPot(){
		playerContribution = 0;
	}

	public Player getPlayer() {
		return player;
	}

	public double getPlayerContribution() {
		return playerContribution;
	}		

}
