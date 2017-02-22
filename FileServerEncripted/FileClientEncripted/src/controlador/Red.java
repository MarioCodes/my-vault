/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import javax.swing.JTree;

/**
 * Recopilacion de la implementacion logica de Red.
 *  Las conexiones que se abren, se cerraran automaticamente al acabar el programa mediante un ShutdownHook.
 * @author Mario Codes Sánchez
 * @since 05/02/2017
 */
public class Red {
    private static final int BUFFER_LENGTH = 8192;
    private final int PUERTO;
    private final String SERVER_IP;
    
    private Socket socket = null;
    private InputStream in = null;
    private OutputStream out = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
            
    /**
     * Constructor a utilizar por defecto.
     * @param serverIP IP del Servidor a conectarse.
     * @param puerto Puerto del Server para la conexion.
     */
    public Red(String serverIP, int puerto) {
        this.SERVER_IP = serverIP;
        this.PUERTO = puerto;
    }
    
    /**
     * Pasos necesarios a realizar antes de ejecutar cualquier accion.
     *  Al final habra que usar la cabecera de fin para cerrar estas conexiones.
     * @throws IOException Posible excepcion lanzada por las conexiones al abrirse.
     */
    private void cabeceraComienzoConexion() throws IOException, IllegalArgumentException {
        socket = new Socket(SERVER_IP, PUERTO); //IP y PORT del Server.

        in = socket.getInputStream();
        out = socket.getOutputStream();
        oos = new ObjectOutputStream(out);
        ois = new ObjectInputStream(in);
    }
    
    /**
     * Cerrado de todas las conexiones usadas, fallos varios sino.
     */
    private void finConexion() {
        try {
            if(ois != null) ois.close();
            if(oos != null) oos.close();
            if(out != null) out.close();
            if(in != null) in.close();
            if(socket != null) socket.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Cerrado de las conexiones Abiertas, se ejecutara en el Shutdown Hook al finalizar el programa para asegurarme de que se ejecuta si o si.
     */
    public void cerrarConexiones() {
        try {
            if(socket != null) socket.close();
            if(in != null) in.close();
            if(out != null) out.close();
            if(ois != null) ois.close();
            if(oos != null) oos.close();
        }catch(SocketException ex){
        }catch(IOException ex) {
            System.out.println("Problema al cerrar las conexiones.");
            ex.printStackTrace();
        }
    }
    
    /**
     * Metodo para el recibimiento de un fichero Server -> Cliente.
     * @param rutaServer Ruta del Servidor recortada de donde pillamos el fichero.
     * @param rutaLocal Ruta local donde queremos el fichero.
     * @param nombreFichero Nombre del fichero recortado.
     */
    public void reciboFicheroServerCliente(String rutaServer, String rutaLocal, String nombreFichero) {
        try {
            cabeceraComienzoConexion();
            
            oos.writeInt(3); //Envio de la accion.
            oos.flush();
            
            byte[] bytesRutaFich = rutaServer.getBytes(); //Envio de la ruta del Server recortada.
            oos.writeByte(bytesRutaFich.length);
            oos.write(bytesRutaFich);
            oos.flush();
            
            byte[] bytesNombreFich = nombreFichero.getBytes(); //Envio del nombre del fichero.
            oos.writeByte(bytesNombreFich.length);
            oos.write(bytesNombreFich);
            oos.flush();
            
            byte[] bytes = new byte[BUFFER_LENGTH]; //Operacion para escribir el contenido.
            out = new FileOutputStream(rutaLocal +nombreFichero);
            
            int count;

            while((count = ois.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }
         
            finConexion();
        }catch(SocketException ex) {
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static byte[] encriptar(Rsa rsa, byte[] bytes, BigInteger[] clavePublicaServer) {
        BigInteger mensaje = new BigInteger(bytes);
        return rsa.encrypt(mensaje, clavePublicaServer).toByteArray();
    }
    
    public static byte[] encriptar(Rsa rsa, String raw, BigInteger[] clavePublicaServer) {
        BigInteger mensaje = new BigInteger(raw.getBytes());
        return rsa.encrypt(mensaje, clavePublicaServer).toByteArray();
    }
    
    /**
     * Metodo para el envio de un fichero del Cliente al Server.
     * @param rsa
     * @param claveServer
     * @param rutaServer Ruta del Servidor donde queremos el  fichero.
     * @param rutaLocal Ruta local recortada (sin incluir el fichero) de donde lo pillamos.
     * @param nombreFichero Nombre recortado del fichero suelto.
     */
    public void envioFicheroClienteServer(Rsa rsa, BigInteger[] claveServer, String rutaServer, String rutaLocal, String nombreFichero) {
        try {
            cabeceraComienzoConexion();
            
            File file = new File(rutaLocal+nombreFichero);
            byte[] bytes = new byte[BUFFER_LENGTH];

            in = new FileInputStream(file);
            
            oos.writeInt(2);
            oos.flush();
            
            byte[] bytesRutaFich = encriptar(rsa, rutaServer.getBytes(), claveServer);
            oos.writeByte(bytesRutaFich.length);
            oos.write(bytesRutaFich);
            oos.flush();
            
            byte[] bytesNombreFich = encriptar(rsa, nombreFichero.getBytes(), claveServer);
            oos.writeByte(bytesNombreFich.length);
            oos.write(bytesNombreFich);
            oos.flush();
            
            int count;
            while((count = in.read(bytes)) > 0) {
                oos.write(encriptar(rsa, bytes, claveServer), 0, count);
            }
            
            oos.flush();
            finConexion();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Obtencion del JTree mapeado del directorio del Servidor.
     * @return JTree mapeado del Server.
     * @deprecated Era la idea original, no me da tiempo.
     */
    public JTree obtencionMapeoServer() {
        try {
            cabeceraComienzoConexion();
            
            oos.writeByte(1);
            oos.flush();
            
            JTree treeServer = (JTree) ois.readObject();
            
            ois.close();
            oos.close();
            out.close();
            in.close();
            
            return treeServer;
        }catch(ClassCastException|ClassNotFoundException ex) {
            System.out.println("Problema de cast de clase.");
            ex.printStackTrace();
        }catch(IOException ex) {
            System.out.println("Problema de IO");
            ex.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Comprobacion de que el servidor esta alcanzable mediante los parametros introducidos.
     * @return Estado de la conexion.
     */
    public boolean checkConexion() {
        try {
            cabeceraComienzoConexion();
            
            in = new BufferedInputStream(socket.getInputStream());
            
            oos.writeInt(0);
            oos.flush();
            
            boolean estado = ois.readBoolean(); //Leemos la respuesta del server.
            System.out.println("Booleano recibido.");
//            finConexion();
            
            return estado;
        }catch(ConnectException ex) {
            System.out.println("Problema en la conexion. " +ex.getLocalizedMessage());
            return false;
        }catch(IllegalArgumentException ex) {
            System.out.println("Numero de argumentos erroneo. Comprobar que el puerto este dentro de rango.\t" +ex.getLocalizedMessage());
            return false;
        }catch(IOException ex) {
            System.out.println("Problema de IO.");
            ex.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtencion de la clave publica del Server y envio de la propia.
     * @param rsa Clase Rsa de donde obtenemos las claves.
     * @return BigInteger[] que conforma la clave publica.
     */
    public BigInteger[] getClavePublica(Rsa rsa) {
        try {
            oos.writeObject(rsa.getPublicKey());
            oos.flush();
            System.out.println("Clave Publica propia enviada.");
            
            BigInteger[] clavePublica = (BigInteger[]) ois.readObject();
            System.out.println("Clave Publica del Server recibida.");
            
            return clavePublica;
        }catch(IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }finally {
            finConexion();
        }
        
        return null;
    }
}
