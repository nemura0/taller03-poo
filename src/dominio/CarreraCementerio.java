package dominio;

/**
 * minijuego 1: carrera del cementerio. premia volar.
 */
public class CarreraCementerio extends MinijuegoBase {

	public CarreraCementerio() {
		super("Carrera del Cementerio",
				"recorre el cementerio de punta a punta antes de que el sepulturero te vea.",
				"Volar");
	}

	// velocidad x resistencia / 100
	@Override
	public double visitar(Zombi zombi) {
		double velocidad = zombi.getVelocidad();
		double resistencia = zombi.getResistencia();
		return velocidad * resistencia / 100;
	}

	// (velocidad + 2 x velocidad de vuelo) / 2
	@Override
	public double visitar(Vampiro vampiro) {
		double velocidad = vampiro.getVelocidad();
		double vuelo = vampiro.getVelocidadVuelo();
		return (velocidad + 2 * vuelo) / 2;
	}

	// velocidad x 100 / (100 + longitud de vendajes)
	@Override
	public double visitar(Momia momia) {
		double velocidad = momia.getVelocidad();
		double vendajes = momia.getLongitudVendajes();
		return velocidad * 100 / (100 + vendajes);
	}

	// (velocidad + ferocidad / 5) x fase lunar / 4
	@Override
	public double visitar(HombreLobo hombreLobo) {
		double velocidad = hombreLobo.getVelocidad();
		double ferocidad = hombreLobo.getFerocidad();
		double faseLunar = hombreLobo.getFaseLunar();
		return (velocidad + ferocidad / 5) * faseLunar / 4;
	}
}
