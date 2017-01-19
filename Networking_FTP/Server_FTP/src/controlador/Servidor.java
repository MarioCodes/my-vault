/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Recopilacion de la implementacion logica del Server.
 *  El servidor opera de forma multihilo para no esperar con cada conexion.
 * @author Mario Codes Sánchez
 * @since 19/01/2017
 */
public class Servidor {
    private static final int BUFFER_LENGTH = 16384; //Tamaño del buffer que se enviara de golpe.
    private static final int PUERTO = 8142;
    
    /**
     * Puesta en marcha del Servidor. Se quedara a la espera de que haya conexiones entrantes.
     * Cada cliente tendra su propio hilo aparte.
     */
    public static void ejecucion() {
        try {
            Socket socket = null;
            ServerSocket serverSocket = new ServerSocket(PUERTO); //Espera y escucha la llegada de los clientes. Una vez establecida, devuelve el Socket.; 
        
            while(true) {
                socket = serverSocket.accept(); /* El ServerSocket me da el Socket.
                                                        Bloquea el programa en esta linea y solo avanza cuando un cliente se conecta.*/

                new Tarea(socket).start(); //Cada Tarea en un nuevo Hilo.
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Inner Class encargada de la Tarea de atender a un Cliente.
     */
    static class Tarea extends Thread {
        private Socket socket = null;
        
        private InputStream in = null;
        private OutputStream out = null;
        
        /**
         * Constructor a usar por defecto.
         * @param socket Socket que estamos usando para este Hilo.
         */
        public Tarea(Socket socket) {
            this.socket = socket;
        }
        
        /**
         * Accion de recibir y guardar el fichero recibido.
         */
        private void recibirFichero() {
            try {
                in = socket.getInputStream();
                out = new FileOutputStream("ficheros/fichero.txt"); //todo: mas adelante debera ser variable. No hardcodeado.
                
                byte[] bytes = new byte[BUFFER_LENGTH];

                int count;
                while((count = in.read(bytes)) > 0) {
                    out.write(bytes, 0, count);
                }
            }catch(IOException ex) {
                ex.printStackTrace();
            }finally {
                try {
                    if(out != null) out.close();
                    if(in != null) in.close();
                    if(socket != null) socket.close();
                }catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        @Override
        public void run() {
            recibirFichero();
        }
    }
}
