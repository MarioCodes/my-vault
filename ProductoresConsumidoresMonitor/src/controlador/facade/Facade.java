/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.facade;

import controlador.aplicacion.Consumidor;
import controlador.aplicacion.Monitor;
import controlador.aplicacion.Productor;

/**
 * Patron de diseño Façade. Clase Intermediaria entre Vista / Controlador del programa. Simplifica la interaccion entre la parte vista y la parte controlador.
 * @author Mario Codes Sánchez
 * @since 14/01/2017
 */
public class Facade {
    private static Monitor buffer;
    
    /**
     * Creacion, instanciacion y .start() de la cantidad de Threads indicada por GUI.
     * @param numeroProductores Numero de Productores a crear.
     * @param numeroConsumidores Numero de Consumidores a crear.
     * @param maxPila Tamanio maximo que va a tener la pila comun.
     */
    private static void creacionYArranqueThreads(int numeroProductores, int numeroConsumidores, int maxPila) {
        Thread threadMonitor = new Thread(buffer = new Monitor(maxPila));
        threadMonitor.start();

        //Threads productores.
        for (int i = 1; i <= numeroProductores; i++) {
            Thread threadProductor = new Thread(new Productor(buffer, "Productor" +i, "P" +i));
            threadProductor.start();
        }

        //Threads consumidores.
        for (int i = 1; i <= numeroConsumidores; i++) {
            Thread threadConsumidor = new Thread(new Consumidor(buffer, "Consumidor" +i));
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
            new Thread(new Consumidor(buffer, "Consumidor" +i)).start();
        }
    }
    
    /**
     * Vuelve a poner en marcha a los productores en nuevos hilos.
     * @param numeroProductores Numero de productores que se pasa como parametro.
     */
    public static void recomenzarProductores(int numeroProductores) {
        Productor.setMatarProductores(false);
        for (int i = 1; i <= numeroProductores; i++) {
            new Thread(new Productor(buffer, "Productor" +i, "P" +i)).start();
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
        //Parametros pasados.
        int maxPila = intArrayParametros[0];
        int numeroProductores = intArrayParametros[1];
        int numeroConsumidores = intArrayParametros[2];
        int tiempoThreadDormido = intArrayParametros[3];
        
        //Asignacion de estos parametros en sus correspondientes campos.
        Productor.setTiempoDormidoThreads(tiempoThreadDormido);
        Consumidor.setTiempoDormidoThreads(tiempoThreadDormido);
        
        creacionYArranqueThreads(numeroProductores, numeroConsumidores, maxPila);
    }
    
    /**
     * Set de flag a true para que todos los Hilos paren su ejecucion de forma correcta (sin .stops() ni nada raro / deprecated).
     *  Realmente no para todos, el monitor no hace falta.
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
