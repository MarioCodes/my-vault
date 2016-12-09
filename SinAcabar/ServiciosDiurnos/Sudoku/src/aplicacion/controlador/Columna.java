/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador;

/**
 * Representacion de una columna del tablero de juego.
 * @author Mario Codes Sánchez
 * @since 08/12/2016
 */
public class Columna {
    private Casilla[] casillas = new Casilla[9];
    
    /**
     * Constructor a utilizar por defecto para rellenar.
     * @param casillas Casilla[] que formara la columna.
     */
    public Columna(Casilla[] casillas) {
        this.casillas = casillas;
    }
    
    @Override
    public String toString() {
        String buffer = "";
        
        for(Casilla cas : casillas) {
            buffer += cas +"\n";
        }
        
        return buffer +"\n";
    }
}
