/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.aplicacion;

import vista.ventanas.WindowEjecucion;

/**
 * Parte mejorada del programa. Es un monitor que cumple la misma funcion que los semaforos. Cuando el buffer esta lleno bloquea a los productores y cuando esta vacio, a los consumidores.
 *  La mayor diferencia aparte de lo obvio, es que los metodos a ejecutar por los Hilos, deben estar aqui metidos y le pasaremos a las clases Productor y Consumidor una instancia del Monitor
 *      a traves de la cual ejecutaran los metodos.
 *  Mejorado para que funcione como cola FIFO.
 * @author Mario Codes Sánchez
 * @since 17/11/2016
 */
public class Monitor implements Runnable {
    private String[] buffer; //Buffer comun donde se almacenaran y consumiran los valores.
    
    private boolean bufferVacio = true; //Tope para que no consuma demasiado.
    private boolean bufferLleno = false; //Tope para que no produzca demasiasdo.
    
    private int contadorComun = 0; //Contador comun para chequear las condiciones anteriores comparandolo con buffer.length.
    
    /**
     * Constructor necesario ya que utilizo al monitor de cola.
     * @param tamanioMax Tamanio maximo que se le dara al char[].
     */
    public Monitor(int tamanioMax) {
        inicializarStringArray(tamanioMax);
    }
    
    /**
     * Ponemos todas las posiciones del char[] en null. Esto se entendera como posicion disponible a partir de ahora.
     * @param tamanioMax Tamanio maximo que se le da al char[].
     */
    private void inicializarStringArray(int tamanioMax) {
        buffer = new String[tamanioMax];
        
        for(int i = 0; i < buffer.length; i++) {
            buffer[i] = "null";
        }
    }
    
    /**
     * Obtenemos el primer indice nulo del String[] para producir en esa posicion.
     * @return Entero con la posicion correcta a escribir.
     */
    private int obtenerPrimerIndiceNulo() {
        for (int i = 0; i < buffer.length; i++) {
            if(buffer[i].matches("null")) return i;
        }
        
        return -1; //En teoria nunca deberia llegar aqui, solo se invocara este metodo cuando haya algun hueco libre.
    }
    
    /**
     * Obtenemos el primer indice NO nulo del String[] para consumir esa posicion.
     * @return Entero con la posicion rellena para consumir y poner a null.
     */
    private int obtenerPrimerIndiceNoNulo() {
        for (int i = 0; i < buffer.length; i++) {
            if(!buffer[i].matches("null")) return i;
        }
        
        return -1; //No deberia llegar a devolver este valor nunca, solo se llamara cuando haya algo para consumir.
    }
    
    /**
     * Metodo Sincrono. Se bloquea mientras no haya sitio para producir. Obtiene el primer indice con ' ', lo rellena y despierta a todos los demas.
     * @param c String con el que se rellena la posicion vacia.
     */
    public synchronized void producir(String c) {
        try {
            while(bufferLleno) { //Si esta lleno que se bloquee.
                wait();
            }
            
            int i = obtenerPrimerIndiceNulo();
            buffer[i] = c; //Asignamos el valor.
            setContadorComun(getContadorComun() + 1); //Sumamos +1 al contador que utilizamos para chequear.
            
            bufferVacio = false;
            bufferLleno = getContadorComun() >= buffer.length;
            
            notifyAll(); //Aviso al resto.
        }catch(InterruptedException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Metodo Sincrono. Version analoga a 'producir()'.
     * @return String consumido para hacer display en ventana.
     */
    public synchronized String consumir() {
        try {
            while(bufferVacio) {
                wait();
            }
            
            int i = obtenerPrimerIndiceNoNulo();
            
            setContadorComun(getContadorComun() - 1);
            String c = buffer[i];
            buffer[i] = "null";
            
            bufferLleno = false;
            bufferVacio = getContadorComun() <= 0;
            
            notifyAll();
            
            return c;
        }catch(InterruptedException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Se encargara de refrescar el contenido de la pila en su jText correspondiente.
     */
    private void outputContenidoPila() {
        while(true) {
            try {
                WindowEjecucion.jTextAreaOutputContenidoPila.setText(null); //Vaciado del jTextArea.
                String[] arrayContenidoPilaTmp = buffer; //Obtenemos una instantanea del contenido actual de la pila.
                
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
     * @return the buffer
     */
    public String[] getBuffer() {
        return buffer;
    }

    /**
     * @param buffer the buffer to set
     */
    public void setBuffer(String[] buffer) {
        this.buffer = buffer;
    }

    /**
     * @return the bufferVacio
     */
    public boolean isBufferVacio() {
        return bufferVacio;
    }

    /**
     * @param bufferVacio the bufferVacio to set
     */
    public void setBufferVacio(boolean bufferVacio) {
        this.bufferVacio = bufferVacio;
    }

    /**
     * @return the bufferLleno
     */
    public boolean isBufferLleno() {
        return bufferLleno;
    }

    /**
     * @param bufferLleno the bufferLleno to set
     */
    public void setBufferLleno(boolean bufferLleno) {
        this.bufferLleno = bufferLleno;
    }

    /**
     * @return the contadorComun
     */
    public int getContadorComun() {
        return contadorComun;
    }

    /**
     * @param contadorComun the contadorComun to set
     */
    public void setContadorComun(int contadorComun) {
        this.contadorComun = contadorComun;
    }
}
