/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.juego;

import aplicacion.controlador.tablero.Casilla;
import aplicacion.controlador.tablero.Tablero;
import java.util.ArrayList;
import javax.swing.JTable;

/**
 * Resolucion automatica del Sudoku mediante fuerza bruta por metodo 'Backtrack' (ver link Anexo).
 * Yo le paso a esta clase una jTable parcialmente rellena y la transforma en un Tablero. El cual resuelvo automaticamente.
 * @author Mario Codes Sánchez
 * @since 30/12/2016
 * @see https://en.wikipedia.org/wiki/Sudoku_solving_algorithms
 */
public class ResolucionAuto {
    private int indiceX, indiceY;
    private final JTable TABLA;
    private final Tablero TABLERO; //Tablero el cual se encontrara parcialmente completado y tendre que encontrar la solucion al resto.

    /**
     * Constructor normal a utilizar.
     * Le paso una tabla grafica como parametro, y desde ella genero el tablero a utilizar.
     * @param tabla Tabla grafica la cual se rellenara.
     */
    public ResolucionAuto(JTable tabla) {
        this.TABLA = tabla;
        this.TABLERO = Tablero.generacionTablero(TABLA);
    }
    
    /**
     * Check para comprobar si el numero que queremos meter en la casilla es valido.
     * @param casilla Casilla que queremos comprobar.
     * @param numeroAProbar Numero que queremos meter.
     * @return True si el numero es valido contra las 3 listas de numeros (Cuadrado, Fila y Columna).
     */
    private boolean checkNumeroCasillaValido(Casilla casilla, int numeroAProbar) {
        ArrayList<Integer> numerosDisponiblesCuadrado = TABLERO.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado();
        ArrayList<Integer> numerosDisponiblesFila = TABLERO.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila();
        ArrayList<Integer> numerosDisponiblesColumna = TABLERO.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna();
        
        return numerosDisponiblesCuadrado.contains((Object) numeroAProbar) && numerosDisponiblesFila.contains((Object) numeroAProbar) && numerosDisponiblesColumna.contains((Object) numeroAProbar);
    }
    
    /**
     * Busca una casilla vacia en el Tablero, si la encuentra asigna las coordenadas a las variables miembro y devuelve true.
     * @return True si hay una casilla vacia, false si el tablero esta completo.
     */
    private boolean casillaVacia() {
        for (int ejeX = 0; ejeX < 9; ejeX++) {
            for (int ejeY = 0; ejeY < 9; ejeY++) {
                if(TABLERO.getFILAS()[ejeX].getCASILLAS()[ejeY].getNumeroPropio() == 0) {
                    indiceX = ejeX;
                    indiceY = ejeY;
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Quitamos el numero que acabamos de asignar de las ArrayLists para que no se pueda volver a asignar.
     * @param casilla Casilla a la cual le estamos asignando el numero.
     * @param numero Numero el cual asignamos a la casilla.
     */
    private void quitarNumeroAsignadoArrayLists(Casilla casilla, int numero) {
        TABLERO.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado().remove((Object) numero);
        TABLERO.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila().remove((Object) numero);
        TABLERO.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna().remove((Object) numero);
    }
    
    /**
     * Ponemos el numero indicado en todas las ArrayLists afectadas. Para cuando damos marcha atras porque hemos llegado
     *      a un punto muerto.
     * @param casilla Casilla a la cual le ponemos el numero en sus listas correspondientes.
     * @param numero Numero el cual volvemos a poner como asignable.
     */
    private void ponerNumeroAsignadoArrayLists(Casilla casilla, int numero) {
        TABLERO.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado().add(numero);
        TABLERO.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila().add(numero);
        TABLERO.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna().add(numero);
    }
    
    /**
     * Operaciones con las coordenadas. Para que no se salga de rangos asignables fuera del tablero.
     */
    private void manejoCoordenadas() {
        if(indiceX == TABLERO.getFILAS().length) { //Salto de row cuando se llegue a columna 9. (daria out of bounds...).
            indiceX = 0;
            indiceY++;
        }
        
        if(indiceX < 0) { //Salto de row por lo bajo si retrocedemos hasta un punto en que sea necesario volver a la anterior.
            indiceX = 8;
            indiceY--;
        }
    }
    
    /**
     * Cambios que se deben hacer en su conjunto al asignar un numero a una casilla para que no haya discrepancias.
     * @param casilla Casilla que queremos cambiar.
     * @param numero Numero que queremos asignar.
     */
    private void asignacionNumeroACasilla(Casilla casilla, int numero) {
        TABLA.setValueAt(numero, indiceX, indiceY);
        casilla.setNumeroPropio(numero);
        quitarNumeroAsignadoArrayLists(casilla, numero);
    }
    
    /**
     * Lo mismo que el metodo anterior pero a la reversa. Cambios necesarios que vayan juntos.
     * @param casilla Casilla a cambiar. De ella saco las coordenadas.
     * @param numero Numero a 'devolver'.
     */
    private void quitarNumeroCasilla(Casilla casilla, int numero) {
        casilla.setNumeroPropio(0);
        ponerNumeroAsignadoArrayLists(casilla, numero);
        TABLA.setValueAt("", casilla.getNUMERO_FILA(), casilla.getNUMERO_COLUMNA());
    }
    
    /**
     * Parte principal de la resolucion por fuerza bruta. Se llamara recursivamente a si misma hasta que este
     *      resolucionado el problema.
     * @return True cuando este resolucionado, false cuando deba dar marcha atras porque se ha llegado a punto muerto.
     */
    private boolean seccionBacktrackRecursiva() {
        if(!casillaVacia()) return true; //Tablero resolucionado.
        
        manejoCoordenadas(); //Operamos con las coordenadas cuando haga falta
        
        Casilla casilla = TABLERO.getFILAS()[indiceX].getCASILLAS()[indiceY]; //Asignacion de la casilla a la correspondiente en esta llamada.
        
        for (int i = 1; i < 10; i++) { //Numero validos a comprobar.
            if(checkNumeroCasillaValido(casilla, i)) { //Miramos si la casilla es valida.
                asignacionNumeroACasilla(casilla, i); //Como lo es, hacemos cambios necesarios.
                indiceX++;
                //</primera parte recursividad>
                
                if(seccionBacktrackRecursiva()) return true; //Cuando ya no haya mas vacias, que vuelva atras.
                else { //Si llega aqui es porque hemos encontrado un punto muerto, y hay que tirar marcha atras.
                    quitarNumeroCasilla(casilla, i); //Quitamos los cambios que hemos hecho.
                }
            }
        }
        return false; //Y devolvemos falso para que poco a poco se deshagan los cambios.
    }
    
    /**
     * Hay algunos casos, en los que asigna el valor correcto en el tablero, a su casilla correspondiente, pero no lo asigna en la
     *  tabla grafica, por lo que esta se queda vacia.
     * Para estos casos hacemos una pasada final a la tabla cuando este resuelto, y los que se encuentren asi, los
     *      ponemos 'a mano'.
     */
    private void repasoFinalTablero() {
        Casilla casilla;
        for (int ejeX = 0; ejeX < 9; ejeX++) {
            for (int ejeY = 0; ejeY < 9; ejeY++) {
                try { //Los que hagan saltar esta excepcion, es porque se encuentran en blanco y son los que hay que pasar manualmente.
                    Integer.parseInt((String) TABLA.getValueAt(ejeX, ejeY));
                }catch(ClassCastException | NumberFormatException ex) {
                    casilla = TABLERO.getFILAS()[ejeX].getCASILLAS()[ejeY];
                    TABLA.setValueAt(casilla.getNumeroPropio(), ejeX, ejeY);
                }
            }
        }
    }
    
    
    /**
     * Resolucion del tablero que contiene esta clase mediante fuerza bruta ('Backtrack').
     *  Despues de esto, hace un check para comprobar que este bien resuelto.
     * @return Devolucion del check realizado. True si el tablero realmente esta solucionado de manera buena.
     */
    public boolean resolucionBacktrack() {
        seccionBacktrackRecursiva();
        repasoFinalTablero();
        return Checks.chequeoResolucion(TABLERO);
    }
}
