package logica;

/**
 * operaciones del sistema de spooky season. el sistema guarda los datos,
 * valida las reglas y hace los cálculos. no lee teclado, no lee ni escribe
 * archivos y no imprime nada: todo lo comunica con valores de retorno.
 */
public interface Sistema {

	/**
	 * agrega un zombi al catálogo de monstruos.
	 *
	 * POST:
	 * - si los atributos están en rango, el zombi queda en el catálogo
	 *
	 * @param vida          vida, entre 1 y 200
	 * @param velocidad     velocidad, entre 1 y 100
	 * @param fuerza        fuerza, entre 1 y 100
	 * @param resistencia   resistencia, entre 1 y 100
	 * @param fuerzaMordida fuerza de mordida, entre 1 y 100
	 * @return true si se agregó, false si algún atributo está fuera de rango
	 */
	boolean agregarZombi(int vida, int velocidad, int fuerza, int resistencia, int fuerzaMordida);

	/**
	 * agrega un vampiro al catálogo de monstruos.
	 *
	 * POST:
	 * - si los atributos están en rango, el vampiro queda en el catálogo
	 *
	 * @param vida           vida, entre 1 y 200
	 * @param velocidad      velocidad, entre 1 y 100
	 * @param fuerza         fuerza, entre 1 y 100
	 * @param resistencia    resistencia, entre 1 y 100
	 * @param velocidadVuelo velocidad de vuelo, entre 1 y 100
	 * @param resistenciaSol resistencia al sol, entre 1 y 100
	 * @return true si se agregó, false si algún atributo está fuera de rango
	 */
	boolean agregarVampiro(int vida, int velocidad, int fuerza, int resistencia, int velocidadVuelo,
			int resistenciaSol);

	/**
	 * agrega una momia al catálogo de monstruos.
	 *
	 * POST:
	 * - si los atributos están en rango, la momia queda en el catálogo
	 *
	 * @param vida             vida, entre 1 y 200
	 * @param velocidad        velocidad, entre 1 y 100
	 * @param fuerza           fuerza, entre 1 y 100
	 * @param resistencia      resistencia, entre 1 y 100
	 * @param longitudVendajes largo de vendajes en metros, entre 1 y 300
	 * @return true si se agregó, false si algún atributo está fuera de rango
	 */
	boolean agregarMomia(int vida, int velocidad, int fuerza, int resistencia, int longitudVendajes);

	/**
	 * agrega un hombre lobo al catálogo de monstruos.
	 *
	 * POST:
	 * - si los atributos están en rango, el hombre lobo queda en el catálogo
	 *
	 * @param vida        vida, entre 1 y 200
	 * @param velocidad   velocidad, entre 1 y 100
	 * @param fuerza      fuerza, entre 1 y 100
	 * @param resistencia resistencia, entre 1 y 100
	 * @param ferocidad   ferocidad, entre 1 y 100
	 * @param faseLunar   fase lunar, entre 1 (luna nueva) y 4 (luna llena)
	 * @return true si se agregó, false si algún atributo está fuera de rango
	 */
	boolean agregarHombreLobo(int vida, int velocidad, int fuerza, int resistencia, int ferocidad,
			int faseLunar);

	/**
	 * agrega un jugador con su récord a los registros.
	 *
	 * POST:
	 * - si los datos son válidos, el jugador queda registrado
	 *
	 * @param nombre        nombre del jugador. no puede ser vacío, ni tener ';',
	 *                      ni estar repetido
	 * @param puntajeMaximo récord del jugador, 0 o más
	 * @return true si se agregó, false si los datos no son válidos
	 */
	boolean agregarJugador(String nombre, int puntajeMaximo);

	/**
	 * @return la cantidad de monstruos cargados en el catálogo
	 */
	int getCantidadMonstruos();

	/**
	 * @return la cantidad de jugadores registrados
	 */
	int getCantidadJugadores();
}
