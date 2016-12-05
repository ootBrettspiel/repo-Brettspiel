package oot.game;

import java.util.Scanner;

/**
 * Helper class for formatting user input.
 * @author Christopher Rotter
 *
 */
public class InputFormatter
{
	/**
	 * Scanner object reference to read from the System.in stream
	 */
	private Scanner scanner;

	/**
	 * The arguments that were read from the console.
	 */
	private String[] arguments;

	/**
	 * The current position in the arguments array.
	 */
	private int pos;

	/**
	 * Creates a new input formatter.
	 */
	public InputFormatter(Scanner scanner)
	{
		this.scanner = scanner;
	}

	/**
	 * Reads the next line of input and formats it.
	 */
	public void readInput()
	{
		arguments = scanner.nextLine().split(" ");
		pos = 0;
	}

	/**
	 *
	 * @return The next argument in the argument array. Null if there are no arguments yet or all arguments have been read.
	 */
	public String getNext()
	{
		if (isAtEnd())
		{
			return null;
		}
		else
		{
			return arguments[pos++];
		}
	}

	public boolean isAtEnd()
	{
		if (arguments == null || pos >= arguments.length)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
