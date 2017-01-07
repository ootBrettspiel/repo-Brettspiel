package ootEnemy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class spielstandListe {
	static LinkedList<Spielstand> spielstaende = new LinkedList<>();
	static File file = new File("spielstandListe.txt");
	ObjectSerializer serializer = new ObjectSerializer();
	private static final int BEGRENZUNG = 10;
	private boolean isHighscore = false;

	public spielstandListe() throws IOException {
		// spielstaende.add(new Spielstand("leer", 0, 0));
		// speicherSpielstandListe();
	}

	public static void main(String[] args) throws IOException {
		spielstandListe spl = new spielstandListe();
		spl.isHighscore(new Spielstand("hallo2", 3, 0));

		spl.getSpielstandListe();
	}

	public void getSpielstandListe() throws IOException {
		System.out.println("Die 10 besten Spielstände sind:");
		ladeSpielstandListe();
		for (int i = 0; i < spielstaende.size(); i++) {
			System.out.print(i + 1 + ".Platz ");
			spielstaende.get(i).getSpielstand();
		}
	}

	public static void speicherSpielstandListe() throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		for (int y = 0; y < spielstaende.size(); y++) {
			Spielstand sps = spielstaende.get(y);
			bw.write(sps.getName() + "," + sps.getEigenePunkte() + "," + sps.getGegnerPunkte() + "," + sps.getDatum()
					+ ",");
			bw.write("\n");
		}
		bw.close();
	}

	public void ladeSpielstandListe() throws IOException {
		FileReader fis = new FileReader(file);
		BufferedReader br = new BufferedReader(fis);
		spielstandListe.spielstaende.clear();
		for (int y = 0; y < BEGRENZUNG; y++) {
			String[] row;
			try {
				row = br.readLine().split(",");
			} catch (NullPointerException e) {
				break;
			}
			spielstandListe.spielstaende
					.add(new Spielstand(row[0], Integer.parseInt(row[1]), Integer.parseInt(row[2]), row[3]));
		}
		br.close();
	}

	public boolean isHighscore(Spielstand sps) throws IOException {
		isHighscore = false;
		ladeSpielstandListe();
		if ((spielstaende.size() == 0) || sps.getDifferenz() >= spielstaende.getLast().getDifferenz()) {
			isHighscore = true;
			setHighscore(sps);
			return isHighscore;
		}
		System.out.println("Es ist kein Highscore :(");
		return isHighscore;
	}

	private void setHighscore(Spielstand sps) throws IOException {
		int j = 0;
		if (spielstaende.size() >= 2) {
			do {
				if (sps.getDifferenz() >= spielstaende.get(j + 1).getDifferenz()) {
					spielstaende.add(j + 1, sps);
					break;
				}
				j++;
			} while (sps.getDifferenz() < spielstaende.get(j).getDifferenz());
		} else {
			spielstaende.addFirst(sps);
		}
		if (spielstaende.size() > 10) {
			spielstaende.removeLast();
		}
		spielstandListe.speicherSpielstandListe();

	}

}
