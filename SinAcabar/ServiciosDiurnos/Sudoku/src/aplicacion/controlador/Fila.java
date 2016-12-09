/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador;

/**
 * Fila del tablero.
 * @author Mario Codes Sánchez
 * @since 08/12/2016
 */
public class Fila {
    private Casilla[] casillas;
    
    /**
     * Constructor por defecto.
     * @param casillas Casillas ya instanciadas que formaran la fila en si misma.
     */
    public Fila(Casilla[] casillas) {
        this.casillas = casillas;
    }
    
    @Override
    public String toString() {
        String buffer = "";
        
        for(Casilla cas : casillas) {
            buffer += cas +" ";
        }
        
        return buffer +"\n";
    }

    /**
     * @return the casillas
     */
    public Casilla[] getCasillas() {
        return casillas;
    }
}
