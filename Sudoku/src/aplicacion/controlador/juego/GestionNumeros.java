/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.juego;

import aplicacion.controlador.tablero.Casilla;
import aplicacion.controlador.tablero.Tablero;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Metodos relacionados con la generacion de numeros aleatorios que sean validos segun las reglas del juego.
 *  Usado Algoritmo de Fisher-Yates para la generacion de numeros completamente aleatorios sin repeticiones, dentro de una pool finita de numeros dada. (Ver enlaces).
 * @author Mario Codes Sánchez
 * @since 24/12/2016
 * @see https://es.wikipedia.org/wiki/Algoritmo_Fisher-Yates#Tabla_paso_a_paso_.28implementaci.C3.B3n_Fisher-Yates.29
 * @see http://stackoverflow.com/questions/8116872/generate-random-numbers-in-array
 * @version 0.1 Soy imbecil, y se me ha ocurrido antes usar un algoritmo innecesario para un problema que no tenia, que sacar directamente un index aleatorio para acceder a una AL ordenada.
 *                  Me ha hecho gracia el invento y me puede ser util tener una implementacion en un futuro. Afectara algo al rendimiento ahi que se queda.
 */
public class GestionNumeros {
    private static final float PORCENTAJE_OCULTACION_CASILLA = 0.35f; /* 35%. Modificar para ocultar mas o menos casillas en nuestro Sudoku. 
                                                                        Segun teorias matematicas, un Sudoku con menos de 17 casillas visibles es imposible que disponga de una unica solucion.*/
    
    /**
     * Le pasamos 3 ArrayList con los numeros validos y devuelve otra con los comunes a todos.
     * @param numerosCuadrado Numeros validos del cuadrado.
     * @param numerosFila Numeros validos de la fila.
     * @param numerosColumna Numeros validos de la columna.
     * @return ArrayList con los numeros validos comunes.
     */
    private static ArrayList<Integer> filtradoNumerosValidosComunes(ArrayList<Integer> numerosCuadrado, ArrayList<Integer> numerosFila, ArrayList<Integer> numerosColumna) {
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
        
        numerosComunesValidos = filtradoNumerosValidosComunes(numerosCuadrado, numerosFila, numerosColumna);
        shuffleRandomFisherYates(numerosComunesValidos); //Para que siempre sea aleatorio y sin repetir.
        
        //Cogemos el primero, que siempre sera uno aleatorio nuevo.
        if(numerosComunesValidos.size() > 0) numeroRandomValido = numerosComunesValidos.get(0);
        
        try { //fixme: Arreglar la chapuza esta. Vale que funciona, pero no se puede quedar asi en la version final ni de coña. Mirar porque revienta.
            numerosCuadrado.remove(numerosCuadrado.indexOf(numeroRandomValido));
            numerosFila.remove(numerosFila.indexOf(numeroRandomValido));
            numerosColumna.remove(numerosColumna.indexOf(numeroRandomValido));
        } catch(ArrayIndexOutOfBoundsException ex) {}
        
        //Y hacemos un set de los numeros validos sin el que hemos usado.
        tablero.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].setNumerosDisponiblesCuadrado(numerosCuadrado);
        tablero.getFILAS()[casilla.getNUMERO_FILA()].setNumerosDisponiblesFila(numerosFila);
        tablero.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].setNumerosDisponiblesColumna(numerosColumna);

        return numeroRandomValido;
    }
    
    /**
     * Ocultacion aleatoria del numero propio de la casilla.
     * @param casilla Casilla que queremos ocultar.
     * @deprecated Como ultima opcion, no deberia hacerlo asi sino ir ocultando hasta que tenga 2 soluciones posibles.
     * todo: borrar o modificar, no deberia utilizarla tal como esta ahora.
     */
    public static void ocultacionNumerosRandom(Casilla casilla) {
        Random random = new Random();
        
        float suerte = random.nextFloat();
        
        if(suerte <= 0.40f) casilla.setVisible(false); //40% de que se oculte.
    }
    
    /**
     * Obtencion de booleano para ocultar una casilla, un % de las veces que queramos.
     * @return booleano aleatorio.
     */
    public static boolean ocultacionNumerosRand() {
        Random random = new Random();
        
        float suerte = random.nextFloat();
        
        return (suerte <= PORCENTAJE_OCULTACION_CASILLA);
    }
}
