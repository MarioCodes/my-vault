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

    public Tablero() {
        generacionCuadrados();
    }
    
    /**
     * Generamos los 9 cuadrados intrinsecos de el tablero de juego.
     */
    private void generacionCuadrados() {
        for (int i = 0; i < 9; i++) {
            cuadrados[i] = new Cuadrado();
        }
    }
    
    /**
     * De cada cuadrado, saco las casillas necesarias. Cuando tenga las 9 de los 3 cuadrados, las metere en la fila correspondiente.
     * @param numeroCuadrado Numero de cuadrado dentro del programa.
     * @return Casilla[] conteniendo las 3 casillas necesarias de esa fila.
     */
    private Casilla[] obtencionDeCasillasSegunFilaDeCuadrado(int numeroCuadrado) {
        Casilla[] casillasFilaCuadrado = new Casilla[3];
        Cuadrado cuadradoActual = cuadrados[numeroCuadrado];
        
        for (int i = 0; i < 3; i++) {
            casillasFilaCuadrado[i] = cuadradoActual.getCasilla()[i];
        }
        
        return casillasFilaCuadrado;
    }
    
    /**
     * todo: ME QUEDO AQUI, terminar de meter las casillas que correspondan en cada fila.
     * Metemos a cada fila las casillas que le corresponde.
     */
    private void rellenoFilasConCasillas() {
        Casilla[] casillasCadaFila = new Casilla[9]; //Esto se metera en la fila cuando este rellena.
        int cuadrado1 = 0, cuadrado2 = 1, cuadrado3 = 2; //Cuadrados de los que obtiene las filas, cada 3 filas obtenidas, se debera cambiar al siguiente cuadrado.
        
        for (int i = 0; i < 9; i++) { //i corresponde al numero de Fila actual.
            
        }
    }
    
//    /**
//     * todo: una vez implementadas de forma correcta las filas y columnas, recorrer las filas (z.B) e imprimirlas.
//     * @return 
//     */
//    @Override
//    public String toString() {
//        String buffer = "";
//        
////        for (int i = 1; i < 10; i++) {
////            buffer += cuadrados[i-1];
////            if(i%3 == 0) buffer += "\n";
////        }
//        
//        return buffer;
//    }
}
