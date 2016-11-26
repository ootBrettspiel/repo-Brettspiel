package oot.game;

import java.util.Random;

/**
 * Represents the GameKI, which plays automatically.
 * @author Christian Coenen
 *
 */
public class GameKI
{
	/*
	 * Hidden class variable to create only one object.
	 */
	private static GameKI instance;

	/*
	 * Value of possible moves - changes every move.
	 */
	private int possibleMoves;

	/*
	 * Size of the inner field.
	 */
	private int innerFieldSize;

	/**
	 * Private constructor, because GameKI is a singleton.
	 */
	private GameKI(){ }

	/**
	 * Method to create only one GameKI object in lifetime.
	 * @return the GameKI objekt.
	 */
	public static synchronized GameKI getInstance()
	{
		if(GameKI.instance == null)
			GameKI.instance = new GameKI();

		return GameKI.instance;
	}

	/**
	 * Method to let the GameKI make a move on the easy mode.
	 * @param board to check the actual board situation.
	 */
	public void easyMode(Cell[][] board) throws NoSuchFieldException
	{
		int[][] fieldStrength = calcFields(board);
		if(possibleMoves == 0)
			throw new NoSuchFieldException();

		int rand = new Random().nextInt(possibleMoves);
		for (int row = 0; row < innerFieldSize; row++){
		     for (int collumn = 0; collumn < innerFieldSize; collumn++)
		     {
		    	if(fieldStrength[row][collumn] > 0 && rand == 0)
		    	{
		    		// TODO: Make a move with actual row and collumn!
		    	}
		     }
		}
	}

	/**
	 * Method to let the GameKI make a move on the normal mode.
	 * @param board to check the actual board situation.
	 */
	public void normalMode(Cell[][] board) throws NoSuchFieldException
	{
		int[][] fieldStrength = calcFields(board);
		int max = 0;
		int[] bestMove = new int[2];
		if(possibleMoves == 0)
			throw new NoSuchFieldException();

		for (int row = 0; row < innerFieldSize; row++){
		     for (int collumn = 0; collumn < innerFieldSize; collumn++)
		     {
		    	 if(fieldStrength[row][collumn] > max)
		    	 {
		    		 bestMove[0] = row;
		    		 bestMove[1] = collumn;
		    	 }

		     }
		}
		// TODO: Make a move with on position bestMove[0] (Row) and bestMove[1] (Collumn)!
	}

	/**
	 * Method to let the GameKI make a move on the hard mode.
	 * @param board to check the actual board situation.
	 */
	public void hardMode(Cell[][] board) throws NoSuchFieldException
	{
		//TODO: implement algorithm
	}


	/**
	 * Calculates the strength of every possible move.
	 * @param board to check the actual board situation.
	 * @return an array with the number of capturable fields of every field.
	 */
	public int[][] calcFields(Cell[][] board)
	{
		innerFieldSize = board[0].length-2;
		int[][] fieldStrength = new int[innerFieldSize][innerFieldSize];
		possibleMoves = 0;

		// check every inner field
		for (int row = 0; row < innerFieldSize; row++){
		     for (int collumn = 0; collumn < innerFieldSize; collumn++)
		     {
		    	 if(board[row][collumn].getToken() == Token.Cross)
		    	 {
		    		 // get the number of grabbed fields with a move on the field
		    		 fieldStrength[row][collumn] = calcOneField(board, row, collumn);
		    		 if(fieldStrength[row][collumn] != 0)
		    			 possibleMoves++;
		    	 }
		     }
		}
		return fieldStrength;
	}

	/**
	 * Calculates the strength of a possible move.
	 * @param board to check the actual board situation.
	 * @param row to calculate
	 * @param collumn to calculate
	 * @return number of capturable fields
	 */
	public int calcOneField(Cell[][] board, int row, int collumn)
	{
		int captureValue = 0;
		/*
		 * Checks how many fields can be captures for every direction.
		 */
		captureValue += calcOnePath(board, row-1, collumn-1, -1, -1); // Top, left
		captureValue += calcOnePath(board, row-1, collumn, -1, 0); // Top, middle
		captureValue += calcOnePath(board, row-1, collumn+1, -1, 1); // Top, right
		captureValue += calcOnePath(board, row, collumn-1, 0, -1); // Middle, left
		captureValue += calcOnePath(board, row, collumn+1, 0, 1); // Middle, right
		captureValue += calcOnePath(board, row+1, collumn-1, 1, -1); // Bottom, left
		captureValue += calcOnePath(board, row+1, collumn+1, 1, 1); // Bottom, right

		return captureValue;
	}

	/**
	 * Checks how many fields can be captures by a given direction of a given field.
	 * @param board to check the actual board situation.
	 * @param row to calculate
	 * @param collumn to calculate
	 * @param moveRow direction to search
	 * @param moveCollumn direction to search
	 * @return number of capturable fields for the direction
	 */
	public int calcOnePath(Cell[][] board, int row, int collumn, int moveRow, int moveCollumn)
	{
		int counter = 0;
		// checking position must not leave the board!
		while(row >= 0 && row <= innerFieldSize + 2 && collumn >= 0 && collumn <= innerFieldSize + 2)
		{
			if(board[row][collumn].getToken() == Token.Circle)
			{
				counter++;
			}
			else if(board[row][collumn].getToken() == Token.Cross && counter == 0 || board[row][collumn].getToken() == Token.Blocked)
			{
				return 0;
			}
			else if(board[row][collumn].getToken() == Token.Cross)
			{
				return counter;
			}
			row += moveRow;
			collumn += moveCollumn;
		}
		return 0;
	}
}
