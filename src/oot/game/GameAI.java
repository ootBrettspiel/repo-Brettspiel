package oot.game;

import java.util.Random;

/**
 * Represents the GameKI, which plays automatically.
 * @author Christian Coenen
 *
 */
public class GameAI extends Player
{
	// Difficulty of the GameAI. (easy, normal, hard)
	private Difficulty difficulty;

	/**
	 * Constructor to create a GameAI object.
	 */
	public GameAI(Token token, Difficulty difficulty)
	{
		super("Computer", token);
		this.difficulty = difficulty;;
	}

	/**
	 * Starts the matching AI method.
	 * @throws NoSuchFieldException
	 */
	public void makeTurn(Calculator c) throws NoSuchFieldException
	{
		if(difficulty == Difficulty.EASY)
			easyMode(c);
		else if(difficulty == Difficulty.MEDIUM)
			normalMode(c);
		else if(difficulty == Difficulty.HARD)
			hardMode(c);
	}

	/**
	 * Method to let the GameKI make a move on the easy mode.
	 */
	private void easyMode(Calculator c) throws NoSuchFieldException
	{
		int[][] fieldStrength = c.calcFields(this.getToken());
		if(c.getPossibleMoves() == 0)
			throw new NoSuchFieldException();

		int random = new Random().nextInt(c.getPossibleMoves());
		for (int row = 0; row < c.getInnerFieldSize(); row++){
		     for (int collumn = 0; collumn < c.getInnerFieldSize(); collumn++)
		     {
		    	if(fieldStrength[row][collumn] > 0 && random == 0)
		    	{
		    		// TODO: Make a move with actual row and collumn!
		    	}
		     }
		}
	}

	/**
	 * Method to let the GameKI make a move on the normal mode.
	 */
	private void normalMode(Calculator c) throws NoSuchFieldException
	{
		int[][] fieldStrength = c.calcFields(this.getToken());
		int max = 0;
		int[] bestMove = new int[2];
		if(c.getPossibleMoves() == 0)
			throw new NoSuchFieldException();

		for (int row = 0; row < c.getInnerFieldSize(); row++){
		     for (int collumn = 0; collumn < c.getInnerFieldSize(); collumn++)
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
	private void hardMode(Calculator c) throws NoSuchFieldException
	{
		//TODO: implement algorithm
	}
}
