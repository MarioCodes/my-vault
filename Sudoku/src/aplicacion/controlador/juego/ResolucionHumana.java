/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.juego;

import aplicacion.controlador.tablero.Tablero;
import java.util.ArrayList;
import javax.swing.JTable;

/**
 * Resolucion del Sudoku mediante utilizacion de algoritmos de solucion 'Humanos'.
 *  Utilizado del metodo 'Single Candidate'. (Ver Link Anexo).
 * @author Mario Codes Sánchez
 * @since 12/01/2016
 * @see http://www.sudokuoftheday.com/techniques/single-candidate/
 */
public class ResolucionHumana {
    private final JTable TABLA;
    private final Tablero TABLERO;
    private final ArrayList<Integer>[][] NUMEROS_POSIBLES_CASILLA = new ArrayList[9][9]; //[EjeX][EjeY].add(numerosPosibles)
    
    /**
     * Constructor por defecto, le pasamos los datos de una JTable y la convertimos a un Tablero con el cual operar.
     * @param tabla Tabla desde la cual sacamos los datos.
     */
    public ResolucionHumana(JTable tabla) {
        this.TABLA = tabla;
        this.TABLERO = Tablero.generacionTablero(tabla);
        
        iniListaCasillasPosibles();
    }
    
    /**
     * Inicializacion de las ArrayList.
     */
    private void iniListaCasillasPosibles() {
        for (int i = 0; i < NUMEROS_POSIBLES_CASILLA.length; i++) {
            for (int j = 0; j < NUMEROS_POSIBLES_CASILLA[i].length; j++) {
                NUMEROS_POSIBLES_CASILLA[i][j] = new ArrayList<>();
            }
        }
    }
    
    /**
     * Metemos los numeros posibles de cada Casilla en la ArrayList[][].
     */
    private void almacenarNumerosPosibles() {
//        TABLERO.getCOLUMNAS()[0].getCASILLAS()[0]
    }
    
    /**
     * Metodo de llamado para ejecutar la resolucion.
     */
    public void resolucionTecnicaHumana() {
        almacenarNumerosPosibles();
    }
}
