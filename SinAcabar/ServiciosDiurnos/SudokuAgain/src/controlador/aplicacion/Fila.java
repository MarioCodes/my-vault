/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.aplicacion;

/**
 * Representacion de una fila del tablero. Dentro de ella misma no se podran repetir numeros tampoco.
 * @author Mario Codes Sánchez
 * @since 30/11/2016
 */
public class Fila {
    private Casilla[] casillas = new Casilla[9];
    
    /**
     * Constructor a utilizar, creo que primero hare las casillas en el cuadrado, y una vez todas esten hechas, las metere en sus casillas.
     * @param casillas Array con todas las casillas de esta fila.
     */
    public Fila(Casilla[] casillas) {
        this.casillas = casillas;
    }

    @Override
    public String toString() {
        String buffer = "\n";
        
        for(Casilla casTmp : casillas) {
            buffer += casTmp +" ";
        }
        
        return buffer;
    }
    
    /**
     * @return the casillas
     */
    public Casilla[] getCasillas() {
        return casillas;
    }

    /**
     * @param casillas the casillas to set
     */
    public void setCasillas(Casilla[] casillas) {
        this.casillas = casillas;
    }
}
