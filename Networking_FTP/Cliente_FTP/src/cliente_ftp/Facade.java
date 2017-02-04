/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_ftp;

import controlador.Red;

/**
 * Patron de diseño Façade. Intermediario entre la vista y el controlador para separar el codigo entre ambos.
 * @author Mario Codes Sánchez
 * @since 04/02/2017
 */
public class Facade {
    /**
     * Realizacion de un testeo de conexion Cliente - Server.
     * @param ip IP del server a Conectarse.
     * @param puerto Puerto del server por donde entra la conexion.
     * @return Estado de la conexion.
     */
    public static boolean testearConexionCliente(String ip, int puerto) {
        return new Red(ip, puerto).comprobacionConexion();
    }
    
    /**
     * Abrimos la conexion desde el Red al Server.
     * @param ip IP del server a Conectarse.
     * @param puerto Puerto del server por donde entra la conexion.
     * @param nombreFich Nombre del fichero a enviar
     */
    public static void envioFicheroClienteServer(String ip, int puerto, String nombreFich) {
        new Red(ip, puerto).envioFichero(nombreFich);
    }
}
