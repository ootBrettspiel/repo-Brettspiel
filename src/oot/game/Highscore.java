package oot.game;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Highscore {

	/**
	 * @author Nico Gensheimer
	 * @param
	 * @throws IOException
	 */

	//Pfad zum speichern des Highscroes
	private String path = "C:/highscore.txt";

	//Speichert name und highscore des übergebendem Spielers
	public void saveHighscore(Player player) throws IOException
	{
		FileOutputStream ioStream = new FileOutputStream(path);
		ObjectOutputStream objStream = new ObjectOutputStream(ioStream);
		objStream.writeObject(player.getName()+" "+player.getHighscore());
		objStream.close();
		ioStream.close();
	}

}
