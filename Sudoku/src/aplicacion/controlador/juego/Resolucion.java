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
import javax.swing.JTable;

/**
 * Resolucion del Sudoku mediante fuerza bruta por metodo 'Backtrack' (ver link Anexo).
 * Yo le paso a esta clase una jTable parcialmente rellena y la transforma en un Tablero. El cual tengo que resolver.
 * Separo la parte de codigo que genera el Tablero a partir de la jTable en una Inner Class.
 * @author Mario Codes Sánchez
 * @since 29/12/2016
 * @see https://en.wikipedia.org/wiki/Sudoku_solving_algorithms
 */
public class Resolucion {
    private int indiceX, indiceY;
    private final JTable TABLA;
    private final Tablero TABLERO; //Tablero el cual se encontrara parcialmente completado y tendre que encontrar la solucion al resto.

    /**
     * Constructor normal a utilizar.
     * Le paso una tabla grafica como parametro, y desde ella genero el tablero a utilizar.
     * @param tabla Tabla grafica la cual se rellenara.
     */
    public Resolucion(JTable tabla) {
        this.TABLA = tabla;
        this.TABLERO = creacionTablero.generacionTablero(TABLA);
        
        creacionTablero.quitarNumerosYaPuestosDeListas(TABLERO);
    }
    
    /**
     * Comprobacion de un numero contra una ArrayList de Integers para ver si lo contiene.
     * @param numeroAProbar Numero que queremos chequear.
     * @param lista ArrayList a comprobar.
     * @return True si la AL contiene el numero, false sino.
     */
    private boolean checkNumeroContraArrayList(int numeroAProbar, ArrayList<Integer> lista) {
        for (int num : lista) {
            if(numeroAProbar == num) return true;
        }
        return false;
    }
    
    /**
     * Check para comprobar si el numero que queremos meter en la casilla es valido.
     * @param casilla Casilla que queremos comprobar.
     * @param numeroAProbar Numero que queremos meter.
     * @return True si el numero es valido contra las 3 listas de numeros (Cuadrado, Fila y Columna).
     */
    private boolean checkNumeroCasillaValido(Casilla casilla, int numeroAProbar) {
        boolean numeroIsOK;
        
        ArrayList<Integer> numerosDisponiblesCuadrado = TABLERO.getCUADRADOS()[casilla.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado();
        ArrayList<Integer> numerosDisponiblesFila = TABLERO.getFILAS()[casilla.getNUMERO_FILA()].getNumerosDisponiblesFila();
        ArrayList<Integer> numerosDisponiblesColumna = TABLERO.getCOLUMNAS()[casilla.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna();
        
        numeroIsOK = checkNumeroContraArrayList(numeroAProbar, numerosDisponiblesCuadrado);
        if(numeroIsOK) numeroIsOK = checkNumeroContraArrayList(numeroAProbar, numerosDisponiblesFila);
        if(numeroIsOK) numeroIsOK = checkNumeroContraArrayList(numeroAProbar, numerosDisponiblesColumna);
        
        return numeroIsOK;
    }
    
    /**
     * Busca una casilla vacia, si la encuentra asigna las coordenadas a las variables miembro y devuelve true.
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
                //</primera parte de la recursividad>
                
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
     * Para estos casos hacemos una pasada final a la tabla cuando este todo resuelto, y los que se encuentren asi, los
     *      ponemos 'a mano'.
     */
    private void pasadoTableroAGrafico() {
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
     * Comprobamos que cada lista contenga los numeros 1-9. Si no contiene uno de estos, es porque hay algo repetido y la solucion
     *      no es valida.
     * @param lista ArrayList que queremos comprobar que este sin duplicaciones.
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
    
    /**
     * Comprobacion de los cuadrados del tablero. Que en ellos mismos no contengan numeros repetidos.
     * @param cuadrados Cuadrados[] que queremos comprobar.
     * @return True si estan resueltos correctamente.
     */
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
    
    /**
     * Chequeo de las filas, que no contengan numeros repetidos entre ellas.
     * @param filas Fila[] que queremos comprobar.
     * @return True si no hay numero repetido.
     */
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
    
    /**
     * Comprobacion de las columnas, que no tengan numeros repetidos.
     * @param columnas Columna[] a comprobar.
     * @return True si no hay numeros repetidos.
     */
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
    
    /**
     * Comprobacion de las 3 anteriores.
     * Check de que el conjunto de Cuadrados, filas y columnas esten bien resueltas.
     * @param tablero Tablero a comprobar.
     * @return True si el conjunto de las 3 posibilidades estan de forma correcta.
     */
    private boolean chequeoResolucion(Tablero tablero) {
        return (chequeoCuadrados(tablero.getCUADRADOS()) && chequeoFilas(tablero.getFILAS()) && chequeoColumnas(tablero.getCOLUMNAS()));
    }
    
    /**
     * Resolucion del tablero que contiene esta clase mediante fuerza bruta ('Backtrack').
     *  Despues de esto, hace un check para comprobar que este bien resuelto.
     * @return Devolucion del check realizado. True si el tablero realmente esta solucionado de manera buena.
     */
    public boolean resolucionBacktrack() {
        seccionBacktrackRecursiva();
        pasadoTableroAGrafico();
        return chequeoResolucion(TABLERO);
    }
    
    /**
     * Inner Static Class. La utilizo para mantener separado el codigo que crea un Tablero a traves de una jTable y el codigo de
     *      resolucion del Tablero en si mismo.
     */
    private static class creacionTablero {
        /**
        * Relleno de Cuadrado[] con los valores de la tabla de su homologo Cuadrado.
        * @param cuadrados Cuadrados donde almaceno los datos de cada casilla.
        * @param tabla Tabla grafica de donde sacamos los datos.
        * @param numeroCuadrado Numero de cuadrado que toca rellenar en esta iteracion.
        */
       private static void rellenoCuadradoTablero(Cuadrado[] cuadrados, JTable tabla, int numeroCuadrado) {
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
       private static void rellenoTablaConNumeros(JTable tabla, Cuadrado[] cuadrados) {
           for(int i = 0; i < cuadrados.length; i++) {
               rellenoCuadradoTablero(cuadrados, tabla, i);
           }
       }

       /**
        * Quitamos los numeros disponibles de las ArrayLists, de los numeros que ya se han puesto a mano.
        * Tengo que hacer un parse a (Object) ya que si no, toma el numero como si fuera el indice y no el objeto que quiero quitar.
        * @param tablero Tablero del cual quitamos los numeros ya puestos.
        */
       private static void quitarNumerosYaPuestosDeListas(Tablero tablero) {
           for(Fila fila: tablero.getFILAS()) {
               for(Casilla cas: fila.getCASILLAS()) {
                   if(cas.getNumeroPropio() != 0) {
                       tablero.getCUADRADOS()[cas.getNUMERO_CUADRADO()].getNumerosDisponiblesCuadrado().remove((Object) cas.getNumeroPropio());
                       tablero.getFILAS()[cas.getNUMERO_FILA()].getNumerosDisponiblesFila().remove((Object) cas.getNumeroPropio());
                       tablero.getCOLUMNAS()[cas.getNUMERO_COLUMNA()].getNumerosDisponiblesColumna().remove((Object) cas.getNumeroPropio());
                   }
               }
           }
       }

       /**
        * Generacion de un tablero a traves de una tabla grafica. Utiliza la tabla que paso como parametro por el constructor.
        * Es muy muy parecido al metodo que hay en Tablero para rellenar una tabla con el contenido de las casillas, pero aqui
        *  interesa rellenas las casillas con el contenido de la tabla.
        * @param tabla tabla grafica que quiero convertir en tablero.
        */
       private static Tablero generacionTablero(JTable tabla) {
           Cuadrado[] cuadrados = new Cuadrado[9];
           Tablero.inicializacionCuadrados(cuadrados);
           rellenoTablaConNumeros(tabla, cuadrados);
           return new Tablero(cuadrados);
       }
    }
}
