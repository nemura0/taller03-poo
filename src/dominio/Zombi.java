package dominio;

/**
 * zombi: puede morder y regenerarse.
 */
public class Zombi extends Monstruo implements Mordedor, Regenerable {

	private int fuerzaMordida;

	/**
	 * crea un zombi.
	 *
	 * @param vida          vida (1 a 200)
	 * @param velocidad     velocidad (1 a 100)
	 * @param fuerza        fuerza (1 a 100)
	 * @param resistencia   resistencia (1 a 100)
	 * @param fuerzaMordida fuerza de mordida (1 a 100)
	 */
	public Zombi(int vida, int velocidad, int fuerza, int resistencia, int fuerzaMordida) {
		super(vida, velocidad, fuerza, resistencia);
		this.fuerzaMordida = fuerzaMordida;
	}

	public int getFuerzaMordida() {
		return fuerzaMordida;
	}

	@Override
	public String getTipo() {
		return "Zombi";
	}

	@Override
	public String morder() {
		return "¡El zombi clava los dientes con una mordida de fuerza " + fuerzaMordida + "!";
	}

	@Override
	public String regenerarse() {
		return "El zombi recoge su brazo del suelo y se lo vuelve a pegar.";
	}

	@Override
	public String toString() {
		return super.toString() + " | Mordida " + fuerzaMordida;
	}
}
