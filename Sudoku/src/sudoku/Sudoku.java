/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import vista.WindowJuego;

/**
 * Solucion al problema del Sudoku. (3er intento Y DEFINITIVO!).
 * @author Mario Codes Sánchez
 * @since 25/12/2016
 * @version 0.4.1 Dejada aparte la solventacion por fuerza bruta, voy a terminar el juego normal primero y ya me pondre con eso.
 *                  Al terminar el juego normal, deberia hacer limpieza y arreglo de codigo antes de pasar a resolucion.
 * @see http://www.sudokuoftheday.com/techniques/
 */
public class Sudoku {

    /**
     * todo: cambiar el color de las celdas fijas a gris. Se deberia diferenciar de las modificables.
     * todo: que los numeros fijos visibles no sean editables.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new WindowJuego();
    }
}
