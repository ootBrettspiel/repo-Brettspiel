package oot.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Highscore {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Highscore x = new Highscore();
		x.print();
		x.printHighscore();
	}

	/**
	 * @author Nico Gensheimer
	 * @param
	 * @throws IOException
	 */





	// Pfad zum speichern des Highscroes
	private String path = "C:/Users/" + System.getProperty("user.name") + "/Documents/highscore.txt";


	public void print(/* Player player */) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(path, true));
		out.println("3"/* player.getName()/*+highscore */);
		out.close();
	}

	public void printHighscore() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));

		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			System.out.println(line);

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				if (line != null) {
					System.out.println(line);
				}
			}
			String everything = sb.toString();
		} finally {
			br.close();
		}
	}
}
