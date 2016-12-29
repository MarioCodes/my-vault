/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.juego;

import aplicacion.controlador.tablero.Casilla;
import aplicacion.controlador.tablero.Cuadrado;
import aplicacion.controlador.tablero.Fila;
import aplicacion.controlador.tablero.Tablero;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTable;

/**
 * Resolucion del problema por 'Backtrack'.
 * @author Mario Codes Sánchez
 * @since 27/12/2016
 * @see https://en.wikipedia.org/wiki/Sudoku_solving_algorithms
 */
public class Resolucion {
    private int indiceX, indiceY;
    private JTable tabla;
    private Tablero tablero; //Tablero el cual se encontrara parcialmente completado y tendre que encontrar la solucion al resto.

    /**
     * Relleno de Cuadrado[] con los valores de la tabla de su homologo Cuadrado.
     * @param cuadrados Cuadrados donde almaceno los datos de cada casilla.
     * @param tabla Tabla grafica de donde sacamos los datos.
     * @param numeroCuadrado Numero de cuadrado que toca rellenar en esta iteracion.
     */
    private void rellenoCuadradoTablero(Cuadrado[] cuadrados, JTable tabla, int numeroCuadrado) {
        for (int indiceCasilla = 0, indiceColumna = cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_COLUMNA(); indiceCasilla < 3; indiceColumna++, indiceCasilla++) { //Una fila de un cuadrado.
            try { //fixme: intentar arreglar esta chapuza, si lo pongo en tries separados funciona. Si no, al saltar la excepcion en la linea que sea, salta y las otras 2 ni siquiera se comprueban. Arreglarlo. Segmentarlo en mas metodos(?).
                cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla].setNumeroPropio(Integer.parseInt(tabla.getValueAt(cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA(), indiceColumna).toString()));
            }catch(NumberFormatException | NullPointerException ex) {}
            
            try {
                cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+3].setNumeroPropio(Integer.parseInt(tabla.getValueAt(cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA()+1, indiceColumna).toString()));
            }catch(NumberFormatException | NullPointerException ex) {}
            
            try {
                cuadrados[numeroCuadrado].getCASILLAS()[indiceCasilla+6].setNumeroPropio(Integer.parseInt(tabla.getValueAt(cuadrados[numeroCuadrado].getCASILLAS()[0].getNUMERO_FILA()+2, indiceColumna).toString()));
            }catch(NumberFormatException | NullPointerException ex) {} //Esto saltara en las casillas que se encuentren vacias, es completamente normal.
        }
    }
    
    /**
     * Rellenamos cada Casilla con su 'casilla' correspondiente de la tabla.
     * @param tabla Tabla de la que sacamos los datos.
     * @param cuadrados Cuadrado[] que rellenamos.
     */
    private void rellenoTablaConNumeros(JTable tabla, Cuadrado[] cuadrados) {
        for(int i = 0; i < cuadrados.length; i++) {
            rellenoCuadradoTablero(cuadrados, tabla, i);
        }
    }
    
    /**
     * Quitamos un numero de la ArrayList. Al ser AL de Integers estoy teniendo problemas para quitarlos de forma correcta, ya
     *      que los toma por el index del que quiero borrar. Deberian haber sido AL de Strings. Fallo mio.
     * @param lista
     * @param numero 
     */
    private void quitarNumero(ArrayList<Integer> lista, int numero) {
        Iterator it = lista.iterator();
        boolean numeroQuitado = false;
        
        while(it.hasNext() && !numeroQuitado) {
            int numeroLocal = (int) it.next();
            if(numero == numeroLocal) {
                numeroQuitado = true;
                it.remove();
            }
        }
    }
    
    /**
     * Quitamos los numeros disponibles de las ArrayLists, de los numeros que ya se han puesto a mano.
     * @param tablero Tablero del cual quitamos los numeros ya puestos.
     */
    private void quitarNumerosPuestos(Tablero tablero) {
        for(Fila fila: tablero.getFILAS()) {
            for(Casilla cas: fila.getCASILLAS()) {
                if(cas.getNumeroPropio() != 0) {
                    quitarNumero(tablero.getCUADRADOS()[cas.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado(), cas.getNumeroPropio());
                    quitarNumero(tablero.getFILAS()[cas.getNUMERO_FILA()].getNumerosDisponiblesFila(), cas.getNumeroPropio());
                    quitarNumero(tablero.getCOLUMNAS()[cas.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna(), cas.getNumeroPropio());
                }
            }
        }
    }
    
    /**
     * Almacenamiento de los valores de una tabla grafica en forma de nuevo tablero.
     * Es muy muy parecido al metodo que hay en Tablero para rellenar una tabla con el contenido de las casillas, pero aqui
     *  interesa rellenas las casillas con el contenido de la tabla.
     * @param tabla Tabla con la cual obtenemos el tablero.
     */
    public void generacionTablero(JTable tabla) {
        this.tabla = tabla;
        Cuadrado[] cuadrados = new Cuadrado[9];
        Tablero.inicializacionCuadrados(cuadrados);
        rellenoTablaConNumeros(tabla, cuadrados);
        this.tablero = new Tablero(cuadrados);
        quitarNumerosPuestos(tablero);
    }
    
    private boolean checkNumeroContraArrayList(int numeroAProbar, ArrayList<Integer> lista) {
        Iterator it = lista.iterator();
        
        while(it.hasNext()) {
            int numero = (int) it.next();
            if(numeroAProbar == numero) return true;
        }
        
        return false;
    }
    
    /**
     * Check de si el numero que queremos meter en la casilla es valido o no.
     * @param tablero
     * @param casilla
     * @param numeroAProbar
     * @return 
     */
    private boolean checkNumeroCasillaValido(Tablero tablero, Casilla casilla, int numeroAProbar) {
        boolean numeroIsOK;
        
        ArrayList<Integer> numerosDisponiblesCuadrado = tablero.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado();
        ArrayList<Integer> numerosDisponiblesFila = tablero.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila();
        ArrayList<Integer> numerosDisponiblesColumna = tablero.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna();
        
        numeroIsOK = checkNumeroContraArrayList(numeroAProbar, numerosDisponiblesCuadrado);
        if(numeroIsOK) numeroIsOK = checkNumeroContraArrayList(numeroAProbar, numerosDisponiblesFila);
        if(numeroIsOK) numeroIsOK = checkNumeroContraArrayList(numeroAProbar, numerosDisponiblesColumna);
        
        return numeroIsOK;
    }
    
    /**
     * Busca una casilla vacia, si la encuentra asigna las coordenadas y devuelve true.
     * @param tablero
     * @param indiceX
     * @param indiceY
     * @return True si hay una casilla vacia, false si el tablero esta completo.
     */
    private boolean casillaVacia(Tablero tablero) {
        for (int ejeX = 0; ejeX < 9; ejeX++) {
            for (int ejeY = 0; ejeY < 9; ejeY++) {
                if(tablero.getFILAS()[ejeX].getCASILLAS()[ejeY].getNumeroPropio() == 0) {
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
     * @param tablero
     * @param numero 
     */
    private void quitarNumeroAsignadoArrayLists(Tablero tablero, Casilla casilla, int numero) {
        tablero.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado().remove((Object) numero);
        tablero.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila().remove((Object) numero);
        tablero.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna().remove((Object) numero);
    }
    
    private void ponerNumeroAsignadoArrayLists(Tablero tablero, Casilla casilla, int numero) {
        tablero.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado().add(numero);
        tablero.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila().add(numero);
        tablero.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna().add(numero);
    }
    
    private void manejoCoordenadas() {
        if(indiceX == 9) {
            indiceX = 0;
            indiceY++;
        }
        
        if(indiceX == -1) {
            indiceX = 8;
            indiceY--;
        }
    }
    
    private boolean solucionBacktrack(Tablero tablero) {
        if(!casillaVacia(tablero)) return true; //Tablero resolucionado.
        
        manejoCoordenadas();
        
        Casilla casilla = tablero.getFILAS()[indiceX].getCASILLAS()[indiceY];
        
        for (int i = 1; i < 10; i++) {
            if(checkNumeroCasillaValido(tablero, casilla, i)) {
                tabla.setValueAt(i, indiceX, indiceY);
                casilla.setNumeroPropio(i);
                quitarNumeroAsignadoArrayLists(tablero, casilla, i);
                
                int indiceXLocal = indiceX, indiceYLocal = indiceY; //Save antes de sumar++ para poder rectificar;
                indiceX++;
                if(solucionBacktrack(tablero)) return true;
                else {
                    tabla.setValueAt("", indiceXLocal, indiceYLocal);
                    casilla.setNumeroPropio(0);
                    ponerNumeroAsignadoArrayLists(tablero, casilla, i);
                }
            }
        }
        
        return false;
    }
    
    /**
     * Resolucion del tablero propuesto mediante fuerza bruta por 'Backtrack'.
     */
    public void resolucionBacktrack() {
        solucionBacktrack(tablero);
    }
}
