/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.DAO;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Patron de diseño Factory junto a Singleton Pattern. Clase 'Factory' que se encarga de instanciar nuevas clases si no han
 *  sido instanciadas previamente, almacena esta instancia en un HashMap y la devuelve. A partir del segundo llamado, busca dentro
 *  del HashMap y en vez de instanciarla de nuevo, devuelve siempre la misma instancia creada; De esta forma evitamos tener
 *  demasiadas instancias innecesarias de una misma clase sin puntero.
 * 
 * Utiliza el archivo de '.properties' que esta en este mismo paquete para elegir que subclaseDAO instanciamos y utilizamos,
 *  segun estemos conectados a MySQL u Oracle.
 * @author Mario Codes Sánchez
 * @since 26/11/2016
 */
public class DAOFactory {
    private final static HashMap <String, Object> INSTANCIAS = new HashMap<>(); //<Key, Valor> //Almacen para guardar las clases que se han instanciado alguna vez.
    
    private DAOFactory() {} //Al ser puramente estatica, no quiero que se puedan hacer instancias.
    
    /**
     * Se le pasa un nombre de subclase de Alojamiento/HabitacionDAO, busca en el .properties la ruta de esta (la instancia 
     *  si no lo ha sido ya previamente) y la devuelve.
     * @param objName Nombre de la clase que queremos obtener. Debe ser exactamente igual a como esta en 'factory.properties'.
     * @return Instancia de la clase que hemos pedido.
     */
    public static Object getInstancia(String objName) {
        try {
            Object obj = INSTANCIAS.get(objName);
            
            if(obj == null) {
                ResourceBundle rb = ResourceBundle.getBundle("controlador.DAO.factory");
                String sClassname = rb.getString(objName);
                obj = Class.forName(sClassname).newInstance();
                
                INSTANCIAS.put(objName, obj);
            }
            return obj;
        }catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
