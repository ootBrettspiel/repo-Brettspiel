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
	private GameBoard board;

	private Player player_1;
	private Player player_2;

	public GameManager(GameBoard board, Player player_1, Player player_2)
	{
		this.board = board;
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
	 * Gives every player the opportunity to make a move and draws the game board.
	 * @return True if the game has ended.
	 */
	private boolean update()
	{
		// TODO: player turns, console dialogue
		player_1.makeTurn();
		// player_2.makeTurn();
		board.draw();

		// TODO: game shutdown logic
		return true;
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
