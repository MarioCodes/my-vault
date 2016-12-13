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
        System.out.println(ContenedorSingletton.getTableroSingleton());
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
        for (int indiceCuadrado = numeroCuadrado, indiceColumna, indiceCasilla = 0; indiceCuadrado < 1; indiceCuadrado++) { //Un cuadrado.
            indiceColumna = primeraColumna;
            for (int i = 0, indiceFila = primeraFila; i < 3; indiceFila++, indiceCasilla++, i++) { //Una fila de un cuadrado.
                tabla.setValueAt(cuadrados[indiceCuadrado].getCASILLAS()[indiceCasilla].getNumeroPropio(), indiceColumna, indiceFila); //Valor, row, columna.
                tabla.setValueAt(cuadrados[indiceCuadrado].getCASILLAS()[indiceCasilla+3].getNumeroPropio(), indiceColumna+1, indiceFila); //Valor, row, columna.
                tabla.setValueAt(cuadrados[indiceCuadrado].getCASILLAS()[indiceCasilla+6].getNumeroPropio(), indiceColumna+2, indiceFila); //Valor, row, columna.
            }
        }        
    }
    
    /**
     * Rellenamos cada casilla de la tabla con su Casilla correspondiente.
     * @param tabla Tabla a rellenar.
     */
    public void rellenoTablaConNumeros(JTable tabla) {
        Cuadrado[] cuadrados = ContenedorSingletton.getTableroSingleton().getCUADRADOS();
        
        rellenoCuadradoGrafico(cuadrados, tabla, 0, 0 ,0);
        rellenoCuadradoGrafico(cuadrados, tabla, 1, 3 ,0);
        
//        for (int indiceCuadrado = 0, indiceFila = 0, indiceColumna; indiceCuadrado < 9; indiceCuadrado++, indiceFila++) {
//            int indiceCasilla;
//            for (indiceCasilla = 0, indiceColumna = 0; indiceCasilla < 9; indiceCasilla++, indiceColumna++) {
//                tabla.setValueAt(cuadrados[indiceCuadrado].getCASILLAS()[indiceCasilla].getNumeroPropio(), indiceFila, indiceColumna); //Valor, row, columna.
//            }
//        }
        
        
    }
}
