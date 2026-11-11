//Vicente Guerra - 21.855.415-6 - ITI
//Diego Meneses - 22.087.010-3 - ICCI

package logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * punto de entrada del taller 03 "spooky season". el main se encarga solo de
 * la entrada y salida: lee archivos y teclado, muestra menús y le pide todo
 * lo demás al sistema.
 */
public class Main {

	private static Sistema sistema = new SistemaImpl();

	/**
	 * inicia el programa.
	 *
	 * @param args no se usan
	 */
	public static void main(String[] args) {
		System.out.println("Cargando archivos...");
		leerMonstruos();
		leerRegistros();
		System.out.println(" > " + sistema.getCantidadMonstruos() + " monstruos cargados desde Monstruos.txt");
		System.out.println(" > " + sistema.getCantidadJugadores() + " jugadores cargados desde Registros.txt");

		// sin monstruos no se puede jugar nada
		if (sistema.getCantidadMonstruos() == 0) {
			System.out.println("[!] No hay ningún monstruo válido en Monstruos.txt. Cerrando el programa.");
			return;
		}

		System.out.println();
		System.out.println("=========================================");
		System.out.println("             SPOOKY SEASON");
		System.out.println("=========================================");
	}

	// ---------------- lectura de archivos ----------------

	/**
	 * lee Monstruos.txt línea por línea y le pasa cada monstruo válido al sistema.
	 */
	private static void leerMonstruos() {
		try {
			Scanner lector = new Scanner(new File("Monstruos.txt"));
			int numeroLinea = 0;
			while (lector.hasNextLine()) {
				String linea = lector.nextLine();
				numeroLinea++;
				procesarLineaMonstruo(linea, numeroLinea);
			}
			lector.close();
		} catch (FileNotFoundException e) {
			System.out.println("[!] No se encontró el archivo Monstruos.txt");
		}
	}

	/**
	 * separa una línea de Monstruos.txt y la envía al sistema. si la línea
	 * tiene algún problema se descarta y se avisa por pantalla.
	 *
	 * @param linea       la línea leída
	 * @param numeroLinea número de la línea en el archivo
	 */
	private static void procesarLineaMonstruo(String linea, int numeroLinea) {
		if (linea.length() == 0) {
			descartar("Monstruos.txt", numeroLinea, linea, "línea vacía");
			return;
		}

		String[] partes = linea.split(";");
		if (partes.length == 0 || partes[0].length() == 0) {
			descartar("Monstruos.txt", numeroLinea, linea, "no tiene tipo de monstruo");
			return;
		}

		// el tipo solo se usa para saber cuántos datos trae la línea y a qué
		// operación del sistema mandarlos (no para calcular nada)
		String tipo = partes[0];
		int camposEsperados = camposSegunTipo(tipo);
		if (camposEsperados == -1) {
			descartar("Monstruos.txt", numeroLinea, linea, "tipo desconocido: " + tipo);
			return;
		}
		if (partes.length != camposEsperados) {
			descartar("Monstruos.txt", numeroLinea, linea,
					"tiene " + partes.length + " campos y se esperaban " + camposEsperados);
			return;
		}

		// todos los atributos después del tipo son enteros
		int[] valores = new int[partes.length - 1];
		try {
			for (int i = 1; i < partes.length; i++) {
				valores[i - 1] = Integer.valueOf(partes[i]);
			}
		} catch (NumberFormatException e) {
			descartar("Monstruos.txt", numeroLinea, linea, "tiene valores que no son números enteros");
			return;
		}

		boolean agregado = false;
		if (tipo.equals("Zombi")) {
			agregado = sistema.agregarZombi(valores[0], valores[1], valores[2], valores[3], valores[4]);
		} else if (tipo.equals("Vampiro")) {
			agregado = sistema.agregarVampiro(valores[0], valores[1], valores[2], valores[3], valores[4],
					valores[5]);
		} else if (tipo.equals("Momia")) {
			agregado = sistema.agregarMomia(valores[0], valores[1], valores[2], valores[3], valores[4]);
		} else if (tipo.equals("HombreLobo")) {
			agregado = sistema.agregarHombreLobo(valores[0], valores[1], valores[2], valores[3], valores[4],
					valores[5]);
		}

		if (!agregado) {
			descartar("Monstruos.txt", numeroLinea, linea, "tiene valores fuera de rango");
		}
	}

	/**
	 * indica cuántos campos debe tener una línea según el tipo de monstruo
	 * (contando el tipo).
	 *
	 * @param tipo el tipo leído
	 * @return la cantidad de campos, o -1 si el tipo no existe
	 */
	private static int camposSegunTipo(String tipo) {
		if (tipo.equals("Zombi") || tipo.equals("Momia")) {
			return 6;
		}
		if (tipo.equals("Vampiro") || tipo.equals("HombreLobo")) {
			return 7;
		}
		return -1;
	}

	/**
	 * lee Registros.txt. si el archivo no existe se parte sin jugadores y se
	 * crea al guardar.
	 */
	private static void leerRegistros() {
		try {
			Scanner lector = new Scanner(new File("Registros.txt"));
			int numeroLinea = 0;
			while (lector.hasNextLine()) {
				String linea = lector.nextLine();
				numeroLinea++;
				procesarLineaRegistro(linea, numeroLinea);
			}
			lector.close();
		} catch (FileNotFoundException e) {
			System.out.println(" > No existe Registros.txt, se parte sin jugadores (se creará al guardar).");
		}
	}

	/**
	 * separa una línea de Registros.txt (Jugador;PuntajeMaximo) y la envía al sistema.
	 *
	 * @param linea       la línea leída
	 * @param numeroLinea número de la línea en el archivo
	 */
	private static void procesarLineaRegistro(String linea, int numeroLinea) {
		if (linea.length() == 0) {
			descartar("Registros.txt", numeroLinea, linea, "línea vacía");
			return;
		}

		String[] partes = linea.split(";");
		if (partes.length != 2) {
			descartar("Registros.txt", numeroLinea, linea,
					"tiene " + partes.length + " campos y se esperaban 2");
			return;
		}

		int puntaje;
		try {
			puntaje = Integer.valueOf(partes[1]);
		} catch (NumberFormatException e) {
			descartar("Registros.txt", numeroLinea, linea, "el puntaje no es un número entero");
			return;
		}

		if (!sistema.agregarJugador(partes[0], puntaje)) {
			descartar("Registros.txt", numeroLinea, linea, "nombre vacío o repetido, o puntaje negativo");
		}
	}

	/**
	 * avisa por pantalla que se ignoró una línea de un archivo.
	 */
	private static void descartar(String archivo, int numeroLinea, String linea, String motivo) {
		System.out.println("[!] " + archivo + " línea " + numeroLinea + " descartada (" + motivo + "): \""
				+ linea + "\"");
	}
}
