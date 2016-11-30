/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_acceso_1diciembre;

import aplicacion.facade.Facade;
import controlador.datos.EjecucionJSON;
import java.io.IOException;
import vista.swing.comun.SingletonVentanas;

/**
 * Segundo Proyecto para Angel (Acceso a Datos).
 * ATENCION! - Revisar readme del proyecto antes de ejecutar esto. En el estan descritos los pasos previos necesarios.
 *  Nota: La primera vez que se ejecute el modo de JSON, si el fichero no esta creado, se necesitara de conectividad a la BDD de MySQL. A partir de esta primera ejecucion o si el fichero
 *          se ha copiado de forma externa, se podra trabajar de modo offline sin ser necesario ningun tipo de conexion con la Base De Datos.
 * @author Mario Codes Sánchez
 * @since 28/11/2016
 * @version 0.5 Comenzando a arreglar el estropicio de JSON.
 * 
 * Arreglos:
 *  Revisar las operaciones de delete en Oraclce, da fallo de clave.
 *  Cambiar los like para que funcionen bien ([...]like '%ab%').
 *  Cambiar toda la parte de JSON para que opere como deba mediante php y no sobre un fichero como ahora.
 */
public class Proyecto_Acceso_1Diciembre {

    /*
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        SingletonVentanas.getVentanaModoEjecucionObtencionSingleton();
    }
}