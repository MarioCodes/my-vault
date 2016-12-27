/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.juego;

import aplicacion.controlador.tablero.Cuadrado;
import aplicacion.controlador.tablero.Tablero;
import javax.swing.JTable;

/**
 * Resolucion del problema por 'Backtrack'.
 * @author Mario Codes Sánchez
 * @since 26/12/2016
 * @see https://en.wikipedia.org/wiki/Sudoku_solving_algorithms
 */
public class Resolucion {
    private Tablero tablero;

    /**
     * Relleno de Cuadrado[] con los valores de la tabla de su homologo Cuadrado.
     * @param cuadrados Cuadrados donde almaceno los datos de cada casilla.
     * @param tabla Tabla grafica de donde sacamos los datos.
     * @param numeroCuadrado Numero de cuadrado que toca rellenar en esta iteracion.
     */
    private void rellenoCuadradoTablero(Cuadrado[] cuadrados, JTable tabla, int numeroCuadrado) {
        for (int indiceCasilla = 0, indiceColumna = cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_COLUMNA(); indiceCasilla < 3; indiceColumna++, indiceCasilla++) { //Una fila de un cuadrado.
            try { //fixme: intentar arreglar esta chapuza, si lo pongo en tries separados funciona. Si no, al saltar la excepcion en la linea que sea, salta y las otras 2 ni siquiera se comprueban. Arreglarlo. Segmentarlo en mas metodos(?).
                cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla].setNumeroPropio(Integer.parseInt(tabla.getValueAt(cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA(), indiceColumna).toString()));
            }catch(NumberFormatException | NullPointerException ex) {}
            
            try {
                cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+3].setNumeroPropio(Integer.parseInt(tabla.getValueAt(cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA()+1, indiceColumna).toString()));
            }catch(NumberFormatException | NullPointerException ex) {}
            
            try {
                cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+6].setNumeroPropio(Integer.parseInt(tabla.getValueAt(cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA()+2, indiceColumna).toString()));
            }catch(NumberFormatException | NullPointerException ex) {} //Esto saltara en las casillas que se encuentren vacias, es completamente normal.
        }
    }
    
    /**
     * Rellenamos cada Casilla con su 'casilla' correspondiente de la tabla.
     * @param tabla Tabla de la que sacamos los datos.
     * @param cuadrados Cuadrado[] que rellenamos.
     */
    private void rellenoTablaConNumeros(JTable tabla, Cuadrado[] cuadrados) {
        for(int i = 0; i < cuadrados.length; i++) {
            rellenoCuadradoTablero(cuadrados, tabla, i);
        }
    }
    
    /**
     * Almacenamiento de los valores de una tabla grafica en forma de nuevo tablero.
     * Es muy muy parecido al metodo que hay en Tablero para rellenar una tabla con el contenido de las casillas, pero aqui
     *  interesa rellenas las casillas con el contenido de la tabla.
     * @param tabla Tabla con la cual obtenemos el tablero.
     */
    public void generacionTablero(JTable tabla) {
        Cuadrado[] cuadrados = new Cuadrado[9];
        Tablero.inicializacionCuadrados(cuadrados);
        rellenoTablaConNumeros(tabla, cuadrados);
        
        this.tablero = new Tablero(cuadrados);
    }
}
