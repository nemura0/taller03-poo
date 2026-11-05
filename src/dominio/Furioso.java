package dominio;

/**
 * habilidad de enfurecerse. la interfaz solo sabe qué decir al usarse,
 * si sirve o no lo decide el minijuego.
 */
public interface Furioso {

	/**
	 * usa la habilidad de enfurecerse.
	 *
	 * @return el mensaje que se muestra al enfurecerse
	 */
	String enfurecerse();
}
