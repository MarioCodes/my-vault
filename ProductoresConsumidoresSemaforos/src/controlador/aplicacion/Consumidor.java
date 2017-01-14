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
 * @since 17/11/2016
 */
public class Consumidor implements Runnable {
    private static int tiempoDormidoThreads; //No lo paso por constructor, lo hago mediante setters en Façade.
    private static boolean matarConsumidores = false;
    private final String NOMBRE_THREAD_CONSUMIDOR;
    
    public Consumidor(String nombreThreadConsumidor) {
        this.NOMBRE_THREAD_CONSUMIDOR = nombreThreadConsumidor;
    }
    
    /**
     * Obtenemos el primer indice no nulo para consumirlo.
     * @return Primer indice no null.
     */
    private int obtenerPrimerIndiceNoNulo() {
        for (int i = 0; i < Pila.getPila().length; i++) {
            if(!Pila.getPila()[i].matches("null")) return i;
        }
        
        return -1; //No deberia llegar a devolver este valor nunca, solo se llamara cuando haya algo para consumir.
    }
    
    @Override
    public void run() {
        try {
            while(!matarConsumidores) {
                Pila.getSEMAFORO_CONTROL_CONSUMIDORES().acquire(); //Consume 1 permiso del semaforo de consumidores para proceder. Si no hay, se queda esperando.
                Pila.getSEMAFORO_MUTEX().acquire(); //Pillamos el permiso del mutex.
                
                //Parte Critica. Consumimos un valor, y restamos 1 al indice comun.
                int primerIndiceNoNulo = obtenerPrimerIndiceNoNulo();
                String valorConsumido = Pila.getPila()[primerIndiceNoNulo];
                Pila.getPila()[primerIndiceNoNulo] = "null";
                
                Pila.getSEMAFORO_MUTEX().release();

                WindowEjecucion.jTextAreaOutputEjecucionConsumidores.append(NOMBRE_THREAD_CONSUMIDOR +" acaba de consumir valor: " +valorConsumido +"\n");
                Pila.getSemaforoControlProductores().release(); //Indicamos al semaforo de productores, que hay un hueco libre que rellenar.
                
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
