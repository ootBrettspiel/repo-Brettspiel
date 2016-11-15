package oot.game;

import java.util.Scanner;

/**
 * The program class contains the main method and is responsible for starting the game.
 * @author Christopher Rotter
 *
 */
public class Program
{
	/**
	 * Sets up the game.
	 * @param args	Not used.
	 */
	public static void main(String[] args)
	{
		System.out.println("Willkommen bei *game*!\nNutzen Sie die Kommandos\n"
				+ "/newgame\n/loadgame -path\n/exit\num ein neues Spiel zu starten, "
				+ "ein gespeichertes Spiel fortzusetzen, oder das Programm zu beenden.");

		Scanner consoleInput = new Scanner(System.in);

		while(true)
		{
			String input = consoleInput.nextLine();

			if (input.equals("/newgame"))
			{
				// TODO: Start new game
			}
			else if (input.split(" ")[0].equals("/loadgame"))
			{
				String path = input.split(" ")[1];
				//TODO: Load game
			}
			else if (input.equals("/exit"))
			{
				System.out.println("Das Programm wurde beendet.");
				System.exit(0);
			}
			else
			{
				System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut:");
			}
		}
	}
}