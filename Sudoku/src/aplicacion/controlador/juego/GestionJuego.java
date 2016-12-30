/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.juego;

import aplicacion.controlador.tablero.Casilla;
import aplicacion.controlador.tablero.Cuadrado;
import aplicacion.controlador.tablero.Tablero;
import aplicacion.patrones.Singleton;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JTable;

/**
 * Metodo relacionados con la gestion del Juego.
 *  Desde la generacion de numeros aleatorios validos segun las reglas del juego, a la ocultacion de casillas.
 *  Para la generacion de los numeros random, uso el Algoritmo de Fisher-Yates. Garantiza no repeticiones, dentro de una pool finita de numeros dada. (Ver enlace).
 * @author Mario Codes Sánchez
 * @since 30/12/2016
 * @see https://es.wikipedia.org/wiki/Algoritmo_Fisher-Yates#Tabla_paso_a_paso_.28implementaci.C3.B3n_Fisher-Yates.29
 * @version 0.1 Implementado algoritmo Fisher-Yates.
 */
public class GestionJuego {
    private static final float PORCENTAJE_OCULTACION_CASILLA = 0.60f; /* 60%. Modificar para ocultar mas o menos casillas en nuestro Sudoku. Hay que tener en cuenta,
                                                                                que este porcentaje es antes de hacer los checks. Por lo que el numero final sera algo menor.*/
    
    /**
     * Le pasamos 3 ArrayList con los numeros validos y devuelve otra con los comunes a todos.
     * @param numerosCuadrado Numeros validos del cuadrado.
     * @param numerosFila Numeros validos de la fila.
     * @param numerosColumna Numeros validos de la columna.
     * @return ArrayList con los numeros validos comunes.
     */
    private static ArrayList<Integer> filtradoConjuntoNumerosValidos(ArrayList<Integer> numerosCuadrado, ArrayList<Integer> numerosFila, ArrayList<Integer> numerosColumna) {
        ArrayList<Integer> numerosComunesValidos = new ArrayList<>();
        
        for(int numeroCuadrado: numerosCuadrado) {
            boolean numIsOk = false; //Para comprobar que este contenido en los 2. 
            for(int numeroFila: numerosFila) {
                if(numeroFila == numeroCuadrado) numIsOk = true;
            }
            
            for(int numeroColumna: numerosColumna) {
                if((numeroColumna == numeroCuadrado) && numIsOk) numerosComunesValidos.add(numeroCuadrado);
            }
        }
        
        return numerosComunesValidos;
    }
    
    /**
     * Shuffle aleatorio de la pool comun de ints, mediante el algoritmo Fisher-Yates (adaptado a ArrayList en vez de int[]).
     *  Ver links del Javadoc de la clase antes de modificar.
     * @param numerosComunesValidos Pool de ints a marear.
     */
    private static void shuffleRandomFisherYates(ArrayList<Integer> numerosComunesValidos) {
        Random rand = ThreadLocalRandom.current();
        for (int i = numerosComunesValidos.size() - 1; i > 0; i--) { //Ordena una lista de manera aleatoria sin repeticiones.
            int index = rand.nextInt(i+1);
            int randomValue = numerosComunesValidos.get(index);
            numerosComunesValidos.set(index, numerosComunesValidos.get(i));
            numerosComunesValidos.set(i, randomValue);
        }
    }
    
    /**
     * Quitamos de las ArrayLists locales el numero que acabamos de coger y hacemos un set de la arrayList actualizada.
     * @param tablero Tablero sobre el que operamos.
     * @param casilla Casilla a la que le estamos asignando numero. Se usa para obtener numeros de cuadrado, fila y columna.
     * @param numeroRandomValido Numero que hemos extraido para asignar.
     * @param numerosCuadrado Lista de numeros validos para el cuadrado.
     * @param numerosFila Lista de numeros validos para la fila.
     * @param numerosColumna Lista de numeros validos para la columna.
     */
    private static void gestionArrayLists(Tablero tablero, Casilla casilla, int numeroRandomValido, ArrayList<Integer> numerosCuadrado, 
        ArrayList<Integer> numerosFila, ArrayList<Integer> numerosColumna) 
    {
        numerosCuadrado.remove((Object) numeroRandomValido);
        numerosFila.remove((Object) numeroRandomValido);
        numerosColumna.remove((Object) numeroRandomValido);
        
        //Y hacemos un set de los numeros validos sin el que hemos usado.
        tablero.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].setNumerosDisponiblesCuadrado(numerosCuadrado);
        tablero.getFILAS()[casilla.getNUMERO_FILA()].setNumerosDisponiblesFila(numerosFila);
        tablero.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].setNumerosDisponiblesColumna(numerosColumna);
    }
    
    /**
     * Le pasamos el tablero y la casilla de donde queremos obtener los numeros validos de: cuadrado, fila y columna; Y sacamos un numero valido aleatorio de entre todos ellos. Quitando
     *  este de cada ArrayList para que no aparezca como un futuro valido.
     * @param tablero Tablero de donde obtener los Cuadrados, Filas y Columnas.
     * @param casilla Casilla para saber que Cuadrados, Filas y Columnas obtener.
     * @return Entero aleatorio valido entre todos. -1 error (punto muerto de generacion del tablero).
     */
    public static int generacionNumeroCasilla(Tablero tablero, Casilla casilla) {
        int numeroRandomValido = -1;

        //Obtenemos las ArrayLists con los numeros validos de Cuadrado, Fila y Columna.
        ArrayList<Integer> numerosCuadrado = tablero.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado();
        ArrayList<Integer> numerosFila = tablero.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila();
        ArrayList<Integer> numerosColumna = tablero.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna();
        ArrayList<Integer> numerosComunesValidos;
        
        numerosComunesValidos = filtradoConjuntoNumerosValidos(numerosCuadrado, numerosFila, numerosColumna);
        shuffleRandomFisherYates(numerosComunesValidos); //Para que siempre sea aleatorio y sin repetir.
        
        //Cogemos el primero, que siempre sera uno aleatorio nuevo.
        if(numerosComunesValidos.size() > 0) numeroRandomValido = numerosComunesValidos.get(0);
        
        gestionArrayLists(tablero, casilla, numeroRandomValido, numerosCuadrado, numerosFila, numerosColumna);

        return numeroRandomValido;
    }
    
    /**
     * Obtencion de booleano para ocultar una casilla, un % de las veces que queramos.
     * @return booleano aleatorio.
     */
    private static boolean ocultacionNumerosRand() {
        Random random = new Random();
        
        float suerte = random.nextFloat();
        
        return (suerte <= PORCENTAJE_OCULTACION_CASILLA);
    }
    
    /**
     * Ocultacion de una casilla en si misma. En este punto ya se ha hecho la asignacion aleatoria.
     * Pasamos una casilla y la tabla.
     * Ocultamos un numero, hacemos fuerza bruta y vemos si es irresoluble. Si lo es, damos marcha atras y deshacemos lo hecho.
     * @param table tabla normal de juego.
     * @param casilla Casilla que queremos ocultar.
     */
    private static void ocultarCasillaGeneracionTablero(JTable table, Casilla casilla) {
        int backupNum = casilla.getNumeroPropio(); //Si da error habra que recuperarlo.
        int resultadoCheck;
        
        casilla.setCasillaFija(false);
        casilla.setNumeroPropio(0);
        
        table.setValueAt("", casilla.getNUMERO_FILA(), casilla.getNUMERO_COLUMNA());
        resultadoCheck = Checks.checkOcultacionNumeros();
        
        if(resultadoCheck == -1) { //Si es irresoluble, damos marcha atras.
            casilla.setCasillaFija(true);
            casilla.setNumeroPropio(backupNum);
            table.setValueAt(backupNum, casilla.getNUMERO_FILA(), casilla.getNUMERO_COLUMNA());
        }
    }
    
    /**
     * Ocultacion de casillas del tablero de juego. Cada casilla tiene un tanto por ciento de ocultarse.
     * @param tabla Tabla de la que queremos ocultar las casillas.
     */
    public static void ocultarNumerosTablero(JTable tabla) {
        Cuadrado[] cuadrados = Singleton.getTableroActual().getCUADRADOS();
        
        for(Cuadrado cuadrado : cuadrados) {
            for(Casilla casilla : cuadrado.getCASILLAS()) {
                if(ocultacionNumerosRand()) {
                    ocultarCasillaGeneracionTablero(tabla, casilla);
                }
            }
        }
    }
}
