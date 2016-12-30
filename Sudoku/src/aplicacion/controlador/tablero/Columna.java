/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.tablero;

import java.util.ArrayList;

/**
 * Representacion de una columna del tablero de juego.
 * Dispone de sus propias Casillas y de una AL de Integers que corresponden con los numeros libres que estan por asignar en 
 *  esta columna.
 * @author Mario Codes Sánchez
 * @since 26/12/2016
 */
public class Columna {
    private final Casilla[] CASILLAS;
    private ArrayList<Integer> numerosDisponiblesColumna = new ArrayList<Integer>() {{
        for (int i = 1; i < 10; i++) {
            add(i);
        }
    }};
    
    /**
     * Constructor a utilizar por defecto para rellenar.
     * @param casillas Casilla[] que formara la columna.
     */
    public Columna(Casilla[] casillas) {
        this.CASILLAS = casillas;
    }

    /**
     * @param numerosDisponiblesColumna the numerosDisponiblesColumna to set
     */
    public void setNumerosDisponiblesColumna(ArrayList<Integer> numerosDisponiblesColumna) {
        this.numerosDisponiblesColumna = numerosDisponiblesColumna;
    }

    /**
     * @return the numerosDisponiblesColumna
     */
    public ArrayList<Integer> getNumerosDisponiblesColumna() {
        return numerosDisponiblesColumna;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for(Casilla cas: getCASILLAS()) {
            sb.append(cas);
            sb.append("\n");
        }
        
        sb.append("\n");
        
        return sb.toString();
    }

    /**
     * @return the CASILLAS
     */
    public Casilla[] getCASILLAS() {
        return CASILLAS;
    }
}
