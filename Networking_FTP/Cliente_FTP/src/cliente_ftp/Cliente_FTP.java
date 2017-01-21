/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_ftp;

import controlador.Cliente;
import vista.MainWindow;

/**
 * Implementacion al proyecto de creacion de un Cliente-Servidor FTP - Parte Cliente.
 * @author Mario Codes Sánchez
 * @since 19/01/2017
 */
public class Cliente_FTP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Cliente("127.0.0.1", 8142).ejecucion();
//        new MainWindow();
    }
    
}
