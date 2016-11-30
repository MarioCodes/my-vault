/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.aplicacion;

/**
 * Clase columna que representa a esta misma dentro del tablero de juego.
 * @author Mario Codes Sánchez
 * @since 30/11/2016
 */
public class Columna {
    private Casilla[] casillas = new Casilla[9];
    
    /**
     * Al igual que con las columnas, creo que primero generare todas las casillas y cuadrados y luego las metere donde les corresponda.
     * @param casillas 
     */
    public Columna(Casilla[] casillas) {
        this.casillas = casillas;
    }
}
