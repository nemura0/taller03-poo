package dominio;

/**
 * operaciones que tiene todo minijuego.
 */
public interface Minijuego {

	/**
	 * @return el nombre del minijuego
	 */
	String getNombre();

	/**
	 * @return la descripción del minijuego
	 */
	String getDescripcion();

	/**
	 * retorna el nombre de la habilidad que premia este minijuego
	 * ("Morder", "Volar", "Regenerarse" o "Enfurecerse").
	 *
	 * @return la habilidad premiada
	 */
	String getHabilidadPremiada();

	/**
	 * calcula el potencial de un monstruo en este minijuego.
	 *
	 * @param monstruo el monstruo que va a jugar
	 * @return el potencial en double
	 */
	double calcularPotencial(Monstruo monstruo);
}
