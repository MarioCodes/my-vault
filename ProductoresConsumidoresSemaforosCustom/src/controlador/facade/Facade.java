/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.facade;

import controlador.aplicacion.Pila;
import controlador.aplicacion.Consumidor;
import controlador.aplicacion.Productor;

/**
 * Patron de diseño Façade. Clase Intermediaria entre Vista / Controlador del programa. Simplifica la separacion de codigo entre ambas.
 * @author Mario Codes Sánchez
 * @since 17/11/2016
 */
public class Facade {
    /**
     * Creacion, instanciacion y .start() de la cantidad de Threads indicada por GUI.
     * @param numeroProductores Numero de Productores a crear.
     * @param numeroConsumidores Numero de Consumidores a crear.
     * @param maxPila Tamanio maximo que va a tener la pila comun.
     */
    private static void creacionYArranqueThreads(int numeroProductores, int numeroConsumidores, int maxPila) {
        Thread threadPila = new Thread(new Pila(maxPila)); //Pila. No le pongo nombre porque solo va a haber 1 Thread derivado de ella en el programa. No hace falta.
        threadPila.start();
        
        for (int i = 1; i <= numeroProductores; i++) { //Threads productores.
            Thread threadProductor = new Thread(new Productor("Productor" +i, "P" +i));
            threadProductor.start();
        }
        
        for (int i = 1; i <= numeroConsumidores; i++) { //Threads consumidores.
            Thread threadConsumidor = new Thread(new Consumidor("Consumidor" +i));
            threadConsumidor.start();
        }
    }
    
    /**
     * Vuelve a poner en marcha a los consumidores con nuevos hilos.
     * @param numeroConsumidores Numero de consumidores que se pasa como parametro.
     */
    public static void recomenzarConsumidores(int numeroConsumidores) {
        Consumidor.setMatarConsumidores(false);
        for (int i = 1; i <= numeroConsumidores; i++) {
            new Thread(new Consumidor("Consumidor" +i)).start();
        }
    }
    
    /**
     * Vuelve a poner en marcha a los productores en nuevos hilos.
     * @param numeroProductores Numero de productores que se pasa como parametro.
     */
    public static void recomenzarProductores(int numeroProductores) {
        Productor.setMatarProductores(false);
        for (int i = 1; i <= numeroProductores; i++) {
            new Thread(new Productor("Productor" +i, "P" +i)).start();
        }
    }
    
    /**
     * Volvemos a poner en marcha a productores y consumidores en nuevos hilos.
     * @param numeroProductores Numero de Productores a crear.
     * @param numeroConsumidores Numero de Consumidores a crear.
     */
    public static void recomenzarTodos(int numeroProductores, int numeroConsumidores) {
        recomenzarProductores(numeroProductores);
        recomenzarConsumidores(numeroConsumidores);
    }
    
    /**
     * Pasos necesarios antes de poder Crear los Threads y empezar con la ejecucion del programa.
     * @param intArrayParametros Parametros obtenidos de GUI. En ellos el usuario indica: tamanioMaxPila; numProductores y Consumidores; tiempo a dormir de estos.
     */
    public static void instanciacionYParametrosPilaProdCons(int[] intArrayParametros) {
        int maxPila = intArrayParametros[0]; //Parametros pasados.
        int numeroProductores = intArrayParametros[1];
        int numeroConsumidores = intArrayParametros[2];
        int tiempoThreadDormido = intArrayParametros[3];
        
        Productor.setTiempoDormidoThreads(tiempoThreadDormido); //Asignacion de estos parametros en sus correspondientes campos.
        Consumidor.setTiempoDormidoThreads(tiempoThreadDormido);
        
        creacionYArranqueThreads(numeroProductores, numeroConsumidores, maxPila);
    }
    
    /**
     * Set de flag a true para que todos los Hilos paren su ejecucion de forma correcta (sin .stops() ni nada raro / deprecated).
     */
    public static void pararEjecucionTodosHilos() {
        Productor.setMatarProductores(true);
        Consumidor.setMatarConsumidores(true);
    }
    
    /**
     * Set de flag a true para parar solo los 'Productores'.
     */
    public static void pararProductores() {
        Productor.setMatarProductores(true);
    }
    
    /**
     * Set de flag a true para parar solo los 'Consumidores'.
     */
    public static void pararConsumidores() {
        Consumidor.setMatarConsumidores(true);
    }
}
