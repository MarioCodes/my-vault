/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.tablero;

import java.util.ArrayList;

/**
 * Fila del tablero.
 * @author Mario Codes Sánchez
 * @since 12/12/2016
 */
public class Fila {
    private Casilla[] casillas;
    private ArrayList<Integer> numerosDisponiblesFila = new ArrayList<Integer>() {{
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
     * Constructor por defecto.
     * @param casillas Casillas ya instanciadas que formaran la fila en si misma.
     */
    public Fila(Casilla[] casillas) {
        this.casillas = casillas;
    }

    /**
     * @param numerosDisponiblesFila the numerosDisponiblesFila to set
     */
    public void setNumerosDisponiblesFila(ArrayList<Integer> numerosDisponiblesFila) {
        this.numerosDisponiblesFila = numerosDisponiblesFila;
    }

    /**
     * @return the numerosDisponiblesFila
     */
    public ArrayList<Integer> getNumerosDisponiblesFila() {
        return numerosDisponiblesFila;
    }

    /**
     * @return the casillas
     */
    public Casilla[] getCasillas() {
        return casillas;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for(Casilla cas : casillas) {
            sb.append(cas);
            sb.append(" ");
        }
        
        return sb.toString() +"\n";
    }
}
