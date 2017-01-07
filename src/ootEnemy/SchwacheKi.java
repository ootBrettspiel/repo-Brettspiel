package ootEnemy;

import java.util.LinkedList;

/**
 * Klasse die den einfachen Computergegner darstellt.
 *
 * @author Luca
 *
 */
public class SchwacheKi extends Ki {
	private Stein[] test;
	private LinkedList<Koordinate> moeglZuegeGegner = new LinkedList<>();
	private String besterZug = "";
	private int wenigstenZugMoegl;
	public LinkedList<Koordinate> moeglZuege = new LinkedList<>();
	private Koordinate[] ecke1;
	private Koordinate[] ecke2;

	/**
	 * Konstrukter der Klasse SchwacheKi mit aufruf des Superkonstruktors.
	 *
	 * @param board
	 *            Spielbrett auf dem gespielt wird.
	 * @param farbe
	 *            Farbe des Steins des Computergegners.
	 */
	protected SchwacheKi(final Stein farbe, String name) {
		super(farbe, name);
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
	 * spezielle Version der setzStein methode welche nur für die Setzphase
	 * gilt.
	 *
	 * @return
	 */
	protected Koordinate setzPhase() {
		int gr = Spielbrett.getSpielbrettGroesse();
		Koordinate[] best = { new Koordinate(0, 1), new Koordinate(1, 0), new Koordinate(0, gr - 2),
				new Koordinate(1, gr - 1), new Koordinate(gr - 2, 0), new Koordinate(gr - 1, 1),
				new Koordinate(gr - 1, gr - 2), new Koordinate(gr - 2, gr - 1) };

		if (ecke1 == null && ecke2 == null) {
			int t = (int) (Math.random() * 4);
			ecke1 = new Koordinate[2];
			ecke1[0] = best[2 * t];
			ecke1[1] = best[2 * t + 1];
			t = ++t % 4;
			ecke2 = new Koordinate[2];
			ecke2[0] = best[2 * t];
			ecke2[1] = best[2 * t + 1];
		}

		if (Spielbrett.zugGueltig(Spielbrett.getSpielbrett(), ecke1[0], getFarbe())) {
			return ecke1[0];
		}
		if (Spielbrett.zugGueltig(Spielbrett.getSpielbrett(), ecke1[1], getFarbe())) {
			return ecke1[1];
		}
		if (Spielbrett.zugGueltig(Spielbrett.getSpielbrett(), ecke2[0], getFarbe())) {
			return ecke2[0];
		}
		if (Spielbrett.zugGueltig(Spielbrett.getSpielbrett(), ecke2[1], getFarbe())) {
			return ecke2[1];
		}
		int t = (int) (Math.random() * 8);
		for (int c = 0; c < 8; c++) {
			if (Spielbrett.zugGueltig(Spielbrett.getSpielbrett(), best[(c + t) % 8], getFarbe())) {
				return best[(c + t) % 8];
			}
		}
		Koordinate random;
		while (true) {
			random = new Koordinate((int) Math.random() * gr, (int) Math.random() * gr);
			if (Spielbrett.zugGueltig(Spielbrett.getSpielbrett(), random, getFarbe())) {
				return random;
			}
		}
	}

	/**
	 * Methode die den besten Zug berechnet. Die Berechnung läuft nach dem
	 * Prinzip das geschaut wird bei welchem Zug der Gegner im nächsten Zug die
	 * wenigsten Möglichkeiten hat.
	 *
	 * @return Zug der gemacht werden soll.
	 */
	public String besterZug() {
		moeglZuege = new LinkedList<>();
		test = Spielbrett.getSpielbrett().clone();
		besterZug = "";
		getMoeglZuege(moeglZuege, this.getFarbe());
		wenigstenZugMoegl = 100;
		for (int i = 0; i < moeglZuege.size(); i++) {
			if (zugmoeglichkeitenGegner(moeglZuege.get(i)) < wenigstenZugMoegl) {
				wenigstenZugMoegl = zugmoeglichkeitenGegner(moeglZuege.get(i));
				besterZug = Koordinate.Parse(moeglZuege.get(i));
			}
		}
		return besterZug;
	}

	/**
	 * Methode die die Möglichen Züge des Gegners nach einem bestimmten Zug
	 * wären.
	 *
	 * @param koord
	 *            bestimmter zug der überprüft werden soll.
	 * @return Anzahl der möglichen Züge des Gegners.
	 */
	public int zugmoeglichkeitenGegner(Koordinate koord) {
		test = Spielbrett.getSpielbrett().clone();
		Spielbrett.setSteinAt(test, this.getFarbe(), koord);
		moeglZuegeGegner = new LinkedList<>();
		getMoeglZuege(moeglZuegeGegner, this.getFarbe().umdrehen());
		Spielbrett.setSteinAt(test, Stein.LEER, koord);
		return moeglZuegeGegner.size();
	}

}
