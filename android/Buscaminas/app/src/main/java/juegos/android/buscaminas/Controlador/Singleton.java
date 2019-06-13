package juegos.android.buscaminas.Controlador;

import juegos.android.buscaminas.Juego.Tablero;

/**
 * Contenedor de Singleton (Singleton Design Pattern).
 * @author Mario Codes Sánchez
 * @since 17/01/2017
 */
public class Singleton {
    private static Tablero tablero; //Instancia unica que iremos pasando por el Programa.

    /**
     * Version a ejecutar la primera vez o cuando queremos iniciar un nuevo Tablero.
     * @param dimensiones Dimensiones del tablero.
     * @param minasMaximas Numero de minax maximo que apareceran en el Juego.
     * @return instancia de Tablero.
     */
    public static Tablero getTablero(int dimensiones, int minasMaximas) {
        return tablero = new Tablero(dimensiones, minasMaximas);
    }

    /**
     * Get de una instancia de Tablero.
     * @return instancia de Tablero previamente inicializada.
     */
    public static Tablero getTablero() {
        return tablero;
    }

    private Singleton() {}
}
