package oot.game;

/**
 *
 * @author Christopher Rotter
 *
 */
@SuppressWarnings("serial")
public class TournamentClient extends GameManager implements ITournament
{
	private Token token;
	private int counter;
	private int fieldSize;

	public TournamentClient(Token token) {
		this.token = token;
	}

	@Override
	public void initializeBoard(int size)
	{
		fieldSize = size;
		board = new GameBoard(size);
	}

	@Override
	public void printBoard()
	{
		board.draw();
	}

	@Override
	public void startGame(boolean isWhitePlayer)
	{
		board = new GameBoard(fieldSize);
		counter = 0;
		resetPhase();
		calculator = new Calculator(board);
		// TODO: remove circle dependency
		board.setCalculator(calculator);

		Token token;

		if (isWhitePlayer)
		{
			token = Token.CIRCLE;
		}
		else
		{
			token = Token.CROSS;
		}

		player_1 = new GameAI(token, board, calculator, Difficulty.HARD);
	}

	@Override
	public int whoHasWon()
	{
		int cross = 0;
		int circle = 0;
		Cell[][] cells = board.getCells();

		for(int i = 0; i < fieldSize; i++) {
			for(int j = 0; j < fieldSize; j++) {
				if(cells[i][j].getToken() == Token.CIRCLE) {
					circle++;
				}
				else if(cells[i][j].getToken() == Token.CROSS) {
					cross ++;
				}
			}
		}

		if(token == Token.CIRCLE && circle > cross) {
			return 1;
		}
		else if(token == Token.CIRCLE && circle == cross) {
			return 0;
		}
		else if(token == Token.CIRCLE && circle < cross) {
			return -1;
		}

		else if(token == Token.CROSS && circle > cross) {
			return -1;
		}
		else if(token == Token.CROSS && circle == cross) {
			return 0;
		}
		else if(token == Token.CROSS && circle < cross) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean canIMove()
	{
		if (calculator.calcPossibleMoves(token) > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean canYouMove()
	{
		if(token == Token.CROSS) {
			if (calculator.calcPossibleMoves(Token.CIRCLE) > 0)
			{
			return true;
			}
		}
		else {
			if (calculator.calcPossibleMoves(Token.CROSS) > 0)
			{
			return true;
			}
		}
		return false;
	}

	private void checkPhase() {
		counter++;
		if(counter >= fieldSize+1) {
			phase = GamePhase.REGULAR;
		}
	}

	private void resetPhase() {
		phase = GamePhase.SET;
	}


	@Override
	public String getBestTurn()
	{
		checkPhase();
		if(calculator.calcPossibleMoves(token) == 0) {
			return "";
		}
		return ((GameAI) player_1).getTurn(phase).toString();
	}

	@Override
	public String[] setStone(String coordinate)
	{
		// Does not work!
//		Coordinate position = player_1.getTurn(phase);
//
//		Coordinate[] reversedCoords = calculator.calculateReversedFields(player_1.getToken(), position);
//		String[] reversedStrings = new String[reversedCoords.length];
//
//		for (int i = 0; i < reversedCoords.length; i++)
//		{
//			reversedStrings[i] = reversedCoords[i].toString();
//		}

		// no function. (not used)
		String[] s = new String[2];

		Coordinate position = new Coordinate(coordinate);
		board.setToken(token, position, phase);

		// no function. (not used)
		return s;
	}

	@Override
	public void setStoneInStartPhase(String coordinate)
	{
		setStone(coordinate);
	}

	@Override
	public boolean isMoveValid(String coordinate, boolean isWhite)
	{
		checkPhase();
		Coordinate position = new Coordinate(coordinate);
		if (token == Token.CIRCLE)
		{
			if (calculator.isMovePossible(Token.CROSS, new Coordinate(coordinate), phase))
			{
				board.setToken(Token.CROSS, position, phase);
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if (calculator.isMovePossible(Token.CIRCLE, new Coordinate(coordinate), phase))
			{
				board.setToken(Token.CIRCLE, position, phase);
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	@Override
	public boolean isMoveValidInStartPhase(String coordinate)
	{
		return isMoveValid(coordinate, true);
	}

}
