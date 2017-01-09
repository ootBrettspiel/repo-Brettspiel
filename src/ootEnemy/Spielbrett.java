package ootEnemy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Spielbrett fuer OOThello mit den benoetigten Funktionen.
 *
 * @author Luca
 *
 */
public class Spielbrett {

	/**
	 * Stellt das Spielbrett dar.
	 */
	private static Stein[] spielbrett;

	public static UebernommenesArray uebernom;
	/**
	 * Zeitpunkt der benoetigt wird um die benoetigte Zeit angeben zu koennen.
	 */
	private static long startzeit = 0;
	/**
	 * Bisher vergangene Zeit wird nur benoetigt falls waehrend dem Spiel
	 * gespeichert, geladen oder gestopt wird.
	 */
	private static long bisherigeZeit;
	/**
	 * Groesse des Spielbretts.
	 */
	private static int spielbrettGroesse;
	/**
	 * Gibt die Punkte der Farbe Weiss an.
	 */
	static int punkteWeiss = 0;
	/**
	 * Gibt die Punkte der Farbe Schwarz an.
	 */
	static int punkteSchwarz = 0;

	private static boolean isSetzphase = true;
	private ObjectSerializer serial = new ObjectSerializer();

	private static int zuege = 0;

	/**
	 * Konstruktor des Spielbretts.
	 *
	 * @param groesse
	 *            gibt die Groesse des inneren Spielbretts an.
	 */
	public Spielbrett(int groesse) {
		uebernom = new UebernommenesArray();
		spielbrettGroesse = groesse + 2;
		spielbrett = new Stein[(spielbrettGroesse * spielbrettGroesse)];
		bisherigeZeit = 0;
		setUp();

	}

	public Spielbrett() {
		this(6);
	}

	public static void setUp() {
		for (int y = 0; y < spielbrettGroesse; y++) {
			for (int x = 0; x < spielbrettGroesse; x++) {
				setSteinAt(Spielbrett.getSpielbrett(), Stein.LEER, x, y);
			}

		}
		zuege = spielbrettGroesse;
		isSetzphase = false;
		// Start Spielzustand wird herrgestellt.
		setSteinAt(Spielbrett.spielbrett, Stein.SCHWARZ, spielbrettGroesse / 2, spielbrettGroesse / 2);
		setSteinAt(Spielbrett.spielbrett, Stein.SCHWARZ, spielbrettGroesse / 2 - 1, spielbrettGroesse / 2 - 1);
		setSteinAt(Spielbrett.spielbrett, Stein.WEISS, spielbrettGroesse / 2 - 1, spielbrettGroesse / 2);
		setSteinAt(Spielbrett.spielbrett, Stein.WEISS, spielbrettGroesse / 2, spielbrettGroesse / 2 - 1);
		setSteinAt(Spielbrett.spielbrett, Stein.GESPERRT, 0, 0);
		setSteinAt(Spielbrett.spielbrett, Stein.GESPERRT, 0, spielbrettGroesse - 1);
		setSteinAt(Spielbrett.spielbrett, Stein.GESPERRT, spielbrettGroesse - 1, 0);
		setSteinAt(Spielbrett.spielbrett, Stein.GESPERRT, spielbrettGroesse - 1, spielbrettGroesse - 1);
		for (int i = 1; i < spielbrettGroesse - 1; i++) {
			for (int j = 1; j < spielbrettGroesse - 1; j++) {
				if (getSteinAt(Spielbrett.getSpielbrett(), i, j) == Stein.LEER) {
					setSteinAt(Spielbrett.getSpielbrett(), Stein.GESPERRT, i, j);
				}
			}
		}
		zuege = 0;
		isSetzphase = true;
		setStartzeit();
	}

	/**
	 * Gibt das Spielbrett auf der Konsole aus damit der Benutzer das aktuelle
	 * Feld mit Beschriftung sieht.
	 */
	public static void zeigeSpielbrett(Stein[] spielfeld) {
		int zaehler = 1;
		for (int y = 0; y < getSpielbrettGroesse(); y++) {
			System.out.print("  ");
			// Obere Spielfeldkante
			for (int k = 0; k < spielbrettGroesse; k++) {
				System.out.print("----");
			}
			if (zaehler > 9) {
				System.out.print("\n" + zaehler + "|");
			} else {
				System.out.print("\n" + " " + zaehler + "|");
			}
			zaehler++;
			for (int x = 0; x < getSpielbrettGroesse(); x++) {
				System.out.print(" " + getSteinAt(spielfeld, x, y).getStein() + " |");
			}
			System.out.println();
		}

		// Untere Spielfeldkante
		System.out.print("  ");
		for (int k = 0; k < getSpielbrettGroesse(); k++) {
			System.out.print("----");
		}

		// Spaltenbeschriftung
		System.out.println();
		System.out.print("  ");
		for (char c = 'A'; c < 'A' + getSpielbrettGroesse(); c++) {
			System.out.print("  " + c + " ");
		}

		System.out.println();

	}

	/**
	 * Speichert das aktuelle Spielbrett und weitere benoetigte Daten in einer
	 * Datei um diese spaeter wieder laden zu koennen.
	 *
	 *
	 * @param speicherpfad
	 *            Speicherort für das Spielbrett.
	 * @throws IOException
	 *             Tritt beim Schreiben auf.
	 */
	public void speichern(String speicherpfad) throws IOException {
		File fout = new File(String.format("%s.txt", speicherpfad));
		FileOutputStream fos = new FileOutputStream(fout);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		// Groesse des Spielbretts
		bw.write(spielbrettGroesse);
		stopZeit();
		bw.write(String.format("%d\n", startzeit != 0 ? getZeit() : 0));

		for (int y = 0; y < spielbrettGroesse; y++) {
			for (int x = 0; x < spielbrettGroesse; x++) {
				bw.write(getSteinAt(Spielbrett.spielbrett, x, y).getSpeicherWert() + ",");
			}
			bw.write("\n");
		}

		bw.close();
	}

	public void speichernSerial(String speicherpfad) {
		File file = new File(speicherpfad + ".txt");
		serial.save2file(this, file);
	}

	/**
	 * Laedt das Spielbrett und alle noetigen Daten eines gespeicherten Spiels
	 *
	 * @param speicherpfad
	 *            Angabe des Namens des Spielstandes oder dessen
	 *            Speicherpfad+Name.
	 * @throws IOException
	 *             Kann geworfen werden falls die Datei nicht existiert.
	 */
	public void laden(String speicherpfad) throws IOException {
		File fin = new File(String.format("Spielstaende\\%s.txt", speicherpfad));
		FileReader fis = new FileReader(fin);

		BufferedReader br = new BufferedReader(fis);
		// Groesse des Spielbretts
		spielbrettGroesse = Integer.parseInt(br.readLine());

		bisherigeZeit = Long.parseLong(br.readLine());
		setStartzeit();
		spielbrett = new Stein[spielbrettGroesse * spielbrettGroesse];
		for (int y = 0; y < spielbrettGroesse; y++) {
			String[] row = br.readLine().split(",");
			for (int x = 0; x < spielbrettGroesse; x++) {
				setSteinAt(Spielbrett.spielbrett, Stein.ladeStein(Integer.parseInt(row[x])), x, y);
			}
		}
		br.close();
	}

	/**
	 * Setzt die startzeit auf die aktuelle Zeit.
	 */
	public static void setStartzeit() {
		stopZeit();
		startzeit = System.currentTimeMillis();
	}

	/**
	 * Speichert die seit dem die Startzeit gesetzt wurde vergangengene Zeit.
	 */
	public static void stopZeit() {
		bisherigeZeit += ((System.currentTimeMillis() - startzeit) / 1000);
	}

	/**
	 * Gibt die benoetigte Zeit zurueck.
	 *
	 * @return Die Zeit die fuer das Spiel benoetigt wurde.
	 */
	public long getZeit() {
		if (bisherigeZeit == 0) {
			return ((System.currentTimeMillis() - startzeit) / 1000);
		} else {
			return (((System.currentTimeMillis() - startzeit) / 1000) + bisherigeZeit);
		}

	}

	/**
	 * Ueberprueft ob der gegeben Zug fuer diese Farbe gueltig ist.
	 *
	 * @param koord
	 *            Die Koordinate des zu setzenden Steins.
	 * @param farbe
	 *            Die Farbe des Stein der gesetzt werden soll.
	 * @return Returns true wenn der Zug fuer die gegeben Farbe gueltig ist.
	 */
	public static boolean zugGueltig(Stein[] spielfeld, Koordinate koord, Stein farbe) {
		if (getSetzPhase() && getSteinAt(spielfeld, koord) == Stein.LEER) {
			return true;
		} else {
			return geflippteSteine(spielfeld, koord, farbe) > 0;
		}
	}

	/**
	 * Gibt die durch diesen Zug gedrehten Gegnersteine zurueck.
	 *
	 * @param koord
	 *            Die Koordinate des zu setzenden Steins.
	 * @param farbe
	 *            Die Farbe des Stein der gesetzt werden soll.
	 * @return Return -1 falls der Zug ungueltig ist bzw. 0 falls kein Stein
	 *         gedreht wird. Ansonten wird die Zahl der gedrehten Steine
	 *         returned.
	 */
	public static int geflippteSteine(Stein[] spielfeld, Koordinate koord, Stein farbe) {
		if (!istInSpielfeld(spielfeld, koord.getSpalte(), koord.getZeile())
				|| getSteinAt(spielfeld, koord) != Stein.LEER) {
			return -1;
		}

		int geflippteSteine = 0;
		for (int y = -1; y <= 1; y++) {
			for (int x = -1; x <= 1; x++) {
				if (y == 0 && x == 0) {
					continue;
				} else {
					geflippteSteine += checkLinie(spielfeld, koord.getSpalte(), koord.getZeile(), x, y, farbe);
				}
			}
		}

		return geflippteSteine;
	}

	/**
	 * Setzt einen Stein falls er gueltig ist, und fuehrt dann die flip Methode
	 * fuer diesen Stein aus.
	 *
	 * @param koord
	 *            Koordinate des feldes auf welches ein Stein gesetzt werden
	 *            soll.
	 * @param farbe
	 *            Farbe des Steines der gesetzt werden soll.
	 */
	public static void setzeStein(Stein[] spielfeld, Koordinate koord, Stein farbe) {
		if (getSetzPhase() && getSteinAt(spielfeld, koord) == Stein.LEER) {
			setSteinAt(Spielbrett.getSpielbrett(), farbe, koord);
			zuege++;
		} else if (zugGueltig(spielfeld, koord, farbe)) {
			flip(spielfeld, koord, farbe);
			setSteinAt(spielfeld, farbe, koord);
			punkteBerechnen(spielfeld);
			zuege++;
		}
	}

	public static boolean getSetzPhase() {
		if (isSetzphase) {
			if (zuege >= spielbrettGroesse) {
				stopSetzphase();
			}
			return isSetzphase;
		}
		return isSetzphase;
	}

	/**
	 * Methode die die Setzphase beendet und dann das Spielfeld umändert für die
	 * Spielphase.
	 */
	private static void stopSetzphase() {
		System.out.println("Setzphase beendet...");
		for (int i = 0; i < spielbrettGroesse; i++) {
			if (getSteinAt(Spielbrett.getSpielbrett(), i, spielbrettGroesse - 1) == Stein.LEER) {
				setSteinAt(Spielbrett.getSpielbrett(), Stein.GESPERRT, i, spielbrettGroesse - 1);
			}
			if (getSteinAt(Spielbrett.getSpielbrett(), spielbrettGroesse - 1, i) == Stein.LEER) {
				setSteinAt(Spielbrett.getSpielbrett(), Stein.GESPERRT, spielbrettGroesse - 1, i);
			}
			if (getSteinAt(Spielbrett.getSpielbrett(), i, 0) == Stein.LEER) {
				setSteinAt(Spielbrett.getSpielbrett(), Stein.GESPERRT, i, 0);
			}
			if (getSteinAt(Spielbrett.getSpielbrett(), 0, i) == Stein.LEER) {
				setSteinAt(Spielbrett.getSpielbrett(), Stein.GESPERRT, 0, i);
			}
		}

		for (int i = 1; i < spielbrettGroesse - 1; i++) {
			for (int j = 1; j < spielbrettGroesse - 1; j++) {
				if (getSteinAt(Spielbrett.getSpielbrett(), i, j) == Stein.GESPERRT) {
					setSteinAt(Spielbrett.getSpielbrett(), Stein.LEER, i, j);
				}
			}
		}

		isSetzphase = false;
		zeigeSpielbrett(spielbrett);
	}

	/**
	 *
	 * Ueberprueft vom stein ausgehend eine Richtung ob es in dieser
	 * uebernehmbare Steine gibt, und gibt die Anzahl dieser zurueck.
	 *
	 * @param startX
	 *            x-Koordinate des Punktes von dem gestartet wird.
	 * @param startY
	 *            y-Koordinate des Punktes von dem gestartet wird.
	 * @param richtungX
	 *            Faktor der die X-Richtung angibt.
	 * @param richtungY
	 *            Faktor der die Y-Richtung angibt.
	 * @param farbe
	 *            Farbe des Steins von dem Geprueft wird.
	 * @return Die Anzahl der Steine die uebernommen werden koennen, oder falls
	 *         es keine gibt 0;
	 */
	private static int checkLinie(Stein[] spielfeld, int startX, int startY, int richtungX, int richtungY,
			Stein farbe) {
		Stein gegnerFarbe = farbe.umdrehen();
		richtungX = Integer.signum(richtungX);
		richtungY = Integer.signum(richtungY);
		if (richtungX == 0 && richtungY == 0) {
			throw new UnsupportedOperationException("Es wurde keine Richtung vorgegeben!");
		}

		// Gibt es einen angrenzenden gegner stein?
		startX += richtungX;
		startY += richtungY;
		if (!istInSpielfeld(spielfeld, startX, startY) || getSteinAt(spielfeld, startX, startY) != gegnerFarbe) {
			return 0;
		}

		int anzahlGegnerSteine = 1;
		// Gibt es in auf dieser Linie einen Spielstein unserer Farbe?
		while (true) {
			startX += richtungX;
			startY += richtungY;
			if (!istInSpielfeld(spielfeld, startX, startY)) {
				return 0;
			} else if (getSteinAt(spielfeld, startX, startY) == gegnerFarbe) {
				anzahlGegnerSteine++;
			} else if (getSteinAt(spielfeld, startX, startY) == farbe) {
				return anzahlGegnerSteine;
			} else {
				// Luecke in der Reihe.
				return 0;
			}
		}
	}

	/**
	 * Methode die von einem Stein ausgehend alle Richtungen ueberprueft und
	 * falls geflippt werden kann wird die Methode flipLinie aufgerufen.
	 *
	 * @param koord
	 *            Koordinate des Steins von dem gestartet wird.
	 * @param farbe
	 *            Farbe des Steins.
	 */
	public static void flip(Stein[] spielfeld, Koordinate koord, Stein farbe) {
		UebernommenesArray.setZwischenspeicher();
		for (int y = -1; y <= 1; y++) {
			for (int x = -1; x <= 1; x++) {
				if (y == 0 && x == 0) {
					continue;
				} else {
					if (0 < checkLinie(spielfeld, koord.getSpalte(), koord.getZeile(), x, y, farbe)) {
						flipLinie(spielfeld, koord.getSpalte(), koord.getZeile(), x, y, farbe);
					} else {

					}
				}
			}
		}
	}

	public static String[] getUebernommeneSteine() {
		return UebernommenesArray.uebernommen;
	}

	/**
	 * Geht von einer Startposition aus in die angegebene Richtung und flippt
	 * alle Steine die von der Gegnerfarbe sind.
	 *
	 * @param startX
	 *            x-Koordinate der Startposition.
	 * @param startY
	 *            y-Koordinate der Startposition.
	 * @param richtungX
	 *            x-teil der Richtung.
	 * @param richtungY
	 *            y-teil der Richtung.
	 * @param farbe
	 *            Farbe des Steins in den geflippt wird.
	 */
	public static void flipLinie(Stein[] spielfeld, int startX, int startY, int richtungX, int richtungY, Stein farbe) {
		Stein gegnerFarbe = farbe.umdrehen();
		richtungX = Integer.signum(richtungX);
		richtungY = Integer.signum(richtungY);
		int i = 0;
		if (richtungX == 0 && richtungY == 0) {
			throw new UnsupportedOperationException("Es wurde keine Richtung vorgegeben!");
		}
		// Gibt es in auf dieser Linie einen Spielstein unserer Farbe?
		while (true) {
			startX += richtungX;
			startY += richtungY;
			if (!istInSpielfeld(spielfeld, startX, startY)) {
				break;
			} else if (getSteinAt(spielfeld, startX, startY) == gegnerFarbe) {
				setSteinAt(spielfeld, farbe, startX, startY);
				String uebernommenesFeld = Koordinate.Parse(new Koordinate(startX, startY));
				UebernommenesArray.zwischenspeicher[i] = uebernommenesFeld;
				i++;
			} else if (getSteinAt(spielfeld, startX, startY) == farbe) {
				break;
			} else if (getSteinAt(spielfeld, startX, startY) == Stein.GESPERRT) {
				break;
			} else {
				break;
			}
		}

	}

	/**
	 * gibt zurueck ob eine angegebene Farbe ziehen kann.
	 *
	 * @param stein
	 *            zu ueberpruefende Spielerfarbe
	 * @return gibt zurueck ob eine Farbe ziehen kann
	 */
	public int zugMoeglichkeiten(Stein stein) {
		int zuege = 0;
		for (int i = 0; i < spielbrettGroesse; i++) {
			for (int j = 0; j < spielbrettGroesse; j++) {
				if (zugGueltig(Spielbrett.getSpielbrett(), new Koordinate(i, j), stein)) {
					zuege++;
				}
			}
		}
		return zuege;
	}

	public boolean kannZiehen(Stein stein) {
		if (isSetzphase) {
			return true;
		}
		return zugMoeglichkeiten(stein) > 0;
	}

	private static boolean istInSpielfeld(Stein[] spielfeld, int spalte, int zeile) {
		return spalte >= 0 && zeile >= 0 && spalte < getSpielbrettGroesse() && zeile < getSpielbrettGroesse();
	}

	private static Stein getSteinAt(Stein[] spielfeld, int spalte, int zeile) {
		return spielfeld[spalte + (zeile * (spielbrettGroesse))];
	}

	private static Stein getSteinAt(Stein[] spielfeld, Koordinate koord) {
		return getSteinAt(spielfeld, koord.getSpalte(), koord.getZeile());
	}

	private static void setSteinAt(Stein[] spielfeld, Stein stein, int spalte, int zeile) {
		spielfeld[spalte + (zeile * (spielbrettGroesse))] = stein;
	}

	static void setSteinAt(Stein[] spielfeld, Stein stein, Koordinate koord) {
		setSteinAt(spielfeld, stein, koord.getSpalte(), koord.getZeile());
	}

	/**
	 * gibt zurueck ob eine angegebene Farbe ziehen kann.
	 *
	 * @param stein
	 *            zu ueberpruefende Spielerfarbe
	 * @return gibt zurueck ob eine Farbe ziehen kann
	 */
	public static boolean kannZiehen(Stein[] spielfeld, Stein stein) {
		for (int i = 0; i < getSpielbrettGroesse(); i++) {
			for (int j = 0; j < getSpielbrettGroesse(); j++) {
				if (zugGueltig(spielfeld, new Koordinate(i, j), stein)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * aktualisiert den Punktestand
	 */
	private static void punkteBerechnen(Stein[] spielfeld) {
		punkteWeiss = 0;
		punkteSchwarz = 0;
		for (int i = 1; i < getSpielbrettGroesse() - 1; i++) {
			for (int j = 1; j < getSpielbrettGroesse() - 1; j++) {
				if (getSteinAt(spielfeld, i, j) == Stein.WEISS) {
					punkteWeiss++;
				}
				if (getSteinAt(spielfeld, i, j) == Stein.SCHWARZ) {
					punkteSchwarz++;
				}
			}
		}
	}

	public static int punkteWeiss() {
		return punkteWeiss;
	}

	public static int punkteSchwarz() {
		return punkteSchwarz;
	}

	public static int punkteFarbe(Stein farbe, Stein[] map) {
		punkteBerechnen(map);
		int ps = punkteSchwarz();
		int pw = punkteWeiss();
		punkteBerechnen(spielbrett);
		if (farbe == Stein.SCHWARZ) {
			return ps;
		}
		if (farbe == Stein.WEISS) {
			return pw;
		}
		return 0;
	}

	/**
	 * gibt den aktuellen Punktestand aus
	 */
	public String toString() {
		return "Weiss " + punkteWeiss + ":" + punkteSchwarz + " Schwarz";
	}

	public static Stein[] getSpielbrett() {
		return spielbrett;
	}

	public static int getSpielbrettGroesse() {
		return spielbrettGroesse;
	}

}
