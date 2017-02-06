/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

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
     * Envio de un Fichero desde el Cliente al Server.
     * @param ip IP a la que nos conectamos.
     * @param puerto Puerto por el cual nos conectamos.
     * @param rutaServer Ruta del server donde queremos el fichero.
     * @param rutaLocal Ruta local del cliente donde se encuentra el fichero.
     * @param nombreFich Nombre del fichero a copiar.
     */
    public static void envioFicheroClienteServer(String ip, int puerto, String rutaServer, String rutaLocal, String nombreFich) {
        red.envioFicheroClienteServer(rutaServer, rutaLocal, nombreFich);
    }
    
    /**
     * Recibo de un fichero o envio desde el Server al Cliente.
     * @param ip IP a la que nos conectamos.
     * @param puerto Puerto por el cual nos conectamos.
     * @param rutaServer Ruta del Server donde se encuentra el fichero.
     * @param rutaLocal Ruta local del Cliente donde queremos el fichero.
     * @param nombreFich Nombre del fichero a copiar.
     */
    public static void envioFicheroServerCliente(String ip, int puerto, String rutaServer, String rutaLocal, String nombreFich) {
        red.reciboFicheroServerCliente(rutaServer, rutaLocal, nombreFich);
    }
    
    /**
     * Cerrado de las conexiones abiertas en Red.
     */
    public static void cerradoConexiones() {
        red.cerrarConexiones();
        System.out.println("Cerrado de Conexiones mediante 'Shutdown Hook'.");
    }
}
