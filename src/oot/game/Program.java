package oot.game;

import java.io.IOException;
import java.util.Scanner;

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
		System.out.println("Willkommen bei OOThello!\nNutzen Sie die Kommandos\n"
				+ "/newgame -size\n/loadgame -path\n/exit\num ein neues Spiel zu starten, "
				+ "ein gespeichertes Spiel fortzusetzen, oder das Programm zu beenden.");

		Scanner scanner = new Scanner(System.in);

		while(true)
		{
			InputFormatter input = new InputFormatter(scanner);
			input.readInput();

			String command = input.getNext();

			if (command != null && command.equals("/newgame") && !input.isAtEnd())
			{
				try
				{
					GameBoard board = new GameBoard(Integer.parseInt(input.getNext()));
					Calculator calc = new Calculator(board);
					//TODO: remove circle dependency
					board.setCalculator(calc);
					gameManager = new GameManager(board, new HumanPlayer("test", Token.CIRCLE, board, calc), new HumanPlayer("test2", Token.CROSS, board, calc));
					break;
				}
				catch (IllegalArgumentException e)
				{
					System.out.println("Die eingegebene Spielfeldgröße ist unzulässig."
							+ "Bitte geben Sie eine gerade Zahl von 6 bis 16 an.");

					continue;
				}
			}
			else if (command != null && command.equals("/loadgame") && !input.isAtEnd())
			{
				try
				{
					gameManager = GameManager.load(input.getNext());
					break;
				}
				catch (IOException e)
				{
					System.out.println("Die angegebene Datei konnte nicht gefunden werden."
							+ "Bitte überprüfen Sie den Dateipfad und versuchen Sie es erneut.");

					continue;
				}
			}
			else if (command != null && command.equals("/exit"))
			{
				System.out.println("Das Programm wurde beendet.");
				System.exit(0);
			}
			else
			{
				System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut:");
				continue;
			}
		}

		gameManager.start();
	}
}