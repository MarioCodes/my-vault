/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import vista.WindowJuego;

/**
 * Solucion al problema del Sudoku.
 *  Permite al usuario jugar al juego japones Sudoku generando los tableros de juego de manera automatica teniendo en cuenta las reglas del juego.
 *  Ademas de esto permite la introduccion de Sudokus y su resolucion mediante diferentes tecnicas:
 *      - Fuerza Bruta: Busca una solucion valida a base de poder computacional mediante prueba y error.
 *      - Tecnicas 'Humanas': Busca una solucion valida mediante tecnicas de solucion que estan orientadas a Humanos.
 * @author Mario Codes Sánchez
 * @since 30/12/2016
 * @version 0.8.1 - Acabado con la resolucion 'Humana'.
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
