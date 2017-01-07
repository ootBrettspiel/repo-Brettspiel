package ootEnemy;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Klasse die den starken Computergegner darstellt. Aktuelle Werte der Heatmap:
 * Ecken: 5.0 Rand: 3.0 Mitte:1.0
 *
 * @author Luca
 *
 */
public class StarkeKi extends Ki {
	private String besteKoord = "";
	private double besterWert = 0;
	public LinkedList<Koordinate> moeglZuege = new LinkedList<>();
	private LinkedList<Koordinate> moeglZuegeGegner = new LinkedList<>();
	static double[][] heatmap;
	private String dateipfad = "Hallo";

	/**
	 * Konstrukter der Klasse StarkeKi mit aufruf des Superkonstruktors.
	 *
	 * @param board
	 *            Spielbrett auf dem gespielt wird.
	 * @param farbe
	 *            Farbe des Steins des Computergegners.
	 */
	protected StarkeKi(final Stein farbe, String name) {
		super(farbe, name);
		try {
			this.heatmapLaden();
			this.heatmapZeigen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setzStein(String koordinate) {
		if (!koordinate.equals("")) {
			Spielbrett.setzeStein(Spielbrett.getSpielbrett(), Koordinate.Parse(koordinate), this.getFarbe());
		} else {
			if (Spielbrett.getSetzPhase()) {
				Spielbrett.setzeStein(Spielbrett.getSpielbrett(), setzPhase(), this.getFarbe());
			} else {
				Spielbrett.setzeStein(Spielbrett.getSpielbrett(), Koordinate.Parse(besterZug()), this.getFarbe());
			}
		}
	}

	/**
	 * Methode die den besten Zug berechnet. Diese berechnung entsteht durch
	 * eine Einfache Funktion, welche mithilfe einer Heatmap einen Wert
	 * erstellt.
	 *
	 * @return Zug der gemacht werden soll.
	 */
	public String besterZug() {
		moeglZuege = new LinkedList<>();
		besteKoord = "A1";
		besterWert = -999999999;
		getMoeglZuege(moeglZuege, this.getFarbe());
		for (int i = 0; i < moeglZuege.size(); i++) {
			if (besterWert < funktion(moeglZuege.get(i))) {
				besterWert = funktion(moeglZuege.get(i));
				besteKoord = Koordinate.Parse(moeglZuege.get(i));
				System.out.println(besterWert + " fuer " + besteKoord + " ausgew�hlt");
			}
			System.out.println(funktion(moeglZuege.get(i)) + " fuer " + Koordinate.Parse(moeglZuege.get(i)));
		}
		if (moeglZuege.size() == 1) {
			return Koordinate.Parse(moeglZuege.get(0));
		}
		return besteKoord;
	}

	/**
	 * Methode die den Wert eines Feldes berechnet.
	 *
	 * @param koord
	 *            Feld für das der Wert berechnet wird.
	 * @return Wert des Felde.
	 */
	public double funktion(Koordinate koord) {
		double wert = ((heatmap[koord.getSpalte()][koord.getZeile()] - zugmoeglichkeitenGegner(koord))
				+ Spielbrett.geflippteSteine(Spielbrett.getSpielbrett(), koord, this.getFarbe()));

		return wert;
	}

	/**
	 * Speichert eine Heatmap.
	 *
	 * @param ecken
	 *            Wert des Parameters für alle Eckfelder.
	 * @param rand
	 *            Wert des Parameters für alle Randfelder.
	 * @param innererRand
	 *            Wert des Parameters für alle innerenRandfelder.
	 * @param mitte
	 *            Wert des Parameters für alle restlichen Felder.
	 */
	public void heatmapSpeichern(double ecken, double rand, double innererRand, double mitte) {
		File file = new File(dateipfad + ".bin");
		ObjectSerializer serializer = new ObjectSerializer();
		heatmap = new double[Spielbrett.getSpielbrettGroesse()][Spielbrett.getSpielbrettGroesse()];
		for (int i = 0; i < heatmap.length; i++) {
			for (int j = 0; j < heatmap.length; j++) {
				heatmap[i][j] = mitte;
			}
		}
		for (int k = 0; k < heatmap.length; k++) {
			heatmap[0][k] = rand;
			heatmap[k][0] = rand;
			heatmap[heatmap.length - 1][k] = rand;
			heatmap[k][heatmap.length - 1] = rand;
		}
		heatmap[0][0] = ecken;
		heatmap[0][heatmap.length - 1] = ecken;
		heatmap[heatmap.length - 1][0] = ecken;
		heatmap[heatmap.length - 1][heatmap.length - 1] = ecken;
		for (int l = 1; l < heatmap.length - 1; l++) {
			heatmap[1][l] = innererRand;
			heatmap[l][1] = innererRand;
			heatmap[heatmap.length - 2][l] = innererRand;
			heatmap[l][heatmap.length - 2] = innererRand;
		}
		serializer.save2file(heatmap, file);
	}

	/**
	 * Lädt eine zuvor gespeicherte Heatmap.
	 *
	 * @throws IOException
	 *             Tritt beim lesen auf.
	 */
	public void heatmapLaden() throws IOException {
		File file = new File(dateipfad + ".bin");
		ObjectSerializer serializer = new ObjectSerializer();
		heatmap = (double[][]) serializer.readFromFile(file);
		serializer.close_in();
	}

	/**
	 * Berechnet die Zugmöglichkeiten des Gegners nach einem bestimmten Zug.
	 *
	 * @param koord
	 *            Koordinate die geprüft wird.
	 * @return Anzahl der Zugmöglichkeiten des Gegners nach dem Ausgewählten
	 *         zug.
	 */
	public int zugmoeglichkeitenGegner(Koordinate koord) {
		Stein[] test = Spielbrett.getSpielbrett().clone();
		Spielbrett.setzeStein(test, koord, this.getFarbe());
		moeglZuegeGegner = new LinkedList<>();
		getMoeglZuege(moeglZuegeGegner, this.getFarbe().umdrehen());
		return moeglZuegeGegner.size();
	}

	/**
	 * Berechnet einen Zug für die Setzphase.
	 *
	 * @return Zug der gemacht werden soll.
	 */
	protected Koordinate setzPhase() {
		Koordinate rand;
		while (true) {
			rand = new Koordinate((int) (Math.random() * Spielbrett.getSpielbrettGroesse()),
					(int) (Math.random() * Spielbrett.getSpielbrettGroesse()));
			if (Spielbrett.zugGueltig(Spielbrett.getSpielbrett(), rand, getFarbe())) {
				break;
			}
		}
		return rand;
	}

	public void heatmapZeigen() {
		for (int i = 0; i < heatmap.length; i++) {
			for (int j = 0; j < heatmap.length; j++) {
				System.out.print("  " + heatmap[i][j] + "  ");
			}
			System.out.println();
		}
	}

}
