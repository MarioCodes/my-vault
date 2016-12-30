/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.patrones;

import aplicacion.controlador.juego.GestionJuego;
import aplicacion.controlador.juego.Checks;
import aplicacion.controlador.juego.ResolucionAuto;
import aplicacion.controlador.tablero.Tablero;
import javax.swing.JTable;
import vista.GestionVista;
import vista.WindowJuego;

/**
 * Patron de disenio Facade. Sirve de intermediario entre vista y controlador del programa.
 * @author Mario Codes Sánchez
 * @since 30/12/2016
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
     * Ocultamos numeros al azar tanto de la tabla indicada como del tablero.
     * @param tabla Tabla de la cual queremos ocultar numeros.
     */
    public void ocultarNumerosTablero(JTable tabla) {
        GestionJuego.ocultarNumerosTablero(tabla);
    }
    
    /**
     * Comprobamos el estado de resolucion de una JTable.
     *  Se convierte en Tablero y se comprueba el estado de resolucion de este.
     * @param tabla Tabla que queremos comprobar.
     * @return 0 - tabla incompleta. -1 - solucion erronea. 1 - solucion correcta.
     */
    public int comprobarSolucionTablero(JTable tabla) {
        Tablero tablero = Singleton.getFacade().conversionTablero(tabla);
        int resultado = 0;
        
        if(Checks.comprobarTableroLleno(tabla)) resultado = Checks.chequeoResolucion(tablero) ? 1 : -1;
        
        return resultado;
    }
    
    /**
     * Copiado del tablero trampas al tablero normal para testeo.
     * @param tabla1 Tablero al que se copiara.
     * @param tabla2 Tablero desde el cual se copiara.
     */
    public void copiarTableros(JTable tabla1, JTable tabla2) {
        WindowJuego.copiarTableros(tabla1, tabla2);
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
