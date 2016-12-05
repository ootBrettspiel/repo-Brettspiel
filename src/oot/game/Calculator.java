package oot.game;

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
		innerFieldSize = cells[0].length - 2;
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
		     for (int collumn = 0; collumn < innerFieldSize; collumn++)
		     {
		    	 if(fieldStrength[row][collumn] != 0)
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

		// check every inner field
		for (int row = 0; row < innerFieldSize; row++){
		     for (int collumn = 0; collumn < innerFieldSize; collumn++)
		     {
		    	 if(cells[row][collumn].getToken() == null)
		    	 {
		    		 // get the number of captured fields with a move on the field
		    		 fieldStrength[row][collumn] = calcOneField(token, row, collumn);
		    	 }
		     }
		}
		return fieldStrength;
	}

	/**
	 * Calculates the strength of a possible move.
	 * @param row to calculate
	 * @param collumn to calculate
	 * @return number of capturable fields
	 */
	public int calcOneField(Token token, int row, int collumn)
	{
		if(cells[row][collumn].getToken() == Token.BLOCKED)
			return 0;
		int captureValue = 0;
		/*
		 * Checks how many fields can be captures for every direction.
		 */
		captureValue += calcOnePath(token, row-1, collumn-1, -1, -1); // Top, left
		captureValue += calcOnePath(token, row-1, collumn, -1, 0); // Top, middle
		captureValue += calcOnePath(token, row-1, collumn+1, -1, 1); // Top, right
		captureValue += calcOnePath(token, row, collumn-1, 0, -1); // Middle, left
		captureValue += calcOnePath(token, row, collumn+1, 0, 1); // Middle, right
		captureValue += calcOnePath(token, row+1, collumn-1, 1, -1); // Bottom, left
		captureValue += calcOnePath(token, row+1, collumn+1, 1, 1); // Bottom, right

		return captureValue;
	}

	/**
	 * Checks how many fields can be captures by a given direction of a given field.
	 * @param row to calculate
	 * @param collumn to calculate
	 * @param moveRow direction to search
	 * @param moveCollumn direction to search
	 * @return number of capturable fields for the direction
	 */
	public int calcOnePath(Token token, int row, int collumn, int moveRow, int moveCollumn)
	{
		cells = board.getCells();
		int counter = 0;

		// Case Player/Computer has Token.CROSS
		if(token == Token.CROSS)
		{
			// position to check must not leave the board!
			while(row >= 0 && row <= innerFieldSize + 2 && collumn >= 0 && collumn <= innerFieldSize + 2)
			{
				if(cells[row][collumn].getToken() == Token.CIRCLE)
				{
					counter++;
				}
				else if(cells[row][collumn].getToken() == Token.CROSS && counter == 0 || cells[row][collumn].getToken() == Token.BLOCKED)
				{
					return 0;
				}
				else if(cells[row][collumn].getToken() == Token.CROSS)
				{
					return counter;
				}
				row += moveRow;
				collumn += moveCollumn;
			}
		}
		// Case Player/Computer has Token.CIRCLE
		else
		{
			while(row >= 0 && row <= innerFieldSize + 2 && collumn >= 0 && collumn <= innerFieldSize + 2)
			{
				if(cells[row][collumn].getToken() == Token.CROSS)
				{
					counter++;
				}
				else if(cells[row][collumn].getToken() == Token.CIRCLE && counter == 0 || cells[row][collumn].getToken() == Token.BLOCKED ||
						cells[row][collumn].getToken() == null)
				{
					return 0;
				}
				else if(cells[row][collumn].getToken() == Token.CIRCLE)
				{
					return counter;
				}
				row += moveRow;
				collumn += moveCollumn;
			}
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


	// Getter

	public int getInnerFieldSize() {
		return innerFieldSize;
	}
}
