/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.aplicacion;
import vista.ventanas.WindowEjecucion;

/**
 * Pila comun donde los Productores almacenaran los valores y los Consumidores los consumiran.
 *  Es un thread en si misma para actualizar la informacion grafica de la ventana.
 * @author Mario Codes Sánchez
 * @since 14/01/2017
 * @version 1.1 Cambiados todos los Semaphore por mis Semaforo customizados.
 */
public class Pila implements Runnable {
    private static String[] pila; //Cola comun donde vamos a almacenar los caracteres a Producir / Consumir.
    
    private static Semaforo semaforoControlProductoresCustom; //Controla que los productores no se pasen y desborden el Array. (Array[i] > Array.length).
    private final static Semaforo SEMAFORO_CONTROL_CONSUMIDORES_CUSTOM = new Semaforo(0); //Controla que los consumidores no consuman mas de lo que hay producido (El Array no llegue a -1).
    private final static Semaforo SEMAFORO_MUTEX_CUSTOM = new Semaforo(1); //Semaforo mutuamente excluyente.
    
    public Pila(int tamanioMax) {
        rellenarPila(tamanioMax);
        semaforoControlProductoresCustom = new Semaforo(pila.length);
    }
    
    /**
     * Instanciacion y relleno inicial de la Cola.
     * @param tamanioMax Capacidad total que se le da a la Pila.
     */
    private void rellenarPila(int tamanioMax) {
        pila = new String[tamanioMax];
        
        for (int i = 0; i < pila.length; i++) {
            pila[i] = "null";
        }
    }
    
    /**
     * Se encargara de refrescar el contenido de la pila en su jText correspondiente.
     */
    private void outputContenidoPila() {
        while(true) {
            try {
                String[] arrayContenidoPilaTmp = pila; //Obtenemos una instantanea del contenido actual de la pila.
                WindowEjecucion.jTextAreaOutputContenidoPila.setText(""); //Vaciado del jTextArea.

                //Output del contenido de la pila.
                for (int i = 0; i < arrayContenidoPilaTmp.length; i++) {
                    WindowEjecucion.jTextAreaOutputContenidoPila.append("                " +(i+1) +" - " +arrayContenidoPilaTmp[i] +"\n"); //Encuadrado a mano para que quede bien en su jTextOutput.
                }
                
                Thread.sleep(Consumidor.getTiempoDormidoThreads()+(Consumidor.getTiempoDormidoThreads()/1000)); //Este conviene que se ejecute siempre un poco despues que los Productores / Consumidores para hacer output de las ultimas modificaciones.
            }catch(InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @Override
    public void run() {
        outputContenidoPila();
    }

    /**
     * @return the pila
     */
    public static String[] getPila() {
        return pila;
    }
    
    /**
     * @return the semaforoControlProductoresCustom
     */
    public static Semaforo getSemaforoControlProductoresCustom() {
        return semaforoControlProductoresCustom;
    }
    
    /**
     * @return the SEMAFORO_CONTROL_CONSUMIDORES_CUSTOM
     */
    public static Semaforo getSEMAFORO_CONTROL_CONSUMIDORES_CUSTOM() {
        return SEMAFORO_CONTROL_CONSUMIDORES_CUSTOM;
    }

    /**
     * @return the SEMAFORO_MUTEX_CUSTOM
     */
    public static Semaforo getSEMAFORO_MUTEX_CUSTOM() {
        return SEMAFORO_MUTEX_CUSTOM;
    }
}
