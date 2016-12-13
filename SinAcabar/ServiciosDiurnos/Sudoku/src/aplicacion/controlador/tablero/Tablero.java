/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.tablero;

import aplicacion.controlador.juego.GeneracionNumeros;

/**
 * Tablero de juego. Es el conjunto de Cuadrados, Filas y Columnas, cada uno con sus casillas correspondientes.
 *  Para ver como funciona el reparto de numeros propio de cuadrados, filas y columnas, ver la representacion en Paint anexada.
 * @author Mario Codes Sánchez
 * @since 13/12/2016
 */
public class Tablero {
    private final Cuadrado[] CUADRADOS = new Cuadrado[9];
    private final Fila[] FILAS = new Fila[9];
    private final Columna[] COLUMNAS = new Columna[9];
    
    /**
     * Constructor para inicializar el Tablero de juego. El se encargara de inicializar lo que sea necesario.
     */
    public Tablero() {
        preparacionTablero();
    }
    
    /**
     * Pasos necesarios para que el tablero este listo.
     */
    private void preparacionTablero() {
        iniElementosNecesarios();
    }
    
    /**
     * Inicializacion de los Cuadrados, Filas y Columnas. 
     */
    private void iniElementosNecesarios() {
                                //numCuadrado, numFila, numColumna.
        for (int i = 0, indiceFila = 0, indiceColumna = 0; i < CUADRADOS.length; i++) {
            CUADRADOS[i] = new Cuadrado(i, indiceFila, indiceColumna);
            FILAS[i] = new Fila();
            COLUMNAS[i] = new Columna();
            
            if((i+1)%3 == 0) {
                indiceFila += 3;
                indiceColumna = 0;
            } else {
                indiceColumna += 3;
            }
        }
    }
    
    /**
     * Le asignamos a cada casilla su numero propio. Si se llega a un punto muerto, se recomenzara con otro tablero.
     * @return Booleano para chequear cuando se ha generado correctamente.
     */
    public boolean asignacionNumeroCasillas() {
        for (int i = 0; i < CUADRADOS.length; i++) {
            Casilla[] casillasCuadrado = CUADRADOS[i].getCASILLAS();
            for (int x = 0; x < casillasCuadrado.length; x++) {
                int num = GeneracionNumeros.generacionNumeroCasilla(this, casillasCuadrado[x]);
                if(num == -1) return false; //Punto muerto.
                casillasCuadrado[x].setNumeroPropio(num);
            }
        }
        
        return true; //Generado correctamente.
    }
    
    @Override
    public String toString() {
        String buffer = "Cuadrados: \n";
        
        for(Cuadrado cuad : CUADRADOS) {
            buffer += cuad;
        }
        
        return buffer;
    }

    /**
     * @return the CUADRADOS
     */
    public Cuadrado[] getCUADRADOS() {
        return CUADRADOS;
    }

    /**
     * @return the FILAS
     */
    public Fila[] getFILAS() {
        return FILAS;
    }

    /**
     * @return the COLUMNAS
     */
    public Columna[] getCOLUMNAS() {
        return COLUMNAS;
    }
}
