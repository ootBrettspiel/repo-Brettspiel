package oot.game;

import java.awt.Point;
import java.util.LinkedList;

/**
 * This Class is used for every kind of game calculation, especially for the GameAI.
 * @author Christian Coenen
 *
 */
public class Calculator {

	GameBoard board;
	private Cell[][] cells;
	private int innerFieldSize;

	Calculator(GameBoard board)
	{
		this.board = board;
		cells = board.getCells();
		innerFieldSize = cells[0].length-2;
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
		    		 fieldStrength[column][row] = calcOneField(token, row, column);
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
	public int calcOneField(Token token, int row, int column)
	{
		if(row >= cells[1].length || column >= cells[0].length || row == 0 || column == 0 || cells[column][row].getToken() == Token.BLOCKED)
			return 0;

		int captureValue = 0;
		/*
		 * Checks how many fields can be captured for every direction.
		 */
		captureValue += calcOnePath(token, column-1, row-1, -1, -1); // Top, left
		captureValue += calcOnePath(token, column-1, row, -1, 0); // Top, middle
		captureValue += calcOnePath(token, column-1, row+1, -1, 1); // Top, right
		captureValue += calcOnePath(token, column, row-1, 0, -1); // Middle, left
		captureValue += calcOnePath(token, column, row+1, 0, 1); // Middle, right
		captureValue += calcOnePath(token, column+1, row-1, 1, -1); // Bottom, left
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
		while(row >= 0 && row < innerFieldSize && column >= 0 && column < innerFieldSize)
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
	public boolean isTheMovePossible(Token token, int row, int column)
	{
		return calcOneField(token, row, column) == 0 ? false : true;
	}

	/**
	 * Calculates all field indexes where a token will be reversed from setting a token at the given position.
	 * @param token The token that should be placed.
	 * @param column The x-index of the field.
	 * @param row The y-index of the field.
	 * @return An array of points that represent the coordinates of reversable tokens.
	 * @author Christopher Rotter
	 */
	public Point[] calculateReversedFields(Token token, int column, int row)
	{
		LinkedList<Point> pointList = new LinkedList<>();
		int directionX = -1;
		int directionY = -1;

		for (int i = 0; i < 8; i++)
		{
			for (int j = 1; j <= calcOnePath(token, column + directionX, row + directionY, directionX, directionY); j++)
			{
				pointList.add(new Point(column + directionX * j, row + directionY * j));
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

		return pointList.toArray(new Point[pointList.size()]);
	}

	// Getter

	public int getInnerFieldSize() {
		return innerFieldSize;
	}
}
