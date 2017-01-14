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
 * @since 07/11/2016
 */
public class Productor implements Runnable {
    private final Monitor BUFFER;
    private final String NOMBRE_THREAD_PRODUCTOR; //Nombre a asignar a este Thread en concreto.
    private final String CLAVE_PRODUCTOR;
    private static int tiempoDormidoThreads; //Tiempo que duerme cada Thread Productor.
    private static boolean matarProductores = false; //Metodo para matar todos los Threads Productores
    
    /**
     * Constructor necesario, al pasar de heredar de Thread a implementar interfaz Runnable no puedo hacer .getName() del Thread directamente cuando lo creo.
     * @param buffer Monitor a utilizar para los metodos sincronizados.
     * @param nombreThreadProductor Nombre a asignar a este Thread en concreto.
     * @param claveProductor Clave unica para identificar que recurso ha producido que Productor.
     */
    public Productor(Monitor buffer, String nombreThreadProductor, String claveProductor) {
        this.BUFFER = buffer;
        this.NOMBRE_THREAD_PRODUCTOR = nombreThreadProductor;
        this.CLAVE_PRODUCTOR = claveProductor;
    }
    
    /**
     * Genera Chars aleatoriamente.
     * @return Char aleatorio.
     * @deprecated
     */
    private char generadorRandomChars() {
        Random r = new Random();
        String diccionarioAlfabeto = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; //Diccionario de donde se obtendra el char.
        return diccionarioAlfabeto.charAt(r.nextInt(diccionarioAlfabeto.length()));
    }

    @Override
    public void run() {
        try {
            while(!matarProductores) {
                String stringActual = CLAVE_PRODUCTOR;
                //Parte Crítica.
                BUFFER.producir(stringActual);
                WindowEjecucion.jTextAreaOutputEjecucionProductores.append(NOMBRE_THREAD_PRODUCTOR +" acaba de producir: " +CLAVE_PRODUCTOR +"\n"); //Output del usuario en la ventana grafica.

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
