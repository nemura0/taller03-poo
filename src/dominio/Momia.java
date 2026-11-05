package dominio;

/**
 * momia: puede regenerarse y enfurecerse.
 */
public class Momia extends Monstruo implements Regenerable, Furioso {

	private int longitudVendajes;

	/**
	 * crea una momia.
	 *
	 * @param vida             vida (1 a 200)
	 * @param velocidad        velocidad (1 a 100)
	 * @param fuerza           fuerza (1 a 100)
	 * @param resistencia      resistencia (1 a 100)
	 * @param longitudVendajes largo de los vendajes en metros (1 a 300)
	 */
	public Momia(int vida, int velocidad, int fuerza, int resistencia, int longitudVendajes) {
		super(vida, velocidad, fuerza, resistencia);
		this.longitudVendajes = longitudVendajes;
	}

	public int getLongitudVendajes() {
		return longitudVendajes;
	}

	@Override
	public String getTipo() {
		return "Momia";
	}

	@Override
	public String regenerarse() {
		return "La momia se enrolla " + longitudVendajes + " metros de vendas nuevas.";
	}

	@Override
	public String enfurecerse() {
		return "¡La momia suelta un grito milenario y se enfurece!";
	}

	@Override
	public String toString() {
		return super.toString() + " | Vendajes " + longitudVendajes;
	}
}
