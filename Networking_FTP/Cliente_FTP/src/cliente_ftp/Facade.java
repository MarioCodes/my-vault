/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_ftp;

import controlador.Cliente;

/**
 * Patron de diseño Façade. Intermediario entre la vista y el controlador para separar el codigo entre ambos.
 * @author Mario Codes Sánchez
 * @since 19/01/2017
 */
public class Facade {
    /**
     * Abrimos la conexion desde el Cliente al Server.
     * @param ip IP del server a Conectarse.
     * @param puerto Puerto del server por donde entra la conexion.
     */
    public static void abrirConexionCliente(String ip, int puerto) {
        new Cliente(ip, puerto).ejecucion();
    }
}
