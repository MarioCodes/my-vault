/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.controlador;

import comun.Rsa;
import java.math.BigInteger;

/**
 * Patron de diseño Façade. Intermediario entre la vista y el controlador para separar el codigo entre ambos.
 * @author Mario Codes Sánchez
 * @since 06/02/2017
 */
public class Facade {
    private static Red red = null; //Propiedades de la conexion para utilizar de aqui en adelante.
    
    /**
     * Comprueba si se puede establecer conexion mediante la IP y Puertos pasados como parametros. Instancia esta conexion para utilizarla de aqui en adelante.
     * @param ip IP del server a Conectarse.
     * @param puerto Puerto del server por donde entra la conexion.
     * @return Estado de la conexion.
     */
    public static boolean abrirConexionCliente(String ip, int puerto) {
        red = new Red(ip, puerto);
        Runtime.getRuntime().addShutdownHook(new NetShutdownHook());
        return red.checkConexion();
    }
    
    /**
     * Obtencion de la clave publica del server y envio de la privada.
     * @param clavePublica Clase estatica de donde obtengo la clave publica propia.
     * @return BigInteger[] que conforma la clave publica propia del server.
     */
    public static BigInteger[] intercambioClaves(Rsa rsa, BigInteger[] clavePublica) {
        return red.intercambioClaves(rsa, clavePublica);
    }
    
    /**
     * Envio de un Fichero desde el Cliente al Server.
     * @param ip IP a la que nos conectamos.
     * @param puerto Puerto por el cual nos conectamos.
     * @param rutaServer Ruta del server donde queremos el fichero.
     * @param rutaLocal Ruta local del cliente donde se encuentra el fichero.
     * @param nombreFich Nombre del fichero a copiar.
     */
    public static void envioFicheroClienteServer(Rsa rsa, String ip, int puerto, String rutaServer, String rutaLocal, String nombreFich) {
        red.envioFicheroClienteServer(rsa, rutaServer, rutaLocal, nombreFich);
    }
    
    /**
     * Recibo de un fichero o envio desde el Server al Cliente.
     * @param ip IP a la que nos conectamos.
     * @param puerto Puerto por el cual nos conectamos.
     * @param rutaServer Ruta del Server donde se encuentra el fichero.
     * @param rutaLocal Ruta local del Cliente donde queremos el fichero.
     * @param nombreFich Nombre del fichero a copiar.
     */
    public static void envioFicheroServerCliente(Rsa rsa, String ip, int puerto, String rutaServer, String rutaLocal, String nombreFich) {
        red.reciboFicheroServerCliente(rsa, rutaServer, rutaLocal, nombreFich);
    }
    
    /**
     * Cerrado de las conexiones abiertas en Red.
     */
    public static void cerradoConexiones() {
        red.cerrarConexiones();
        System.out.println("Cerrado de Conexiones mediante 'Shutdown Hook'.");
    }
}
