/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.aplicacion;

/**
 * Creacion de un semaforo personal mediante el uso de un Monitor.
 *  Intentare replicar la mayoria de metodos de 'Semaphore' por si acaso los necesitara mas adelante.
 * @author Mario Codes Sánchez
 * @since 14/01/2017
 */
public class Semaforo {
    private int permisos;
    
    /**
     * Constructor por defecto.
     * @param permisos Numero de permisos con los que se inicia el semaforo.
     */
    public Semaforo(int permisos) {
        this.permisos = permisos;
    }
    
    /**
     * Adquirimos un permiso del Semaforo.
     *  Mientras no haya ninguno libre, nos quedamos esperando.
     */
    public synchronized void adquirir() {
        try {
            while(permisos < 1) {
                wait();
            }
            
            permisos--;
        }catch(InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Adquirimos i permisos del Semaforo.
     *  Mientras no haya esa cantidad libres, esperamos.
     * @param permisos Cantidad de permisos a extraer.
     */
    public synchronized void adquirir(int permisos) {
        try {
            while(this.permisos < permisos) {
                wait();
            }
            
            this.permisos -= permisos;
        }catch(InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Devolucion de todos los permisos disponibles en el Semaforo y puesta a 0.
     * @return Numero de permisos disponibles actualmente en el semaforo.
     */
    public synchronized int adquirirTodos() {
        int permisos = this.permisos;
        this.permisos = 0;
        return permisos;
    }

    /**
     * Incrementa el numero de permisos de este semaforo en 1 y despierta a todos para que comprueben si sus condiciones de bloqueo han cambiado.
     */
    public synchronized void liberar() {
        this.permisos++;
        this.notifyAll();
    }

    /**
     * Incrementa el numero de permisos de este semaforo en i y despierta a todos para que comprueben si sus condiciones de bloqueo han cambiado.
     * @param permisos Numero de permisos que se aniaden al contador del Semaforo.
     */
    public synchronized void liberar(int permisos) {
        this.permisos += permisos;
        this.notifyAll();
    }
    
    /**
     * Intenta adquirir en el momento de llamado de este metodo un permiso, devolviendo true y restando 1 si hay disponibles.
     * @return True y resta 1 si hay permisos. False si no los hay.
     */
    public synchronized boolean intentarAdquirir() {
        if(this.permisos > 0) {
            permisos--;
            return true;
        } else return false;
    }
    
    /**
     * Intenta adquirir un numero i de permisos en el momento de llamado de este metodo, devolviendo true y restando i si es posible.
     * @param permisos Numero de permisos a intentar adquirir.
     * @return True y resta i si hay tantos permisos disponibles. False si no.
     */
    public synchronized boolean intentarAdquirir(int permisos) {
        if(this.permisos >= permisos) {
            this.permisos -= permisos;
            return true;
        } else return false;
    }
    
    /**
     * @return the permisos
     */
    public int getPermisos() {
        return permisos;
    }
}
