package oot.game;

import java.io.IOException;

/**
 * The program class contains the main method and is responsible for starting the game.
 * @author Christopher Rotter
 *
 */
public class Program
{
	private static GameManager gameManager;

	/**
	 * Sets up the game and calls the update method of GameManager.
	 * @param args	Not used.
	 */
	public static void main(String[] args)
	{
		InputPrompt prompt = new InputPrompt("Willkommen bei OOThello!\nNutzen Sie die Kommandos\n"
				+ "/newgame -size\n/loadgame -path\n/exit\num ein neues Spiel zu starten, "
				+ "ein gespeichertes Spiel fortzusetzen, oder das Programm zu beenden.");

		while(true)
		{
			prompt.show();
			String command = prompt.getNext();

			if (command.equals("/newgame") && !prompt.isAtEnd())
			{
				try
				{
					gameManager = new GameManager(new GameBoard(Integer.parseInt(prompt.getNext())));
					break;
				}
				catch (IllegalArgumentException e)
				{
					System.out.println("Die eingegebene Spielfeldgröße ist unzulässig."
							+ "Bitte geben Sie eine gerade Zahl zwischen 6 und 16 an.");

					continue;
				}
			}
			else if (command.equals("/loadgame") && !prompt.isAtEnd())
			{
				try
				{
					gameManager = GameManager.load(prompt.getNext(), prompt.getNext());
					break;
				}
				catch (IOException e)
				{
					System.out.println("Die angegebene Datei konnte nicht gefunden werden."
							+ "Bitte überprüfen Sie den Dateipfad und versuchen Sie es erneut.");

					continue;
				}
			}
			else if (command.equals("/exit"))
			{
				System.out.println("Das Programm wurde beendet.");
				System.exit(0);
			}
			else
			{
				System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut:");
			}
		}

		while (true)
		{
			if (gameManager.update())
			{
				break;
			}
		}
	}
}