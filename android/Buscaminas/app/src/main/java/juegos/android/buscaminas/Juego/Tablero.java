package juegos.android.buscaminas.Juego;

/**
 * Representacion del Tablero de juego.
 * @author Mario Codes Sánchez
 * @since 17/01/2017
 */

public class Tablero {
    private final double POSIBILIDAD_MINA = 0.1; //10%. Al ser tan baja, se reparten mejor.
    private final Casilla[][] CASILLAS;
    private final int MAXIMO_MINAS;
    private final int DIMENSIONES;

    /**
     * Tablero a utilizar por defecto.
     * @param dimensiones Tamaño Maximo del tablero.
     * @param maximoMinas Numero Maximo de minas en el juego.
     */
    public Tablero(int dimensiones, int maximoMinas) {
        this.MAXIMO_MINAS = maximoMinas;
        this.DIMENSIONES = dimensiones;

        CASILLAS = new Casilla[dimensiones][dimensiones];

        iniTablero();
    }

    /**
     * Todos los metodos y pasos necesarios hasta tener el Tablero listo para jugar.
     */
    private void iniTablero() {
        iniCasillas();
        iniMinas();
        iniNumerosMinasProximas();
    }

    /**
     * Inicializacion de las Casillas dentro de su Array.
     */
    private void iniCasillas() {
        for (int indexFila = 0; indexFila < getCASILLAS().length; indexFila++) {
            for (int indexCasilla = 0; indexCasilla < getCASILLAS()[indexFila].length; indexCasilla++) {
                getCASILLAS()[indexFila][indexCasilla] = new Casilla(indexFila, indexCasilla);
            }
        }
    }

    /**
     * Comprobacion de las Casillas que rodean a la que nos interesa y set del numero apropiado.
     * @param casilla Casilla a la que le queremos poner su numero.
     */
    private void setNumeroMinasCasilla(Casilla casilla) {
        int[] coordenadas = casilla.getCOORDENADAS();
        coordenadas[0] -= 1;
        coordenadas[1] -= 1;
        int numeroMinas = 0;

        for (int indiceFila = coordenadas[0]; indiceFila < coordenadas[0]+3; indiceFila++) {
            for (int indiceCasilla = coordenadas[1]; indiceCasilla < coordenadas[1]+3; indiceCasilla++) {
                try {
                    Casilla casillaTmp = CASILLAS[indiceFila][indiceCasilla];
                    if (casillaTmp.isMina() && (casillaTmp != casilla)) numeroMinas++; //Comparo las 2 direcciones de memoria para que no se tenga en cuenta a si misma.
                }catch(IndexOutOfBoundsException ex) {} //Para los casos que se salga de la Array, no pete.
            }
        }

        casilla.setNumeroPropio(numeroMinas);
    }

    /**
     * Set de los numeros de cada Casilla, que indica el numero de Minas proximas que hay.
     * @todo: ME QUEDO CON ESTO.
     */
    private void iniNumerosMinasProximas() {
        for (int indiceColumna = 0; indiceColumna < CASILLAS.length; indiceColumna++) {
            for (int indiceCasilla = 0; indiceCasilla < CASILLAS[indiceColumna].length; indiceCasilla++) {
                setNumeroMinasCasilla(CASILLAS[indiceColumna][indiceCasilla]);
            }
        }

        //setNumeroMinasCasilla(CASILLAS[1][1]); //// FIXME: Cambiar esto para que recorra todas las casillas si funciona.
    }

    /**
     * Set de una Casilla como Mina mediante un porcentaje aleatorio.
     * @return True si esa Casilla debe ser una mina.
     */
    private boolean minaRandom() {
        return Math.random() < POSIBILIDAD_MINA;
    }

    /**
     * Set de las casillas que seran Minas.
     */
    private void iniMinas() {
        int contadorMinas = 0;

        while(contadorMinas < getMAXIMO_MINAS()) {
            for (int indexFila = 0; indexFila < getCASILLAS().length; indexFila++) {
                for (int indexCasilla = 0; indexCasilla < getCASILLAS()[indexFila].length; indexCasilla++) {
                    if (minaRandom() && (contadorMinas < getMAXIMO_MINAS())) {
                        getCASILLAS()[indexFila][indexCasilla].setMina(true);
                        contadorMinas++;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");

        for (Casilla[] casillas: getCASILLAS()) {
            for (Casilla casilla: casillas) {
                sb.append(casilla.isMina());
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public int getDIMENSIONES() {
        return DIMENSIONES;
    }

    public Casilla[][] getCASILLAS() {
        return CASILLAS;
    }

    public int getMAXIMO_MINAS() {
        return MAXIMO_MINAS;
    }
}
