package ootEnemy;

import java.util.Date;
import java.util.Calendar;

public class Spielstand {
	private int eigenePunkte;
	private int gegnerPunkte;
	private int differenz;
	private String name;
	private Calendar calendar;
	private String Datum;
	private Date date;

	@SuppressWarnings("deprecation")
	public Spielstand(String name, int eigenePunkte, int gegnerPunkte) {
		this.eigenePunkte = eigenePunkte;
		this.gegnerPunkte = gegnerPunkte;
		this.name = name;
		calendar = Calendar.getInstance();
		date = calendar.getTime();
		Datum = date.toLocaleString();
		differenz = eigenePunkte - gegnerPunkte;
	}

	public Spielstand(String name, int eigenePunkte, int gegnerPunkte, String datum) {
		this.eigenePunkte = eigenePunkte;
		this.gegnerPunkte = gegnerPunkte;
		this.name = name;
		Datum = datum;
		differenz = eigenePunkte - gegnerPunkte;
	}

	public int getDifferenz() {
		return differenz;
	}

	public void getSpielstand() {
		System.out.println("Spiel beendet mit " + name + " :" + eigenePunkte + " Gegner : " + gegnerPunkte + " als "
				+ name + " am " + Datum);
	}

	public int getEigenePunkte() {
		return eigenePunkte;
	}

	public int getGegnerPunkte() {
		return gegnerPunkte;
	}

	public String getName() {
		return name;
	}

	public String getDatum() {
		return Datum;
	}

}
