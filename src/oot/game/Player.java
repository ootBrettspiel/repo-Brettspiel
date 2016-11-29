package oot.game;

/**
 * Represents a Player.
 *@author Nico Gensheimer
 *
 */
public class Player {

	// The players Token (Cross or Circle)
	private Token token;
	// The players name.
	private String name;

	/**
	 * Constructor to create a player.
	 * @param name of the player.
	 * @param token cross or circle.
	 */
	Player(String name, Token token){
		this.name = name;
		this.token = token;
	}

	public Token getToken() {
		return token;
	}


	public String getName() {
		return name;
	}

	/**
	 * Method to count the players tokens on the field.
	 * @return how many tokens the player has on the field.
	 */
	public int countToken()
	{
		// TODO: implement
		return 0;
	}


}
