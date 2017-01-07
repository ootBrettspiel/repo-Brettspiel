package ootEnemy;

import java.util.LinkedList;

public class RecursiveKi extends Ki {
	private Koordinate[] ecke1;
	private Koordinate[] ecke2;
	private int stack = 0;

	RecursiveKi(final Stein farbe, String name) {
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
				Spielbrett.setzeStein(Spielbrett.getSpielbrett(), rekursiveBest(), this.getFarbe());
			}
		}
	}

	private Koordinate setzPhase() {
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

	private Koordinate rekursiveBest(Koordinate neu, Stein[] map, Stein farbe) {
		if (stack > 2000) {
			stack--;
			return null;
		}
		System.out.println("---------------" + stack++);
		Stein gegfarbe = farbe.umdrehen();
		// map = Spielbrett.setzeStein(map, neu, farbe);
		if (hatGewonnen(getFarbe(), map)) {
			System.out.println("Weg gefunden!!!!");
			return neu;
		}
		if (hatGewonnen(getFarbe().umdrehen(), map)) {
			System.out.println("Weg abgebrochen!!!! Gegner gewonnen.");
			return null;
		}
		if (!Spielbrett.kannZiehen(map, farbe.umdrehen())) {
			System.out.println("Weg gefunden!!!!");
			return rekursiveBest(null, map, gegfarbe);
		}
		LinkedList<Koordinate> moeglZuegeGegner = new LinkedList<>();
		getMoeglZuege(moeglZuegeGegner, farbe.umdrehen(), map);
		for (Koordinate koordinate : moeglZuegeGegner) {
			// Spielbrett.zeigeSpielbrett(map);
			if (rekursiveBest(koordinate, map, farbe) != null) {
				return koordinate;
			} else {
				return setztRandom();
			}
		}
		return null;
	}

	private Koordinate rekursiveBest() {
		LinkedList<Koordinate> moeglZuegeGegner = new LinkedList<>();
		getMoeglZuege(moeglZuegeGegner, getFarbe().umdrehen(), Spielbrett.getSpielbrett());
		for (Koordinate koordinate : moeglZuegeGegner) {
			Stein[] map = null;
			// map = Spielbrett.setzeStein(Spielbrett.getSpielbrett(),
			// koordinate, getFarbe());
			if (rekursiveBest(koordinate, map, getFarbe()) != null) {
				return koordinate;
			} else {
				System.out.println("random pick");
				return setztRandom();
			}
		}
		return null;
	}

	private Koordinate setztRandom() {
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

	public LinkedList<Koordinate> getMoeglZuege(LinkedList<Koordinate> moeglZuege, Stein farbe, Stein[] map) {
		for (int i = 1; i <= Spielbrett.getSpielbrettGroesse(); i++) {
			for (int j = 1; j <= Spielbrett.getSpielbrettGroesse(); j++) {
				if (Spielbrett.zugGueltig(map, new Koordinate(i, j), farbe) == true) {
					moeglZuege.add(new Koordinate(i, j));
				}
			}
		}
		return moeglZuege;
	}

	private boolean isFull(Stein[] map) {
		for (Stein stein : map) {
			if (stein != Stein.LEER) {
				return false;
			}
		}
		return true;
	}

	private boolean hatGewonnen(Stein farbe, Stein[] map) {
		int pe = Spielbrett.punkteFarbe(farbe, map);
		int pg = Spielbrett.punkteFarbe(farbe.umdrehen(), map);
		if (pe == 0) {
			return false;
		}
		if (pg == 0) {
			return true;
		}
		if (isFull(map) && pe > pg) {
			return true;
		} else {
			return false;
		}

	}
}
