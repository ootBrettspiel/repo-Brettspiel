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
	 * @return the coordinate, where the AI wants to place a stone
	 */
	@Override
	public Coordinate getTurn(GamePhase phase)
	{
		if(difficulty == Difficulty.EASY)
		{
			return easyMode(phase);
		}
		else if(difficulty == Difficulty.MEDIUM)
		{
			return normalMode(phase);
		}
		else if(difficulty == Difficulty.HARD)
		{
			return hardMode(phase);
		}
		throw new IllegalArgumentException("Unable to make a turn!");
	}

	/**
	 * Method to let the GameKI make a move on the easy mode.
	 */
	private Coordinate easyMode(GamePhase phase)
	{
		if(phase == GamePhase.SET)
		{
			int[] field = calculator.calcSetPhase(difficulty);
			return new Coordinate(field[0], field[1]);
		}
		else if(phase == GamePhase.REGULAR)
		{
			int[][] fieldStrength = calculator.calcFields(this.getToken());
			int counter = 0;
			Random random = new Random();
			while(counter < 10)
			{
				for (int row = 0; row < calculator.getInnerFieldSize(); row++){
				     for (int column = 0; column < calculator.getInnerFieldSize(); column++)
				     {
				    	if(fieldStrength[column][row] > 0 && (random.nextInt(20) == 0 || counter > 5))
				    	{
				    		return new Coordinate(column, row);
				    	}
				     }
				}
			}
		}
		throw new IllegalArgumentException("Found no possible turn!");
	}

	/**
	 * Method to let the GameKI make a move on the normal mode.
	 */
	private Coordinate normalMode(GamePhase phase)
	{
		if(phase == GamePhase.SET)
		{
			int[] field = calculator.calcSetPhase(difficulty);
			return new Coordinate(field[0], field[1]);
		}
		else if(phase == GamePhase.REGULAR)
		{
			int[][] fieldStrength = calculator.calcFields(this.getToken());
			int max = 0;
			int[] bestMove = new int[2];

			for (int row = 0; row < calculator.getInnerFieldSize(); row++){
			     for (int column = 0; column < calculator.getInnerFieldSize(); column++)
			     {
			    	 if(fieldStrength[column][row] > max)
			    	 {
			    		 bestMove[0] = column;
			    		 bestMove[1] = row;
			    	 }

			     }
			}
			return new Coordinate(bestMove[0], bestMove[1]);
		}
		throw new IllegalArgumentException("Found no possible turn!");
	}

	/**
	 * Method to let the GameKI make a move on the hard mode.
	 */
	private Coordinate hardMode(GamePhase phase)
	{

		if(phase == GamePhase.SET)
		{
			int[] field = calculator.calcSetPhase(difficulty);
			return new Coordinate(field[0], field[1]);
		}
		else if(phase == GamePhase.REGULAR)
		{
			int max = 0;
			int[] bestMove = new int[2];
			int[][] fieldStrength = calculator.calcFields(this.getToken());
			int fieldSize = calculator.getInnerFieldSize()+1;

			for (int row = 0; row < calculator.getInnerFieldSize(); row++){
			     for (int column = 0; column < calculator.getInnerFieldSize(); column++)
			     {
			    	 // Add field ratings to the possible moves.
			    	 if(fieldStrength[column][row] > 0) {
			    		 if(row == fieldSize -1 && column == fieldSize -1 || row == fieldSize -1 && column == 1 ||
			    			row == 1 && column == fieldSize -1 || row == 1 && column == 1 ||
			    			row == fieldSize -1 && column == fieldSize -2 || row == fieldSize -2 && column == fieldSize -1 ||
			    			row == -2 && column == 0 || row == fieldSize -1 && column == 1 ||
			    			row == 0 && column == fieldSize -2 || row == 1 && column == fieldSize -1 ||
			    			row == 0 && column == 1 || row == 1 && column == 0)
			    		 {
			    			fieldStrength[row][column] += 9;
			    		 }
			    		 else if(row == 1 || row == fieldSize -1 || column == 1 || column == fieldSize -1)
			    		 {
			    			 fieldStrength[row][column] += 7;
			    		 }
			    		 else if(row == fieldSize -2 && column == fieldSize -2 || row == 2 && column == fieldSize -2 ||
			    				 row == fieldSize -2 && column == 2 || row == 2 && column == 2)
			    		 {
			    			 fieldStrength[row][column] += 0;
			    		 }
			    		 else if(row == 2 || row == fieldSize -2 || column == 2 || column == fieldSize -2)
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
			for (int row = 0; row < calculator.getInnerFieldSize(); row++){
			     for (int column = 0; column < calculator.getInnerFieldSize(); column++)
			     {
			    	 if(fieldStrength[column][row] > max)
			    	 {
			    		 bestMove[0] = column;
			    		 bestMove[1] = row;
			    	 }

			     }
			}

			return new Coordinate(bestMove[0], bestMove[1]);
		}
		throw new IllegalArgumentException("Found no possible turn!");
	}
}
