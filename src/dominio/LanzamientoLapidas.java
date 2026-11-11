package dominio;

/**
 * minijuego 3: lanzamiento de lápidas. premia enfurecerse.
 */
public class LanzamientoLapidas extends MinijuegoBase {

	public LanzamientoLapidas() {
		super("Lanzamiento de Lápidas",
				"lanza una lápida lo más lejos posible.",
				"Enfurecerse");
	}

	// fuerza x resistencia / 100
	@Override
	public double visitar(Zombi zombi) {
		double fuerza = zombi.getFuerza();
		double resistencia = zombi.getResistencia();
		return fuerza * resistencia / 100;
	}

	// fuerza + velocidad de vuelo / 4
	@Override
	public double visitar(Vampiro vampiro) {
		double fuerza = vampiro.getFuerza();
		double vuelo = vampiro.getVelocidadVuelo();
		return fuerza + vuelo / 4;
	}

	// fuerza + longitud de vendajes / 10
	@Override
	public double visitar(Momia momia) {
		double fuerza = momia.getFuerza();
		double vendajes = momia.getLongitudVendajes();
		return fuerza + vendajes / 10;
	}

	// fuerza + ferocidad x fase lunar / 8
	@Override
	public double visitar(HombreLobo hombreLobo) {
		double fuerza = hombreLobo.getFuerza();
		double ferocidad = hombreLobo.getFerocidad();
		double faseLunar = hombreLobo.getFaseLunar();
		return fuerza + ferocidad * faseLunar / 8;
	}
}
