package dominio;

/**
 * base de los 4 minijuegos. guarda nombre, descripción y habilidad premiada,
 * y deja las fórmulas (los visitar) a cada minijuego concreto.
 */
public abstract class MinijuegoBase implements Minijuego, VisitorMinijuego {

	private String nombre;
	private String descripcion;
	private String habilidadPremiada;

	/**
	 * crea la base de un minijuego.
	 *
	 * @param nombre            nombre del minijuego
	 * @param descripcion       descripción que se muestra al elegirlo
	 * @param habilidadPremiada habilidad que da el multiplicador x1.5
	 */
	public MinijuegoBase(String nombre, String descripcion, String habilidadPremiada) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.habilidadPremiada = habilidadPremiada;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public String getHabilidadPremiada() {
		return habilidadPremiada;
	}

	/**
	 * le pasa este minijuego (el visitor) al monstruo, y el monstruo llama al
	 * visitar que le corresponde según su tipo. así no se pregunta por el tipo.
	 */
	@Override
	public double calcularPotencial(Monstruo monstruo) {
		return monstruo.aceptar(this);
	}
}
