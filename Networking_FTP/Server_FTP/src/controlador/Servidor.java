/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Recopilacion de la implementacion logica del Server.
 *  El servidor opera de forma multihilo para no esperar con cada conexion.
 * @author Mario Codes Sánchez
 * @since 19/01/2017
 */
public class Servidor {
    private static final int BUFFER_LENGTH = 3; //Tamaño del buffer que se enviara de golpe.
    
    public static void ejecucion() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        
        Socket socket = null;
        ServerSocket serverSocket = null; 
        
        try {
            while(true) {
                serverSocket = new ServerSocket(8142); //Espera y escucha la llegada de los clientes. Una vez establecida, devuelve el Socket.

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
        private BufferedReader br = null;
        private BufferedWriter bw = null;
        
        public Tarea(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            try {
                System.out.println("Se conectaron desde la IP: " +socket.getInetAddress()); //Info en la consola.

                //Enmascarado de la entrada y salida de bytes.
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bw = new BufferedWriter(new PrintWriter(socket.getOutputStream()));

                //Buffers para enviar / recibir info.
                char bEnviar[];
                char bRecibe[] = new char[BUFFER_LENGTH];

                StringBuffer sb = new StringBuffer(); //Buffer temporal.

                //Leo el nombre que envia el cliente.
                int n;
                while((n = br.read(bRecibe)) == BUFFER_LENGTH) { //Hace un .read() del BR y almacena el contenido en bRecibe
                    sb.append(bRecibe);
                }

                sb.append(bRecibe, 0, n); //Longitud 0-n.

                //Saludo personalizado.
                String saludo = "Hola Mundo (" +sb +") _ " +System.currentTimeMillis();

                //Enviamos el saludo al cliente.
                bEnviar = saludo.toCharArray(); //Hace falta convertirlo para poder enviarlo.
                bw.write(bEnviar);
                bw.flush(); //Accion de enviar.

                System.out.println("Saludo enviado...");
            }catch(Exception ex) {
                ex.printStackTrace();
            }finally { //Cerramos todo.
                try {
                    if(bw != null) bw.close();
                    if(br != null) br.close();
                    if(socket != null) socket.close();
                    System.out.println("Conexion Cerrada.");
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
