/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.aplicacion;

/**
 * Tablero de juego que contiene los Cuadrados, Filas y Columnas.
 * @author Mario Codes Sánchez
 * @since 30/11/2016
 */
public class Tablero {
    private Cuadrado[] cuadrados = new Cuadrado[9];
    private Fila[] filas = new Fila[9];
    private Columna[] columnas = new Columna[9];

    /**
     * Constructor por defecto del Tablero.
     * El mismo se encarga de 1ero, generar los cuadrados, y despues meter las casillas de estos en las filas.
     */
    public Tablero() {
        generacionCuadrados();
        rellenoFilasConCasillas();
    }
    
    /**
     * De los 9 Cuadrados, este va a ser el unico que se genere el solo sin tener que chequear a nadie mas.
     */
    private void generacionPrimerCuadrado() {
        cuadrados[0] = new Cuadrado(GeneracionNumeros.generacionNumerosPrimerCuadrado());
    }
    
    /**
     * Generamos el resto de los 8 cuadrados.
     */
    private void generacionCuadrados() {
        generacionPrimerCuadrado();
        
        for (int i = 1; i < 9; i++) {
            cuadrados[i] = new Cuadrado();
        }
    }
    
    /**
     * Core del relleno de filas, lo explico bien ahora que aun lo tengo fresco y reciente.
     *  numeroComienzoCasillaEnSuCuadrado - Empieza en 0. Es el indice con el que se accede al numero de Casilla de cada Cuadrado.
     * 
     * Busca las Casillas #0, 1, 2 de los Cuadrados #0, 1, 2. Sumamos +3 a numeroComienzoCasillaEnSuCuadrado.
     * Busca las Casillas #3, 4, 5 de los Cuadrados #0, 1, 2. Sumamos +3 a numeroComienzoCasillaEnSuCuadrado.
     * Busca las Casillas #6, 7, 8 de los Cuadrados #0, 1, 2. Sumamos +3 a numeroComienzoCasillaEnSuCuadrado.
     * 
     * Esto lo repito 3 veces para los 3 juegos de 3 cuadrados distintos que tengo.
     * todo: mirar bien las filas, yo creo que de aqui podre sacar directamente las columnas sin tener que hacer otra paja mental como esta.
     */
    private void rellenoFilasConCasillas() {
        Casilla[] filaARellenar = null;
        
        for(int numeroDelCuadradoAOperar = 0, indiceFila = 0; numeroDelCuadradoAOperar < 9; numeroDelCuadradoAOperar += 3) {
            for(int numeroComienzoCasillaEnSuCuadrado = 0; numeroComienzoCasillaEnSuCuadrado < 9; numeroComienzoCasillaEnSuCuadrado += 3, indiceFila++) {
                filaARellenar = new Casilla[9]; //Fila nueva donde metemos las casillas de los cuadrados a medida que las vamos obteniendo.
                for(int obtencionFilaCompleta = 0, indiceFilaRellenar = 0, numeroDeCasillaEnSuCuadrado = numeroComienzoCasillaEnSuCuadrado; obtencionFilaCompleta < 3; numeroDeCasillaEnSuCuadrado++, indiceFilaRellenar++, obtencionFilaCompleta++) { //Con esto, obtengo 1 fila.
                    filaARellenar[indiceFilaRellenar] = cuadrados[numeroDelCuadradoAOperar].getCasilla()[numeroDeCasillaEnSuCuadrado]; //En la fila pos 0,1,2 metemos del Cuadrado[0], las casillas 0,1,2
                    filaARellenar[indiceFilaRellenar+3] = cuadrados[numeroDelCuadradoAOperar+1].getCasilla()[numeroDeCasillaEnSuCuadrado]; //En la fila pos 3,4,5 metemos Cuadrado[1], las casillas 0,1,2
                    filaARellenar[indiceFilaRellenar+6] = cuadrados[numeroDelCuadradoAOperar+2].getCasilla()[numeroDeCasillaEnSuCuadrado]; //En la fila pos 6,7,8 metemos Cuadrado[2], las casillas 0,1,2
                }
                filas[indiceFila] = new Fila(filaARellenar); //Instanciacion de la Fila con sus casillas intrinsecas.
            }
        }
    }
    
    /**
     * todo: Ir aniadiendo el output de filas, columnas y cuadrados a medida que los voy implementando.
     * @return Output del Tablero completo para comprobar que esta bien hecho.
     */
    @Override
    public String toString() {
        String buffer = "Cuadrados Standalone: (#1-9 de Arriba a Abajo por orden de aparicion [Representacion hecha con el Paint])\n";
        for(Cuadrado cuadTmp: cuadrados) {
            buffer += cuadTmp;
        }
        
        buffer += "\nFilas Standalone:";
        for(Fila filaTmp : filas) {
            buffer += filaTmp;
        }
        
        return buffer;
    }
}
