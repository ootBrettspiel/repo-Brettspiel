package ootEnemy;

import java.util.LinkedList;

/**
 * Klasse die die kuenstliche Intelligenz darstellt also den Computergegner.
 *
 * @author Luca
 *
 */
public abstract class Ki extends Benutzer {
	/**
	 * Konstruktor der Klasse Ki mit aufruf des Superkonstruktor.
	 *
	 * @param board
	 *            Spielbrett auf dem Gespielt wird.
	 * @param farbe
	 *            Farbe des Steins des Computergegners.
	 */
	Ki(final Stein farbe, String name) {
		super(farbe, name);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Methode welche die Möglichen Züge berechnet und als Liste zurückgibt.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LinkedList getMoeglZuege(LinkedList moeglZuege, Stein farbe) {
		Stein[] test = Spielbrett.getSpielbrett().clone();
		for (int i = 0; i < Spielbrett.getSpielbrettGroesse(); i++) {
			for (int j = 0; j < Spielbrett.getSpielbrettGroesse(); j++) {
				if (Spielbrett.zugGueltig(test, new Koordinate(i, j), farbe) == true) {
					moeglZuege.add(new Koordinate(i, j));
				} else {

				}
			}
		}
		return moeglZuege;
	}

}