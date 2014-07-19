package com.xfinity.poker;



import static org.junit.Assert.*;
import com.xfinity.poker.*;
import org.junit.Test;

public class PlayerTestsJUnitTest {

	@Test
	public void playerShouldHaveAName(){
		Player player = new Player("TEST PALY",1);		
		Player player2 = new Player("Name",2);		
		assertNotNull(player.getName());
		assertTrue(player2.getName().equals("Name"));
	}

}
