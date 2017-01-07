package ootEnemy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Speichert Objekte als Binärdatei.
 *
 * @author Luca
 *
 */
public class ObjectSerializer {
	private FileOutputStream FOS = null;
	private ObjectOutputStream OOS = null;
	private FileInputStream FIS = null;
	private ObjectInputStream OIS = null;

	public ObjectSerializer() {
	}

	public void save2file(Object obj, File file) {
		if (OOS == null || FOS == null) {
			open_out(file);
		}

		try {
			OOS.writeObject(obj);
		} catch (IOException ioe) {
			System.err.println("Error: Could not serialize object.");
			ioe.printStackTrace(System.err);
			System.exit(1);
		}
	}

	public Object readFromFile(File file) {
		if (OIS == null || FIS == null) {
			open_in(file);
		}

		try {
			Object obj = (Object) OIS.readObject();
			return obj;
		} catch (IOException ioe) {
			System.err.println("Error: Could not deserialize object.");
			ioe.printStackTrace(System.err);
			System.exit(1);
		} catch (ClassNotFoundException cnfe) {
			System.err.println("Error: Could not find class!");
			cnfe.printStackTrace(System.err);
			System.exit(1);
		}
		return null;
	}

	private void open_out(File file) {
		if (OOS != null || FOS != null) {
			close_out();
		}

		try {
			FOS = new FileOutputStream(file);
			OOS = new ObjectOutputStream(FOS);
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
			ioe.printStackTrace(System.out);
			System.exit(1);
		}
	}

	private void open_in(File file) {
		if (FIS != null || OIS != null) {
			close_in();
		}

		try {
			FIS = new FileInputStream(file);
			OIS = new ObjectInputStream(FIS);
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
			ioe.printStackTrace(System.out);
			System.exit(1);
		}
	}

	public void close_out() {
		if (OOS != null && FOS != null) {
			try {
				OOS.close();
				OOS = null;
				FOS.close();
				FOS = null;
			} catch (IOException ioe) {
				System.err.println(ioe.getMessage());
				ioe.printStackTrace(System.out);
				System.exit(1);
			}
		}

	}

	public void close_in() {
		if (OIS != null && FIS != null) {
			try {
				OIS.close();
				OIS = null;
				FIS.close();
				FIS = null;
			} catch (IOException ioe) {
				System.err.println(ioe.getMessage());
				ioe.printStackTrace(System.out);
				System.exit(1);
			}
		}
	}
}
