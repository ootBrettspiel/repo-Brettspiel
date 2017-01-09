package ootTunier;

import java.util.concurrent.ExecutionException;

import oot.game.TournamentClient;
import oot.game.ITournament;
import oot.game.Token;

public class Tunier {
	static boolean FarbeP1 = true;
	static boolean FarbeP2 = false;
	static int p1Gewinnt = 0;
	static int p2Gewinnt = 0;

	public static void main(String[] args) throws ExecutionException {
		ITournament p1 = new TournamentClient(Token.CIRCLE);
		// ITournament p2 = new TournamentClient(Token.CIRCLE);
		//ITournament p1 = new TunierKi()
		ITournament p2 = new TunierKi();
		p1.initializeBoard(12);
		p2.initializeBoard(12);

		for (int i = 1; i <= 50; i++) { // 50 Spiele
			p1.startGame(FarbeP1);
			p2.startGame(FarbeP2);
			setzPhase(p1, p2);
			spielPhase(p1, p2, FarbeP1, FarbeP2);
			auswertung(p1, p2);
			System.out.println("Spiel NR: " + i + " beendet");
		}
		System.out.println("P1 Punkte: " + p1Gewinnt);
		System.out.println("P2 Punkte: " + p2Gewinnt);
	}

	public static void setzPhase(ITournament p1, ITournament p2) throws ExecutionException {
		for (int setzPhase = 0; setzPhase < 6; setzPhase++) { // 6 Zuege lang

			String zugP2 = p2.getBestTurn();
			zugP2 = zugP2.replaceAll(" ", "");
			System.out.println(zugP2);
			if (p1.isMoveValidInStartPhase(zugP2)) {
				p2.setStone(zugP2);
				System.out.println("P2 zieht nach: " + zugP2);
			} else {
				throw new ExecutionException(null, null);
			}
			p1.printBoard();
			p2.printBoard();
			String zugP1 = p1.getBestTurn();
			zugP1 = zugP1.replaceAll(" ", "");
			if (p2.isMoveValidInStartPhase(zugP1)) {
				p1.setStone(zugP1);
				System.out.println("P1 zieht nach: " + zugP1);
			} else {
				throw new ExecutionException(null);
			}
			p1.printBoard();
			p2.printBoard();
		}
	}

	public static void spielPhase(ITournament p1, ITournament p2, Boolean farbe1, Boolean farbe2)
			throws ExecutionException {
		while ((p1.canIMove() || p1.canYouMove())) {
			if (!(p1.getBestTurn().equals(""))) {
				String zugP1 = p1.getBestTurn();
				zugP1 = zugP1.replaceAll(" ", "");
				if (p2.isMoveValid(zugP1, farbe1)) {
					p1.setStone(zugP1);
				} else {
					if (!(p1.canIMove() || p1.canYouMove())) {
						break;
					}
					throw new ExecutionException(null);
				}
			}
			p1.printBoard();
			p2.printBoard();
			if (!(p2.getBestTurn().equals(""))) {
				String zugP2 = p2.getBestTurn();
				zugP2 = zugP2.replaceAll(" ", "");
				if (p1.isMoveValid(zugP2, farbe2)) {
					p2.setStone(zugP2);
				} else {
					if (!(p1.canIMove() || p1.canYouMove())) {
						break;
					}
					throw new ExecutionException(null, null);
				}
				p1.printBoard();
				p2.printBoard();
			}
		}
	}

	public static void auswertung(ITournament p1, ITournament p2) throws ExecutionException {
		if (p1.whoHasWon() == p2.whoHasWon() * (-1)) {
			if (p1.whoHasWon() == 1 && p2.whoHasWon() == -1) {
				p1Gewinnt += 3;
			}
			if (p1.whoHasWon() == -1 && p2.whoHasWon() == 1) {
				p2Gewinnt += 3;
			}
			if (p1.whoHasWon() == p2.whoHasWon()) {
				System.out.println("Unentschieden...");
				p1Gewinnt++;
				p2Gewinnt++;
			}
		} else {
			throw new ExecutionException(null);
		}
	}
}
