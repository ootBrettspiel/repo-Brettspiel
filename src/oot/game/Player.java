package oot.game;

/**
 * Represents a Player.
 *@author Nico Gensheimer
 *
 */
public abstract class Player
{
	// The players Token (Cross or Circle)
	protected Token token;
	// The players name.
	protected String name;

	protected Calculator calculator;

	protected GameBoard board;

	/**
	 * Constructor to create a player.
	 * @param name of the player.
	 * @param token cross or circle.
	 */
	Player(String name, Token token, GameBoard board, Calculator calculator)
	{
		this.name = name;
		this.token = token;
		this.board = board;
		this.calculator = calculator;
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

	/**
	 * Gives this palyer the opportunity to make a turn.
	 * @author Christopher Rotter
	 */
	public abstract boolean makeTurn(GamePhase phase);

	public Token getToken()
	{
		return token;
	}

	public String getName()
	{
		return name;
	}
}
