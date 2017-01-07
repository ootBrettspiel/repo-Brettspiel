package ootEnemy;

import java.io.IOException;
import java.util.Scanner;

public class Eingaben {
	Scanner sc = new Scanner(System.in);
	private boolean spielstart = false;
	String nameSpieler2;
	Benutzer spieler2;

	/**
	 * Methode die die Farbeingabe des Spielers handhabt, der Spieler kann seine
	 * Farbe weiss oder schwarz setzen oder zur Namenseingabe zurueckkehren.
	 *
	 * @return Die farbe die der Spieler gewaehlt hat.
	 */
	public Stein farbEingabe() {
		do {
			System.out.println(Konstanten.farbEingabeAbfrage);
			String farbenEingabe = sc.nextLine().toLowerCase();
			if (farbenEingabe.equals("weiß")) {
				return Stein.WEISS;
			} else if (farbenEingabe.equals("schwarz")) {
				return Stein.SCHWARZ;
			} else if (farbenEingabe.equals("zurück")) {
				Spiel.menue();
			} else {
				System.err.println("Ungueltige Eingabe");
			}
		} while (true);
	}

	/**
	 * Methode die die allgemeinen Moeglichkeiten handhabt, der Benutzer kann
	 * spielen, speichern, laden oder zurueck zur Farbeingabe.
	 */
	public void menueEingaben() {
		do {
			hilfe();
			String eingabe = sc.nextLine().toLowerCase();
			switch (eingabe) {
			case ("spielen"): {
				spielenEingabe();
			}
			case ("speichern"): {
				System.out.println(Konstanten.dateipfad);
				String speicherpfad = sc.nextLine();
				try {
					Spiel.spielbrett.speichern(speicherpfad);
					break;
				} catch (IOException e) {
					System.err.println(Konstanten.nochKeinSpiel);
					break;
				}
			}
			case ("laden"): {
				System.out.println(Konstanten.dateipfad);
				String speicherpfad = sc.nextLine();
				try {
					Spiel.spielbrett.laden(speicherpfad);
				} catch (IOException e) {
					System.err.println(Konstanten.dateiNichtVorhanden);
				}
			}
			case ("zurück"): {
				Spiel.menue();
			}
			default: {
				System.err.println(Konstanten.ungueltigeEingabe);
				menueEingaben();
			}

			}
		} while (Spiel.getSpielLaeuft());

	}

	/**
	 * Methode die die weiteren Eingaben handhabt welche nach dem waehlen von
	 * Spielen passieren.
	 */
	private void spielenEingabe() {
		do {
			System.out.println(Konstanten.spielEingabe);
			String eingabe = sc.nextLine().toLowerCase();
			switch (eingabe) {
			case ("1"): {
				einzelspielerEingabe();
				break;
			}
			case ("2"): {
				zweispielerEingabe();
				break;
			}
			case ("zurück"): {
				menueEingaben();
				break;
			}
			default: {
				System.err.println(Konstanten.ungueltigeEingabe);
			}
			}
		} while (true);
	}

	/**
	 * Methode welche die weiteren Eingaben nach der Eingabe von Spielen im
	 * Menue handhabt. Zuerst wird die Spielfeldgroesse gesetzt,dann wird das
	 * Spiel gestartet.
	 */
	private void zweispielerEingabe() {
		System.out.println(Konstanten.nameAbfrageSpieler2);
		nameSpieler2 = sc.nextLine();
		spieler2 = new Spieler(Spiel.spieler1.getFarbe().umdrehen(), nameSpieler2);
		spielbrettGroessenEingabe();
		Spiel.starteSpiel(Spiel.spielbrett, Spiel.spieler1, spieler2);
	}

	/**
	 * Methode welche die abfrage und eingabe der spielfeldgröße ausführt.
	 */
	private void spielbrettGroessenEingabe() {
		int groesse = 0;
		do {
			System.out.println(Konstanten.groessenEingabe);
			try {
				groesse = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
			}

			if ((groesse >= 6) && (groesse <= 16) && (groesse % 2 == 0)) {
				Spiel.spielbrett = new Spielbrett(groesse);
				break;
			} else {
				System.err.println(Konstanten.ungueltigeEingabe);
			}
		} while (true);
	}

	/**
	 * Methode die die weiteren Eingaben handhabt welche nach der Auswahl des
	 * einzelspieler moduses geschehen.
	 */
	private void einzelspielerEingabe() {
		spielbrettGroessenEingabe();
		Benutzer spieler2 = null;
		do {
			System.out.println(Konstanten.kieingabe);
			String eingabe = sc.nextLine().toLowerCase();

			switch (eingabe) {
			case ("stark"): {
				spieler2 = new StarkeKi(Spiel.spieler1.getFarbe().umdrehen(), "SchwererComputer");
				spielstart = true;
				break;
			}
			case ("mittel"): {
				spieler2 = new SchwacheKi(Spiel.spieler1.getFarbe().umdrehen(), "MittlererComputer");
				spielstart = true;
				break;
			}
			case ("schwach"): {
				spieler2 = new MittlereKi(Spiel.spieler1.getFarbe().umdrehen(), "LeichterComputer");
				spielstart = true;
				break;
			}
			case ("zurück"): {
				spielenEingabe();
			}
			default: {
				System.err.println(Konstanten.ungueltigeEingabe);
			}
			}
		} while (!spielstart);
		Spiel.starteSpiel(Spiel.spielbrett, Spiel.spieler1, spieler2);
	}

	/**
	 * Methode die alle Moeglichkeiten des Menues ausgibt.
	 */
	public void hilfe() {
		System.out.println(Konstanten.eingabenHilfe);
	}
}
