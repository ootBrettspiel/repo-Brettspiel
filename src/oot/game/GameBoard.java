package oot.game;

import java.io.Serializable;

/**
 * Represents a game board for the game OOThello.
 * @author Christopher Rotter
 *
 */
@SuppressWarnings("serial")
public class GameBoard implements Serializable
{
	/**
	 * The cells that make up the board.
	 */
	private Cell[][] cells;

	/**
	 * Creates a new game board object.
	 * @param size The number of columns and rows for the inner field. Valid numbers must be even and range from 6 to 16.
	 * @throws IllegalArgumentException Thrown when the parameter size is invalid (see above).
	 */
	public GameBoard(int size) throws IllegalArgumentException
	{
		if (size < 6 || size > 16 || size % 2 != 0)
		{
			throw new IllegalArgumentException("The size was not valid.");
		}

		cells = new Cell[size + 2][size + 2];

		for (int i = 0; i < cells.length; i++)
		{
			for (int j = 0; j < cells.length; j++)
			{
				if (i == 0 && j == 0 || i == cells.length - 1 && j == 0 || i == 0 && j == cells.length - 1 || i == cells.length - 1 && j == cells.length - 1)
				{
					cells[i][j] = new Cell(Token.valueOf("Blocked"));
				}
				else
				{
					cells[i][j] = new Cell(null);
				}
			}
		}
	}

	/**
	 * Draws the game board on the console.
	 */
	public void draw()
	{
		for (int i = 0; i < cells.length; i++)
		{
			String spacer = "";

			for (int j = 0; j < cells.length; j++)
			{
				if (cells[i][j].getToken() != null)
				{
					System.out.print("  " + cells[i][j].getToken().getSymbol() + "  ");
				}
				else
				{
					System.out.print("     ");
				}

				System.out.print("|");
				spacer += "______";
			}

			System.out.println();
			System.out.println(spacer);
			System.out.println();
		}
	}
}
