/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.tablero;

import java.util.ArrayList;

/**
 * Representacion de un Cuadrado con 9 casillas.
 * @author Mario Codes Sánchez
 * @since 12/12/2016
 */
public class Cuadrado {
    private final Casilla[] CASILLAS = new Casilla[9];
    private ArrayList<Integer> numerosDisponiblesCuadrado = new ArrayList<Integer>() {{
        add(1);
        add(2);
        add(3);
        add(4);
        add(5);
        add(6);
        add(7);
        add(8);
        add(9);
    }};
    
    /**
     * Constructor final a utilizar. Le paso los numeros de cuadrado, fila y columna para utilizarlos al instanciar las Casillas.
     * @param numeroCuadrado Numero propio del primer cuadrado.
     * @param numeroPrimeraFila Numero propio de la primera fila que corresponde.
     * @param numeroPrimeraColumna Numero propio de la primera columna.
     */
    public Cuadrado(int numeroCuadrado, int numeroPrimeraFila, int numeroPrimeraColumna) {
        iniCasillas(numeroCuadrado, numeroPrimeraFila, numeroPrimeraColumna);
    }
    
    /**
     * Inicializacion de las 9 casillas teniendo en cuenta su numero de cuadrado, fila y columna.
     * @param numeroCuadrado Numero propio del cuadrado de la casilla.
     * @param numeroPrimeraFila Numero de la fila.
     * @param numeroPrimeraColumna Numero de la columna.
     */
    private void iniCasillas(int numeroCuadrado, int numeroPrimeraFila, int numeroPrimeraColumna) {
        for (int i = 0, numFila = numeroPrimeraFila, numColumna = numeroPrimeraColumna; i < 9; i += 3, numFila++) {
            CASILLAS[i] = new Casilla(numeroCuadrado, numFila, numColumna);
            CASILLAS[i+1] = new Casilla(numeroCuadrado, numFila, numColumna+1);
            CASILLAS[i+2] = new Casilla(numeroCuadrado, numFila, numColumna+2);
        }
    }
    
    @Override
    public String toString() {
        String buffer = "";
        
        for (int i = 0; i < CASILLAS.length; i++) {
            buffer += CASILLAS[i] +" ";
            if((i+1) % 3 == 0) buffer += "\n";
        }
        
        return buffer +"\n";
    }

    /**
     * @return the CASILLAS
     */
    public Casilla[] getCASILLAS() {
        return CASILLAS;
    }

    /**
     * @param numerosDisponiblesCuadrado the numerosDisponiblesCuadrado to set
     */
    public void setNumerosDisponiblesCuadrado(ArrayList<Integer> numerosDisponiblesCuadrado) {
        this.numerosDisponiblesCuadrado = numerosDisponiblesCuadrado;
    }

    /**
     * @return the numerosDisponiblesCuadrado
     */
    public ArrayList<Integer> getNumerosDisponiblesCuadrado() {
        return numerosDisponiblesCuadrado;
    }
}
