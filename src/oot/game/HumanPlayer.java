package oot.game;

import java.util.Scanner;

/**
 *
 * @author Christopher Rotter
 *
 */
public class HumanPlayer extends Player
{
	HumanPlayer(String name, Token token, GameBoard board, Calculator calculator)
	{
		super(name, token, board, calculator);
	}

	@Override
	public void makeTurn()
	{
		System.out.println("Spieler " + name + " ist am Zug:");

		Scanner scanner = new Scanner(System.in);
		int row, column;

		while (true)
		{
			InputFormatter input = new InputFormatter(scanner);
			input.readInput();

			if (!input.isAtEnd())
			{
				String s = input.getNext();

				if (s.length() > 1)
				{
					System.out.println("Fehlerhafte eingabe. Bitte wiederholen:");
					continue;
				}

				column = s.toCharArray()[0] - 65;

				if (!input.isAtEnd())
				{
					try
					{
						row = Integer.parseInt(input.getNext())-1;
					}
					catch (NumberFormatException e)
					{
						System.out.println("Fehlerhafte eingabe. Bitte wiederholen:");
						continue;
					}
				}
				else
				{
					System.out.println("Fehlerhafte eingabe. Bitte wiederholen:");
					continue;
				}
			}
			else
			{
				System.out.println("Fehlerhafte eingabe. Bitte wiederholen:");
				continue;
			}

			if (calculator.isTheMovePossible(token, column, row))
			{
				board.setToken(token, column, row);
				break;
			}
			else
			{
				System.out.println("Ungültiger Zug. Versuchen Sie es erneut:");
				continue;
			}
		}
	}
}
