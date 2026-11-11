package dominio;

/**
 * visitor de los minijuegos. cada minijuego implementa esta interfaz y tiene
 * una fórmula de potencial distinta para cada tipo de monstruo.
 */
public interface VisitorMinijuego {

	/**
	 * calcula el potencial de un zombi en este minijuego.
	 *
	 * @param zombi el zombi visitado
	 * @return el potencial calculado en double
	 */
	double visitar(Zombi zombi);

	/**
	 * calcula el potencial de un vampiro en este minijuego.
	 *
	 * @param vampiro el vampiro visitado
	 * @return el potencial calculado en double
	 */
	double visitar(Vampiro vampiro);

	/**
	 * calcula el potencial de una momia en este minijuego.
	 *
	 * @param momia la momia visitada
	 * @return el potencial calculado en double
	 */
	double visitar(Momia momia);

	/**
	 * calcula el potencial de un hombre lobo en este minijuego.
	 *
	 * @param hombreLobo el hombre lobo visitado
	 * @return el potencial calculado en double
	 */
	double visitar(HombreLobo hombreLobo);
}
