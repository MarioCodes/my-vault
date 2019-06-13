package juegos.android.buscaminas.Juego;

/**
 * @author Mario Codes Sánchez
 * @since 18/01/2017
 */

public class Juego {
    private static Tablero tablero;

    private static void reveladoCeros(Casilla[][] casillas, int x, int y) {
        try {
            if(casillas[x][y].getNumeroPropio() == 0 && !casillas[x][y].isPulsada() && !casillas[x][y].isMina()) {
                casillas[x][y].setPulsada(true); //asignado de 'usada' if 0 en masa
                reveladoCerosEnMasa(casillas, x, y); //Recursividad, check y atencion a esto.
            } else if(casillas[x][y].getNumeroPropio() != 0) {
                casillas[x][y].setPulsada(true); //Para numeros en el borde de un 0.
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Array fuera de posicion. reveladoCeros()");
        }
    }

    private static void reveladoCerosUpper(Casilla[][] casillas, int x, int y) {
        if(x-1 >= 0) {
            if(y-1 >= 0) reveladoCeros(casillas, x-1, y-1);

            reveladoCeros(casillas, x-1, y);

            if(y+1 < tablero.getDIMENSIONES()) reveladoCeros(casillas, x-1, y+1);
        }
    }

    private static void reveladoCerosMiddle(Casilla[][] casillas, int x, int y) {
        if(y-1 >= 0) reveladoCeros(casillas, x, y-1);

        if(y+1 < tablero.getDIMENSIONES()) reveladoCeros(casillas, x, y+1);
    }

    private static void reveladoCerosLower(Casilla[][] casillas, int x, int y) {
        if(x+1 < tablero.getDIMENSIONES()) {
            if(y-1 >= 0) reveladoCeros(casillas, x+1, y-1);

            reveladoCeros(casillas, x+1, y);

            if(y+1 < tablero.getDIMENSIONES()) reveladoCeros(casillas, x+1, y+1);
        }
    }

    private static void reveladoCerosEnMasa(Casilla[][] casillas, int x, int y) {
        reveladoCerosUpper(casillas, x, y);
        reveladoCerosMiddle(casillas, x, y);
        reveladoCerosLower(casillas, x, y);
    }

    /**
     * Metodo principal de Llamado. Check de 0's segun las normas del juego. Implementa Recursividad.
     * @param casillas Casilla[][] conteniendo todas las del juego.
     * @param casilla Casilla actual a comprobar.
     */
    public static void checkCasillaCeros(Casilla[][] casillas, Casilla casilla) {
        reveladoCerosEnMasa(casillas, casilla.getCOORDENADAS()[0], casilla.getCOORDENADAS()[1]);
        casilla.setPulsada(true);
    }

    public static void setTablero(Tablero tablero) {
        Juego.tablero = tablero;
    }
}
