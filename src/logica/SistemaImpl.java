package logica;

import java.util.ArrayList;

import dominio.HombreLobo;
import dominio.Jugador;
import dominio.Momia;
import dominio.Monstruo;
import dominio.Vampiro;
import dominio.Zombi;

/**
 * implementación del sistema de spooky season.
 */
public class SistemaImpl implements Sistema {

	private ArrayList<Monstruo> monstruos;
	private ArrayList<Jugador> jugadores;

	public SistemaImpl() {
		this.monstruos = new ArrayList<>();
		this.jugadores = new ArrayList<>();
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
