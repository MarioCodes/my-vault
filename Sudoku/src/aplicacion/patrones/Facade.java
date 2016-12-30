/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.patrones;

import aplicacion.controlador.juego.GestionNumeros;
import aplicacion.controlador.juego.Checks;
import aplicacion.controlador.juego.ResolucionAuto;
import aplicacion.controlador.tablero.Casilla;
import aplicacion.controlador.tablero.Cuadrado;
import aplicacion.controlador.tablero.Tablero;
import javax.swing.JTable;
import vista.GestionVista;
import vista.WindowJuego;

/**
 * Patron de disenio Facade. Sirve de intermediario entre vista y controlador del programa.
 * @author Mario Codes Sánchez
 * @since 25/12/2016
 */
public class Facade {

    /**
     * Rellenamos una JTable con los datos de un Tablero.
     * @param tabla Tabla a rellenar.
     * @param mostrarTodos Si true, se mostraran todos los numeros en la tabla independientemente del estado de la casilla. Esto lo uso en el tablero de trampas.
     */
    public void rellenoTablaConTablero(JTable tabla, boolean mostrarTodos) {
        new GestionVista().rellenoTablaConTablero(tabla, mostrarTodos);
    }
    
    /**
     * OCultamos una casilla especifica para comprobar la integridad del tablero / tabla.
     * @param tabla Tabla de la cual ocultamos la casilla.
     * @param row Numero de fila.
     * @param numeroCasillaFila Numero de casilla dentro de esta fila.
     */
    public void ocultacionCasillaEspecifica(JTable tabla, int row, int numeroCasillaFila) {
        Checks.ocultarCasillaEspecificaTesteo(tabla, row, numeroCasillaFila);
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
    public void copiarTableros(JTable tablaNormal, JTable tablaTrampas) {
        WindowJuego.copiarTableros(tablaNormal, tablaTrampas);
    }
    
    /**
     * ResolucionAuto de un tablero mediante fuerza bruta con el metodo 'Backtrack'.
     * @param tabla 
     * @param tablero 
     * @return  
     */
    public boolean solucionBacktrack(JTable tabla) {
        ResolucionAuto resolucion = new ResolucionAuto(tabla);
        return resolucion.resolucionBacktrack();
    }
    
    public void borrarTablero(JTable tabla) {
        for (int indiceX = 0; indiceX < 9; indiceX++) {
            for (int indiceY = 0; indiceY < 9; indiceY++) {
                tabla.setValueAt("", indiceX, indiceY);
            }
        }
    }
    
    public Tablero conversionTablero(JTable tabla) {
        return Tablero.generacionTablero(tabla);
    }
}
