/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.juego;

import aplicacion.controlador.tablero.Casilla;
import aplicacion.controlador.tablero.Tablero;
import aplicacion.patrones.Singleton;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 * Clase estatica. Metodos para la resolucion del Sudoku.
 * @author Mario Codes Sánchez
 * @since 21/12/2016
 * @version 0.2 Con la resolucion del tablero de juego normal.
 * @see https://en.wikipedia.org/wiki/Sudoku_solving_algorithms
 */
public class Resolucion {
    private static final Tablero TABLERO = Singleton.getTableroSingleton();
//    private static int[][][] numerosComprobados = new int[9][9][1]; //[Cuadrado][Casilla][valor]. //Representacion de valores de toda la tabla. //fixme: borrar si al final no lo necesito.
    
    /**
     * Comprobacion mediante expresion regular que no se meta nada que no se deba y que no haya nada vacio.
     * @param comprobacion Contenido de la casilla a comprobar.
     * @return True si se cumple la condicion regular.
     */
    private static boolean comprobacionTableroSoloNumeros(String comprobacion) {
        Pattern pattern = Pattern.compile("[1-9]");
        Matcher matcher = pattern.matcher(comprobacion);
        
        return matcher.matches();
    }
    
    /**
     * Comprobacion de que en el tablero no se haya metido paja o casillas vacias antes de chequear la solucion.
     * @param tabla Tabla que queremos comprobar.
     * @return True si esta correcto, ni paja ni casillas vacias.
     */
    public static boolean comprobarTableroLleno(JTable tabla) {
        boolean tableroCompleto = true;

        try {
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                for (int j = 0; j < tabla.getRowCount(); j++) {
                    comprobacionTableroSoloNumeros(Integer.toString((int) tabla.getValueAt(j, i)));
                }
            }
        } catch(NullPointerException | ClassCastException ex) {
            tableroCompleto = false; //Aqui porque petara antes de poder asignar el booleano del resultado.
        }
        
        return tableroCompleto;
    }
    
    /**
     * Comprobamos los numeros introducidos en el tablero grafico que pasamos como parametro. Este sera el tablero principal de juego.
     * @param tablaNormal Tablero normal de input de user.
     * @param tablaTrampas Tablero contra el que se compara.
     * @return El tablero normal es igual al de trampas.
     */
    public static boolean comprobarResolucionTableroGrafico(JTable tablaNormal, JTable tablaTrampas) {
        boolean tableroSolucionado = true;
        
        for (int indexRow = 0; indexRow < 9; indexRow++) {
            for (int indexColumna = 0; indexColumna < 9; indexColumna++) {
                if(tablaNormal.getValueAt(indexRow, indexColumna) != tablaTrampas.getValueAt(indexRow, indexColumna)) tableroSolucionado = false;
            }
        }
        
        return tableroSolucionado;
    }
    
    /**
     * Copiado del contenido del tablero de trampas al tablero normal de juego.
     * Para hacer pruebas al querer comprobar si el otro tablero esta lleno, con datos bien introducidos o no.
     * @param tablaNormal Tablero al que se copiara.
     * @param tablaTrampas Tablero desde el cual se copiara.
     */
    public static void copiarTableroTrampasAlNormal(JTable tablaNormal, JTable tablaTrampas) {
        int valorCasilla;
        
        for (int indexColumns = 0; indexColumns < tablaTrampas.getColumnCount(); indexColumns++) {
            for (int indexRows = 0; indexRows < tablaTrampas.getRowCount(); indexRows++) {
                valorCasilla = (int) tablaTrampas.getValueAt(indexRows, indexColumns);
                tablaNormal.setValueAt(valorCasilla, indexRows, indexColumns);
            }
        }
    }
    
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
     * Dejado para mas adelante, NO funciona. Resuelve hasta donde le sale, deberia volver atras y retomarlo con otros valores si no puede.
     * @param tabla Tabla que queremos solucionar.
     * @return 0 salida correcta. -1 salida error.
     */
    public static int solucionFuerzaBruta(JTable tabla) {
        for (int indexCuadrado = 0; indexCuadrado < TABLERO.getCUADRADOS().length; indexCuadrado++) { //Para cada cuadrado.
            for (int indexCasilla = 0; indexCasilla < TABLERO.getCUADRADOS()[indexCuadrado].getCASILLAS().length; indexCasilla++) { //Para cada casilla de cada cuadrado.
                Casilla casillaActual = TABLERO.getCUADRADOS()[indexCuadrado].getCASILLAS()[indexCasilla];
                if(!casillaActual.isVisible()) {
                    int numeroDevuelto = checkCasilla(casillaActual);
                    if(numeroDevuelto != -1) {
                        casillaActual.setNumeroPropio(numeroDevuelto);
//                        numerosComprobados[indexCuadrado][indexCasilla][0] = numeroDevuelto;
                        tabla.setValueAt(numeroDevuelto, casillaActual.getNUMERO_FILA(), casillaActual.getNUMERO_COLUMNA());
                    } else return -1;
                }
            }
        }
        return 0;
    }
    
    /**
     * Check al ocultar numeros para saber si hay +1 solucion posible o podemos seguir ocultando.
     * @return 0 salida normal. -1 salida error.
     * fixme: si sale bien todo esto, mover a la clase que corresponda.
     * fixme: revisar bien, copia del metodo solucionFuerzaBruta
     */
    public static int checkOcultacionNumeros() {
        for (int indexCuadrado = 0; indexCuadrado < TABLERO.getCUADRADOS().length; indexCuadrado++) { //Para cada cuadrado.
            for (int indexCasilla = 0; indexCasilla < TABLERO.getCUADRADOS()[indexCuadrado].getCASILLAS().length; indexCasilla++) { //Para cada casilla de cada cuadrado.
                Casilla casillaActual = TABLERO.getCUADRADOS()[indexCuadrado].getCASILLAS()[indexCasilla];
                if(!casillaActual.isVisible()) {
                    int numeroDevuelto = checkCasilla(casillaActual);
                    if(numeroDevuelto == -1) {
                        return numeroDevuelto;
                    }
                }
            }
        }
        return 0;
    }
}
