package dominio;

/**
 * minijuego 4: sobrevivir al amanecer. premia regenerarse.
 */
public class SobrevivirAmanecer extends MinijuegoBase {

	public SobrevivirAmanecer() {
		super("Sobrevivir al Amanecer",
				"aguanta en pie cuando salgan los primeros rayos de sol.",
				"Regenerarse");
	}

	// resistencia x vida / 100
	@Override
	public double visitar(Zombi zombi) {
		double resistencia = zombi.getResistencia();
		double vida = zombi.getVida();
		return resistencia * vida / 100;
	}

	// resistencia x resistencia al sol / 100
	@Override
	public double visitar(Vampiro vampiro) {
		double resistencia = vampiro.getResistencia();
		double sol = vampiro.getResistenciaSol();
		return resistencia * sol / 100;
	}

	// (resistencia + vida) / 2 x (1 + longitud de vendajes / 300)
	@Override
	public double visitar(Momia momia) {
		double resistencia = momia.getResistencia();
		double vida = momia.getVida();
		double vendajes = momia.getLongitudVendajes();
		return (resistencia + vida) / 2 * (1 + vendajes / 300);
	}

	// (resistencia + vida) / 2 x (5 - fase lunar) / 4
	@Override
	public double visitar(HombreLobo hombreLobo) {
		double resistencia = hombreLobo.getResistencia();
		double vida = hombreLobo.getVida();
		double faseLunar = hombreLobo.getFaseLunar();
		return (resistencia + vida) / 2 * (5 - faseLunar) / 4;
	}
}
