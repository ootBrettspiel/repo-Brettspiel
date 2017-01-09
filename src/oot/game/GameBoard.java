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

	private Calculator calculator;

	/**
	 * Creates a new game board object.
	 * @param size The number of columns and rows for the inner field. Valid numbers must be even and range from 8 to 12.
	 * @throws IllegalArgumentException Thrown when the parameter size is invalid (see above).
	 */
	public GameBoard(int size) throws IllegalArgumentException
	{
		if (size < 8 || size > 12 || size % 2 != 0)
		{
			throw new IllegalArgumentException("The size was not valid.");
		}

		cells = new Cell[size][size];

		for (int i = 0; i < cells.length; i++)
		{
			for (int j = 0; j < cells.length; j++)
			{
				if (i == 0 && j == 0 || i == cells.length - 1 && j == 0 || i == 0 && j == cells.length - 1 || i == cells.length - 1 && j == cells.length - 1)
				{
					cells[i][j] = new Cell(Token.BLOCKED);
				}
				else if (i == cells.length / 2 - 1 && j == cells.length / 2 - 1 || i == cells.length / 2 && j == cells.length / 2)
				{
					cells[i][j] = new Cell(Token.CROSS);
				}
				else if (i == cells.length / 2 && j == cells.length / 2 - 1 || i == cells.length / 2 - 1 && j == cells.length / 2)
				{
					cells[i][j] = new Cell(Token.CIRCLE);
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
		String space = "______";
		String line = "     _";
		char column = 'A';
		int row = 1;

		System.out.print("   ");

		for (int i = 0; i < cells.length; i++)
		{
			System.out.print("     " + column++);
			line = line.concat(space);
		}

		System.out.println();
		System.out.println(line);
		System.out.println();

		for (int i = 0; i < cells.length; i++)
		{
			if (row < 10)
			{
				System.out.print("  " + row++ + "  ");
			}
			else
			{
				System.out.print(" " + row++ + "  ");
			}

			System.out.print("|");

			for (int j = 0; j < cells.length; j++)
			{
				if (cells[j][i].getToken() != null)
				{
					System.out.print("  " + cells[j][i].getToken().getSymbol() + "  ");
				}
				else
				{
					System.out.print("     ");
				}

				System.out.print("|");
			}

			System.out.println();
			System.out.println(line);
			System.out.println();
		}
	}

	public Cell[][] getCells()
	{
		return cells;
	}

	/**
	 * Places a token at the given position and reverses all enemy tokens that should be reversed.
	 * @param token The token that will be placed.
	 * @param position The position where the token will be placed.
	 * @param phase The game phase in which the move is happening.
	 */
	public void setToken(Token token, Coordinate position, GamePhase phase)
	{
		cells[position.getX()][position.getY()].setToken(token);

		if (phase == GamePhase.REGULAR)
		{
			Coordinate[] reversed = calculator.calculateReversedFields(token, position);

			System.out.print("Es wurden folgende " + reversed.length + " Felder übernommen: ");

			for (int i = 0; i < reversed.length; i++)
			{
				cells[reversed[i].getX()][reversed[i].getY()].reverse();
				System.out.print(reversed[i].toString() + "  ");
			}

			System.out.println();
		}
	}

	public int countTokens(Token token)
	{
		int tokens = 0;

		for (int i = 0; i < cells.length; i++)
		{
			for (int j = 0; j < cells.length; j++)
			{
				if (cells[i][j].getToken() == token)
				{
					tokens++;
				}
			}
		}

		return tokens;
	}

	//TODO: remove circle dependency
	public void setCalculator(Calculator calculator)
	{
		this.calculator = calculator;
	}
}