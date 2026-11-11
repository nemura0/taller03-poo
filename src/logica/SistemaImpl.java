package logica;

import java.util.ArrayList;
import java.util.LinkedList;

import dominio.CarreraCementerio;
import dominio.CazaCalabazas;
import dominio.Furioso;
import dominio.HombreLobo;
import dominio.Jugada;
import dominio.Jugador;
import dominio.LanzamientoLapidas;
import dominio.Minijuego;
import dominio.Momia;
import dominio.Monstruo;
import dominio.Mordedor;
import dominio.Regenerable;
import dominio.SobrevivirAmanecer;
import dominio.Vampiro;
import dominio.Volador;
import dominio.Zombi;

/**
 * implementación del sistema de spooky season.
 */
public class SistemaImpl implements Sistema {

	private ArrayList<Monstruo> monstruos;
	private ArrayList<Jugador> jugadores;
	private ArrayList<Minijuego> minijuegos;

	// historial de la sesión actual, en el orden en que se jugó
	private LinkedList<Jugada> historial;
	private String jugadorActual;

	public SistemaImpl() {
		this.monstruos = new ArrayList<>();
		this.jugadores = new ArrayList<>();
		this.minijuegos = new ArrayList<>();
		this.historial = new LinkedList<>();
		this.jugadorActual = null;

		// los 4 minijuegos, en el orden del menú
		minijuegos.add(new CarreraCementerio());
		minijuegos.add(new CazaCalabazas());
		minijuegos.add(new LanzamientoLapidas());
		minijuegos.add(new SobrevivirAmanecer());
	}

	// ---------------- carga de datos ----------------

	@Override
	public boolean agregarZombi(int vida, int velocidad, int fuerza, int resistencia, int fuerzaMordida) {
		if (!baseValida(vida, velocidad, fuerza, resistencia) || !enRango(fuerzaMordida, 1, 100)) {
			return false;
		}
		monstruos.add(new Zombi(vida, velocidad, fuerza, resistencia, fuerzaMordida));
		return true;
	}

	@Override
	public boolean agregarVampiro(int vida, int velocidad, int fuerza, int resistencia, int velocidadVuelo,
			int resistenciaSol) {
		if (!baseValida(vida, velocidad, fuerza, resistencia) || !enRango(velocidadVuelo, 1, 100)
				|| !enRango(resistenciaSol, 1, 100)) {
			return false;
		}
		monstruos.add(new Vampiro(vida, velocidad, fuerza, resistencia, velocidadVuelo, resistenciaSol));
		return true;
	}

	@Override
	public boolean agregarMomia(int vida, int velocidad, int fuerza, int resistencia, int longitudVendajes) {
		if (!baseValida(vida, velocidad, fuerza, resistencia) || !enRango(longitudVendajes, 1, 300)) {
			return false;
		}
		monstruos.add(new Momia(vida, velocidad, fuerza, resistencia, longitudVendajes));
		return true;
	}

	@Override
	public boolean agregarHombreLobo(int vida, int velocidad, int fuerza, int resistencia, int ferocidad,
			int faseLunar) {
		if (!baseValida(vida, velocidad, fuerza, resistencia) || !enRango(ferocidad, 1, 100)
				|| !enRango(faseLunar, 1, 4)) {
			return false;
		}
		monstruos.add(new HombreLobo(vida, velocidad, fuerza, resistencia, ferocidad, faseLunar));
		return true;
	}

	@Override
	public boolean agregarJugador(String nombre, int puntajeMaximo) {
		if (nombre == null || nombre.length() == 0 || nombre.indexOf(";") != -1) {
			return false;
		}
		if (puntajeMaximo < 0 || buscarJugador(nombre) != null) {
			return false;
		}
		jugadores.add(new Jugador(nombre, puntajeMaximo));
		return true;
	}

	@Override
	public int getCantidadMonstruos() {
		return monstruos.size();
	}

	@Override
	public int getCantidadJugadores() {
		return jugadores.size();
	}

	// ---------------- sesión ----------------

	@Override
	public boolean existeJugador(String nombre) {
		return buscarJugador(nombre) != null;
	}

	@Override
	public int obtenerRecord(String nombre) {
		Jugador jugador = buscarJugador(nombre);
		if (jugador == null) {
			return -1;
		}
		return jugador.getPuntajeMaximo();
	}

	@Override
	public void iniciarSesion(String nombre) {
		jugadorActual = nombre;
		// sesión nueva: sin jugadas, así todo queda pendiente y disponible
		historial.clear();
	}

	@Override
	public int getPuntajeSesion() {
		int total = 0;
		for (Jugada jugada : historial) {
			total += jugada.getPuntaje();
		}
		return total;
	}

	// ---------------- minijuegos ----------------

	@Override
	public int getCantidadMinijuegos() {
		return minijuegos.size();
	}

	@Override
	public String obtenerNombreMinijuego(int indice) {
		return minijuegos.get(indice).getNombre();
	}

	@Override
	public String obtenerDescripcionMinijuego(int indice) {
		return minijuegos.get(indice).getDescripcion();
	}

	@Override
	public boolean minijuegoJugado(int indice) {
		return buscarJugada(minijuegos.get(indice)) != null;
	}

	@Override
	public int obtenerPuntajeMinijuego(int indice) {
		Jugada jugada = buscarJugada(minijuegos.get(indice));
		if (jugada == null) {
			return -1;
		}
		return jugada.getPuntaje();
	}

	// ---------------- monstruos disponibles ----------------

	@Override
	public int getCantidadMonstruosDisponibles() {
		int disponibles = 0;
		for (Monstruo monstruo : monstruos) {
			if (!fueUsado(monstruo)) {
				disponibles++;
			}
		}
		return disponibles;
	}

	@Override
	public String obtenerDatosMonstruoDisponible(int posicion) {
		return buscarMonstruoDisponible(posicion).toString();
	}

	@Override
	public double calcularPotencial(int indiceMinijuego, int posicion) {
		Monstruo monstruo = buscarMonstruoDisponible(posicion);
		// polimorfismo + visitor: el minijuego no pregunta el tipo del monstruo
		return minijuegos.get(indiceMinijuego).calcularPotencial(monstruo);
	}

	@Override
	public String[] obtenerHabilidades(int posicion) {
		return habilidadesDe(buscarMonstruoDisponible(posicion));
	}

	@Override
	public String usarHabilidad(int posicion, String habilidad) {
		Monstruo monstruo = buscarMonstruoDisponible(posicion);

		// cast a la interfaz de la habilidad elegida para escuchar su mensaje
		if (habilidad.equals("Morder") && monstruo instanceof Mordedor) {
			return ((Mordedor) monstruo).morder();
		}
		if (habilidad.equals("Volar") && monstruo instanceof Volador) {
			return ((Volador) monstruo).volar();
		}
		if (habilidad.equals("Regenerarse") && monstruo instanceof Regenerable) {
			return ((Regenerable) monstruo).regenerarse();
		}
		if (habilidad.equals("Enfurecerse") && monstruo instanceof Furioso) {
			return ((Furioso) monstruo).enfurecerse();
		}
		return "";
	}

	@Override
	public boolean esHabilidadPremiada(int indiceMinijuego, String habilidad) {
		// la habilidad no sabe si sirve, eso lo decide el minijuego
		return minijuegos.get(indiceMinijuego).getHabilidadPremiada().equals(habilidad);
	}

	@Override
	public int jugarMinijuego(int indiceMinijuego, int posicion, String habilidad) {
		Minijuego minijuego = minijuegos.get(indiceMinijuego);
		Monstruo monstruo = buscarMonstruoDisponible(posicion);

		double potencial = minijuego.calcularPotencial(monstruo);
		double multiplicador = 1.0;
		if (esHabilidadPremiada(indiceMinijuego, habilidad)) {
			multiplicador = 1.5;
		}
		int puntaje = (int) Math.round(potencial * 10 * multiplicador);

		// al quedar en el historial, el minijuego pasa a jugado y el monstruo a usado
		historial.add(new Jugada(minijuego, monstruo, habilidad, puntaje));
		return puntaje;
	}

	// ---------------- métodos auxiliares ----------------

	/**
	 * revisa los rangos de los atributos que tienen todos los monstruos.
	 */
	private boolean baseValida(int vida, int velocidad, int fuerza, int resistencia) {
		return enRango(vida, 1, 200) && enRango(velocidad, 1, 100) && enRango(fuerza, 1, 100)
				&& enRango(resistencia, 1, 100);
	}

	/**
	 * @return true si el valor está entre min y max (ambos incluidos)
	 */
	private boolean enRango(int valor, int min, int max) {
		return valor >= min && valor <= max;
	}

	/**
	 * detecta con instanceof qué interfaces de habilidad implementa el monstruo.
	 *
	 * @param monstruo el monstruo a revisar
	 * @return los nombres de sus habilidades
	 */
	private String[] habilidadesDe(Monstruo monstruo) {
		ArrayList<String> encontradas = new ArrayList<>();
		if (monstruo instanceof Mordedor) {
			encontradas.add("Morder");
		}
		if (monstruo instanceof Volador) {
			encontradas.add("Volar");
		}
		if (monstruo instanceof Regenerable) {
			encontradas.add("Regenerarse");
		}
		if (monstruo instanceof Furioso) {
			encontradas.add("Enfurecerse");
		}

		String[] habilidades = new String[encontradas.size()];
		for (int i = 0; i < encontradas.size(); i++) {
			habilidades[i] = encontradas.get(i);
		}
		return habilidades;
	}

	/**
	 * @return true si el monstruo ya se usó en alguna jugada de esta sesión
	 */
	private boolean fueUsado(Monstruo monstruo) {
		for (Jugada jugada : historial) {
			if (jugada.getMonstruo() == monstruo) {
				return true;
			}
		}
		return false;
	}

	/**
	 * busca el monstruo que está en cierta posición contando solo los que no se
	 * han usado. como cada línea del archivo es un monstruo distinto, dos
	 * zombis son dos objetos distintos y se pueden usar por separado.
	 *
	 * @param posicion posición dentro de los disponibles
	 * @return el monstruo, o null si no hay uno en esa posición
	 */
	private Monstruo buscarMonstruoDisponible(int posicion) {
		int contador = 0;
		for (Monstruo monstruo : monstruos) {
			if (!fueUsado(monstruo)) {
				if (contador == posicion) {
					return monstruo;
				}
				contador++;
			}
		}
		return null;
	}

	/**
	 * @return la jugada de la sesión hecha en ese minijuego, o null si no se ha jugado
	 */
	private Jugada buscarJugada(Minijuego minijuego) {
		for (Jugada jugada : historial) {
			if (jugada.getMinijuego() == minijuego) {
				return jugada;
			}
		}
		return null;
	}

	/**
	 * busca un jugador por su nombre.
	 *
	 * @param nombre nombre a buscar
	 * @return el jugador, o null si no existe
	 */
	private Jugador buscarJugador(String nombre) {
		for (Jugador jugador : jugadores) {
			if (jugador.getNombre().equals(nombre)) {
				return jugador;
			}
		}
		return null;
	}
}
