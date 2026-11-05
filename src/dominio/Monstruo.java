package dominio;

/**
 * clase base de todos los monstruos. guarda los atributos que comparten
 * todos los tipos (vida, velocidad, fuerza y resistencia).
 */
public abstract class Monstruo {

	private int vida;
	private int velocidad;
	private int fuerza;
	private int resistencia;

	/**
	 * crea un monstruo con sus atributos base.
	 *
	 * @param vida        vida del monstruo (1 a 200)
	 * @param velocidad   velocidad del monstruo (1 a 100)
	 * @param fuerza      fuerza del monstruo (1 a 100)
	 * @param resistencia resistencia del monstruo (1 a 100)
	 */
	public Monstruo(int vida, int velocidad, int fuerza, int resistencia) {
		this.vida = vida;
		this.velocidad = velocidad;
		this.fuerza = fuerza;
		this.resistencia = resistencia;
	}

	public int getVida() {
		return vida;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public int getFuerza() {
		return fuerza;
	}

	public int getResistencia() {
		return resistencia;
	}

	/**
	 * retorna el nombre del tipo de monstruo (Zombi, Vampiro, Momia o HombreLobo).
	 *
	 * @return el tipo del monstruo
	 */
	public abstract String getTipo();

	/**
	 * arma el texto con el tipo y los atributos base. cada subclase le agrega
	 * sus atributos propios al final.
	 */
	@Override
	public String toString() {
		return rellenar(getTipo(), 10)
				+ " | Vida " + rellenar("" + vida, 3)
				+ " | Vel " + rellenar("" + velocidad, 3)
				+ " | Fue " + rellenar("" + fuerza, 3)
				+ " | Res " + rellenar("" + resistencia, 3);
	}

	/**
	 * agrega espacios a la derecha hasta que el texto tenga el largo pedido,
	 * así las columnas quedan alineadas al listar.
	 *
	 * @param texto el texto a rellenar
	 * @param largo largo mínimo que debe quedar
	 * @return el texto con espacios al final
	 */
	protected String rellenar(String texto, int largo) {
		String resultado = texto;
		while (resultado.length() < largo) {
			resultado = resultado + " ";
		}
		return resultado;
	}
}
