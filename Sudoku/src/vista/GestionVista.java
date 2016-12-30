/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import aplicacion.controlador.tablero.Cuadrado;
import aplicacion.patrones.Singleton;
import javax.swing.JTable;

/**
 * Clase donde intento recopilar todo el codigo relacionado con la gestion de la 'Vista' del programa para
 *      desaturar un poco 'WindowJuego'.
 * @author Mario Codes Sánchez
 * @since 30/12/2016
 */
public class GestionVista {
    /**
     * Copiado del contenido de una JTable a otra.
     * Para hacer pruebas al querer comprobar si el otro tablero esta lleno, con datos bien introducidos o no.
     * @param tabla1 Tablero al que se copiara.
     * @param tabla2 Tablero desde el cual se copiara.
     */
    public void copiarTableros(JTable tabla1, JTable tabla2) {
        int valorCasilla;
        for (int indexColumns = 0; indexColumns < tabla2.getColumnCount(); indexColumns++) {
            for (int indexRows = 0; indexRows < tabla2.getRowCount(); indexRows++) {
                try {
                    valorCasilla = (int) tabla2.getValueAt(indexRows, indexColumns);
                    tabla1.setValueAt(valorCasilla, indexRows, indexColumns);
                } catch (ClassCastException ex) {} //Para cuando haya casillas en blanco, no pete.
            }
        }
    }
    
    /**
     * Relleno de un 'cuadrado' de la tabla, con los valores de las casillas de su homologo Cuadrado.
     *  Son necesarios los datos de la primera columna y fila propia de cada cuadrado, para ir rellenando a partir de alli. Antes los pasaba como parametro, pero estos datos los contiene
     *      la Casilla[0] de cada Cuadrado, por lo que los puedo obtener de alli directamente.
     * @param cuadrados Cuadrados creados previamente de donde extraer los datos de valor de cada Casilla.
     * @param tabla Tabla grafica que queremos rellenar.
     * @param numeroCuadrado Numero de cuadrado que toca rellenar en esta iteracion.
     */
    private void rellenoCuadradoGrafico(Cuadrado[] cuadrados, JTable tabla, int numeroCuadrado, boolean mostrarTodos) {
        for (int indiceCasilla = 0, indiceColumna = cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_COLUMNA(); indiceCasilla < 3; indiceColumna++, indiceCasilla++) { //Una fila de un cuadrado.
            if(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla].isCasillaFija() || mostrarTodos) tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla].getNumeroPropio(), cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA(), indiceColumna); //Valor, row, columna.
            if(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+3].isCasillaFija() || mostrarTodos)tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+3].getNumeroPropio(), cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA()+1, indiceColumna); //Valor, row, columna.
            if(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+6].isCasillaFija() || mostrarTodos)tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+6].getNumeroPropio(), cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA()+2, indiceColumna); //Valor, row, columna.
        }
    }
    
    /**
     * Rellenamos cada 'casilla' de la tabla con su Casilla correspondiente.
     * @param tabla Tabla a rellenar.
     * @param mostrarTodos mostrar todas las casillas (usado en la version de trampas).
     */
    private void rellenoTablaConNumeros(JTable tabla, boolean mostrarTodos) {
        Cuadrado[] cuadrados = Singleton.getTableroActual().getCUADRADOS();
        for(int i = 0; i < cuadrados.length; i++) {
            rellenoCuadradoGrafico(cuadrados, tabla, i, mostrarTodos);
        }
    }
    
    /**
     * Rellenamos una jTable con los valores del Tablero de juego actual.
     * @param tabla JTable para rellenar con los numeros.
     * @param mostrarTodos mostrar todas las casillas (para version JTable Trampas).
     */
    public void rellenoTablaConTablero(JTable tabla, boolean mostrarTodos) {
        Singleton.getTableroActual();
        rellenoTablaConNumeros(tabla, mostrarTodos);
    }
}
