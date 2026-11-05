package dominio;

/**
 * habilidad de volar. la interfaz solo sabe qué decir al usarse,
 * si sirve o no lo decide el minijuego.
 */
public interface Volador {

	/**
	 * usa la habilidad de volar.
	 *
	 * @return el mensaje que se muestra al volar
	 */
	String volar();
}
