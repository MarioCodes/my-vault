/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.tablero;

import aplicacion.controlador.juego.GestionNumeros;

/**
 * Tablero de juego. Es el conjunto de Cuadrados, Filas y Columnas, cada uno con sus Casillas correspondientes y sus propias
 *  listas de numeros disponibles.
 *  Para ver como funciona el reparto de numeros propio de cuadrados, filas y columnas, ver la representacion en Paint anexada.
 * @author Mario Codes Sánchez
 * @since 20/12/2016
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
        rellenoFilas();
        rellenoColumnas();
    }
    
    /**
     * Inicializacion de los Cuadrados, Filas y Columnas. 
     */
    private void iniElementosNecesarios() {
                                //numCuadrado, numFila, numColumna.
        for (int i = 0, indiceFila = 0, indiceColumna = 0; i < CUADRADOS.length; i++) {
            CUADRADOS[i] = new Cuadrado(i, indiceFila, indiceColumna);
            
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
                int num = GestionNumeros.generacionNumeroCasilla(this, casillasCuadrado[x]);
                if(num == -1) return false; //Punto muerto.
                casillasCuadrado[x].setNumeroPropio(num);
            }
        }
        
        return true; //Generado correctamente.
    }
    
    /**
     * Introduccion en si misma. Se debera repetir 3 veces.
     * Funciona. Evitar tocarlo a no ser que sea extremamente necesario.
     * @param numPrimerCuadrado Numero del primer cuadrado. Debera ser 0/3/6.
     */
    private void introduccionCasillasCuadradosEnFilas(int numPrimerCuadrado) {
        Casilla[] casillasTmp; //Almacen temporal hasta que este la fila completa y se introduzcan.
        
        for (int indiceFila = numPrimerCuadrado, indiceCasillaTmp, indiceCasillaCuadrado, indiceCasillaBaseCuadrado = 0; indiceFila < numPrimerCuadrado+3; indiceFila++, indiceCasillaCuadrado++, indiceCasillaBaseCuadrado += 3) {
            casillasTmp = new Casilla[9];
            indiceCasillaCuadrado = indiceCasillaBaseCuadrado;
            indiceCasillaTmp = 0;
            for (int x = 0; x <= 2; x++, indiceCasillaTmp++, indiceCasillaCuadrado++) {
                casillasTmp[indiceCasillaTmp] = CUADRADOS[numPrimerCuadrado].getCASILLAS()[indiceCasillaCuadrado];
                casillasTmp[indiceCasillaTmp+3] = CUADRADOS[numPrimerCuadrado+1].getCASILLAS()[indiceCasillaCuadrado];
                casillasTmp[indiceCasillaTmp+6] = CUADRADOS[numPrimerCuadrado+2].getCASILLAS()[indiceCasillaCuadrado];
            }
            FILAS[indiceFila] = new Fila(casillasTmp);
        }
    }
    
    /**
     * Introduccion de las Casillas de los Cuadrados en las Filas.
     */
    private void rellenoFilas() {
        for (int i = 0; i <= 6; i += 3) {
            introduccionCasillasCuadradosEnFilas(i); //0, 3, 6.
        }
    }

    /**
     * Introduccion de casillas en columnas. Se debera repetir tres veces.
     * Funciona. Evitar tocarlo a no ser que sea extremamente necesario.
     * @param numPrimerCuadrado Numero del primer cuadrado del set de 3.
     */
    private void introduccionCasillasCuadradosEnColumnas(int numPrimerCuadrado) {
        Casilla[] casillasTmp;
        
        for(int indiceColumna = numPrimerCuadrado*3, indiceCasillaTmp, indiceCasillaCuadrado, indiceCasillaCuadradoBase = 0; indiceColumna < ((numPrimerCuadrado*3)+3); indiceColumna++, indiceCasillaCuadradoBase++) {
            casillasTmp = new Casilla[9];
            indiceCasillaCuadrado = indiceCasillaCuadradoBase;
            indiceCasillaTmp = 0;
            for (int x = 0; x <= 2; x++, indiceCasillaTmp++, indiceCasillaCuadrado+=3) {
                casillasTmp[indiceCasillaTmp] = CUADRADOS[numPrimerCuadrado].getCASILLAS()[indiceCasillaCuadrado];
                casillasTmp[indiceCasillaTmp+3] = CUADRADOS[numPrimerCuadrado+3].getCASILLAS()[indiceCasillaCuadrado];
                casillasTmp[indiceCasillaTmp+6] = CUADRADOS[numPrimerCuadrado+6].getCASILLAS()[indiceCasillaCuadrado];
            }
            COLUMNAS[indiceColumna] = new Columna(casillasTmp);
        }
    }
    
    /**
     * Introduccion de las Casillas de los Cuadrados en las Columnas.
     */
    private void rellenoColumnas() {
        for (int i = 0; i < 3; i++) {
            introduccionCasillasCuadradosEnColumnas(i); //0, 1, 2.
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Diferentes partes del tablero para comprobar que todas ellas coinciden y cuadran: \nCuadrados: \n");
        
        for(Cuadrado cuad : CUADRADOS) {
            sb.append(cuad);
        }
        
        sb.append("Filas: \n");
        
        for(Fila fila: FILAS) {
            sb.append(fila);
            sb.append("\n");
        }
        
        sb.append("Columnas: \n");
        
        for(Columna columna: COLUMNAS) {
            sb.append(columna);
            sb.append("\n");
        }
        
        return sb.toString();
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
