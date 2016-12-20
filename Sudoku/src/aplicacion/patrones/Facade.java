/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.patrones;

import aplicacion.controlador.juego.GestionNumeros;
import aplicacion.controlador.tablero.Casilla;
import aplicacion.controlador.tablero.Cuadrado;
import aplicacion.controlador.tablero.Tablero;
import javax.swing.JTable;

/**
 * Patron de disenio Facade. Sirve de intermediario entre vista y controlador del programa.
 * @author Mario Codes Sánchez
 * @since 20/12/2016
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
    private void rellenoCuadradoGrafico(Cuadrado[] cuadrados, JTable tabla, int numeroCuadrado, boolean mostrarTodos) {
        for (int indiceCasilla = 0, indiceColumna = cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_COLUMNA(); indiceCasilla < 3; indiceColumna++, indiceCasilla++) { //Una fila de un cuadrado.
            if(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla].isVisible() || mostrarTodos) tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla].getNumeroPropio(), cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA(), indiceColumna); //Valor, row, columna.
            if(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+3].isVisible() || mostrarTodos)tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+3].getNumeroPropio(), cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA()+1, indiceColumna); //Valor, row, columna.
            if(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+6].isVisible() || mostrarTodos)tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+6].getNumeroPropio(), cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA()+2, indiceColumna); //Valor, row, columna.
        }
    }
    
    /**
     * Rellenamos cada 'casilla' de la tabla con su Casilla correspondiente.
     * @param tabla Tabla a rellenar.
     * @param mostrarTodos mostrar todas las casillas (usado en la version de trampas).
     */
    private void rellenoTablaConNumeros(JTable tabla, boolean mostrarTodos) {
        Cuadrado[] cuadrados = Singleton.getTableroSingleton().getCUADRADOS();
        for(int i = 0; i < cuadrados.length; i++) {
            rellenoCuadradoGrafico(cuadrados, tabla, i, mostrarTodos);
        }
    }
    
    /**
     * Generacion / obtencion del tablero mediante Singleton Pattern.
     * @param tabla JTable para rellenar con los numeros.
     * @param mostrarTodos mostrar todas las casillas (version JTable Trampas).
     */
    public void generacionTablero(JTable tabla, boolean mostrarTodos) {
        Singleton.getTableroSingleton();
        rellenoTablaConNumeros(tabla, mostrarTodos);
    }
    
    /**
     * Ocultacion de casillas para hacer el juego, 5 casillas por cuadrado.
     * fixme: cambiarlo y arreglarlo.
     */
    public void ocultacionNumerosAleatorios() {
        Cuadrado[] cuadrados = Singleton.getTableroSingleton().getCUADRADOS();
        
        for(Cuadrado cuadrado : cuadrados) {
            for(Casilla casilla : cuadrado.getCASILLAS()) {
                GestionNumeros.ocultacionNumeros(casilla);
            }
        }
    }
    
    /**
     * Oculta una casilla suelta tanto en la matriz de casillas como en el tablero de forma grafica.
     * todo: hacerlo en un hilo aparte, asi no se quedara colgado el programa.
     * @param tabla Tabla de la cual queremos ocultar. Sera la de juego.
     * @param row Fila donde se encuentra la casilla.
     * @param numeroCasillaFila Numero de casilla dentro de la fila.
     */
    public void ocultarCasilla(JTable tabla, int row, int numeroCasillaFila) {
        try {
            Tablero tablero = Singleton.getTableroSingleton();
            tablero.getFILAS()[row].getCASILLAS()[numeroCasillaFila].setVisible(false);
            tablero.getFILAS()[row].getCASILLAS()[numeroCasillaFila].setNumeroPropio(0);
            tabla.setValueAt("", row, tablero.getFILAS()[row].getCASILLAS()[numeroCasillaFila].getNUMERO_COLUMNA()); //Necesito el numero de columna pero lo puedo sacar de la propia casilla.
            System.out.println("Casilla Ocultada.");
        }catch(ArrayIndexOutOfBoundsException ex) {
            System.out.println("Valores fuera de rango.");
        }
    }
}
