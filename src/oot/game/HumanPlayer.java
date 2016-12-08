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
	public boolean makeTurn(GamePhase phase)
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

				if (s.length() != 1)
				{
					System.out.println("Fehlerhafte eingabe. Bitte wiederholen:");
					continue;
				}

				column = s.toCharArray()[0] - 65;

				if (!input.isAtEnd())
				{
					try
					{
						row = Integer.parseInt(input.getNext()) - 1;
					}
					catch (NumberFormatException e)
					{
						System.out.println("Fehlerhafte eingabe. Bitte wiederholen:");
						continue;
					}

					switch (phase)
					{
					case REGULAR:
						if (column < 1 || column >= board.getCells().length - 1 || row < 1 || row >= board.getCells().length - 1)
						{
							System.out.println("Fehlerhafte eingabe. Bitte wiederholen:");
							continue;
						}
						break;
					case SET:
						if (column > 0 && column < board.getCells().length - 1 && row > 0 && row < board.getCells().length - 1 || column < 0 || column >= board.getCells().length ||  row < 0 || row >= board.getCells().length)
						{
							System.out.println("Fehlerhafte eingabe. Bitte wiederholen:");
							continue;
						}
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

			if (calculator.isTheMovePossible(token, column, row, phase))
			{
				board.setToken(token, column, row, phase);
				break;
			}
			else
			{
				System.out.println("Ungültiger Zug. Versuchen Sie es erneut:");
				continue;
			}
		}

		// TODO: return false if no token was placed
		return true;
	}
}
