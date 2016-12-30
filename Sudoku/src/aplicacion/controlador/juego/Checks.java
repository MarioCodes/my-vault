/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.juego;

import aplicacion.controlador.tablero.Casilla;
import aplicacion.controlador.tablero.Columna;
import aplicacion.controlador.tablero.Cuadrado;
import aplicacion.controlador.tablero.Fila;
import aplicacion.controlador.tablero.Tablero;
import aplicacion.patrones.Singleton;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;

/**
 * Clase estatica. Recopilacion de comprobaciones y checks necesarios, ademas de los metodos para testing que he ido necesitando.
 * @author Mario Codes Sánchez
 * @since 29/12/2016
 */
public class Checks {
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
        try {
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                for (int j = 0; j < tabla.getRowCount(); j++) {
                    comprobacionTableroSoloNumeros(Integer.toString((int) tabla.getValueAt(j, i)));
                }
            }
        } catch (NullPointerException | ClassCastException ex) {
            return false; //Si hay alguna casilla vacia o que no cuadre, saltara la excepcion. 
       }
        return true;
    }
        
    /**
     * Para cada casilla prueba y comprueba cada numero.
     * @param tablero Tablero sobre el cual operamos.
     * @param casillaActual Casilla a comprobar.
     * @return numero valido o -1 si no lo hay.
     */
    private static int checkCasilla(Tablero tablero, Casilla casillaActual) {
        for (int numeroAProbar = 1; numeroAProbar < 10; numeroAProbar++) { //Comprobacion de los 9 numeros posibles de cada casilla de cada cuadrado.
            boolean numeroValido = true;
            
            for (int indiceCasilla = 0; numeroValido && indiceCasilla < 9; indiceCasilla++) { //Utilizo el primer comprobante para ahorrar tiempo de CPU, asi si no es valido, directamente pasa al siguiente.
                if(tablero.getFILAS()[casillaActual.getNUMERO_FILA()].getCASILLAS()[indiceCasilla].getNumeroPropio() == numeroAProbar) numeroValido = false;
                if(tablero.getCOLUMNAS()[casillaActual.getNUMERO_COLUMNA()].getCASILLAS()[indiceCasilla].getNumeroPropio() == numeroAProbar) numeroValido = false;
                if(tablero.getCUADRADOS()[casillaActual.getNUMERO_CUADRADO()].getCASILLAS()[indiceCasilla].getNumeroPropio() == numeroAProbar) numeroValido = false;
            }
            
            if(numeroValido) return numeroAProbar;
        }
        return -1;
    }
    
    /**
     * Check al ocultar numeros para saber si hay +1 solucion posible o podemos seguir ocultando.
     * Esto se suponia que era temporal, pero funciona y no se porque.
     * @return 0 salida normal. -1 salida error.
     */
    public static int checkOcultacionNumeros() {
        Tablero tablero = Singleton.getTableroActual();
        for (int indexCuadrado = 0; indexCuadrado < tablero.getCUADRADOS().length; indexCuadrado++) { //Para cada cuadrado.
            for (int indexCasilla = 0; indexCasilla < tablero.getCUADRADOS()[indexCuadrado].getCASILLAS().length; indexCasilla++) { //Para cada casilla de cada cuadrado.
                Casilla casillaActual = tablero.getCUADRADOS()[indexCuadrado].getCASILLAS()[indexCasilla];
                if(!casillaActual.isCasillaFija()) {
                    int numeroDevuelto = checkCasilla(tablero, casillaActual);
                    if(numeroDevuelto == -1) {
                        return numeroDevuelto;
                    }
                }
            }
        }
        return 0;
    }
    
    /**
     * Creamos una lista con los numeros del 1 al 9 y comparamos la que queremos comprobar, con esta.
     * @param lista Lista que queremos comprobar si contiene todos los numeros.
     * @return True si es igual a la lista correcta.
     * @version 0.2 Arreglada la comprobacion. Antes no lo hacia correctamente.
     */
    private static boolean comprobarLista(ArrayList<Integer> lista) {
        ArrayList<Integer> listaNumerosCompleta = new ArrayList<Integer>() {{
            for (int i = 1; i < 10; i++) {
                add(i);
            }
        }};
        
        return lista.containsAll(listaNumerosCompleta);
    }
    
    /**
     * Comprobacion de los cuadrados del tablero. Que en ellos mismos no contengan numeros repetidos.
     * @param cuadrados Cuadrados[] que queremos comprobar.
     * @return True si estan resueltos correctamente.
     */
    private static boolean chequeoCuadrados(Cuadrado[] cuadrados) {
        ArrayList<Integer> numerosLeidos = null;
        for (Cuadrado cuadrado : cuadrados) {
            numerosLeidos = new ArrayList<>();
            for (Casilla casilla : cuadrado.getCASILLAS()) {
                numerosLeidos.add(casilla.getNumeroPropio());
            }
            if (!comprobarLista(numerosLeidos)) {
                System.out.println("Cuadrado malo: " + cuadrado.getCASILLAS()[0].getNUMERO_CUADRADO());
                return false;
            }
        }
        return true;
    }

    /**
     * Chequeo de las filas, que no contengan numeros repetidos entre ellas.
     * @param filas Fila[] que queremos comprobar.
     * @return True si no hay numero repetido.
     */
    private static boolean chequeoFilas(Fila[] filas) {
        ArrayList<Integer> numerosLeidos = null;
        for (Fila fila : filas) {
            numerosLeidos = new ArrayList<>();
            for (Casilla casilla : fila.getCASILLAS()) {
                numerosLeidos.add(casilla.getNumeroPropio());
            }
            if (!comprobarLista(numerosLeidos)) {
                System.out.println("Fila mala: " + fila.getCASILLAS()[0].getNUMERO_FILA());
                return false;
            }
        }
        return true;
    }

    /**
     * Comprobacion de las columnas, que no tengan numeros repetidos.
     * @param columnas Columna[] a comprobar.
     * @return True si no hay numeros repetidos.
     */
    private static boolean chequeoColumnas(Columna[] columnas) {
        ArrayList<Integer> numerosLeidos = null;
        for (Columna columna : columnas) {
            numerosLeidos = new ArrayList<>();
            for (Casilla casilla : columna.getCASILLAS()) {
                numerosLeidos.add(casilla.getNumeroPropio());
            }
            if (!comprobarLista(numerosLeidos)) {
                System.out.println("Columna mala: " + columna.getCASILLAS()[0].getNUMERO_COLUMNA());
                return false;
            }
        }
        return true;
    }
    
    /**
     * Comprobacion de las 3 anteriores.
     * Check de que el conjunto de Cuadrados, filas y columnas esten bien resueltas.
     * @param tablero Tablero a comprobar.
     * @return True si el conjunto de las 3 posibilidades estan de forma correcta.
     */
    public static boolean chequeoResolucion(Tablero tablero) {
        return chequeoCuadrados(tablero.getCUADRADOS()) && chequeoFilas(tablero.getFILAS()) && chequeoColumnas(tablero.getCOLUMNAS());
    }

    /**
     * Oculta una casilla suelta tanto en la matriz de casillas como en el tablero de forma grafica.
     * @param tabla Tabla de la cual queremos ocultar. Sera la de juego.
     * @param row Fila donde se encuentra la casilla.
     * @param numeroCasillaFila Numero de casilla dentro de la fila.
     */
    public static void ocultarCasillaEspecificaTesteo(JTable tabla, int row, int numeroCasillaFila) {
        try {
            Tablero tablero = Singleton.getTableroActual();
            tablero.getFILAS()[row].getCASILLAS()[numeroCasillaFila].setCasillaFija(false);
            tablero.getFILAS()[row].getCASILLAS()[numeroCasillaFila].setNumeroPropio(0);
            tabla.setValueAt("", row, tablero.getFILAS()[row].getCASILLAS()[numeroCasillaFila].getNUMERO_COLUMNA());
            System.out.println("Casilla Ocultada.");
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Valores fuera de rango.");
        }
    }
}
