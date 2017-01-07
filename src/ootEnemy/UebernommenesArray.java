package ootEnemy;

public class UebernommenesArray {
	static String[] uebernommen;
	static String[] zwischenspeicher = new String[40];

	public UebernommenesArray() {
		setZwischenspeicher();
	}

	public static void setZwischenspeicher() {
		for (int i = 0; i < zwischenspeicher.length; i++) {
			zwischenspeicher[i] = "leer";
		}

	}

	public static String[] getUebernommen() {
		setZwischenspeicher();
		int groesse = 0;
		for (int i = 0; i < zwischenspeicher.length; i++) {
			if (zwischenspeicher[i].equals("leer")) {
				groesse = i;
				break;
			}
		}

		uebernommen = new String[groesse];
		for (int j = 0; j < groesse; j++) {
			uebernommen[j] = zwischenspeicher[j];
		}
		System.out.println();
		for (int i = 0; i < UebernommenesArray.uebernommen.length; i++) {
			System.out.print(UebernommenesArray.uebernommen[i]);
		}
		System.out.println();
		return uebernommen;
	}
}
