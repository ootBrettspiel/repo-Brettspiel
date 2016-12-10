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
				+ "/newgame -size -colorPlayer_1 {white, black} -AILevel(optional) {easy, medium, hard}\n/loadgame "
				+ "-path\n/exit\num ein neues Spiel zu starten, ein gespeichertes Spiel fortzusetzen, "
				+ "oder das Programm zu beenden.");

		Scanner scanner = new Scanner(System.in);

		while(true)
		{
			InputFormatter input = new InputFormatter(scanner);
			input.readInput();

			String command = input.getNext();

			if (command != null && command.equals("/newgame") && (input.remaining() == 2 || input.remaining() == 3))
			{
				try
				{
					GameBoard board = new GameBoard(Integer.parseInt(input.getNext()));
					Calculator calc = new Calculator(board);
					gameManager = new GameManager(board, calc);

					//TODO: remove circle dependency
					board.setCalculator(calc);
					String colorPlayer_1 = input.getNext();
					Difficulty difficulty = null;

					if (!input.isAtEnd())
					{
						difficulty = Difficulty.valueOf(input.getNext().toUpperCase());
					}

					Player player_1, player_2;

					if (colorPlayer_1.equals("white") || colorPlayer_1.equals("black"))
					{
						if (difficulty != null && colorPlayer_1.equals("white"))
						{
							player_1 = new HumanPlayer("Spieler 1", Token.CIRCLE, board, calc, gameManager);
							player_2 = new GameAI(Token.CROSS, board, calc, difficulty);
						}
						else if (difficulty != null)
						{
							player_1 = new GameAI(Token.CIRCLE, board, calc, difficulty);
							player_2 = new HumanPlayer("Spieler", Token.CROSS, board, calc, gameManager);
						}
						else if (colorPlayer_1.equals("white"))
						{
							player_1 = new HumanPlayer("Spieler 1", Token.CIRCLE, board, calc, gameManager);
							player_2 = new HumanPlayer("Spieler 2", Token.CROSS, board, calc, gameManager);
						}
						else
						{
							player_1 = new HumanPlayer("Spieler 1", Token.CROSS, board, calc, gameManager);
							player_2 = new HumanPlayer("Spieler 2", Token.CIRCLE, board, calc, gameManager);
						}
					}
					else
					{
						throw new IllegalArgumentException();
					}

					gameManager.setPlayer(player_1, 1);
					gameManager.setPlayer(player_2, 2);
					break;
				}
				catch (IllegalArgumentException e)
				{
					System.out.println("Die eingegebene Spielfeldgröße ist unzulässig."
							+ "Bitte geben Sie eine gerade Zahl von 6 bis 10 an.");

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