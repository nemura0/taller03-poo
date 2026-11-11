package dominio;

/**
 * minijuego 2: caza de calabazas. premia morder.
 */
public class CazaCalabazas extends MinijuegoBase {

	public CazaCalabazas() {
		super("Caza de Calabazas",
				"muerde la mayor cantidad de calabazas del huerto en 60 segundos.",
				"Morder");
	}

	// fuerza de mordida x vida / 100 + resistencia / 2
	@Override
	public double visitar(Zombi zombi) {
		double mordida = zombi.getFuerzaMordida();
		double vida = zombi.getVida();
		double resistencia = zombi.getResistencia();
		return mordida * vida / 100 + resistencia / 2;
	}

	// (fuerza + velocidad) / 3
	@Override
	public double visitar(Vampiro vampiro) {
		double fuerza = vampiro.getFuerza();
		double velocidad = vampiro.getVelocidad();
		return (fuerza + velocidad) / 3;
	}

	// longitud de vendajes x fuerza / 200
	@Override
	public double visitar(Momia momia) {
		double vendajes = momia.getLongitudVendajes();
		double fuerza = momia.getFuerza();
		return vendajes * fuerza / 200;
	}

	// ferocidad x fase lunar / 4 + velocidad / 4
	@Override
	public double visitar(HombreLobo hombreLobo) {
		double ferocidad = hombreLobo.getFerocidad();
		double faseLunar = hombreLobo.getFaseLunar();
		double velocidad = hombreLobo.getVelocidad();
		return ferocidad * faseLunar / 4 + velocidad / 4;
	}
}
