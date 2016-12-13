/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import aplicacion.facade.Facade;
import vista.WindowJuego;

/**
 * Solucion al problema del Sudoku. (3er intento).
 * @author Mario Codes Sánchez
 * @since 12/12/2016
 * @version 0.2 Solucionado el marron de la generacion aleatoria de numeros de forma correcta. A por la parte grafica.
 */
public class Sudoku {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Facade fachada = new Facade();
        fachada.obtencionTablero();
        new WindowJuego();
    }
}
