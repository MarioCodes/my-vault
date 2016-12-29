/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.juego;

import aplicacion.controlador.tablero.Casilla;
import aplicacion.controlador.tablero.Columna;
import aplicacion.controlador.tablero.Cuadrado;
import aplicacion.controlador.tablero.Fila;
import aplicacion.controlador.tablero.Tablero;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
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
<<<<<<< HEAD
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
    
    /**
     * Ponemos el numero indicado en todas las ArrayLists afectadas. Para cuando damos marcha atras.
     * @param tablero
     * @param casilla
     * @param numero 
     */
    private void ponerNumeroAsignadoArrayLists(Tablero tablero, Casilla casilla, int numero) {
        tablero.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado().add(numero);
        tablero.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila().add(numero);
        tablero.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna().add(numero);
    }
    
    /**
     * Escalada de las coordenadas.
     */
    private void manejoCoordenadas() {
        if(indiceX == 9) { //Salto de row cuando se llegue a columna 9. (daria out of bounds...).
            indiceX = 0;
            indiceY++;
        }
        
        if(indiceX == -1) { //Salto de row por lo bajo si retrocedemos hasta un punto en que sea necesario volver a la anterior.
            indiceX = 8;
            indiceY--;
        }
    }
    
    /**
     * Parte principal de la resolucion por fuerza bruta. Se llamara recursivamente a si misma hasta que este resolucionado el problema.
     * @param tablero Tablero que queremos resolucionar.
     * @return True cuando este resolucionado, false cuando deba dar marcha atras porque se ha llegado a punto muerto.
     */
    private boolean seccionBacktrackRecursiva(Tablero tablero) {
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
                if(seccionBacktrackRecursiva(tablero)) return true;
                else {
                    casilla.setNumeroPropio(0);
                    ponerNumeroAsignadoArrayLists(tablero, casilla, i);
                    tabla.setValueAt("", indiceXLocal, indiceYLocal);
                }
            }
        }
        
        return false;
    }
    
    /**
     * Hay algunos casos, en los que asigna el valor correcto en el tablero, a su casilla correspondiente, pero no lo asigna en la
     *  tabla grafica, por lo que esta se queda vacia.
     * Para estos casos hacemos una pasada final a la tabla y los que se encuentren asi los ponemos 'a mano'.
     */
    private void pasadoTableroAGrafico() {
        Casilla casilla;
        for (int indiceX = 0; indiceX < 9; indiceX++) {
            for (int indiceY = 0; indiceY < 9; indiceY++) {
                try { //Los que hagan saltar esta excepcion, es porque se encuentran en blanco y son los que hay que pasar manualmente.
                    Integer.parseInt((String) tabla.getValueAt(indiceX, indiceY));
                }catch(ClassCastException | NumberFormatException ex) {
                    casilla = tablero.getFILAS()[indiceX].getCASILLAS()[indiceY];
                    tabla.setValueAt(casilla.getNumeroPropio(), indiceX, indiceY);
=======
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
>>>>>>> 6c1a7f71c60cdfaf51ceea838ff8348aec8ad21e
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
    
    /**
     * Comprobamos que cada lista contenga los numeros 1-9. Si no contiene uno de estos, es porque hay algo repetido y la solucion
     *      no es valida.
     * @param lista
     * @return True si la lista es correcta. False si hay un numero que falta.
     */
    private boolean comprobarLista(ArrayList<Integer> lista) {
        Iterator it = lista.iterator();
        boolean numeroContenido;
        
        while(it.hasNext()) {
            numeroContenido = false;
            for (int i = 1, numTmp = (int) it.next(); i < 10; i++) {
                if(i == numTmp) numeroContenido = true;
            }
            if(!numeroContenido) return false;
        }
        
        return true;
    }
    
    private boolean chequeoCuadrados(Cuadrado[] cuadrados) {
        ArrayList<Integer> numerosLeidos = null;
        
        for(Cuadrado cuadrado: cuadrados) {
            for(Casilla casilla: cuadrado.getCASILLAS()) {
                numerosLeidos = new ArrayList<>();
                numerosLeidos.add(casilla.getNumeroPropio());
            }
            if(!comprobarLista(numerosLeidos)) {
                System.out.println("Cuadrado malo: " +cuadrado.getCASILLAS()[0].getNUMERO_CUADRADO());
                return false;
            }
        }
        return true;
    }
    
    private boolean chequeoFilas(Fila[] filas) {
        ArrayList<Integer> numerosLeidos = null;
        
        for(Fila fila: filas) {
            for(Casilla casilla: fila.getCASILLAS()) {
                numerosLeidos = new ArrayList<>();
                numerosLeidos.add(casilla.getNumeroPropio());
            }
            if(!comprobarLista(numerosLeidos)) {
                System.out.println("Fila mala: " +fila.getCASILLAS()[0].getNUMERO_FILA());
                return false;
            }
        }
        return true;
    }
    
    private boolean chequeoColumnas(Columna[] columnas) {
        ArrayList<Integer> numerosLeidos = null;
        
        for(Columna columna: columnas) {
            for(Casilla casilla: columna.getCASILLAS()) {
                numerosLeidos = new ArrayList<>();
                numerosLeidos.add(casilla.getNumeroPropio());
            }
            if(!comprobarLista(numerosLeidos)) {
                System.out.println("Columna mala: " +columna.getCASILLAS()[0].getNUMERO_COLUMNA());
                return false;
            }
        }
        return true;
    }
    
    private boolean chequeoResolucion(Tablero tablero) {
        return (chequeoCuadrados(tablero.getCUADRADOS()) && chequeoFilas(tablero.getFILAS()) && chequeoColumnas(tablero.getCOLUMNAS()));
    }
    
    /**
     * Resolucion del tablero propuesto mediante fuerza bruta por 'Backtrack'.
     * Comprobacion de esta resolucion.
     * @return Devolucion del check realizado. True si el tablero realmente esta solucionado de manera buena.
     */
    public boolean resolucionBacktrack() {
        seccionBacktrackRecursiva(tablero);
        pasadoTableroAGrafico();
        return chequeoResolucion(tablero);
    }
}
