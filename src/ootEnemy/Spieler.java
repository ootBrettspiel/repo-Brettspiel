package ootEnemy;

import java.io.IOException;
import java.util.Scanner;

/**
 * Klasse die die spielende Person darstellt.
 *
 * @author Luca
 *
 */
public class Spieler extends Benutzer {

	/**
	 * Konstruktor der Klasse Spieler mit Aufruf des Super Konstruktors.
	 *
	 * @param board
	 *            Spielbrett auf dem gespielt wird.
	 * @param farbe
	 *            Farbe des Steins des Spielers.
	 */
	Spieler(final Stein farbe, String name) {
		super(farbe, name);
	}

	@Override
	public void setzStein(String koordinate) {
		if (!koordinate.equals("")) {
			Spielbrett.setzeStein(Spielbrett.getSpielbrett(), Koordinate.Parse(koordinate), this.getFarbe());
		} else {
			if (Spielbrett.getSetzPhase()) {
				Spielbrett.setzeStein(Spielbrett.getSpielbrett(), Koordinate.Parse(setzPhaseZug()), this.getFarbe());
			} else {
				Spielbrett.setzeStein(Spielbrett.getSpielbrett(), Koordinate.Parse(zug()), this.getFarbe());
			}
		}
	}

	/**
	 * Führt einen Zug eines Spielers aus und bietet ihm verschiedene
	 * Möglichkeiten. Die Möglichkeiten sind: Zug, Start, Stop, Speichern oder
	 * Beenden.
	 *
	 * @return Der Zug der korrekt eingeben wurde und nun gemacht werden soll.
	 */
	private String zug() {
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println(getName() + " ist dran gib einen Zug, Start, Stop, Speichern oder Beenden ein");
			String eingabe1 = sc.next().toLowerCase();
			switch (eingabe1) {
			case ("stop"): {
				Spielbrett.stopZeit();
				System.out.println(Konstanten.stopZug);
				break;
			}
			case ("start"): {
				Spielbrett.setStartzeit();
				break;
			}
			case ("speichern"): {
				System.out.println(Konstanten.dateipfad);
				String speicherpfad = sc.next();
				try {
					Spiel.spielbrett.speichern(speicherpfad);
					break;
				} catch (IOException e) {
					System.err.println(Konstanten.nochKeinSpiel);
					break;
				}
			}
			case ("beenden"): {
				Spiel.menue();
				break;
			}
			default: {
				try {
					if (Spielbrett.zugGueltig(Spielbrett.getSpielbrett(), Koordinate.Parse(eingabe1), getFarbe())) {
						UebernommenesArray.getUebernommen();
						Spielbrett.setStartzeit();
						return eingabe1;
					}
				} catch (NumberFormatException e) {
					System.err.println(Konstanten.koordFalsch);
				}
			}
			}
		} while (Spiel.getSpielLaeuft());
		sc.close();
		return zug();
	}

	/**
	 * Methode die alle Züge der Setzphase handhabt.
	 *
	 * @return Ein in der Setzphase gültiger Zug der jetzt gemacht werden soll.
	 */
	private String setzPhaseZug() {
		do {
			Scanner sc = new Scanner(System.in);
			String eingabe1 = sc.next().toLowerCase();
			try {
				Koordinate eingabe = Koordinate.Parse(eingabe1);
				int ez = eingabe.getZeile();
				int es = eingabe.getSpalte();
				int groesse = Spielbrett.getSpielbrettGroesse();
				if ((es <= groesse) && (es >= 0) && (ez <= groesse) && (ez >= 0)) {
					if (((es == 1) & (ez == 1)) || ((es == groesse) && (ez == groesse))
							|| ((es == 1) && (ez == groesse)) || ((es == groesse) && (ez == 1))) {
						System.out.println(Konstanten.eckenFehler);
					} else {
						if (Spielbrett.zugGueltig(Spielbrett.getSpielbrett(), eingabe, getFarbe())) {
							return eingabe1;
						} else {
							System.out.println(Konstanten.ungueltigeEingabe);
						}
					}
				} else {
					System.err.println(Konstanten.koordFalsch);
				}
			} catch (NumberFormatException e) {
				System.err.println(Konstanten.koordFalsch);
			}
		} while (true);
	}
}
