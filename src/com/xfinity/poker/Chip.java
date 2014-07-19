package com.xfinity.poker;

import java.util.ArrayList;
import java.util.List;

public class Chip {
	
	public static final int[] VALID_CHIPS = {5,10,25,50,100,500,1000};
	
	private int value;

	public Chip(int value) {
		//TODO check whether the value is valid from the VALID_CHIPS array
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}	
	
	public static List<Chip> createChips(int value, int count){
		List<Chip> chips = new ArrayList<Chip>();
		
		for(int i=0;i<count;i++)
			chips.add(new Chip(value));
		
		return chips;
	}

}
