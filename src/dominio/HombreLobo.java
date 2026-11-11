package dominio;

/**
 * hombre lobo: puede morder y enfurecerse.
 */
public class HombreLobo extends Monstruo implements Mordedor, Furioso {

	private int ferocidad;
	private int faseLunar;

	/**
	 * crea un hombre lobo.
	 *
	 * @param vida        vida (1 a 200)
	 * @param velocidad   velocidad (1 a 100)
	 * @param fuerza      fuerza (1 a 100)
	 * @param resistencia resistencia (1 a 100)
	 * @param ferocidad   ferocidad (1 a 100)
	 * @param faseLunar   fase lunar (1 = luna nueva, 4 = luna llena)
	 */
	public HombreLobo(int vida, int velocidad, int fuerza, int resistencia, int ferocidad, int faseLunar) {
		super(vida, velocidad, fuerza, resistencia);
		this.ferocidad = ferocidad;
		this.faseLunar = faseLunar;
	}

	public int getFerocidad() {
		return ferocidad;
	}

	public int getFaseLunar() {
		return faseLunar;
	}

	@Override
	public String getTipo() {
		return "HombreLobo";
	}

	@Override
	public double aceptar(VisitorMinijuego visitor) {
		return visitor.visitar(this);
	}

	@Override
	public String morder() {
		return "¡El hombre lobo muerde con una ferocidad de " + ferocidad + "!";
	}

	@Override
	public String enfurecerse() {
		return "¡El hombre lobo le aúlla a la luna y se enfurece!";
	}

	@Override
	public String toString() {
		return super.toString() + " | Ferocidad " + rellenar("" + ferocidad, 3) + " | Luna " + faseLunar;
	}
}
