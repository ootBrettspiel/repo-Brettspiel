package oot.game;

import java.util.Scanner;

/**
 * Helper class for easier user dialog via the console.
 * @author Christopher Rotter
 *
 */
public class InputPrompt
{
	/**
	 * The message that will show when the prompt is started.
	 */
	private String message;

	/**
	 * The arguments that were read from the console.
	 */
	private String[] arguments;

	/**
	 * The current position in the arguments array.
	 */
	private int pos;

	/**
	 * Creates a new input prompt with the give message.
	 * @param message
	 */
	public InputPrompt(String message)
	{
		this.message = message;
	}

	/**
	 * Shows the message to the user and reads the next line of input.
	 */
	public void show()
	{
		System.out.println(message);

		Scanner scanner = new Scanner(System.in);
		arguments = scanner.nextLine().split(" ");
		pos = 0;
		scanner.close();
	}

	/**
	 *
	 * @return The next argument in the argument array. Null if there are no arguments yet or all arguments have been read.
	 */
	public String getNext()
	{
		if (arguments == null || pos >= arguments.length)
		{
			return null;
		}
		else
		{
			return arguments[pos++];
		}
	}

	public int getArgumentCount()
	{
		if (arguments == null)
		{
			return 0;
		}
		else
		{
			return arguments.length;
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
