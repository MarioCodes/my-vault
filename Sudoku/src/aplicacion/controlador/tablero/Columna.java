/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.tablero;

import java.util.ArrayList;

/**
 * Representacion de una columna del tablero de juego.
 * @author Mario Codes Sánchez
 * @since 20/12/2016
 */
public class Columna {
    private final Casilla[] CASILLAS;
    private ArrayList<Integer> numerosDisponiblesColumna = new ArrayList<Integer>() {{
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
