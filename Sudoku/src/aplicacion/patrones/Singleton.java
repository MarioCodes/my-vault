/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.patrones;

import aplicacion.controlador.tablero.Tablero;
import java.util.HashMap;

/**
 * Patron de disenio Singleton. Para tener una unica instancia de clases que iremos pasando por el programa.
 * @author Mario Codes Sánchez
 * @since 12/12/2016
 */
public class Singleton {
    private final static HashMap<String, Object> INSTANCIAS = new HashMap();
    
    /**
     * Generacion del tablero una unica vez. A partir de entonces iremos pasando siempre ese tablero por el programa.
     * @return Tablero generado correctamente.
     */
    public static Tablero getTableroSingleton() {
        Tablero tablero = (Tablero) INSTANCIAS.get("Tablero");
        if(tablero == null) {
            boolean generacionCorrecta = false;
        
            while(!generacionCorrecta) { //Para descartar los casos que no se puedan completar.
                tablero = new Tablero();
                generacionCorrecta = tablero.asignacionNumeroCasillas();
            }
            
            INSTANCIAS.put("Tablero", tablero);
        }
        
        return tablero;
    }
    
    /**
     * Generacion de un tablero nuevo para comenzar una nueva partida.
     * @return Nuevo tablero generado correctamente.
     */
    public static Tablero getTableroNuevoSingleton() {
        INSTANCIAS.remove("Tablero");
        return getTableroSingleton();
    }
    
    /**
     * Generacion del Facade una sola vez.
     * @return Facade instanciado.
     */
    public static Facade getFacadeSingleton() {
        Facade facade = (Facade) INSTANCIAS.get("Facade");
        
        if(facade == null) {
            facade = new Facade();
            INSTANCIAS.put("Facade", facade);
        }
        
        return facade;
    }
}
