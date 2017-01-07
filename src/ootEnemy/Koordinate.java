package ootEnemy;

/**
 * Immutable Klasse zum speichern einer Koordinate auf dem Spielbrett.
 */
public class Koordinate {
	private int spalte, zeile;

	/**
	 * Erstellt eine neue Koordinate.
	 *
	 * @param spalte
	 *            Die X Koordinate, diese muss als Buchstabe zwischen A und Z
	 *            darstellbar sein.
	 * @param zeile
	 *            Die Y Koordinate
	 */
	public Koordinate(int spalte, int zeile) {
		if (!Character.isUpperCase('A' + spalte)) {
			throw new UnsupportedOperationException();
		}

		this.spalte = spalte;
		this.zeile = zeile;
	}

	/**
	 * Konvertiert einen Koordinaten String der Form [A-Z][0-9]*.
	 *
	 * @param eingabe
	 *            Der Eingabestring der Form [A-Z][0-9]*.
	 * @return Returns die Koordinate die durch den String gegeben wurde.
	 */
	public static Koordinate Parse(String eingabe) throws NumberFormatException {
		eingabe = eingabe.toUpperCase();
		char spalteCh = eingabe.charAt(0);
		if (!Character.isUpperCase(spalteCh)) {
			throw new NumberFormatException();
		} else {
			return new Koordinate(spalteCh - 'A', Integer.parseUnsignedInt(eingabe.substring(1)) - 1);
		}
	}

	/**
	 * Konvertiert eine Koordinate in einen String der Form [A-Z][0-9]*.
	 *
	 * @param eingabe
	 *            Die Eingabekoordinate.
	 * @return Returns den String der durch die Koordinate gegeben wurde.
	 */
	public static String Parse(Koordinate eingabe) {
		int spalte = eingabe.getSpalte();
		int zeile = eingabe.getZeile() + 1;
		char spalteInChar = (char) (spalte + 'A');
		// char zeileInChar = (char) (zeile + '1');
		String koordinate = ("" + spalteInChar + zeile);
		return (koordinate);

	}

	@Override
	public String toString() {
		return String.format("%c%d", spalte + 'A', zeile);
	}

	/**
	 * Die X Koordinate.
	 *
	 * @return Returns die X Koordinate.
	 */
	public int getSpalte() {
		return spalte;
	}

	/**
	 * Die Y Koordinate.
	 *
	 * @return Returns die Y Koordinate.
	 */
	public int getZeile() {
		return zeile;
	}
}
