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
	private static Scanner teclado = new Scanner(System.in);

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

		// cada vuelta es una sesión de un jugador distinto
		boolean salir = false;
		while (!salir) {
			String nombre = iniciarSesion();
			salir = menuPrincipal(nombre);
		}
		teclado.close();
		System.out.println("¡Hasta la próxima noche de brujas!");
	}

	// ---------------- inicio de sesión y menú ----------------

	/**
	 * pide el nombre de usuario hasta que sea válido, da la bienvenida e inicia
	 * una sesión nueva en el sistema.
	 *
	 * @return el nombre del jugador que inició sesión
	 */
	private static String iniciarSesion() {
		System.out.println();
		System.out.println("=========================================");
		System.out.println("             SPOOKY SEASON");
		System.out.println("=========================================");

		String nombre = "";
		boolean valido = false;
		while (!valido) {
			System.out.print("Ingrese su nombre de usuario: ");
			nombre = teclado.nextLine();
			if (nombre.length() == 0) {
				System.out.println("[!] El nombre no puede estar vacío.");
			} else if (nombre.indexOf(";") != -1) {
				System.out.println("[!] El nombre no puede contener el carácter ';'.");
			} else {
				valido = true;
			}
		}

		System.out.println();
		if (sistema.existeJugador(nombre)) {
			System.out.println("¡Bienvenido de vuelta, " + nombre + "! Tu récord actual es de "
					+ sistema.obtenerRecord(nombre) + " puntos.");
		} else {
			System.out.println("¡Bienvenido, " + nombre + "! Es tu primera vez jugando.");
		}
		sistema.iniciarSesion(nombre);
		return nombre;
	}

	/**
	 * muestra el menú principal hasta que el jugador termine la sesión o salga.
	 *
	 * @param nombre nombre del jugador de la sesión
	 * @return true si el jugador eligió salir del programa
	 */
	private static boolean menuPrincipal(String nombre) {
		while (true) {
			System.out.println();
			System.out.println("------ MENÚ PRINCIPAL (" + nombre + ") ------");
			System.out.println("1) Jugar un minijuego");
			System.out.println("2) Ver catálogo de monstruos");
			System.out.println("3) Ver historial de la sesión");
			System.out.println("4) Ver ranking de jugadores");
			System.out.println("5) Terminar sesión");
			System.out.println("6) Salir");

			int opcion = leerOpcion(1, 6);
			switch (opcion) {
			case 1:
				jugarMinijuego();
				break;
			case 5:
				System.out.println("Sesión terminada.");
				return false;
			case 6:
				return true;
			default:
				System.out.println("[!] Opción en construcción.");
			}
		}
	}

	// ---------------- jugar un minijuego ----------------

	/**
	 * flujo completo para jugar un minijuego: elegir minijuego, elegir monstruo,
	 * ver potencial, elegir habilidad y ver el puntaje.
	 */
	private static void jugarMinijuego() {
		int indiceMinijuego = elegirMinijuego();
		if (indiceMinijuego == -1) {
			return;
		}
		String nombreMinijuego = sistema.obtenerNombreMinijuego(indiceMinijuego);
		System.out.println();
		System.out.println(nombreMinijuego + ": " + sistema.obtenerDescripcionMinijuego(indiceMinijuego));

		int posicion = elegirMonstruo();
		if (posicion == -1) {
			return;
		}

		System.out.println();
		System.out.println("Te comes un dulce de dudosa procedencia...");
		double potencial = sistema.calcularPotencial(indiceMinijuego, posicion);
		System.out.println("Potencial para " + nombreMinijuego + ": " + dosDecimales(potencial));

		String habilidad = elegirHabilidad(indiceMinijuego, posicion);

		int puntaje = sistema.jugarMinijuego(indiceMinijuego, posicion, habilidad);
		System.out.println();
		System.out.println(">> Puntaje obtenido: " + puntaje + " puntos");
	}

	/**
	 * lista los minijuegos con su estado y pide uno que siga pendiente.
	 *
	 * @return el índice del minijuego elegido, o -1 si el jugador vuelve
	 */
	private static int elegirMinijuego() {
		System.out.println();
		System.out.println("------ MINIJUEGOS ------");
		int cantidad = sistema.getCantidadMinijuegos();
		for (int i = 0; i < cantidad; i++) {
			String estado = "[PENDIENTE]";
			if (sistema.minijuegoJugado(i)) {
				estado = "[JUGADO - " + sistema.obtenerPuntajeMinijuego(i) + " pts]";
			}
			System.out.println((i + 1) + ") " + rellenar(sistema.obtenerNombreMinijuego(i), 24) + estado);
		}
		System.out.println("0) Volver");

		while (true) {
			int opcion = leerOpcion(0, cantidad);
			if (opcion == 0) {
				return -1;
			}
			if (sistema.minijuegoJugado(opcion - 1)) {
				// no se puede repetir un minijuego en la misma sesión
				System.out.println("[!] Ya jugaste " + sistema.obtenerNombreMinijuego(opcion - 1)
						+ " en esta sesión.");
			} else {
				return opcion - 1;
			}
		}
	}

	/**
	 * lista los monstruos que quedan disponibles y pide uno.
	 *
	 * @return la posición del monstruo dentro de los disponibles, o -1 si vuelve
	 */
	private static int elegirMonstruo() {
		System.out.println();
		System.out.println("Elige el monstruo en el que te transformarás:");
		int disponibles = sistema.getCantidadMonstruosDisponibles();
		for (int i = 0; i < disponibles; i++) {
			// si hay 10 o más, se rellena el número para que las columnas no se corran
			String numero = (i + 1) + ")";
			if (disponibles >= 10) {
				numero = rellenar(numero, 3);
			}
			System.out.println(numero + " " + sistema.obtenerDatosMonstruoDisponible(i));
		}
		System.out.println("0) Volver");

		int opcion = leerOpcion(0, disponibles);
		return opcion - 1;
	}

	/**
	 * muestra las habilidades del monstruo y deja usar como máximo una.
	 *
	 * @param indiceMinijuego minijuego que se está jugando
	 * @param posicion        monstruo elegido
	 * @return la habilidad usada, o "Ninguna"
	 */
	private static String elegirHabilidad(int indiceMinijuego, int posicion) {
		String[] habilidades = sistema.obtenerHabilidades(posicion);
		System.out.println();
		System.out.println("¿Quieres usar una habilidad?");
		for (int i = 0; i < habilidades.length; i++) {
			System.out.println((i + 1) + ") " + habilidades[i]);
		}
		System.out.println("0) No usar habilidad");

		int opcion = leerOpcion(0, habilidades.length);
		if (opcion == 0) {
			return "Ninguna";
		}

		String habilidad = habilidades[opcion - 1];
		System.out.println(sistema.usarHabilidad(posicion, habilidad));
		if (sistema.esHabilidadPremiada(indiceMinijuego, habilidad)) {
			System.out.println("¡La habilidad fue efectiva! Puntaje x1.5");
		} else {
			System.out.println("La habilidad no tuvo efecto en este minijuego.");
		}
		return habilidad;
	}

	// ---------------- lectura de opciones y formato ----------------

	/**
	 * lee una opción del teclado hasta que sea un número entre min y max.
	 *
	 * @param min opción mínima válida
	 * @param max opción máxima válida
	 * @return la opción elegida
	 */
	private static int leerOpcion(int min, int max) {
		while (true) {
			System.out.print("> ");
			String linea = teclado.nextLine();
			try {
				int opcion = Integer.valueOf(linea);
				if (opcion >= min && opcion <= max) {
					return opcion;
				}
				System.out.println("[!] Opción fuera de rango. Intente nuevamente.");
			} catch (NumberFormatException e) {
				System.out.println("[!] Debe ingresar un número. Intente nuevamente.");
			}
		}
	}

	/**
	 * deja un double con 2 decimales (por ejemplo 110.0 queda "110.00").
	 *
	 * @param valor número a mostrar (no negativo)
	 * @return el número como texto con 2 decimales
	 */
	private static String dosDecimales(double valor) {
		long centesimas = Math.round(valor * 100);
		long parteEntera = centesimas / 100;
		long parteDecimal = centesimas % 100;
		if (parteDecimal < 10) {
			return parteEntera + ".0" + parteDecimal;
		}
		return parteEntera + "." + parteDecimal;
	}

	/**
	 * agrega espacios a la derecha hasta completar el largo pedido.
	 *
	 * @param texto texto a rellenar
	 * @param largo largo mínimo
	 * @return el texto con espacios al final
	 */
	private static String rellenar(String texto, int largo) {
		String resultado = texto;
		while (resultado.length() < largo) {
			resultado = resultado + " ";
		}
		return resultado;
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
