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
 * @since 12/12/2016
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
//        rellenoFilas();
//        rellenoColumnas();
    }
    
    /**
     * Inicializacion de los Cuadrados. 
     * todo: Mirar de automatizarlo con un 'for' en vez de hacerlo a mano. no tengo ganas de comermer la cabeza ahora.
     */
    private void iniElementosNecesarios() {
                                //numCuadrado, numFila, numColumna.
        CUADRADOS[0] = new Cuadrado(0, 0, 0);
        CUADRADOS[1] = new Cuadrado(1, 0, 3);
        CUADRADOS[2] = new Cuadrado(2, 0, 6);
        CUADRADOS[3] = new Cuadrado(3, 3, 0);
        CUADRADOS[4] = new Cuadrado(4, 3, 3);
        CUADRADOS[5] = new Cuadrado(5, 3, 6);
        CUADRADOS[6] = new Cuadrado(6, 6, 0);
        CUADRADOS[7] = new Cuadrado(7, 6, 3);
        CUADRADOS[8] = new Cuadrado(8, 6, 6);
        
        for (int i = 0; i < FILAS.length; i++) {
            FILAS[i] = new Fila();
            COLUMNAS[i] = new Columna();
        }
    }
    
//    /**
//     * Introduccion en si misma. Se debera repetir 3 veces.
//     * @param numPrimerCuadrado Numero del primer cuadrado. Debera ser 0/3/6.
//     */
//    private void introduccionCasillasCuadradosEnFilas(int numPrimerCuadrado) {
//        Casilla[] casillasTmp; //Almacen temporal hasta que este la fila completa y se introduzcan.
//        
//        for (int indiceFila = numPrimerCuadrado, indiceCasillaTmp, indiceCasillaCuadrado = 0; indiceFila <= numPrimerCuadrado+2; indiceFila++, indiceCasillaCuadrado += 3) {
//            casillasTmp = new Casilla[9];
//            indiceCasillaTmp = 0;
//            for (int x = 0; x <= 2; x++) {
//                casillasTmp[indiceCasillaTmp++] = CUADRADOS[numPrimerCuadrado].getCASILLAS()[indiceCasillaCuadrado];
//                casillasTmp[indiceCasillaTmp++] = CUADRADOS[numPrimerCuadrado+1].getCASILLAS()[indiceCasillaCuadrado+1];
//                casillasTmp[indiceCasillaTmp++] = CUADRADOS[numPrimerCuadrado+2].getCASILLAS()[indiceCasillaCuadrado+2];
//            }
//            FILAS[indiceFila] = new Fila(casillasTmp);
//        }
//    }
    
//    /**
//     * Introduccion de las Casillas de los Cuadrados en las Filas.
//     */
//    private void rellenoFilas() {
//        introduccionCasillasCuadradosEnFilas(0);
//        introduccionCasillasCuadradosEnFilas(3);
//        introduccionCasillasCuadradosEnFilas(6);
//    }
    
//    /**
//     * Obtencion del numero de la primera columna que no se haya instanciado, para saber a partir de cual empezar a rellenar.
//     * @return Int con la primera posicion a rellenar.
//     */
//    private int obtenerPrimeraColumnaNula() {
//        for (int i = 0; i < COLUMNAS.length; i++) {
//            if(COLUMNAS[i] == null) return i;
//        }
//        
//        return -1; //Nunca deberia llegar a este punto.
//    }
    
//    /**
//     * Idem a las Filas pero introduciendo las Casillas en las columnas.
//     * @param numPrimerCuadrado Numero del primer cuadrado. Debera ser 0/1/2.
//     */
//    private void introduccionCasillasCuadradosEnColumnas(int numPrimerCuadrado) {
//        Casilla[] casillasTmp;
//        int indicePrimeraColumna = obtenerPrimeraColumnaNula();
//        
//        for (int indiceColumna = indicePrimeraColumna, indiceCasillaTmp, indiceCasillaCuadrado = 0; indiceColumna <= indicePrimeraColumna+2; indiceColumna++, indiceCasillaCuadrado += 1) {
//            casillasTmp = new Casilla[9];
//            indiceCasillaTmp = 0;
//            for (int i = 0; i <= 2; i++) {
//                casillasTmp[indiceCasillaTmp++] = CUADRADOS[numPrimerCuadrado].getCASILLAS()[indiceCasillaCuadrado];
//                casillasTmp[indiceCasillaTmp++] = CUADRADOS[numPrimerCuadrado+3].getCASILLAS()[indiceCasillaCuadrado+3];
//                casillasTmp[indiceCasillaTmp++] = CUADRADOS[numPrimerCuadrado+6].getCASILLAS()[indiceCasillaCuadrado+6];
//            }
//            COLUMNAS[indiceColumna] = new Columna(casillasTmp);
//        }
//    }
    
//    /**
//     * Introduccion de las Casillas en las columnas correspondientes.
//     */
//    private void rellenoColumnas() {
//        introduccionCasillasCuadradosEnColumnas(0);
//        introduccionCasillasCuadradosEnColumnas(1);
//        introduccionCasillasCuadradosEnColumnas(2);
//    }
    
    /**
     * Le asignamos a cada casilla su numero propio. Si se llega a un punto muerto, se recomienza.
     * @return Booleano para chequear cuando se ha generado correctamente.
     */
    public boolean asignacionNumeroCasillas() {
        for (int i = 0; i < CUADRADOS.length; i++) {
            Casilla[] casillasCuadrado = CUADRADOS[i].getCASILLAS();
            for (int x = 0; x < casillasCuadrado.length; x++) {
                int num = GeneracionNumeros.generacionNumeroCasilla(this, casillasCuadrado[x]);
                if(num == -1) return false;
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
