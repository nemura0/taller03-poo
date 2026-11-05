package dominio;

/**
 * jugador registrado con su récord histórico (puntaje máximo de una sesión).
 */
public class Jugador {

	private String nombre;
	private int puntajeMaximo;

	/**
	 * crea un jugador.
	 *
	 * @param nombre        nombre de usuario (no vacío y sin ';')
	 * @param puntajeMaximo récord del jugador (0 o más)
	 */
	public Jugador(String nombre, int puntajeMaximo) {
		this.nombre = nombre;
		this.puntajeMaximo = puntajeMaximo;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPuntajeMaximo() {
		return puntajeMaximo;
	}

	public void setPuntajeMaximo(int puntajeMaximo) {
		this.puntajeMaximo = puntajeMaximo;
	}
}
