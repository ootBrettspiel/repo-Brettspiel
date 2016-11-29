package oot.game;

import java.io.Serializable;

/**
 * Represents a cell of a game board object.
 * @author Christopher Rotter
 *
 */
@SuppressWarnings("serial")
public class Cell implements Serializable
{
	/**
	 * A token that occupies this cell.
	 */
	private Token token;

	/**
	 * Creates a new cell object. Pass null as a parameter for an empty cell.
	 * @param token The contained token or null.
	 */
	public Cell(Token token)
	{
		this.token = token;
	}

	/**
	 * Reverses the contained token from cross to circle and vice versa.
	 * @throws IllegalStateException Thrown when the cell is empty or blocked.
	 */
	public void reverse() throws IllegalStateException
	{
		if (token == null || token == Token.BLOCKED)
		{
			throw new IllegalStateException("The accessed cell is blocked or contains no token.");
		}
		else if (token == Token.CIRCLE)
		{
			token = Token.CROSS;
		}
		else
		{
			token = Token.CIRCLE;
		}
	}

	public Token getToken()
	{
		return token;
	}
}
