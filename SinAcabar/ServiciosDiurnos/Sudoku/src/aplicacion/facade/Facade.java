/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.facade;

import aplicacion.controlador.tablero.Casilla;
import aplicacion.controlador.tablero.Cuadrado;
import javax.swing.JTable;

/**
 * Patron de disenio Facade. Sirve de intermediario entre vista y controlador del programa.
 * @author Mario Codes Sánchez
 * @since 13/12/2016
 */
public class Facade {
    /**
     * Generacion del tablero mediante singleton pattern.
     */
    public void obtencionTablero() {
        ContenedorSingletton.getTableroSingleton();
    }
    
    /**
     * Relleno de un cuadrado grafico de la tabla con su homologo Cuadrado.
     * @param cuadrados
     * @param tabla
     * @param numeroCuadrado
     * @param primeraColumna
     * @param primeraFila 
     */
    private void rellenoCuadradoGrafico(Cuadrado[] cuadrados, JTable tabla, int numeroCuadrado, int primeraColumna, int primeraFila) {
        for (int indiceCasilla = 0, indiceFila = primeraFila; indiceCasilla < 3; indiceFila++, indiceCasilla++) { //Una fila de un cuadrado.
            tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla].getNumeroPropio(), primeraColumna, indiceFila); //Valor, row, columna.
            tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+3].getNumeroPropio(), primeraColumna+1, indiceFila); //Valor, row, columna.
            tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+6].getNumeroPropio(), primeraColumna+2, indiceFila); //Valor, row, columna.
        }        
    }
    
    /**
     * Rellenamos cada casilla de la tabla con su Casilla correspondiente.
     * @param tabla Tabla a rellenar.
     */
    public void rellenoTablaConNumeros(JTable tabla) {
        Cuadrado[] cuadrados = ContenedorSingletton.getTableroSingleton().getCUADRADOS();
        
        rellenoCuadradoGrafico(cuadrados, tabla, 0, 0 ,0); //fixme: arreglar esta chapuza y pasarlo a un 'for' para hacerlo mediante bucle.
        rellenoCuadradoGrafico(cuadrados, tabla, 1, 0 ,3);
        rellenoCuadradoGrafico(cuadrados, tabla, 2, 0 ,6);
        rellenoCuadradoGrafico(cuadrados, tabla, 3, 3 ,0);
        rellenoCuadradoGrafico(cuadrados, tabla, 4, 3 ,3);
        rellenoCuadradoGrafico(cuadrados, tabla, 5, 3 ,6);
        rellenoCuadradoGrafico(cuadrados, tabla, 6, 6 ,0);
        rellenoCuadradoGrafico(cuadrados, tabla, 7, 6 ,3);
        rellenoCuadradoGrafico(cuadrados, tabla, 8, 6 ,6);
    }
}
