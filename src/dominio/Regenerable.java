package dominio;

/**
 * habilidad de regenerarse. la interfaz solo sabe qué decir al usarse,
 * si sirve o no lo decide el minijuego.
 */
public interface Regenerable {

	/**
	 * usa la habilidad de regenerarse.
	 *
	 * @return el mensaje que se muestra al regenerarse
	 */
	String regenerarse();
}
