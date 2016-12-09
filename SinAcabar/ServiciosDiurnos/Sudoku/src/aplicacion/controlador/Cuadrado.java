/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador;

/**
 * Representacion de un Cuadrado con 9 casillas.
 * @author Mario Codes Sánchez
 * @since 08/12/2016
 */
public class Cuadrado {
    private final Casilla[] CASILLAS = new Casilla[9];
    
    /**
     * Constructor a utilizar por defecto, se encarga de generar las casillas de cada Cuadrado.
     */
    public Cuadrado() {
        iniCasillas();
    }
    
    /**
     * Inicializacion de las 9 casillas del cuadrado.
     */
    private void iniCasillas() {
        for (int i = 0; i < CASILLAS.length; i++) {
            CASILLAS[i] = new Casilla(i);
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
}
