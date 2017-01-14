/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.aplicacion;

import java.util.Random;
import vista.ventanas.WindowEjecucion;

/**
 * Produce elementos semi-aleatorios y los almacena dentro de la Pila. 
 * @author Mario Codes Sánchez
 * @since 17/11/2016
 */
public class Productor implements Runnable {
    private final String NOMBRE_THREAD_PRODUCTOR; //Nombre a asignar a este Thread en concreto.
    private final String CLAVE_PRODUCTOR; //Clave propia del productor.
    private static int tiempoDormidoThreads; //Tiempo que duerme cada Thread Productor.
    private static boolean matarProductores = false; //Metodo para matar todos los Threads Productores
    
    /**
     * Constructor necesario, al pasar de heredar de Thread a implementar interfaz Runnable no puedo hacer .getName() del Thread directamente cuando lo creo.
     * @param nombreThreadProductor Nombre a asignar a este Thread en concreto.
     * @param clave Clave para que quede claro quien ha producido el recurso.
     */
    public Productor(String nombreThreadProductor, String clave) {
        this.NOMBRE_THREAD_PRODUCTOR = nombreThreadProductor;
        this.CLAVE_PRODUCTOR = clave;
    }
    
    /**
     * Genera Chars aleatoriamente. Lo utilizaba antes. Ahora simplemente meten un codigo segun el productor y punto.
     * @return Char aleatorio.
     * @deprecated 
     */
    private char generadorRandomChars() {
        Random r = new Random();
        String diccionarioAlfabeto = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; //Diccionario de donde se obtendra el char.
        return diccionarioAlfabeto.charAt(r.nextInt(diccionarioAlfabeto.length()));
    }
    
    /**
     * Obtenemos el primer indice "null" para producir en el.
     * @return Primer indice "null".
     */
    private int obtenerPrimerIndiceNulo() {
        for (int i = 0; i < Pila.getPila().length; i++) {
            if(Pila.getPila()[i].matches("null")) return i;
        }
        
        return -1; //No deberia llegar a devolver este valor nunca, solo se llamara cuando haya algo para consumir.
    }
    
    @Override
    public void run() {
        try {
            while(!matarProductores) {
                Pila.getSemaforoControlProductores().acquire(); //Adquiere un permiso del semaforo para poder producir.
                String claveProductor = CLAVE_PRODUCTOR;

                //Parte Crítica.
                Pila.getSEMAFORO_MUTEX().acquire(); //Adquiere permiso del mutex para ser el unico que ejecuta parte critica.
                Pila.getPila()[obtenerPrimerIndiceNulo()] = claveProductor; //Metemos el char actual en la cola y sumamos 1 al contador compartido.
                Pila.getSEMAFORO_MUTEX().release(); //Y lo liberamos para que pueda cogerlo otro.

                WindowEjecucion.jTextAreaOutputEjecucionProductores.append(NOMBRE_THREAD_PRODUCTOR +" acaba de producir: " +claveProductor +"\n"); //Output del usuario en la ventana grafica.
                Pila.getSEMAFORO_CONTROL_CONSUMIDORES().release(); //Y liberamos 1 permiso del otro semaforo para que un consumidor, pueda consumir.
                
                Thread.sleep(tiempoDormidoThreads); //Dormimos este Thread el tiempo indicado por ventana.
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * @return the tiempoDormidoThreads
     */
    public static int getTiempoDormidoThreads() {
        return tiempoDormidoThreads;
    }

    /**
     * @param aTiempoDormidoThread the tiempoDormidoThreads to set
     */
    public static void setTiempoDormidoThreads(int aTiempoDormidoThread) {
        tiempoDormidoThreads = aTiempoDormidoThread;
    }
    
    /**
     * @return the matarProductores
     */
    public static boolean isMatarProductores() {
        return matarProductores;
    }

    /**
     * @param aMatarThreads the matarProductores to set
     */
    public static void setMatarProductores(boolean aMatarThreads) {
        matarProductores = aMatarThreads;
    }
}
