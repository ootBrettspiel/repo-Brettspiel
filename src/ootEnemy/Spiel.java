package ootEnemy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class Spiel {
	private static String name;
	static Spielbrett spielbrett;
	static Benutzer spieler1;
	private static boolean spieler1Dran;
	@SuppressWarnings("unused")
	private LinkedList<Integer> highscore;
	static int zuege = 0;
	static int pw = 0;
	static int ps = 0;
	ObjectSerializer serializer = new ObjectSerializer();
	File highscorePfad = new File("highscore.bin");

	public static void main(String[] args) {
		menue();
//		 Spielbrett brett = new Spielbrett(6);
//		 heatmap = new
//		 int[Spielbrett.getSpielbrettGroesse()][Spielbrett.getSpielbrettGroesse()];
//		 for (int i = 0; i < 1; i++) {
//		 starteSpiel(brett, new Spieler(Stein.WEISS, "Ki 1"), new
//		 SchwacheKi(Stein.SCHWARZ, "Ki 2"));
//		 while (getSpielLaeuft()) {
//
//		 }
//
//		 brett.setUp();
//		 }
//		 System.out.println("Weiss: " + pw + ", Schwarz: " + ps);
//		 System.out.println("\n\nFelder die zum Gewinnen gefuehrt haben: \n");
//		 for (int i = 0; i < Spielbrett.getSpielbrettGroesse(); i++) {
//		 for (int j = 0; j < Spielbrett.getSpielbrettGroesse(); j++) {
//		 String akt = heatmap[i][j] + "";
//
//		 switch (akt.length()) {
//		 case 1:
//		 akt = " " + akt + " |";
//		 break;
//		 case 2:
//		 akt = " " + akt + " |";
//		 break;
//		 case 3:
//		 akt = " " + akt + " |";
//		 break;
//		 case 4:
//		 akt = akt + " |";
//		 break;
//		 case 5:
//		 akt = akt + "|";
//		 break;
//		 }
//		 System.out.print(akt);
//		 }
//		 System.out.println();
//		 for (int i1 = 0; i1 < Spielbrett.getSpielbrettGroesse(); i1++) {
//		 System.out.print("------");
//		 }
//		 System.out.println();
//		 }
//		 highscore= new LinkedList();
//
//		 File file = new File("highscore.bin");
//		// // // serializer erzeugen
//		 ObjectSerializer serializer = new ObjectSerializer();
//		 // arrays speichern
//		 serializer.save2file(highscore, file);
//		 serializer.close_out();

	}

	/**
	 * Fraegt den Benutzer nach seinem Namen und seiner gewuenschten Farbe und
	 * startet dann das Menue.
	 */
	public static void menue() {
		Eingaben ein = new Eingaben();
		Scanner sc = new Scanner(System.in);
		System.out.println("Geben sie bitte ihren Namen ein");
		name = sc.nextLine();
		sc.close();
		spieler1 = new Spieler(ein.farbEingabe(), name);
		ein.menueEingaben();

	}

	/**
	 * Methode die die Eingabe und Setzung der Spielfeldgröße handhabt.
	 *
	 * @param i
	 *            gewünschte Größe des Spielfeldes.
	 * @return Spielfeld der gewünschten Größe.
	 */
	public Spielbrett spielfeldGroesse(int i) {
		if ((i <= 16) && (i >= 6) && (i % 2 == 0)) {
			return new Spielbrett(i);
		} else {
			System.err.println("Die Spielfeldgroesse muss gerade und zwischen 6 und 16 sein");
			return null;
		}
	}

	/**
	 * Startet das Spiel, indem es den Spieler mit der Stein-Farbe Weiss den
	 * ersten Zug machen laesst und dann imer nach dem ein Zug erfolgreich
	 * ausgefuehrt wurde zum anderen Spieler wechselt.
	 *
	 * @param brett
	 *            Spielbrett auf dem gespielt wird.
	 * @param spieler1
	 *            Spieler 1 der an dem Spiel teilnimmt.
	 * @param spieler2
	 *            Spieler 2 der an dem Spiel teilnimmt
	 */
	public static void starteSpiel(Spielbrett brett, Benutzer spieler1, Benutzer spieler2) {
		Scanner sc = new Scanner(System.in);
		boolean kannStarten = false;
		do {
			System.out.println(Konstanten.farbeFaengtAn);
			String eingabe1 = sc.next().toLowerCase();
			if (eingabe1.equals("weiß")) {
				System.out.println("Weiß beginnt...");
				if (spieler1.getFarbe() == Stein.WEISS) {
					spieler1Dran = true;
				} else {
					spieler1Dran = false;
				}
				kannStarten = true;
			} else if (eingabe1.equals("schwarz")) {
				System.out.println("Schwarz beginnt...");
				if (spieler1.getFarbe() == Stein.WEISS) {
					spieler1Dran = false;
				} else {
					spieler1Dran = true;
				}
				kannStarten = true;
			} else {
				System.out.println(Konstanten.ungueltigeEingabe);
			}
		} while (!kannStarten);

		System.out.println("Setzphase gestartet...");
		while (getSpielLaeuft()) {
			if (spieler1Dran) {
				if (brett.kannZiehen(Stein.WEISS)) {
					Spielbrett.zeigeSpielbrett(Spielbrett.getSpielbrett());
					zug(brett, spieler1);
				} else {
					System.out.println(spieler1.getName() + Konstanten.kannNichtZiehen);
					spieler1Dran = false;
					if (!brett.kannZiehen(Stein.SCHWARZ)) {
						break;
					}
				}
			} else {
				spieler1Dran = false;
				if (brett.kannZiehen(Stein.SCHWARZ)) {
					Spielbrett.zeigeSpielbrett(Spielbrett.getSpielbrett());
					zug(brett, spieler2);
				} else {
					System.out.println(spieler2.getName() + Konstanten.kannNichtZiehen);
					if (!brett.kannZiehen(Stein.WEISS)) {
						break;
					}
					spieler1Dran = true;
				}
			}
		}
		if (Spielbrett.punkteSchwarz() > Spielbrett.punkteWeiss()) {
			System.out.println(Konstanten.sGewinnt);
			// ps++;
			// addUp(Stein.SCHWARZ);
		}
		if (Spielbrett.punkteSchwarz() < Spielbrett.punkteWeiss()) {
			System.out.println(Konstanten.wGewinnt);
			// pw++;
			// addUp(Stein.WEISS);
		}
		sc.close();
	}

	// private static void addUp(Stein farbe) {
	// Stein gegner = Stein.LEER;
	// if (farbe == Stein.WEISS) {
	// gegner = Stein.SCHWARZ;
	// }
	// if (farbe == Stein.SCHWARZ) {
	// gegner = Stein.WEISS;
	// }
	//
	// for (int i = 0; i < Spielbrett.getSpielbrettGroesse(); i++) {
	// for (int j = 0; j < Spielbrett.getSpielbrettGroesse(); j++) {
	// Stein[] brett = Spielbrett.getSpielbrett();
	// if (brett[i * Spielbrett.getSpielbrettGroesse() + j] == farbe) {
	// heatmap[i][j]++;
	// }
	// if (brett[i * Spielbrett.getSpielbrettGroesse() + j] == gegner) {
	// heatmap[i][j]--;
	// }
	// }
	// }
	// }

	/**
	 * Laesst den Uebergebenen Spieler einen Zug auf dem uebergebenen Spielbrett
	 * machen, die Zeit stoppen oder die Zeit wieder weiterlaufen.
	 *
	 * @param brett
	 *            Spielbrett auf dem gespielt wird.
	 * @param spieler
	 *            Spieler der den zug machen soll.
	 */
	public static void zug(Spielbrett brett, Benutzer spieler) {
		spieler.setzStein("");
		spieler1Dran = !spieler1Dran;
	}

	public static boolean getSpielLaeuft() {
		return (Spielbrett.kannZiehen(Spielbrett.getSpielbrett(), Stein.SCHWARZ)
				|| Spielbrett.kannZiehen(Spielbrett.getSpielbrett(), Stein.WEISS));
	}

	/**
	 * Methode die das Spielbrett speichert.
	 *
	 * @param speicherpfad
	 *            Speicherpfad wo das Spielbrett gespeichert werden soll.
	 * @throws IOException
	 *             Kann beim Schreiben passieren.
	 */
	public static void speichern(String speicherpfad) throws IOException {
		File fout = new File(String.format("C:\\Users\\Luca\\Desktop\\%s.txt", speicherpfad));
		FileOutputStream fos = new FileOutputStream(fout);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.close();
	}

}
