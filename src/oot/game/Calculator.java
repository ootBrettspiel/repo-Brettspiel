package oot.game;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

/**
 * This Class is used for every kind of game calculation, especially for the GameAI.
 * @author Christian Coenen
 *
 */
public class Calculator {

	GameBoard board;
	private Cell[][] cells;
	private int innerFieldSize;
	private int fieldSize;

	Calculator(GameBoard board)
	{
		this.board = board;
		cells = board.getCells();
		innerFieldSize = cells[0].length-2;
		fieldSize = innerFieldSize+2;
	}


	/**
	 * Calcultes the number of possible moves for a given Token (Circle or Cross).
	 * @param token
	 * @return the number of possible moves.
	 */
	public int calcPossibleMoves(Token token)
	{
		int possibleMoves = 0;
		int[][] fieldStrength = calcFields(token);
		for (int row = 0; row < innerFieldSize; row++){
		     for (int column = 0; column < innerFieldSize; column++)
		     {
		    	 if(fieldStrength[column][row] != 0)
		    		 possibleMoves++;
		     }
		}
		return possibleMoves;
	}
	/**
	 * Calculates the strength of every possible move.
	 * @return an array with the number of capturable fields of every field.
	 */
	public int[][] calcFields(Token token)
	{
		cells = board.getCells();
		int[][] fieldStrength = new int[innerFieldSize][innerFieldSize];

		// check every field
		for (int row = 0; row < innerFieldSize; row++){
		     for (int column = 0; column < innerFieldSize; column++)
		     {
		    	 if(cells[column][row].getToken() == null)
		    	 {
		    		 // get the number of captured fields with a move on the field
		    		 fieldStrength[column][row] = calcOneField(token, column, row);
		    	 }
		     }
		}
		return fieldStrength;
	}

	/**
	 * Calculates the strength of a possible move.
	 * @param row to calculate
	 * @param column to calculate
	 * @return number of capturable fields
	 */
	public int calcOneField(Token token, int column, int row)
	{
		if(row >= cells.length - 1 || column >= cells.length - 1 || row < 1 || column < 1 || cells[column][row].getToken() != null)
			return 0;

		int captureValue = 0;
		/*
		 * Checks how many fields can be captured for every direction.
		 */
		captureValue += calcOnePath(token, column-1, row-1, -1, -1); // Top, left
		captureValue += calcOnePath(token, column-1, row, -1, 0); // Middle, left
		captureValue += calcOnePath(token, column-1, row+1, -1, 1); // Bottom, left
		captureValue += calcOnePath(token, column, row-1, 0, -1); // Top, middle
		captureValue += calcOnePath(token, column, row+1, 0, 1); // Bottom, middle
		captureValue += calcOnePath(token, column+1, row-1, 1, -1); // Top, right
		captureValue += calcOnePath(token, column+1, row, 1, 0); // Middle, right
		captureValue += calcOnePath(token, column+1, row+1, 1, 1); // Bottom, right

		return captureValue;
	}

	/**
	 * Checks how many fields can be captures by a given direction of a given field.
	 * @param row to calculate
	 * @param column to calculate
	 * @param moveRow direction to search
	 * @param moveCollumn direction to search
	 * @return number of capturable fields for the direction
	 */
	public int calcOnePath(Token token, int column, int row, int moveColumn, int moveRow)
	{
		cells = board.getCells();
		int counter = 0;
		Token playerToken = token;
		Token oppToken = null;

		if(playerToken == Token.CIRCLE)
			oppToken = Token.CROSS;
		else
			oppToken = Token.CIRCLE;

		// Calculates how many stones can be captured.
		while(row >= 0 && row < fieldSize && column >= 0 && column < fieldSize)
		{
			if(cells[column][row].getToken() == oppToken)
			{
				counter++;
			}
			else if(cells[column][row].getToken() == playerToken && counter == 0 || cells[column][row].getToken() == Token.BLOCKED ||
					cells[column][row].getToken() == null)
			{
				return 0;
			}
			else if(cells[column][row].getToken() == playerToken)
			{
				return counter;
			}
			row += moveRow;
			column += moveColumn;
		}
		return 0;
	}

	/**
	 * Checks if a given move is possible or not.
	 * @param row
	 * @param column
	 * @return true if the move is possible, false if not.
	 */
	public boolean isMovePossible(Token token, Coordinate position, GamePhase phase)
	{
		if (phase == GamePhase.REGULAR)
		{
			return calcOneField(token, position.getX(), position.getY()) == 0 ? false : true;
		}
		else if (board.getCells()[position.getX()][position.getY()].getToken() == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Calculates all field indexes where a token will be reversed from setting a token at the given position.
	 * @param token The token that should be placed.
	 * @param column The x-index of the field.
	 * @param row The y-index of the field.
	 * @return An array of coordinates that represent the coordinates of reversable tokens.
	 * @author Christopher Rotter
	 */
	public Coordinate[] calculateReversedFields(Token token, Coordinate position)
	{
		LinkedList<Coordinate> coordinateList = new LinkedList<>();
		int directionX = -1;
		int directionY = -1;

		for (int i = 0; i < 8; i++)
		{
			for (int j = 1; j <= calcOnePath(token, position.getX() + directionX, position.getY() + directionY, directionX, directionY); j++)
			{
				coordinateList.add(new Coordinate(position.getX() + directionX * j, position.getY() + directionY * j));
			}

			if (i < 2)
			{
				directionX++;
			}
			else if (i < 4)
			{
				directionY++;
			}
			else if (i < 6)
			{
				directionX--;
			}
			else
			{
				directionY--;
			}
		}

		return coordinateList.toArray(new Coordinate[coordinateList.size()]);
	}

	// Getter

	public int getInnerFieldSize() {
		return innerFieldSize;
	}

	/**
	 * Calculates the next move for the GameAI.
	 * @param diffictuly - Diffictuly mode the GameAI is actual playing
	 * @return the field to place the stone
	 */
	public int[] calcSetPhase(Difficulty difficulty)
	{
		cells = board.getCells();
		int[] field = new int[2];
		int counter = 0;

		while(counter < 10)
		{
			for(int row = 0; row < fieldSize; row += fieldSize-1)
			{
				for(int column = 1; column < fieldSize-1; column++)
				{
					if(cells[column][row].getToken() == null)
					{
						if(calcSetPhaseHelper(difficulty, column, row, counter))
						{
							field[0] = column;
							field[1] = row;
							return field;
						}
					}
				}
			}
			for(int column = 0; column < fieldSize; column += fieldSize-1)
			{
				for(int row = 1; row < fieldSize-1; row++)
				{
					if(cells[column][row].getToken() == null)
					{
						if(calcSetPhaseHelper(difficulty, column, row, counter))
						{
							field[0] = column;
							field[1] = row;
							return field;
						}
					}
				}
			}
			counter ++;
		}
		throw new IllegalArgumentException("Found no valid field!");
	}

	/**
	 * Helps the calcSetPhase method with calculations.
	 * @param difficulty - Diffictuly mode the GameAI is actual playing
	 * @param column to check
	 * @param row to check
	 * @param counter - to prevent problems with the randoms
	 * @return true if the move is good, false if not
	 */
	public boolean calcSetPhaseHelper(Difficulty difficulty, int column, int row, int counter)
	{
		Random random = new Random();
		cells = board.getCells();
		boolean randField = false;

		if(cells[column][row].getToken() == null)
		{
			if((column == 0 && (row == 1 || row == fieldSize-2)) || (column == fieldSize -1 && ((row == 1 || row == fieldSize -2))) ||
			   (row == 0 && (column == 1 || column == fieldSize-2)) || (row == fieldSize -1 && ((column == 1 || column == fieldSize -2))))
			{
				randField = true;
			}

			if(difficulty == Difficulty.EASY && (random.nextInt(10) == 0 || counter > 5))
				return true;
			else if(difficulty == Difficulty.MEDIUM && ((randField && random.nextInt(2) == 0) || random.nextInt(20) == 0 || counter > 5))
				return true;
			else if(difficulty == Difficulty.HARD && (randField || counter > 5))
				return true;
		}
		return false;
	}
}
