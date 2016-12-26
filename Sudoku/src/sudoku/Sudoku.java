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
 * @since 26/12/2016
 * @version 0.5.1 A por los metodos de resolucion por 'Backtrack'. Metido los contenidos de la tabla grafica en un tablero.
 *                  Lo siguiente es generar un tablero propio y obtenerlos de alli. Poner opcion para copiar el de juego.
 * @see http://www.sudokuoftheday.com/techniques/
 */
public class Sudoku {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new WindowJuego();
    }
}
