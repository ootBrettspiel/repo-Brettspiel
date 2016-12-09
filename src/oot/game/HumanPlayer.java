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
	public Coordinate getTurn(GamePhase phase)
	{
		System.out.println("Spieler " + name + " ist am Zug:");

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Coordinate position;

		while (true)
		{
			String input = scanner.nextLine();

			if (phase == GamePhase.REGULAR && input.equals("-"))
			{
				return null;
			}

			try
			{
				position = new Coordinate(input);
			}
			catch (IllegalArgumentException e)
			{
				System.out.println("Fehlerhafte eingabe. Bitte wiederholen:");
				continue;
			}

			switch (phase)
			{
			case REGULAR:
				if (position.getX() < 1 || position.getX() >= board.getCells().length - 1 || position.getY() < 1 || position.getY() >= board.getCells().length - 1)
				{
					System.out.println("Ungültiger Zug. Bitte wiederholen:");
					continue;
				}
				break;
			case SET:
				if (position.getX() > 0 && position.getX() < board.getCells().length - 1 && position.getY() > 0 && position.getY() < board.getCells().length - 1 || position.getX() < 0 || position.getX() >= board.getCells().length ||  position.getY() < 0 || position.getY() >= board.getCells().length)
				{
					System.out.println("Ungültiger Zug. Bitte wiederholen:");
					continue;
				}
			}

			if (calculator.isMovePossible(token, position, phase))
			{
				// board.setToken(token, position.getX(), position.getY(), phase);
				break;
			}
			else
			{
				System.out.println("Ungültiger Zug. Versuchen Sie es erneut:");
				continue;
			}
		}

		return position;
	}
}
