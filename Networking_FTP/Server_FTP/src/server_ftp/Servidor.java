/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Recopilacion de la implementacion logica del Server. 
 * ¡ATENCION! -> Por como opera la forma de mapear el Server desde el Cliente, los 2 se deben encontrar en la misma Carpeta Base. Intente enviar el mapeo por red pero me daba una serie de problemas que no me daba tiempo a solucionar.
 *  El servidor opera de forma multihilo para no esperar con cada conexion.
 * @author Mario Codes Sánchez
 * @since 06/02/2017
 */
public class Servidor {
    private static final int BUFFER_LENGTH = 8192; //Tamaño del buffer que se enviara de golpe.
    private static final int PUERTO = 8142;
    
    private static Socket socket = null;    
    private static InputStream in = null;
    private static OutputStream out = null;
        
    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;
    
    /**
     * Cerrado de todas las conexiones que se han usado. Problemas varios si no las cierro despues de usarlas.
     */
    private static void cerrarCabecerasConexion() {
        try {
            if(oos != null) oos.close();
            if(ois != null) ois.close();
            if(out != null) out.close();
            if(in != null) in.close();
            if(socket != null) socket.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Recogida de los parametros necesarios para recibir un fichero y paso a recibo del mismo.
     */
    private static void recogidaParametrosReciboFichero() {
        try {
            byte rutaLength = ois.readByte();
            StringBuilder rutaFichero = new StringBuilder();
            for (int i = 0; i < rutaLength; i++) {
                byte bit = (byte) ois.read();
                rutaFichero.append((char) bit);
            }
            
            byte nameLength = ois.readByte(); //Tamaño del nombre.
            StringBuilder nombreFichero = new StringBuilder();
            for (int i = 0; i < nameLength; i++) { //Operacion para sacar el nombre, con el tamaño antes obtenido.
                byte bit = (byte) ois.read();
                nombreFichero.append((char) bit);
            }
            
            recibirFichero(rutaFichero.toString(), nombreFichero.toString());
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Accion de recibir y guardar el fichero recibido.
     */
    private static void recibirFichero(String rutaFichero, String nombreFichero) {
        try {
            byte[] bytes = new byte[BUFFER_LENGTH]; //Operacion para escribir el contenido.
            out = new FileOutputStream(rutaFichero +nombreFichero);
            
            int count;
            while((count = ois.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Recogida de los parametros necesarios para el envio de un fichero y paso al envio del mismo.
     */
    private static void reciboParametrosEnvioFichero() {
        try {
            byte rutaServerLength = ois.readByte();
            StringBuilder rutaServer = new StringBuilder();
            for (int i = 0; i < rutaServerLength; i++) {
                byte bit = (byte) ois.read();
                rutaServer.append((char) bit);
            }
            
            byte nombreFicheroLength = ois.readByte();
            StringBuilder nombreFichero = new StringBuilder();
            for (int i = 0; i < nombreFicheroLength; i++) {
                byte bit = (byte) ois.read();
                nombreFichero.append((char) bit);
            }
            
            enviarFichero(rutaServer.toString(), nombreFichero.toString());
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Accion de enviar un fichero al Cliente que lo ha solicitado.
     */
    private static void enviarFichero(String rutaServer, String nombreFichero) {
        try {
            File file = new File(rutaServer+nombreFichero);
            byte[] bytes = new byte[BUFFER_LENGTH];

            in = new FileInputStream(file);
            
            int count;
            while((count = in.read(bytes)) > 0) {
                oos.write(bytes, 0, count);
            }
            
            oos.flush();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Contestacion al chequeo realizado por el Cliente para comprobar si el Server esta 'reachable'.
     *  Envia true al cliente.
     */
    private static void comprobacionConexion() {
        try {
            oos.writeBoolean(true);
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Ejecucion de la apertura de las cabeceras de la conexion.
     */
    private static void aperturaCabecerasConexion() {
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Recoge y lee el primer byte de los datos entrantes. Este es el que indica la accion a realizar.
     */
    private static void gestionAcciones() {
        try {
            aperturaCabecerasConexion();
            
            byte opcion = (byte) ois.readInt();
            switch(opcion) {
                case 0: //Testeo de Conexion.
                    comprobacionConexion();
                    break;
                case 1: //Mapeo del Server y envio de esta informacion al Cliente. Deprecated, me da demasiados problemas y no tengo tiempo.
                    //envioMapeoCliente(mapearServer());
                    break;
                case 2: //Recibir fichero.
                    recogidaParametrosReciboFichero();
                    break;
                case 3: //Envio de fichero.
                    reciboParametrosEnvioFichero();
                    break;
                default:
                    System.out.println("Comprobar selector de Acciones.");
                    break;
            }
            
            cerrarCabecerasConexion();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Puesta en marcha del Servidor. Se quedara a la espera de que haya conexiones entrantes.
     * Cada cliente tendra su propio hilo aparte.
     */
    public static void ejecucionServidor() {
        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO); //Espera y escucha la llegada de los clientes. Una vez establecida, devuelve el Socket.; 
        
            while(true) {
                socket = serverSocket.accept(); /* El ServerSocket me da el Socket.
                                                        Bloquea el programa en esta linea y solo avanza cuando un cliente se conecta.*/
                new Thread(() -> gestionAcciones()).start(); //Comienzo de la faena en un Hilo aparte.
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ejecucionServidor();
    }
}
