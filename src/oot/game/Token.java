package oot.game;

/**
 * Represents the occupancy status of a cell.
 * @author Christopher Rotter
 *
 */
public enum Token
{
	BLOCKED('-'), CIRCLE('O'), CROSS('X');

	/**
	 * The char that is representing this token graphically.
	 */
	private final char symbol;

	/**
	 * Creates a new token object.
	 * @param symbol The char that is representing this token graphically.
	 */
	private Token(char symbol)
	{
		this.symbol = symbol;
	}

	public char getSymbol()
	{
		return symbol;
	}
}
