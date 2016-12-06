package oot.game;

/**
 * This Class is used for every kind of game calculation, especially for the GameAI.
 * @author Christian Coenen
 *
 */
public class Calculator {

	GameBoard board;
	private Cell[][] cells;
	private int fieldSize;

	Calculator(GameBoard board)
	{
		this.board = board;
		cells = board.getCells();
		fieldSize = cells[0].length;
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
		for (int row = 0; row < fieldSize; row++){
		     for (int column = 0; column < fieldSize; column++)
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
		int[][] fieldStrength = new int[fieldSize][fieldSize];

		// check every field
		for (int row = 0; row < fieldSize; row++){
		     for (int column = 0; column < fieldSize; column++)
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
		if(cells[column][row].getToken() == Token.BLOCKED)
			return 0;

		int captureValue = 0;
		/*
		 * Checks how many fields can be captured for every direction.
		 */
		captureValue += calcOnePath(token, row-1, column-1, -1, -1); // Top, left
		captureValue += calcOnePath(token, row-1, column, -1, 0); // Top, middle
		captureValue += calcOnePath(token, row-1, column+1, -1, 1); // Top, right
		captureValue += calcOnePath(token, row, column-1, 0, -1); // Middle, left
		captureValue += calcOnePath(token, row, column+1, 0, 1); // Middle, right
		captureValue += calcOnePath(token, row+1, column-1, 1, -1); // Bottom, left
		captureValue += calcOnePath(token, row+1, column+1, 1, 1); // Bottom, right

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
	public int calcOnePath(Token token, int row, int column, int moveRow, int moveColumn)
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
	public boolean isTheMovePossible(Token token, int row, int column)
	{
		return calcOneField(token, row, column) == 0 ? false : true;
	}


	// Getter

	public int getFieldSize() {
		return fieldSize;
	}
}
