/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.patrones;

import aplicacion.controlador.juego.GestionJuego;
import aplicacion.controlador.juego.Checks;
import aplicacion.controlador.juego.ResolucionBacktrack;
import aplicacion.controlador.tablero.Tablero;
import javax.swing.JTable;
import vista.GestionVista;

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
        new GestionVista().copiarTableros(tabla1, tabla2);
    }
    
    /**
     * Resolucion de un tablero mediante fuerza bruta con el metodo 'Backtrack'.
     * @param tabla JTable que queremos resolucionar.
     * @return Estado de resolucion de la tabla, true si se ha conseguido resolucionar de manera correcta.
     */
    public boolean solucionBacktrack(JTable tabla) {
        ResolucionBacktrack resolucion = new ResolucionBacktrack(tabla);
        return resolucion.resolucionBacktrack();
    }
    
    /**
     * Borramos el contenido de una JTable.
     * @param tabla Tabla la cual queremos borrar.
     */
    public void borrarTablero(JTable tabla) {
        new GestionVista().borrarTablero(tabla);
    }
    
    /**
     * Generacion de un Tablero a traves de una JTable.
     * @param tabla JTable el cual queremos convertir.
     * @return Tablero con los datos de esa JTable.
     */
    public Tablero conversionTablero(JTable tabla) {
        return Tablero.generacionTablero(tabla);
    }
}
