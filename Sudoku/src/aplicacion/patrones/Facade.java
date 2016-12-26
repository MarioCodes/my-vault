/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.patrones;

import aplicacion.controlador.juego.GestionNumeros;
import aplicacion.controlador.juego.Checks;
import aplicacion.controlador.juego.Resolucion;
import aplicacion.controlador.tablero.Casilla;
import aplicacion.controlador.tablero.Cuadrado;
import aplicacion.controlador.tablero.Tablero;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;

/**
 * Patron de disenio Facade. Sirve de intermediario entre vista y controlador del programa.
 * @author Mario Codes Sánchez
 * @since 25/12/2016
 */
public class Facade {

    /**
     * Relleno de un 'cuadrado' de la tabla, con los valores de las casillas de su homologo Cuadrado.
     *  Son necesarios los datos de la primera columna y fila propia de cada cuadrado, para ir rellenando a partir de alli. Antes los pasaba como parametro, pero estos datos los contiene
     *      la Casilla[0] de cada Cuadrado, por lo que los puedo obtener de alli directamente.
     * @param cuadrados Cuadrados creados previamente de donde extraer los datos de valor de cada Casilla.
     * @param tabla Tabla grafica que queremos rellenar.
     * @param numeroCuadrado Numero de cuadrado que toca rellenar en esta iteracion.
     */
    private void rellenoCuadradoGrafico(Cuadrado[] cuadrados, JTable tabla, int numeroCuadrado, boolean mostrarTodos) {
        for (int indiceCasilla = 0, indiceColumna = cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_COLUMNA(); indiceCasilla < 3; indiceColumna++, indiceCasilla++) { //Una fila de un cuadrado.
            if(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla].isCasillaFija() || mostrarTodos) tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla].getNumeroPropio(), cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA(), indiceColumna); //Valor, row, columna.
            if(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+3].isCasillaFija() || mostrarTodos)tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+3].getNumeroPropio(), cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA()+1, indiceColumna); //Valor, row, columna.
            if(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+6].isCasillaFija() || mostrarTodos)tabla.setValueAt(cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+6].getNumeroPropio(), cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA()+2, indiceColumna); //Valor, row, columna.
        }
    }
    
    /**
     * Rellenamos cada 'casilla' de la tabla con su Casilla correspondiente.
     * @param tabla Tabla a rellenar.
     * @param mostrarTodos mostrar todas las casillas (usado en la version de trampas).
     */
    private void rellenoTablaConNumeros(JTable tabla, boolean mostrarTodos) {
        Cuadrado[] cuadrados = Singleton.getTableroActual().getCUADRADOS();
        for(int i = 0; i < cuadrados.length; i++) {
            rellenoCuadradoGrafico(cuadrados, tabla, i, mostrarTodos);
        }
    }
    
    /**
     * Generacion / obtencion del tablero.
     * @param tabla JTable para rellenar con los numeros.
     * @param mostrarTodos mostrar todas las casillas (para version JTable Trampas).
     */
    public void generacionTablero(JTable tabla, boolean mostrarTodos) {
        Singleton.getTableroActual();
        rellenoTablaConNumeros(tabla, mostrarTodos);
    }
    
    /**
     * Oculta una casilla suelta tanto en la matriz de casillas como en el tablero de forma grafica.
     * @param tabla Tabla de la cual queremos ocultar. Sera la de juego.
     * @param row Fila donde se encuentra la casilla.
     * @param numeroCasillaFila Numero de casilla dentro de la fila.
     */
    public void ocultarCasillaEspecificaTesteo(JTable tabla, int row, int numeroCasillaFila) {
        try {
            Tablero tablero = Singleton.getTableroActual();
            tablero.getFILAS()[row].getCASILLAS()[numeroCasillaFila].setCasillaFija(false);
            tablero.getFILAS()[row].getCASILLAS()[numeroCasillaFila].setNumeroPropio(0);
            tabla.setValueAt("", row, tablero.getFILAS()[row].getCASILLAS()[numeroCasillaFila].getNUMERO_COLUMNA()); //Necesito el numero de columna pero lo puedo sacar de la propia casilla.
            System.out.println("Casilla Ocultada.");
        }catch(ArrayIndexOutOfBoundsException ex) {
            System.out.println("Valores fuera de rango.");
        }
    }
    
    /**
     * Ocultacion de una casilla en si misma. En este punto ya se ha hecho la asignacion aleatoria.
     * Pasamos una casilla y la tabla.
     * Ocultamos un numero, hacemos fuerza bruta y vemos si es irresoluble. Si lo es, damos marcha atras y deshacemos lo hecho.
     * @param table tabla normal de juego.
     * @param casilla Casilla que queremos ocultar.
     */
    private void ocultarCasillaGeneracionTablero(JTable table, Casilla casilla) {
        int backupNum = casilla.getNumeroPropio(); //Si da error habra que recuperarlo.
        int resultado;
        
        casilla.setCasillaFija(false);
        casilla.setNumeroPropio(0);
        
        table.setValueAt("", casilla.getNUMERO_FILA(), casilla.getNUMERO_COLUMNA());
        resultado = Checks.checkOcultacionNumeros();
        
        if(resultado == -1) { //Si es irresoluble, damos marcha atras.
            casilla.setCasillaFija(true);
            casilla.setNumeroPropio(backupNum);
            table.setValueAt(backupNum, casilla.getNUMERO_FILA(), casilla.getNUMERO_COLUMNA());
        }
    }
    
    /**
     * Ocultacion de casillas del tablero de juego. Cada casilla tiene un tanto por ciento de ocultarse.
     * @param tabla Tabla de la que queremos ocultar las casillas.
     */
    public void ocultarNumerosTablero(JTable tabla) {
        Cuadrado[] cuadrados = Singleton.getTableroActual().getCUADRADOS();
        
        for(Cuadrado cuadrado : cuadrados) {
            for(Casilla casilla : cuadrado.getCASILLAS()) {
                if(GestionNumeros.ocultacionNumerosRand()) {
                    ocultarCasillaGeneracionTablero(tabla, casilla);
                }
            }
        }
    }
    
    /**
     * Comprobamos la solucion introducida en el tablero grafico, si esta correcto se entra a comprobar los numeros.
     * @param tablaNormal Tablero a chequear.
     * @param tablaTrampas Tablero de trampas.
     * @return Integer con el resultado. 1 correcto, 0 incompleto, -1 no correcto.
     */
    public int comprobarSolucionTablero(JTable tablaNormal, JTable tablaTrampas) {
        if(!Checks.comprobarTableroLleno(tablaNormal)) return 0;
        if(Checks.comprobarResolucionTableroGrafico(tablaNormal, tablaTrampas)) return 1;
        else return -1;
    }
    
    /**
     * Copiado del tablero trampas al tablero normal para testeo.
     * @param tablaNormal Tablero al que se copiara.
     * @param tablaTrampas Tablero desde el cual se copiara.
     */
    public void copiarTableroTrampasAlNormal(JTable tablaNormal, JTable tablaTrampas) {
        Checks.copiarTableroTrampasAlNormal(tablaNormal, tablaTrampas);
    }
    
    private void generarTableroConTablaGrafica(JTable tabla) {
        Resolucion resolucion = new Resolucion(tabla);
        resolucion.generacionTablero();
    }
    
    public void solucionFuerzaBruta(JTable tabla) {
        generarTableroConTablaGrafica(tabla);
    }
}
