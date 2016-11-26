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

	// TODO: wait for player class to be committed
	// private Player player_1;
	// private Player player_2;

	// TODO: wait for player class to be committed
	public GameManager(GameBoard board/*, Player player_1, Player player_2*/)
	{
		this.board = board;
	}

	/**
	 * Gives every player the opportunity to make a move and draws the game board.
	 * @return True if the game has ended.
	 */
	public boolean update()
	{
		// TODO: player turns, console dialogue

		board.draw();

		// TODO: game shutdown logic
		return true;
	}

	/**
	 * Saves the game to file.
	 * @param path The path where the file should be saved.
	 * @param fileName The name of the file (excluding file extension).
	 * @throws IOException Thrown when the file path could not be found or accessed.
	 */
	public void save(String path, String fileName) throws IOException
	{
		FileOutputStream ioStream = new FileOutputStream(path + "/" + fileName + ".dat");
		ObjectOutputStream objStream = new ObjectOutputStream(ioStream);
		objStream.writeObject(this);
		objStream.close();
		ioStream.close();
	}

	/**
	 * Loads a game from file.
	 * @param path The path where the file is saved.
	 * @param fileName The name of the file (excluding file extension).
	 * @return The loaded game.
	 * @throws IOException Thrown when the file path could not be found or accessed.
	 */
	public static GameManager load(String path, String fileName) throws IOException
	{
		FileInputStream ioStream = new FileInputStream(path + "/" + fileName + ".dat");
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
