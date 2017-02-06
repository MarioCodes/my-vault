/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

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
import javax.swing.JTree;

/**
 * Recopilacion de la implementacion logica del Server.
 *  El servidor opera de forma multihilo para no esperar con cada conexion.
 *  El primer bit que se recibe indica la operacion. 
 *      El segundo bit el .length del nombre del archivo a leer. //fixme: revisar esto, solo se aplica a cuando enviaba ficheros y no hacia nada mas.
 *      El tercer BLOQUE es el nombre del archivo.
 *      El resto es el contenido del fichero.
 * @author Mario Codes Sánchez
 * @since 05/02/2017
 */
public class Servidor {
    private static final int BUFFER_LENGTH = 8192; //Tamaño del buffer que se enviara de golpe.
    private static final int PUERTO = 8142; //fixme: el servidor debera dejar elegir el puerto por el cual se conecta.
    
    private static Socket socket = null;    
    private static InputStream in = null;
    private static OutputStream out = null;
        
    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;
    
    /**
     * Accion de recibir y guardar el fichero recibido.
     * Se ejecuta en un hilo aparte.
     */
    private static void recibirFichero() {
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
            
            byte[] bytes = new byte[BUFFER_LENGTH]; //Operacion para escribir el contenido.
            out = new FileOutputStream(rutaFichero +nombreFichero.toString()); //todo: mas adelante debera ser variable. No hardcodeado.
            
            int count;
            while((count = ois.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }
            
        }catch(IOException ex) {
            ex.printStackTrace();
        }finally {
//            try {
//                if(out != null) out.close();
//                if(in != null) in.close();
//                if(socket != null) socket.close();
//            }catch(IOException ex) {
//                ex.printStackTrace();
//            }
        }
    }
    
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
//            out = socket.getOutputStream();
//            oos = new ObjectOutputStream(out);
            
//            byte[] bytesRutaFich = rutaServer.getBytes(); //Esto es lo que creo que no hace falta.
//            oos.writeByte(bytesRutaFich.length);
//            oos.write(bytesRutaFich);
//            oos.flush();
//            
//            byte[] bytesNombreFich = nombreFichero.getBytes();
//            oos.writeByte(bytesNombreFich.length);
//            oos.write(bytesNombreFich);
//            oos.flush();
            
            int count;
            while((count = in.read(bytes)) > 0) {
                oos.write(bytes, 0, count);
            }
            
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
     * Mapeado local del server y retorno de esta informacion en forma de JTree; Para hacer set directo de su mode(). //todo: comprobar que es el mode lo que hay que hacer set. no recuerdo.
     * @return JTree con el contenido mapeado del server.
     * @deprecated no me da tiempo a implementar esto.
     */
    private static JTree mapearServer() {
        return new Mapeador().mapear();
    } 
    
    /**
     * Envio por Socket del Tree local mapeado, al Cliente.
     * @param tree Tree mapeado que queremos enviar.
     * @deprecated era la idea original, no me da tiempo ni de fly.
     */
    private static void envioMapeoCliente(JTree tree) {
        try {
            oos.writeObject(tree);
            oos.flush();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Recoge y lee el primer byte de los datos entrantes. Este es el que indica la accion a realizar.
     */
    private static void gestionAcciones() {
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);
            
            byte opcion = (byte) ois.readInt();
            System.out.println(opcion); //todo: borrar al final.
            
            switch(opcion) {
                case 0: //Testeo de Conexion.
                    comprobacionConexion();
                    break;
                case 1: //Mapeo del Server y envio de esta informacion al Cliente.
                    envioMapeoCliente(mapearServer());
                    break;
                case 2: //Recibir fichero.
                    recibirFichero();
                    break;
                case 3: //Envio de fichero.
                    reciboParametrosEnvioFichero();
//                    enviarFichero();
                    break;
                default:
                    System.out.println("SHIT.");
                    break;
            }
            
//            ois.close();
//            oos.close();
//            out.close();
//            in.close();
//            socket.close();
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
}
