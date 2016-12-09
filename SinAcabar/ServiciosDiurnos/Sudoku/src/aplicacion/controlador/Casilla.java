/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador;

/**
 * Representacion de una casilla.
 * @author Mario Codes Sánchez
 * @since 08/12/2016
 */
public class Casilla {
    private final int NUMERO_PROPIO;
    private boolean visible;
    
    /**
     * Constructor usado por defecto para inicializar todas las casillas mientras voy desarrollando.
     */
    public Casilla() {
        this.NUMERO_PROPIO = 0;
        this.visible = true;
    }
    
    /**
     * Constructor al que se le pasa un numero y se pone visible la casilla.
     * @param numero Numero propio de la casilla a inicializar.
     */
    public Casilla(int numero) {
        this.NUMERO_PROPIO = numero;
        this.visible = true;
    }
    
    @Override
    public String toString() {
        return "" +this.NUMERO_PROPIO;
    }
}
