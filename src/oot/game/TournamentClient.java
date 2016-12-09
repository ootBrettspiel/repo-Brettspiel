package oot.game;

/**
 *
 * @author Christopher Rotter
 *
 */
@SuppressWarnings("serial")
public class TournamentClient extends GameManager implements ITournament
{

	@Override
	public void initializeBoard(int size)
	{
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
		// TODO: Implement solution
		return 0;
	}

	@Override
	public boolean canIMove()
	{
		if (calculator.calcPossibleMoves(player_1.getToken()) > 0)
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
		if (calculator.calcPossibleMoves(player_2.getToken()) > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public String getBestTurn()
	{
		return ((GameAI) player_1).getTurn(phase).toString();
	}

	@Override
	public String[] setStone(String coordinate)
	{
		Coordinate position = player_1.getTurn(phase);

		Coordinate[] reversedCoords = calculator.calculateReversedFields(player_1.getToken(), position);
		String[] reversedStrings = new String[reversedCoords.length];

		for (int i = 0; i < reversedCoords.length; i++)
		{
			reversedStrings[i] = reversedCoords[i].toString();
		}

		board.setToken(player_1.getToken(), position, phase);

		return reversedStrings;
	}

	@Override
	public void setStoneInStartPhase(String coordinate)
	{
		setStone(coordinate);
	}

	@Override
	public boolean isMoveValid(String coordinate, boolean isWhite)
	{
		if (isWhite)
		{
			if (calculator.isMovePossible(player_1.getToken(), new Coordinate(coordinate), phase))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if (calculator.isMovePossible(player_2.getToken(), new Coordinate(coordinate), phase))
			{
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
		if (calculator.isMovePossible(player_1.getToken(), new Coordinate(coordinate), phase))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
