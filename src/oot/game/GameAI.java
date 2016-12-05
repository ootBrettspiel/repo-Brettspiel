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
	public GameAI(Token token, GameBoard board, Calculator calculator, Difficulty difficulty)
	{
		super("Computer", token, board, calculator);
		this.difficulty = difficulty;
	}

	/**
	 * Starts the matching AI method.
	 */
	@Override
	public void makeTurn()
	{
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
	private void easyMode()
	{
		int[][] fieldStrength = calculator.calcFields(this.getToken());

		int random = new Random().nextInt(calculator.calcPossibleMoves(this.getToken()));
		for (int row = 0; row < calculator.getInnerFieldSize(); row++){
		     for (int collumn = 0; collumn < calculator.getInnerFieldSize(); collumn++)
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
	private void normalMode()
	{
		int[][] fieldStrength = calculator.calcFields(this.getToken());
		int max = 0;
		int[] bestMove = new int[2];

		for (int row = 0; row < calculator.getInnerFieldSize(); row++){
		     for (int collumn = 0; collumn < calculator.getInnerFieldSize(); collumn++)
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
	private void hardMode()
	{
		//TODO: implement algorithm
	}
}
