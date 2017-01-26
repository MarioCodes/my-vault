/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_acceso_1diciembre;

import java.io.IOException;
import vista.swing.comun.SingletonVentanas;

/**
 * Segundo Proyecto para Angel (Acceso a Datos).
 * ATENCION! - Antes de ejecutar el proyecto, copiar los .php con su carpeta dentro del servidor raiz de Apache. Aparte, ejecutar las Queries para rellenar las 2 BDD necesarias (MySQL y Oracle XE).
 * @author Mario Codes Sánchez
 * @since 30/11/2016
 * @version 0.6 Arreglado el estropicio de JSON. Ya funciona como deberia.
 */
public class Proyecto_Acceso_1Diciembre {

    /*
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        SingletonVentanas.getVentanaModoEjecucionObtencionSingleton();
    }
}