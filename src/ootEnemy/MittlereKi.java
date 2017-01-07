package ootEnemy;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Klasse die den mittleren Computergegner darstellt.
 *
 * @author Luca
 *
 */
public class MittlereKi extends Ki {
	static int[][] heatmap;
	private String dateipfad = "heatmap.bin";
	private int besterWert = -999999;
	private String besteKoord = "";
	public LinkedList<Koordinate> moeglZuege = new LinkedList<>();
	public LinkedList<Koordinate> moeglZuege2 = new LinkedList<>();

	/**
	 * Konstrukter der Klasse MittlereKi mit aufruf des Superkonstruktors.
	 *
	 * @param board
	 *            Spielbrett auf dem gespielt wird.
	 * @param farbe
	 *            Farbe des Steins des Computergegners.
	 */
	MittlereKi(final Stein farbe, String name) {
		super(farbe, name);
		try {
			this.heatmapLaden(dateipfad);
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
	 * Methode die den besten Zug des Benutzers auswählt. Die Auswahl folgt
	 * durch Kombination der Auswahl der Schweren und Schwachen Ki.
	 *
	 * @return Der beste Zug des Benutzers.
	 */
	public String besterZug() {
		moeglZuege = new LinkedList<>();
		besterWert = -999999;
		besteKoord = "A1";
		getMoeglZuege(moeglZuege, this.getFarbe());
		for (int i = 0; i < moeglZuege.size(); i++) {
			if (besterWert < funktion(moeglZuege.get(i))) {
				besterWert = funktion(moeglZuege.get(i));
				besteKoord = Koordinate.Parse(moeglZuege.get(i));
			}
		}
		if (moeglZuege.size() == 1) {
			return Koordinate.Parse(moeglZuege.get(0));
		}
		return besteKoord;
	}

	/**
	 * Methode die einen Funktionswert für eine Koordinate berechnet.
	 *
	 * @param koord
	 *            Koordinate für die der Funktionswert berechnet werden soll.
	 * @return Der berechnete Funktionswert.
	 */
	public int funktion(Koordinate koord) {
		return (heatmap[koord.getSpalte()][koord.getZeile()]
				+ Spielbrett.geflippteSteine(Spielbrett.getSpielbrett(), koord, this.getFarbe()) * 10
				+ getMoeglZuege(moeglZuege2, this.getFarbe()).size() * 10
				- getMoeglZuege(moeglZuege2, this.getFarbe().umdrehen()).size() * 10);
	}

	/**
	 * Lädt eine Heatmap nach welcher die Funktion die Werte berechnet.
	 *
	 * @param dateipfad
	 *            Dateipfad der Heatmap die verwendet werden soll.
	 * @throws IOException
	 *             Kann beim lesen entstehen.
	 */
	public void heatmapLaden(String dateipfad) throws IOException {
		File file = new File("test123.bin");
		ObjectSerializer serializer = new ObjectSerializer();
		heatmap = (int[][]) serializer.readFromFile(file);
		serializer.close_in();
	}

	/**
	 * Sonderfall der SetzStein Methode welche für die Setzphase verwendet wird.
	 *
	 * @return Koordinate die gesetzt werden soll.
	 */
	private Koordinate setzPhase() {
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



}
