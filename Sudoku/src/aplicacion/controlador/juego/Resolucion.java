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
import javax.swing.JTable;

/**
 * Resolucion del Sudoku.
 * No se tiene en cuenta el valor de la casilla para conseguir la resolucion, metodos independientes a esto.
 * @author Mario Codes Sánchez
 * @since 20/12/2016
 * @version 0.1 Intentando implementar a fuerza bruta.
 * @see https://en.wikipedia.org/wiki/Sudoku_solving_algorithms
 */
public class Resolucion {
    private static final Tablero TABLERO = Singleton.getTableroSingleton();
    private static int[][][] numerosComprobados = new int[9][9][1]; //[Cuadrado][Casilla][valor]. //Representacion de valores de toda la tabla.
    
    /**
     * Para cada casilla prueba y comprueba cada numero.
     * @param casillaActual Casilla a comprobar.
     * @return numero valido o -1 si no lo hay.
     */
    private static int checkCasilla(Casilla casillaActual) {
        for (int numeroAProbar = 1; numeroAProbar < 10; numeroAProbar++) { //Comprobacion de los 9 numeros posibles de cada casilla de cada cuadrado.
            boolean numeroValido = true;
            
            for (int i = 0; numeroValido && i < 9; i++) { //Utilizo el primer comprobante para ahorrar tiempo de CPU, asi si no es valido, directamente pasa al siguiente.
                if(TABLERO.getFILAS()[casillaActual.getNUMERO_FILA()].getCASILLAS()[i].getNumeroPropio() == numeroAProbar) numeroValido = false;
                if(TABLERO.getCOLUMNAS()[casillaActual.getNUMERO_COLUMNA()].getCASILLAS()[i].getNumeroPropio() == numeroAProbar) numeroValido = false;
                if(TABLERO.getCUADRADOS()[casillaActual.getNUMERO_CUADRADO()].getCASILLAS()[i].getNumeroPropio() == numeroAProbar) numeroValido = false;
            }
            
            if(numeroValido) return numeroAProbar;
        }
        return -1;
    }
    
    /**
     * Intenta solucionar el Sudoku por fuerza bruta.
     */
    public static void solucionFuerzaBruta() {
        for (int indexCuadrado = 0; indexCuadrado < TABLERO.getCUADRADOS().length; indexCuadrado++) { //Para cada cuadrado.
            for (int indexCasilla = 0; indexCasilla < TABLERO.getCUADRADOS()[indexCuadrado].getCASILLAS().length; indexCasilla++) { //Para cada casilla de cada cuadrado.
                Casilla casillaActual = TABLERO.getCUADRADOS()[indexCuadrado].getCASILLAS()[indexCasilla];
                if(!casillaActual.isVisible()) {
                    int numeroDevuelto = checkCasilla(casillaActual);
                    if(numeroDevuelto != -1) {
                        casillaActual.setNumeroPropio(numeroDevuelto);
                        numerosComprobados[indexCuadrado][indexCasilla][0] = numeroDevuelto;
                        System.out.println(numeroDevuelto); //fixme: borrame. testeo. //todo: me quedo aqui, terminar de implementar solucion fuerza bruta. Que rellene el contenido de casilla y la jtable.
                    }
                }
            }
        }
    }
}
