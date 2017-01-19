/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Mario
 */
public class Server {
    private static final int BUFFER_LENGTH = 3;
    
    /**
     * Server: Programa que una vez levantado, debe permanecer esperando a que se conecten los clientes.
     *  El codigo comentado era como se hacia enviando objetos serializados. Esta version envia bytes directamente.
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
//        ObjectInputStream ois = null;
//        ObjectOutputStream oos = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        
        Socket s = null;
        ServerSocket ss = new ServerSocket(5432); //Espera y escucha la llegada de los clientes. Una vez establecida, devuelve el Socket.
        
        while(true) {
            try {
                //El ServerSocket me da el Socket.
                s = ss.accept(); //Bloquea el programa en esta linea y solo avanza cuando algun cliente se conecte, retornando el socket.
                
                new Tarea(s).start(); //La diferencia, es que ahora cuando entra una conexion, crea un nuevo Hilo para atenderlo y puede atender a uno nuevo instantaneamente.
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Inner class donde se delega la tarea de atender al cliente.
     */
    static class Tarea extends Thread {
        private Socket s = null;
//        private ObjectInputStream ois = null;
//        private ObjectOutputStream oos = null;
        private BufferedReader br = null;
        private BufferedWriter bw = null;
        
        public Tarea(Socket socket) {
            this.s = socket;
        }
        
        @Override
        public void run() {
            try {
            //Informacion en la consola.
                System.out.println("Se conectaron desde la IP: " +s.getInetAddress());
                
                //Enmascaro la entrada y salida de bytes.
//                ois = new ObjectInputStream(s.getInputStream()); //Relacionamos el inputStream local con la conexion del socket.
//                oos = new ObjectOutputStream(s.getOutputStream()); //Y lo mismo con el outputStream.
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                bw = new BufferedWriter(new PrintWriter(s.getOutputStream()));
                
                //Buffers para enviar / recibir informacion.
                char bEnviar[];
                char bRecibe[] = new char[BUFFER_LENGTH];
                
                StringBuffer sb = new StringBuffer();
                
                //Leo el nombre que envia el cliente.
//                String nom = (String) ois.readObject();
                int n;
                while((n = br.read(bRecibe)) == BUFFER_LENGTH) { //Hace un .read() del BufferedReader y almacena el contenido en bRecibe.
                    sb.append(bRecibe); //Metemos la lectura actual del bufferRecibe en el StringBuffer.
                }
                
                sb.append(bRecibe, 0, n); //Y pasamos el contenido final total al buffer con longitud de 0 a n.
                
                //Armo el saludo personalizado que le quiero enviar.
//                String saludo = "Hola Mundo (" +nom +") _ " +System.currentTimeMillis();
                String saludo = "Hola Mundo (" +sb +") _ " +System.currentTimeMillis();
                
                
                //Envio el saludo al cliente
//                oos.writeObject(saludo);
                bEnviar = saludo.toCharArray(); //Convertimos el saludo personalizado a un char Array.
                bw.write(bEnviar); //Almacenamos este en el BufferedWriter.
                bw.flush(); //Y hacemos un .flush() para enviarlo.
                System.out.println("Saludo enviado...");
            }catch(Exception ex) {
                ex.printStackTrace();
            }finally {
                try {
//                    if(oos != null) oos.close();
//                    if(ois != null) ois.close();
                    if(bw != null) bw.close();
                    if(br != null) br.close();
                    if(s != null) s.close();
                    System.out.println("Conexion cerrada");
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
}
