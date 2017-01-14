/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.aplicacion;

import vista.ventanas.WindowEjecucion;



/**
 * Busca un elemento en la pila que estamos utilizando y lo consume.
 * @author Mario Codes Sánchez
 * @since 07/11/2016
 */
public class Consumidor implements Runnable {
    private final Monitor BUFFER;
    private final String NOMBRE_THREAD_CONSUMIDOR;
    private static int tiempoDormidoThreads;
    private static boolean matarConsumidores = false;
    
    public Consumidor(Monitor buffer, String nombreThreadConsumidor) {
        this.BUFFER = buffer;
        this.NOMBRE_THREAD_CONSUMIDOR = nombreThreadConsumidor;
    }
    
    @Override
    public void run() {
        try {
            while(!matarConsumidores) {
                //Parte Critica. Consumimos un valor, y restamos 1 al indice comun.
                String valorConsumido = BUFFER.consumir();

                WindowEjecucion.jTextAreaOutputEjecucionConsumidores.append(NOMBRE_THREAD_CONSUMIDOR +" acaba de consumir valor: " +valorConsumido +"\n");
                Thread.sleep(tiempoDormidoThreads);
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
     * @return the matarConsumidores
     */
    public static boolean isMatarConsumidores() {
        return matarConsumidores;
    }

    /**
     * @param aMatarThreads the matarConsumidores to set
     */
    public static void setMatarConsumidores(boolean aMatarThreads) {
        matarConsumidores = aMatarThreads;
    }
}
