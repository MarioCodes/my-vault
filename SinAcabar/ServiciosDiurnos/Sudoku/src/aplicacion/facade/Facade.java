/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.facade;

import aplicacion.controlador.tablero.Cuadrado;
import javax.swing.JTable;

/**
 * Patron de disenio Facade. Sirve de intermediario entre vista y controlador del programa.
 * @author Mario Codes Sánchez
 * @since 13/12/2016
 */
public class Facade {
    /**
     * Relleno de un 'cuadrado' de la tabla, con los valores de las casillas de su homologo Cuadrado.
     *  Son necesarios los datos de la primera columna y fila propia de cada cuadrado, para ir rellenando a partir de alli. Antes los pasaba como parametro, pero estos datos los contiene
     *      la Casilla[0] de cada Cuadrado, por lo que los puedo obtener de alli directamente.
     * @param cuadrados Cuadrados creados previamente de donde extraer los datos de valor de cada Casilla.
     * @param tabla Tabla grafica que queremos rellenar.
     * @param numeroCuadrado Numero de cuadrado que toca rellenar en esta iteracion.
     */
    private void rellenoCuadradoGrafico(Cuadrado[] cuadrados, JTable tabla, int numeroCuadrado) {
        for (int indiceCasilla = 0, indiceColumna = cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_COLUMNA(); indiceCasilla < 3; indiceColumna++, indiceCasilla++) { //Una fila de un cuadrado.
            tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla].getNumeroPropio(), cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA(), indiceColumna); //Valor, row, columna.
            tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+3].getNumeroPropio(), cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA()+1, indiceColumna); //Valor, row, columna.
            tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+6].getNumeroPropio(), cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA()+2, indiceColumna); //Valor, row, columna.
        }
    }
    
    /**
     * Rellenamos cada 'casilla' de la tabla con su Casilla correspondiente.
     * @param tabla Tabla a rellenar.
     */
    private void rellenoTablaConNumeros(JTable tabla) {
        Cuadrado[] cuadrados = ContenedorSingletton.getTableroSingleton().getCUADRADOS();
        for(int i = 0; i < cuadrados.length; i++) {
            rellenoCuadradoGrafico(cuadrados, tabla, i);
        }
    }
    
    /**
     * Generacion / obtencion del tablero mediante Singleton Pattern.
     * @param tabla JTable para rellenar con los numeros.
     */
    public void generacionTablero(JTable tabla) {
        ContenedorSingletton.getTableroSingleton();
        rellenoTablaConNumeros(tabla);
    }
}
