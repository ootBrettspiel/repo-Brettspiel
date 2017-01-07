package ootEnemy;

/**
 * Enumeration die die Zustaende des Spielfeldes darstellen.
 *
 * @author Luca
 *
 */
public enum Stein {
	/**
	 * Die vier verschiedenen Zustaende einer Position auf dem Gameboard.
	 */
	WEISS('o', 1), SCHWARZ('x', -1), LEER(' ', 0), GESPERRT('/', 2);
	/**
	 * Werte der verschiedenen Enumerationen.
	 */
	private final char werte;
	private final int speicherWert;

	/**
	 * Konstruktor der Enumerationen.
	 *
	 * @param darstellung
	 *            Wert der Enumeration.
	 */
	Stein(final char darstellung, int speicherWert) {
		this.werte = darstellung;
		this.speicherWert = speicherWert;
	}

	/**
	 * Wechselt den Stein von Weiﬂ zu Schwarz und anders herum.
	 */
	public Stein umdrehen() {
		if (this == WEISS) {
			return Stein.SCHWARZ;
		} else if (this == SCHWARZ) {
			return Stein.WEISS;
		} else {
			throw new UnsupportedOperationException("Nur farbige Spielsteine koennen gedreht werden!");
		}
	}

	public char getStein() {
		return this.werte;
	}

	public int getSpeicherWert() {
		return this.speicherWert;
	}

	public static Stein ladeStein(int speicherWert) {
		if (speicherWert == 1) {
			return Stein.WEISS;
		} else if (speicherWert == -1) {
			return Stein.SCHWARZ;
		} else if (speicherWert == 0) {
			return Stein.LEER;
		} else {
			return Stein.GESPERRT;
		}
	}

	@Override
	public String toString() {
		return String.format("%s : %c", this.name(), this.werte);
	}
}
