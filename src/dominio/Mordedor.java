package dominio;

/**
 * habilidad de morder. la interfaz solo sabe qué decir al usarse,
 * si sirve o no lo decide el minijuego.
 */
public interface Mordedor {

	/**
	 * usa la habilidad de morder.
	 *
	 * @return el mensaje que se muestra al morder
	 */
	String morder();
}
