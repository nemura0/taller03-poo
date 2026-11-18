# Taller 03 — Spooky Season

Taller 03 de Programación Orientada a Objetos (UCN, II semestre 2026).

Simulación de minijuegos de Halloween: cada jugador inicia sesión, se transforma en un monstruo distinto para cada minijuego (zombi, vampiro, momia u hombre lobo), puede usar una habilidad y compite por el mejor puntaje. Los récords quedan guardados en `Registros.txt`.

## Integrantes

| Nombre | RUT | Carrera |
|---|---|---|
| Vicente Guerra | 21.855.415-6 | ITI |
| Diego Meneses | 22.087.010-3 | ICCI |

## Requisitos

- JDK 17 o superior (`java -version` para revisar).
- Eclipse (opcional, el proyecto ya trae `.project` y `.classpath`).

## Cómo clonar y ejecutar

### 1. Clonar el repositorio

```bash
git clone https://github.com/nemura0/taller03-poo.git
cd taller03-poo
```

### 2a. Ejecutar desde Eclipse

1. `File > Import... > General > Existing Projects into Workspace`.
2. En *Select root directory* elegir la carpeta `taller03-poo` y presionar *Finish*.
3. Abrir `src/logica/Main.java` y ejecutar con `Run As > Java Application`.

Eclipse ejecuta el programa desde la raíz del proyecto, que es donde están `Monstruos.txt` y `Registros.txt`.

### 2b. Ejecutar desde la terminal

Siempre desde la raíz del proyecto (donde están los `.txt`).

**macOS / Linux**

```bash
javac -encoding UTF-8 -d bin src/dominio/*.java src/logica/*.java
java -cp bin logica.Main
```

**Windows (PowerShell o CMD)**

```bat
javac -encoding UTF-8 -d bin src\dominio\*.java src\logica\*.java
java -cp bin logica.Main
```

## Estructura del proyecto

```
taller03-poo/
├── Monstruos.txt            catálogo de monstruos (entrada)
├── Registros.txt            récords de los jugadores (entrada y salida)
├── docs/
│   ├── diagrama-clases.pdf  diagrama de clases UML
│   └── diagrama-clases.png
└── src/
    ├── logica/
    │   ├── Main.java        entrada y salida (teclado, archivos, menús)
    │   ├── Sistema.java     interfaz con los contratos de todas las operaciones
    │   └── SistemaImpl.java lógica, colecciones, cálculos y validaciones
    └── dominio/
        ├── Monstruo.java    clase abstracta base
        ├── Zombi.java, Vampiro.java, Momia.java, HombreLobo.java
        ├── Mordedor.java, Volador.java, Regenerable.java, Furioso.java   (habilidades)
        ├── VisitorMinijuego.java, Minijuego.java, MinijuegoBase.java
        ├── CarreraCementerio.java, CazaCalabazas.java,
        │   LanzamientoLapidas.java, SobrevivirAmanecer.java             (visitors)
        ├── Jugada.java      resultado de un minijuego en la sesión
        └── Jugador.java     jugador con su récord
```

## Diseño

- **Herencia:** `Monstruo` es abstracta y `Zombi`, `Vampiro`, `Momia` y `HombreLobo` agregan sus atributos propios.
- **Interfaces de habilidad:** `Mordedor`, `Volador`, `Regenerable` y `Furioso`. Cada monstruo implementa exactamente dos. Solo saben qué decir al usarse; si la habilidad sirve lo decide el minijuego con su habilidad premiada.
- **Patrón Visitor (bonificación):** cada minijuego es un visitor con las 4 fórmulas (una por tipo de monstruo). `Monstruo` declara `aceptar(VisitorMinijuego)` y cada subclase responde `visitor.visitar(this)`, así el potencial se calcula sin preguntar el tipo ni usar `instanceof`. `MinijuegoBase` es una clase abstracta que implementa `Minijuego` y `VisitorMinijuego`, y guarda nombre, descripción y habilidad premiada.
- **Main / Sistema (bonificación):** `Main` solo lee teclado y archivos, separa líneas con `split`, muestra menús y escribe `Registros.txt`. `Sistema` (interfaz con contratos) y `SistemaImpl` crean los objetos, validan rangos, guardan las colecciones, manejan la sesión, calculan puntajes y ordenan el ranking. El sistema no imprime ni lee nada y no recibe ni retorna objetos del dominio, solo `String`, `int`, `double` y `boolean`.
- **Colecciones:** `ArrayList<Monstruo>`, `ArrayList<Jugador>`, `ArrayList<Minijuego>` y el historial de la sesión en una `LinkedList<Jugada>`. Un minijuego está "jugado" y un monstruo está "usado" si aparecen en el historial de la sesión.
- **Ranking:** ordenamiento por intercambio implementado a mano (mayor a menor puntaje y, en empate, por nombre con `compareTo`). Se ordena una copia de la lista para no alterar los registros.

## Cómo se probó

### Carga de archivos con los archivos de prueba

Con los `Monstruos.txt` y `Registros.txt` incluidos, el programa debe mostrar:

- **11 monstruos cargados** (líneas 1, 2, 3, 4, 6, 8, 10, 13, 16, 20 y 22). Se descartan, avisando el motivo, las líneas 5 (tipo `Fantasma`), 7 y 9 (cantidad de campos), 11 y 19 (valores no enteros), 12 (vacía), 14, 15, 17 y 18 (fuera de rango) y 21 (`;;;;;`).
- **6 jugadores cargados** (Koshao, Clapt, Churrasquita, ElConde, MomiaFeliz y Calabacin). Se descartan `SinPuntaje;`, `Fantasmita;abc`, `Negativo;-50`, `;1200` y `Dos;Puntos;100`.

### Ejemplo del enunciado

Con un `Monstruos.txt` de solo las 4 primeras líneas, ingresando `Clapt`, Vampiro con *Volar* en Carrera del Cementerio y Zombi con *Morder* en Lanzamiento de Lápidas, se obtienen 1650 y 420 puntos (2070 en total), igual que en el ejemplo del enunciado.

### Control de errores (sección 5)

| Caso | Resultado esperado |
|---|---|
| Letras, decimales, entrada vacía o números fuera de rango en un menú | Mensaje `[!]` y se vuelve a pedir la opción |
| Nombre vacío o con `;` | Mensaje `[!]` y se vuelve a pedir el nombre |
| `Monstruos.txt` no existe o no tiene monstruos válidos | Se informa y el programa termina de forma controlada |
| `Registros.txt` no existe | Se parte sin jugadores y el archivo se crea al guardar |
| Repetir un minijuego | `[!] Ya jugaste ... en esta sesión.` |
| Reutilizar un monstruo | Los monstruos usados no aparecen en la lista para elegir |
| Varios monstruos del mismo tipo | Cada uno aparece por separado y se usa una sola vez |
| Quedarse sin monstruos | `[!] No quedan monstruos disponibles...` y se vuelve al menú |
| Todos los minijuegos jugados | Se informa y se sugiere terminar la sesión |
| Opción `0) Volver` en cada paso | Vuelve al menú sin jugar |
| Se cierra la entrada (Ctrl+D / Ctrl+Z) | Se termina la sesión, se guarda y el programa cierra sin caerse |

## Decisiones y supuestos

- Un récord negativo en `Registros.txt` (por ejemplo `Negativo;-50`) se considera inválido y se descarta, porque un puntaje nunca puede ser negativo.
- Un nombre repetido en `Registros.txt` se descarta (se mantiene el primero).
- Los nombres de usuario distinguen mayúsculas de minúsculas (`clapt` y `Clapt` son jugadores distintos).
- Al terminar una sesión sin jugar ningún minijuego no se modifica `Registros.txt`.
- Las habilidades se muestran en el orden Morder, Volar, Regenerarse, Enfurecerse según las que tenga el monstruo.

## Diagrama de clases

El diagrama UML está en [`docs/diagrama-clases.pdf`](docs/diagrama-clases.pdf) (también en PNG). Muestra la herencia de monstruos y minijuegos, la implementación de las interfaces de habilidad, el patrón Visitor y la relación `Main → Sistema ← SistemaImpl`.
