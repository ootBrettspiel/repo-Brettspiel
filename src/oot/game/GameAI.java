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
		for (int row = 0; row < calculator.getFieldSize(); row++){
		     for (int column = 0; column < calculator.getFieldSize(); column++)
		     {
		    	if(fieldStrength[row][column] > 0 && random == 0)
		    	{
		    		board.setToken(token, column, row);
		    		return;
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

		for (int row = 0; row < calculator.getFieldSize(); row++){
		     for (int column = 0; column < calculator.getFieldSize(); column++)
		     {
		    	 if(fieldStrength[row][column] > max)
		    	 {
		    		 bestMove[0] = row;
		    		 bestMove[1] = column;
		    	 }

		     }
		}
		board.setToken(token, bestMove[1], bestMove[0]);
		return;
	}

	/**
	 * Method to let the GameKI make a move on the hard mode.
	 */
	private void hardMode()
	{
		int max = 0;
		int[] bestMove = new int[2];
		int[][] fieldStrength = calculator.calcFields(this.getToken());
		int fieldSize = calculator.getFieldSize()+2;

		for (int row = 0; row < fieldSize; row++){
		     for (int column = 0; column < fieldSize; column++)
		     {
		    	 // Add field ratings to the possible moves.
		    	 if(fieldStrength[row][column] > 0) {
		    		 if(row == fieldSize -1 && column == fieldSize -2 || row == fieldSize -2 && column == fieldSize -1 ||
		    			row == -2 && column == 0 || row == fieldSize -1 && column == 1 ||
		    			row == 0 && column == fieldSize -2 || row == 1 && column == fieldSize -1 ||
		    			row == 0 && column == 1 || row == 1 && column == 0)
		    		 {
		    			fieldStrength[row][column] += 9;
		    		 }
		    		 else if(row == 0 || row == fieldSize -1 || column == 0 || column == fieldSize -1)
		    		 {
		    			 fieldStrength[row][column] += 7;
		    		 }
		    		 else if(row == fieldSize -2 && column == fieldSize -2 || row == 1 && column == fieldSize -2 ||
		    				 row == fieldSize -2 && column == 1 || row == 1 && column == 1)
		    		 {
		    			 fieldStrength[row][column] += 0;
		    		 }
		    		 else if(row == 1 || row == fieldSize -2 || column == 1 || column == fieldSize -2)
		    		 {
		    			 fieldStrength[row][column] += 2;
		    		 }
		    		 else
		    		 {
		    			 fieldStrength[row][column] += 4;
		    		 }
		    	 }
		     }
		}

		// Calculation to find the best move.
		for (int row = 0; row < calculator.getFieldSize(); row++){
		     for (int column = 0; column < calculator.getFieldSize(); column++)
		     {
		    	 if(fieldStrength[row][column] > max)
		    	 {
		    		 bestMove[0] = row;
		    		 bestMove[1] = column;
		    	 }

		     }
		}
		board.setToken(token, bestMove[1], bestMove[0]);
		return;
	}
}
