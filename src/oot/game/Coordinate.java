package oot.game;

/**
 *
 * @author Christopher Rotter
 *
 */
public class Coordinate
{
	private int x;
	private int y;
	private char nativeX;
	private int nativeY;

	public Coordinate(String value) throws IllegalArgumentException
	{
		try
		{
			value = value.toUpperCase();
			nativeX = value.charAt(0);
			x = nativeX - 65;

			nativeY = Integer.parseInt(value.substring(1));
			y = nativeY - 1;
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException();
		}
	}

	public Coordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
		nativeX = (char) (x + 65);
		nativeY = y + 1;
	}

	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}

	@Override
	public String toString()
	{
		return nativeX + " " + nativeY;
	}
}
