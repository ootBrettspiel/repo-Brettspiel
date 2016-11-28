package oot.game;

import java.util.Random;

/**
 * Represents the GameKI, which plays automatically.
 * @author Christian Coenen
 *
 */
public class GameAI
{
	 // Value of possible moves - changes every move.
	private int possibleMoves;

	// Size of the inner field.
	private int innerFieldSize;

	// Difficulty of the GameAI. (easy, normal, hard)
	private Difficulty difficulty;

	// The GameBoard object.
	private GameBoard gameBoard;

	// Array which stores the value of every field
	private Cell[][] board;

	/**
	 * Constructor to create a GameAI object.
	 */
	public GameAI(Difficulty difficulty, GameBoard gameBoard)
	{
		this.difficulty = difficulty;
		this.gameBoard = gameBoard;
	}

	/**
	 * Starts the matching AI method.
	 * @throws NoSuchFieldException if the GameKI can´t make a move.
	 */
	public void makeTurn() throws NoSuchFieldException
	{
		board = gameBoard.getCells();

		if(difficulty == Difficulty.EASY)
			easyMode();
		else if(difficulty == Difficulty.MEDIUM)
			normalMode();
		else if(difficulty == Difficulty.HARD)
			hardMode();
	}

	/**
	 * Method to let the GameKI make a move on the easy mode.
	 */
	private void easyMode() throws NoSuchFieldException
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
	 */
	private void normalMode() throws NoSuchFieldException
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
	 */
	private void hardMode() throws NoSuchFieldException
	{
		//TODO: implement algorithm
	}


	/**
	 * Calculates the strength of every possible move.
	 * @return an array with the number of capturable fields of every field.
	 */
	private int[][] calcFields(Cell[][] board)
	{
		innerFieldSize = board[0].length-2;
		int[][] fieldStrength = new int[innerFieldSize][innerFieldSize];
		possibleMoves = 0;

		// check every inner field
		for (int row = 0; row < innerFieldSize; row++){
		     for (int collumn = 0; collumn < innerFieldSize; collumn++)
		     {
		    	 if(board[row][collumn].getToken() == Token.CROSS)
		    	 {
		    		 // get the number of grabbed fields with a move on the field
		    		 fieldStrength[row][collumn] = calcOneField(row, collumn);
		    		 if(fieldStrength[row][collumn] != 0)
		    			 possibleMoves++;
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
	private int calcOneField(int row, int collumn)
	{
		int captureValue = 0;
		/*
		 * Checks how many fields can be captures for every direction.
		 */
		captureValue += calcOnePath(row-1, collumn-1, -1, -1); // Top, left
		captureValue += calcOnePath(row-1, collumn, -1, 0); // Top, middle
		captureValue += calcOnePath(row-1, collumn+1, -1, 1); // Top, right
		captureValue += calcOnePath(row, collumn-1, 0, -1); // Middle, left
		captureValue += calcOnePath(row, collumn+1, 0, 1); // Middle, right
		captureValue += calcOnePath(row+1, collumn-1, 1, -1); // Bottom, left
		captureValue += calcOnePath(row+1, collumn+1, 1, 1); // Bottom, right

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
	private int calcOnePath(int row, int collumn, int moveRow, int moveCollumn)
	{
		int counter = 0;
		// checking position must not leave the board!
		while(row >= 0 && row <= innerFieldSize + 2 && collumn >= 0 && collumn <= innerFieldSize + 2)
		{
			if(board[row][collumn].getToken() == Token.CIRCLE)
			{
				counter++;
			}
			else if(board[row][collumn].getToken() == Token.CROSS && counter == 0 || board[row][collumn].getToken() == Token.BLOCKED)
			{
				return 0;
			}
			else if(board[row][collumn].getToken() == Token.CROSS)
			{
				return counter;
			}
			row += moveRow;
			collumn += moveCollumn;
		}
		return 0;
	}
}
