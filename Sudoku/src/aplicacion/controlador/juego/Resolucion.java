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
                    
//                    tablero.getCUADRADOS()[cas.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado().remove((Object) cas.getNumeroPropio());
//                    tablero.getFILAS()[cas.getNUMERO_FILA()].getNumerosDisponiblesFila().remove((Object) cas.getNumeroPropio());
//                    tablero.getCOLUMNAS()[cas.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna().remove((Object) cas.getNumeroPropio());
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
    private boolean checkNumeroCasillaValido(Tablero tablero, Casilla casilla, int numeroAProbar) throws Exception{
        boolean numeroIsOK;
        
        ArrayList<Integer> numerosDisponiblesCuadrado = tablero.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado();
        ArrayList<Integer> numerosDisponiblesFila = tablero.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila();
        ArrayList<Integer> numerosDisponiblesColumna = tablero.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna();
        
        numeroIsOK = checkNumeroContraArrayList(numeroAProbar, numerosDisponiblesCuadrado);
        if(numeroIsOK) numeroIsOK = checkNumeroContraArrayList(numeroAProbar, numerosDisponiblesFila);
        if(numeroIsOK) numeroIsOK = checkNumeroContraArrayList(numeroAProbar, numerosDisponiblesColumna);
        
        if(!numeroIsOK && numeroAProbar == 8) throw new Exception(); //Punto muerto, aqui debo dar marcha atras a la recursividad.
        
        return numeroIsOK;
    }
    
    /**
     * Recursividad para llamado y resolucion, esto va a ser un follon.
     * @param tablero
     * @param valorCasillaPropia
     * @param valorCasillaAnterior
     * @param indiceX
     * @param indiceY 
     */
    private boolean casillaRecursiva(Tablero tablero, int indiceX, int indiceY) throws StackOverflowError{
        if(!casillasLeft(tablero)) return true; //Sudoku solucionado.

        if(indiceX == 9) {
            indiceX = 0;
            indiceY++;
        }

        if(indiceX == -1) {
            indiceX = 9;
            indiceY--;
        }
        
        Casilla casilla = tablero.getFILAS()[indiceX].getCASILLAS()[indiceY];

        try {
            if(casilla.getNumeroPropio() == 0) {
                for (int i = 1; i < 10; i++) {
                    if(checkNumeroCasillaValido(tablero, casilla, i)) {
                        casilla.setNumeroPropio(i);
                        tabla.setValueAt(i, indiceX, indiceY);

                        quitarNumero(tablero.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado(), i);
                        quitarNumero(tablero.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila(), i);
                        quitarNumero(tablero.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna(), i);

                        if(casillaRecursiva(tablero, indiceX++, indiceY)) return true;
                        else {
                            tabla.setValueAt("", indiceX--, indiceY);
                            casilla.setNumeroPropio(0);
                            
                            tablero.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado().add(i);
                            tablero.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila().add(i);
                            tablero.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna().add(i);
                        }
                    }
                }
            } else {
                if (casillaRecursiva(tablero, indiceX++, indiceY)) return true;
            }
        }catch(Exception ex) {
            System.out.println("punto muerto");
            return false;
        }
        
        return false;
//            if(casilla.getNumeroPropio() == 0) {
//                boolean numeroAsignado = false;
//                for (int i = 1; i < 9 && !numeroAsignado;) {
//                    while(!checkNumeroCasillaValido(tablero, casilla, i)) {
//                        i++;
//                    }
//
//                    numeroAsignado = true;
//                    tabla.setValueAt(i, indiceX, indiceY);
//                    casilla.setNumeroPropio(i);
//
//                    quitarNumero(tablero.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado(), i);
//                    quitarNumero(tablero.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila(), i);
//                    quitarNumero(tablero.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna(), i);
//                }
//            }
//        }
    }
    
//    private void operacionCasilla(int indiceX, int indiceY) throws Exception {
//        int i = 1;
//        Casilla casilla = tablero.getCOLUMNAS()[indiceY].getCASILLAS()[indiceX];
//        if(casilla.getNumeroPropio() == 0) {
//            while(!checkNumeroCasillaValido(tablero, casilla, i)) {
//                i++;
//            }
//
//            tabla.setValueAt(i, indiceX, indiceY);
//            casilla.setNumeroPropio(i);
//
//            quitarNumero(tablero.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado(), i);
//            quitarNumero(tablero.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila(), i);
//            quitarNumero(tablero.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna(), i);
//        }
//    }
//    
//    private void valoresAsignados(int indiceX, int indiceY) throws Exception {
//        for (; indiceY < 9; indiceY++) {
//            for (; indiceX < 9; indiceX++) {
//                 operacionCasilla(indiceX, indiceY);
//            }
//        }
//    }
//    
//    public void resolucionBacktrack() {
//        int indiceX = 0, indiceY = 0;
//        
//        try {
//            valoresAsignados(indiceX, indiceY);
//        }catch(Exception ex) {
//            System.out.println("Punto muerto.");
//        }
//    }
    
    /**
     * Comprueba si hay mas casillas por rellenar.
     * @param tabla Tabla de la cual comprobamos todas las casillas.
     * @return True si quedan casillas por asignar.
     */
    private boolean casillasLeft(Tablero tablero) {
        for(Cuadrado cuadrado: tablero.getCUADRADOS()) {
            for(Casilla casilla: cuadrado.getCASILLAS()) {
                if(casilla.getNumeroPropio() == 0) return true; //Casilla sin asignar encontrada.
            }
        }
        
        return false; //Tablero completo.
    }
    
    public void resolucionSudoku() {
        try {
            casillaRecursiva(tablero, 0, 0);
        }catch(StackOverflowError ex) {
            System.out.println("Stack Overflow");
        }
    }
}
