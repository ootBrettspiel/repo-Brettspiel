package oot.game;

/**
 * A class that implements this interface can play the game in tournaments.
 * @author Christopher Rotter
 *
 */
public interface ITournament
{
	/**
	 * Creates the game board and places the initial Tokens in the center.
	 * @param size The size of the board.
	 */
	public void initializeBoard(int size);

	/**
	 * Draws the board on the console.
	 */
	public void printBoard();

	/**
	 * Starts the game and tells it whether it is player white or not.
	 * @param isWhitePlayer Determines if this instance plays with white or not.
	 */
	public void startGame(boolean isWhitePlayer);

	/**
	 * Returns an integer that represents the game result.
	 * @return -1 if the enemy has won, 0 at a draw, 1 if this instance has won.
	 */
	public int whoHasWon();

	/**
	 * Determines whether or not this instance can still make a move.
	 * @return True if it can, false if it can't.
	 */
	public boolean canIMove();

	/**
	 * Determines whether or not the enemy can still make a move.
	 * @return True if it can, false if it can't.
	 */
	public boolean canYouMove();

	/**
	 * Calculates the best possible turn for this instance.
	 * @return A string representing the move, for example: "A2".
	 */
	public String getBestTurn();

	/**
	 * Puts a token on the board and returns the coordinates of tokens that were reversed by that move.
	 * @param coordinate The coordinate of the move, for example: "A2".
	 * @return An array of strings representing the coordinates of all tokens that were reversed.
	 */
	public String[] setStone(String coordinate);

	/**
	 * Works like setStone, but without token reversion.
	 * @param coordinate The coordinate of the move, for example: "A2".
	 * @see setStone.
	 */
	public void setStoneInStartPhase(String coordinate);

	/**
	 * Determines if a move at the given position would be possible.
	 * @param coordinate The coordinate of the move, for example: "A2".
	 * @param isWhite Determines if the move is from player white or not.
	 * @return True if the move is valid, false if not.
	 */
	public boolean isMoveValid(String coordinate, boolean isWhite);

	/**
	 * Works like isMoveValid, but for the start phase.
	 * @see isMoveValid
	 * @param coordinate The coordinate of the move, for example: "A2".
	 * @return True if the move is valid, false if not.
	 */
	public boolean isMoveValidInStartPhase(String coordinate);
}
