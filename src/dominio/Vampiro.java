package dominio;

/**
 * vampiro: puede morder y volar.
 */
public class Vampiro extends Monstruo implements Mordedor, Volador {

	private int velocidadVuelo;
	private int resistenciaSol;

	/**
	 * crea un vampiro.
	 *
	 * @param vida           vida (1 a 200)
	 * @param velocidad      velocidad (1 a 100)
	 * @param fuerza         fuerza (1 a 100)
	 * @param resistencia    resistencia (1 a 100)
	 * @param velocidadVuelo velocidad de vuelo (1 a 100)
	 * @param resistenciaSol resistencia al sol (1 a 100, más alto aguanta mejor)
	 */
	public Vampiro(int vida, int velocidad, int fuerza, int resistencia, int velocidadVuelo,
			int resistenciaSol) {
		super(vida, velocidad, fuerza, resistencia);
		this.velocidadVuelo = velocidadVuelo;
		this.resistenciaSol = resistenciaSol;
	}

	public int getVelocidadVuelo() {
		return velocidadVuelo;
	}

	public int getResistenciaSol() {
		return resistenciaSol;
	}

	@Override
	public String getTipo() {
		return "Vampiro";
	}

	@Override
	public double aceptar(VisitorMinijuego visitor) {
		return visitor.visitar(this);
	}

	@Override
	public String morder() {
		return "¡El vampiro muestra los colmillos y da un mordisco rápido!";
	}

	@Override
	public String volar() {
		return "¡El vampiro se convierte en murciélago y sale volando a velocidad " + velocidadVuelo + "!";
	}

	@Override
	public String toString() {
		return super.toString() + " | Vuelo " + rellenar("" + velocidadVuelo, 3) + " | Sol " + resistenciaSol;
	}
}
