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

	// ---------------- sesión ----------------

	/**
	 * @param nombre nombre a buscar
	 * @return true si el jugador ya está en los registros
	 */
	boolean existeJugador(String nombre);

	/**
	 * @param nombre nombre del jugador
	 * @return el récord del jugador, o -1 si no está registrado
	 */
	int obtenerRecord(String nombre);

	/**
	 * comienza una sesión de juego nueva para un jugador.
	 *
	 * PRE:
	 * - el nombre no es vacío ni tiene ';'
	 *
	 * POST:
	 * - todos los minijuegos quedan pendientes, todos los monstruos quedan
	 *   disponibles y el puntaje acumulado parte en 0
	 *
	 * @param nombre nombre del jugador que inicia sesión
	 */
	void iniciarSesion(String nombre);

	/**
	 * @return el puntaje acumulado en la sesión actual
	 */
	int getPuntajeSesion();

	// ---------------- minijuegos ----------------

	/**
	 * @return la cantidad de minijuegos que existen
	 */
	int getCantidadMinijuegos();

	/**
	 * @param indice posición del minijuego, entre 0 y getCantidadMinijuegos() - 1
	 * @return el nombre del minijuego
	 */
	String obtenerNombreMinijuego(int indice);

	/**
	 * @param indice posición del minijuego, entre 0 y getCantidadMinijuegos() - 1
	 * @return la descripción del minijuego
	 */
	String obtenerDescripcionMinijuego(int indice);

	/**
	 * @param indice posición del minijuego, entre 0 y getCantidadMinijuegos() - 1
	 * @return true si el minijuego ya se jugó en esta sesión
	 */
	boolean minijuegoJugado(int indice);

	/**
	 * @param indice posición del minijuego, entre 0 y getCantidadMinijuegos() - 1
	 * @return el puntaje que se sacó en ese minijuego, o -1 si sigue pendiente
	 */
	int obtenerPuntajeMinijuego(int indice);

	// ---------------- monstruos disponibles ----------------

	/**
	 * @return cuántos monstruos quedan sin usar en la sesión actual
	 */
	int getCantidadMonstruosDisponibles();

	/**
	 * retorna el tipo y todos los atributos de un monstruo disponible.
	 *
	 * @param posicion posición dentro de los disponibles, entre 0 y
	 *                 getCantidadMonstruosDisponibles() - 1
	 * @return los datos del monstruo en una línea
	 */
	String obtenerDatosMonstruoDisponible(int posicion);

	/**
	 * calcula el potencial de un monstruo disponible en un minijuego.
	 *
	 * @param indiceMinijuego posición del minijuego
	 * @param posicion        posición del monstruo dentro de los disponibles
	 * @return el potencial en double
	 */
	double calcularPotencial(int indiceMinijuego, int posicion);

	/**
	 * revisa qué habilidades tiene un monstruo disponible.
	 *
	 * @param posicion posición del monstruo dentro de los disponibles
	 * @return los nombres de sus habilidades ("Morder", "Volar", "Regenerarse"
	 *         o "Enfurecerse")
	 */
	String[] obtenerHabilidades(int posicion);

	/**
	 * hace que un monstruo disponible use una de sus habilidades.
	 *
	 * @param posicion  posición del monstruo dentro de los disponibles
	 * @param habilidad nombre de la habilidad, debe ser una que tenga el monstruo
	 * @return el mensaje que dice el monstruo al usarla
	 */
	String usarHabilidad(int posicion, String habilidad);

	/**
	 * @param indiceMinijuego posición del minijuego
	 * @param habilidad       nombre de la habilidad usada
	 * @return true si es la habilidad que premia el minijuego (multiplicador 1.5)
	 */
	boolean esHabilidadPremiada(int indiceMinijuego, String habilidad);

	/**
	 * juega un minijuego con un monstruo disponible y registra el resultado.
	 *
	 * PRE:
	 * - hay una sesión iniciada
	 * - el minijuego no se ha jugado en esta sesión
	 *
	 * POST:
	 * - la jugada queda en el historial de la sesión
	 * - el minijuego queda jugado y el monstruo queda usado
	 *
	 * @param indiceMinijuego posición del minijuego
	 * @param posicion        posición del monstruo dentro de los disponibles
	 * @param habilidad       habilidad usada, o "Ninguna"
	 * @return el puntaje obtenido: Math.round(potencial x 10 x multiplicador)
	 */
	int jugarMinijuego(int indiceMinijuego, int posicion, String habilidad);
}
