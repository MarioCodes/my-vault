/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.datos;

import vista.swing.comun.VentanaPrincipal;

/**
 * Aplicacion del Patron de disenio 'Singleton' para tener una unica instancia de las ventanas de menu que vamos a utilizar varias veces en el programa.
 *  Este patron se encuentra explicado en la clase 'controlador.DAO.DAOFactory'.
 * @author Mario Codes Sánchez.
 * @since 21/11/2016
 */
public class Singleton {
    private static VentanaPrincipal vp;
    
    /**
     * Obtencion de instancia unica de 'VentanaPrincipal'.
     * @return Instancia de 'VentanaPrincipal'.
     */
    public static VentanaPrincipal getVentanaPrincipalObtencionSingleton() {
        if(vp == null) vp = new VentanaPrincipal();
        return vp;
    }
}
