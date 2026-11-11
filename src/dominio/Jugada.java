package dominio;

/**
 * resultado de un minijuego jugado en la sesión: qué minijuego, con qué
 * monstruo, qué habilidad se usó y cuánto puntaje dio.
 */
public class Jugada {

	private Minijuego minijuego;
	private Monstruo monstruo;
	private String habilidad;
	private int puntaje;

	/**
	 * crea una jugada.
	 *
	 * @param minijuego minijuego jugado
	 * @param monstruo  monstruo en el que se transformó el jugador
	 * @param habilidad habilidad usada, o "Ninguna"
	 * @param puntaje   puntaje obtenido
	 */
	public Jugada(Minijuego minijuego, Monstruo monstruo, String habilidad, int puntaje) {
		this.minijuego = minijuego;
		this.monstruo = monstruo;
		this.habilidad = habilidad;
		this.puntaje = puntaje;
	}

	public Minijuego getMinijuego() {
		return minijuego;
	}

	public Monstruo getMonstruo() {
		return monstruo;
	}

	public String getHabilidad() {
		return habilidad;
	}

	public int getPuntaje() {
		return puntaje;
	}
}
