/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.aplicacion;

/**
 * Representacion de la entidad Casilla dentro del juego Sudoku.
 * @author Mario Codes Sánchez
 * @since 30/11/2016
 */
public class Casilla {
    private final int NUMERO_PROPIO;
    private boolean visible;
    
    /**
     * Constructor usado mientras desarrollo, le paso un numero y pongo el boolean por defecto a 'true'.
     * @param numero Numero propio de la casilla.
     */
    public Casilla(int numero) {
        this.NUMERO_PROPIO = numero;
        this.visible = true;
    }
    
    /**
     * Constructor normal para utilizar.
     * @param numero numero propio intrinseco de la casilla.
     * @param visible booleano que indica si el numero es visible.
     */
    public Casilla(int numero, boolean visible) {
        this.NUMERO_PROPIO = numero;
        this.visible = visible;
    }

    /**
     * Me he acostumbrado a sobreescribir este metodo, asi luego no tengo que hacer cosas raras para ir testeando/viendo el estado de las cosas mientras programo.
     * @return Numero propio de la casilla.
     */
    @Override
    public String toString() {
        return "" +this.NUMERO_PROPIO;
    }
    
    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return the NUMERO_PROPIO
     */
    public int getNUMERO_PROPIO() {
        return NUMERO_PROPIO;
    }
}
