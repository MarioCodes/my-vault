/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import comun.Rsa;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Recopilacion de la implementacion logica del Server. Version mejorada mediante la implementacion de Criptografia Asimetrica.
 * ¡ATENCION! -> Por como opera la forma de mapear el Server desde el Cliente, los 2 se deben encontrar en la misma Carpeta Base. Intente enviar el mapeo por red pero me daba una serie de problemas que no me daba tiempo a solucionar.
 *  El servidor opera de forma multihilo para no esperar con cada conexion.
 * @author Mario Codes Sánchez
 * @since 24/02/2017
 */
public class Servidor {
    private static final String firma = "Server";
    private static final Rsa RSA = new Rsa(256);
    
    private static final int BUFFER_LENGTH = 128; //Tamaño del buffer que se enviara de golpe.
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
     * Desencripta los datos recibidos y los almacena en un fichero.
     * @param rutaFichero Ruta donde creamos el fichero.
     * @param nombreFichero Nombre con el cual guardamos el fichero.
     */
    private static void inputFichero(String rutaFichero, String nombreFichero) {
        try {
            out = new FileOutputStream(rutaFichero +nombreFichero);
            
            try {
                BigInteger recibido;
                while("1".equals(RSA.decryptMensaje(ois.readUTF()))) {
                    recibido = (BigInteger) ois.readObject();
                    byte[] array = RSA.decrypt(recibido).toByteArray();
                    out.write(array, 1, array.length - 1);
                    
                    BigInteger biFirmado = (BigInteger) ois.readObject();
                    String firma = RSA.desfirmar(biFirmado);
//                    String firma = RSA.desencriptar(biFirmado); //Testing, sin la clave no se reconoce la firma.
                    System.out.println("Mensaje firmado por: " +firma);
                }
            }catch(EOFException ex) {
                System.out.println("EOF Exception. Es normal: " +ex.getLocalizedMessage());
            }
            out.flush();
            
            
        }catch(IOException|ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Recogida de los parametros necesarios para recibir un fichero y paso a recibo del mismo.
     * Estos se encuentra encriptados.
     */
    private static void getParametrosInput() {
        try {
            String rutaFichero = null;
            BigInteger biCifrado = (BigInteger) ois.readObject();
            rutaFichero = RSA.desencriptar(biCifrado);
            
            String nombreFichero = null;
            biCifrado = (BigInteger) ois.readObject();
            nombreFichero = RSA.desencriptar(biCifrado);
            
            inputFichero(rutaFichero, nombreFichero);
        }catch(IOException|ClassNotFoundException ex) {
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
            BigInteger cifrado = null;
            while((count = in.read(bytes)) > 0) {
                oos.writeUTF(RSA.encryptMensajes(RSA, "1", RSA.getCLAVE_AJENA_PUBLICA()));
                byte[] arrayAux = new byte[count+1];
                for (int i = 0; i < arrayAux.length; i++) {
                    if(i == 0) arrayAux[i] = 1;
                    else arrayAux[i] = bytes[i-1];
                }
                
                cifrado = RSA.encrypt(new BigInteger(arrayAux), RSA.getCLAVE_AJENA_PUBLICA());
                oos.writeObject(cifrado);
                oos.flush();
                
                BigInteger firmaCifrada = RSA.firmar(firma);
                oos.writeObject(firmaCifrada);
                oos.flush();
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Chequeo de conexion mejorado a las versiones previas.
     * Ahora tambien se utiliza para recibir la clave publica del cliente y mandar la propia.
     */
    private static void intercambioClaves() {
        try {
            oos.writeBoolean(true);
            oos.flush();
            System.out.println("Booleano enviado.");
            
            try {
                RSA.setCLAVE_AJENA_PUBLICA((BigInteger[]) ois.readObject());
                System.out.println("Clave Publica del Cliente recibida.");
                
                oos.writeObject(RSA.getCLAVE_PROPIA_PUBLICA());
                oos.flush();
                System.out.println("Clave Publica Propia enviada");
            }catch(ClassNotFoundException ex) {
                ex.printStackTrace();
            }
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
                    intercambioClaves();
                    break;
                case 1: //Mapeo del Server y envio de esta informacion al Cliente. Deprecated, me da demasiados problemas y no tengo tiempo.
                    //envioMapeoCliente(mapearServer());
                    break;
                case 2: //Recibir fichero.
                    getParametrosInput();
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
            System.out.println("Servidor Simetrico Firmado.");
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
    
    /**
     * Test funcional temporal para encriptar y recuperar el mensaje. Trasladarlo a Cliente y Servidor.
     * todo: borrar antes de entregar.
     */
    private static void testEncriptar() {
        String mensaje = "Hola Mundo";
        System.out.println("\nMensaje Original: " +mensaje);
        
        byte[] bytes = mensaje.getBytes();
        System.out.println("Bytes del mensaje Original: " +bytes);
        
        BigInteger biNormal = new BigInteger(bytes);
        System.out.println("BigInteger del mensaje Original: " +biNormal);
        
        BigInteger cifrado = RSA.encriptarPropia(biNormal);
        System.out.println("\nBigInteger cifrado: " +cifrado);
        
        byte[] bytesCifrados = cifrado.toByteArray(); //Esto es lo que pasaria si se interceptara sin la clave privada.
        System.out.println("Bytes cifrados: " +bytesCifrados);
        
        String bufferCifrado = "";
        for(byte b: bytesCifrados) {
            bufferCifrado += (char) b;
        }
        System.out.println("Mensaje Cifrado: " +bufferCifrado);
        
        BigInteger descifrado = RSA.desencriptarPropia(cifrado);
        System.out.println("\nBigInteger descifrado: " +descifrado);
        
        byte[] bytesDescifrados = descifrado.toByteArray();
        System.out.println("Bytes descifrados: " +bytesDescifrados);
        
        String bufferDescifrado = "";
        for(byte b: bytesDescifrados) {
            bufferDescifrado += (char) b;
        }
        System.out.println("Mensaje Descifrado: " +bufferDescifrado);
    }
}
