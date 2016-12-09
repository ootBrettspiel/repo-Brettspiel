package oot.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Christopher Rotter
 *
 */
@SuppressWarnings("serial")
public class GameManager implements Serializable
{
	protected GameBoard board;
	protected Calculator calculator;

	protected Player player_1;
	protected Player player_2;

	protected GamePhase phase = GamePhase.SET;
	protected PlayerTurn turn = PlayerTurn.TURNPLAYER_1;

	protected int turnCounter = 0;

	public GameManager()
	{

	}

	public GameManager(GameBoard board, Calculator calculator, Player player_1, Player player_2)
	{
		this.board = board;
		this.calculator = calculator;
		this.player_1 = player_1;
		this.player_2 = player_2;
	}

	public boolean start()
	{
		board.draw();

		while (true)
		{
			if (update())
			{
				return true;
			}
		}
	}

	/**
	 * Gives the player whose turn it is the opportunity to make a move and draws the game board, then checks if the game has ended.
	 * @return True if the game has ended.
	 */
	protected boolean update()
	{
		Coordinate position;

		switch (turn)
		{
		case TURNPLAYER_1:
			position = player_1.getTurn(phase);
			if (position != null)
			{
				board.setToken(player_1.getToken(), position, phase);
			}
			turn = PlayerTurn.TURNPLAYER_2;
			break;
		case TURNPLAYER_2:
			position = player_2.getTurn(phase);
			if (position != null)
			{
				board.setToken(player_2.getToken(), position, phase);
			}
			turn = PlayerTurn.TURNPLAYER_1;
		}

		turnCounter++;

		if (phase == GamePhase.SET && turnCounter >= board.getCells().length)
		{
			phase = GamePhase.REGULAR;
		}

		board.draw();

		if (calculator.calcPossibleMoves(player_1.getToken()) == 0 && calculator.calcPossibleMoves(player_2.getToken()) == 0)
		{
			// TODO: save highscore, declare winner etc.
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Saves the game to file.
	 * @param path The path where the file will be saved, including file name.
	 * @throws IOException Thrown when the file path could not be found or accessed.
	 */
	public void save(String path) throws IOException
	{
		FileOutputStream ioStream = new FileOutputStream(path);
		ObjectOutputStream objStream = new ObjectOutputStream(ioStream);
		objStream.writeObject(this);
		objStream.close();
		ioStream.close();
	}

	/**
	 * Loads a game from file.
	 * @param path The path and name of the saved file.
	 * @return The loaded game.
	 * @throws IOException Thrown when the file path could not be found or accessed.
	 */
	public static GameManager load(String path) throws IOException
	{
		FileInputStream ioStream = new FileInputStream(path);
		ObjectInputStream objStream = new ObjectInputStream(ioStream);
		GameManager manager;

		try
		{
			manager = (GameManager) objStream.readObject();
		}
		catch (ClassNotFoundException e)
		{
			throw new IOException("Invalid file.");
		}
		finally
		{
			objStream.close();
			ioStream.close();
		}

		return manager;
	}
}
