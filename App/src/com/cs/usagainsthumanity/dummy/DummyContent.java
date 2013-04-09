package com.cs.usagainsthumanity.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R;

import com.cs.usagainsthumanity.Objects.Game;
import com.cs.usagainsthumanity.Objects.Player;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {
	
	

	/**
	 * An array of sample (dummy) items.
	 */
	
	public static List<Game> DummyGames = new ArrayList<Game>();	

	static {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(1, "Stewart"));
		players.add(new Player(2, "Mitch"));
		players.add(new Player(3, "Charlie"));
		players.add(new Player(4, "Sean"));
		players.add(new Player(5, "Jordan"));
		players.add(new Player(6, "Mike"));
		
		// Add 3 sample items.
		addDummyGame(1, 8, 6, false, "Stewart", players);
		addDummyGame(2, 8, 6, false, "Mitch", players);
		addDummyGame(3, 8, 6, false, "Sean", players);

	}	

	private static void addDummyGame(int id, int pointsToWin, int slots, boolean isPrivate, String hostname, List<Player> playerList){
		DummyGames.add(new Game(id, pointsToWin, slots, isPrivate, hostname, playerList));
	}

	
}
