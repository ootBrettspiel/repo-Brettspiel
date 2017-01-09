package ootTunier;

import oot.game.ITournament;
import ootEnemy.Koordinate;
import ootEnemy.SchwacheKi;
import ootEnemy.Spielbrett;
import ootEnemy.StarkeKi;
import ootEnemy.Stein;

public class TunierKi extends SchwacheKi implements ITournament {
	static Spielbrett spielbrett;

	public TunierKi() {
		super(Stein.SCHWARZ, "Ki Datacorp");
		spielbrett = new Spielbrett(10);
	}

	@Override
	public void initializeBoard(int size) {
		spielbrett = new Spielbrett(size);
	}

	@Override
	public void printBoard() {
		Spielbrett.zeigeSpielbrett(Spielbrett.getSpielbrett());
	}

	@Override
	public void startGame(boolean isWhitePlayer) {
		Spielbrett.setUp();
		setFarbe(isWhitePlayer ? Stein.WEISS : Stein.SCHWARZ);
	}

	@Override
	public int whoHasWon() {
		if (Spielbrett.kannZiehen(Spielbrett.getSpielbrett(), Stein.SCHWARZ)
				|| Spielbrett.kannZiehen(Spielbrett.getSpielbrett(), Stein.WEISS)) {
			return 0;
		} else {
			if (Spielbrett.punkteFarbe(getFarbe(), Spielbrett.getSpielbrett()) > Spielbrett
					.punkteFarbe(getFarbe().umdrehen(), Spielbrett.getSpielbrett())) {
				return 1;
			}
			if (Spielbrett.punkteFarbe(getFarbe(), Spielbrett.getSpielbrett()) < Spielbrett
					.punkteFarbe(getFarbe().umdrehen(), Spielbrett.getSpielbrett())) {
				return -1;
			}
			if (Spielbrett.punkteFarbe(getFarbe(), Spielbrett.getSpielbrett()) == Spielbrett
					.punkteFarbe(getFarbe().umdrehen(), Spielbrett.getSpielbrett())) {
				return 0;
			}
		}
		return 0;

	}

	@Override
	public boolean canIMove() {
		return Spielbrett.kannZiehen(Spielbrett.getSpielbrett(), getFarbe());
	}

	@Override
	public boolean canYouMove() {
		return Spielbrett.kannZiehen(Spielbrett.getSpielbrett(), getFarbe().umdrehen());
	}

	@Override
	public String getBestTurn() {
		return besterZug();
	}

	@Override
	public String[] setStone(String coordinate) {
		setzStein(coordinate);
		return Spielbrett.getUebernommeneSteine();
	}

	@Override
	public void setStoneInStartPhase(String coordinate) {
		setzStein(setzPhase().toString());
	}

	@Override
	public boolean isMoveValid(String coordinate, boolean isWhite) {
		boolean moeglich = Spielbrett.zugGueltig(Spielbrett.getSpielbrett(), Koordinate.Parse(coordinate),
				(isWhite) ? Stein.WEISS : Stein.SCHWARZ);
		if (moeglich) {
			Spielbrett.setzeStein(Spielbrett.getSpielbrett(), Koordinate.Parse(coordinate),
					(isWhite) ? Stein.WEISS : Stein.SCHWARZ);
		}
		return moeglich;
	}

	@Override
	public boolean isMoveValidInStartPhase(String coordinate) {
		boolean moeglich = Spielbrett.zugGueltig(Spielbrett.getSpielbrett(), Koordinate.Parse(coordinate),
				Stein.WEISS);
		if (moeglich) {
			Spielbrett.setzeStein(Spielbrett.getSpielbrett(), Koordinate.Parse(coordinate),
					Stein.WEISS);
		}
		return moeglich;
		//return Spielbrett.zugGueltig(Spielbrett.getSpielbrett(), Koordinate.Parse(coordinate), getFarbe());
	}
}
