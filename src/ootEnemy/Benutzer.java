package ootEnemy;

/**
 * Klasse die einen Benutzer des Spiels darstellt.
 *
 * @author Luca
 *
 */
public abstract class Benutzer {
	/**
	 * Name des Users.
	 */
	private String name;
	/**
	 * Farbe des Benutzer.
	 */
	private Stein farbe;
	/**
	 * Angabe ob der Spieler gerade dran ist.
	 */
	private boolean meinZug;
	/**
	 * Angabe ob der Spieler eine Moeglichkeit hat zu Setzen.
	 */
	private boolean kannZiehen;

	public final String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Stein getFarbe() {
		return farbe;
	}

	public void setFarbe(Stein farbe) {
		this.farbe = farbe;
	}

	public boolean isMeinZug() {
		return meinZug;
	}

	public void setMeinZug(boolean meinZug) {
		this.meinZug = meinZug;
	}

	public boolean isKannZiehen() {
		return kannZiehen;
	}

	public void setKannZiehen(boolean kannZiehen) {
		this.kannZiehen = kannZiehen;
	}

	/**
	 * Konstruktor von User.
	 *
	 * @param board
	 *            Gameboard auf dem gespielt wird.
	 * @param farbe
	 *            Farbe des Steins des Users.
	 */
	Benutzer(final Stein farbe, String name) {
		this.farbe = farbe;
		this.name = name;
	}

	/**
	 * Abstrakte Methode, welche später den Stein setzen soll.
	 */
	public abstract void setzStein(String koordinate);

}
