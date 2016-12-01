/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokuagain;

import controlador.aplicacion.Cuadrado;
import controlador.aplicacion.Tablero;

/**
 * Programado del juego Sudoku. Se genera automaticamente y se puede de la misma manera o jugando a mano.
 * @author Mario Codes Sánchez
 * @since 30/11/2016
 * @version 0.2.2 Metidas las Casillas en sus Filas. Modificando la generacion del numero aleatorio para que se comparta entre fila y casilla.
 */
public class SudokuAgain {

    /**
     * todo: Paso Actual -> que el tablero genere 9 cuadrados, 9 filas y 9 columnas.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Cuadrado c = new Cuadrado(); System.out.println(c);
        Tablero t = new Tablero();
        System.out.println(t);
    }
    
}