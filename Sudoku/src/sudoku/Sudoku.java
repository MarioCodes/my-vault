/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import vista.WindowJuego;

/**
 * Solucion al problema del Sudoku. (3er intento).
 * @author Mario Codes Sánchez
 * @since 13/12/2016
 * @version 0.3 Parte grafica medio hecha. A ocultar numeros. Logica:
 *      Oculto numero.
 *      Comprobar si sudoku tiene solo 1 solucion
 *      Ocultar otro mientras solo 1 solucion.
 * @see http://www.sudokuoftheday.com/techniques/
 */
public class Sudoku {

    /**
     * todo: que al cliquear una celda, haga un highlight de su fila y columna enteros.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new WindowJuego();
    }
}
