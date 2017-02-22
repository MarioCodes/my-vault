/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.datos;

import controlador.dto.Alojamiento;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

/**
 * Clase funcional que recopila todo lo necesario para operar mediante NeoDatis.
 * @author Mario Codes Sánchez
 * @since 22/02/2017
 */
public class NeoDatis {
    private static String database = "neodatis.alojamientos";
    
    /**
     * Obtenemos los objetos Alojamiento de la BDD.
     * @return Objects con los Alojamientos dentro de la BDD.
     */
    public static Objects<Alojamiento> getAlojamientos() {
        ODB odb = ODBFactory.open(database);
        Objects<Alojamiento> objects = odb.getObjects(Alojamiento.class);
        odb.close();
        return objects;
    }
    
    /**
     * Obtencion de los Alojamiento de la BDD con una valoracion SUPERIOR a la indicada.
     * @param valoracion Valoracion minima del Alojamiento.
     * @return Objects de NeoDatis con todos los Alojamiento que tienen una valoracion superior a la indicada.
     */
    public static Objects<Alojamiento> getAlojamientos(int valoracion) {
        ODB odb = ODBFactory.open(database);
        Objects<Alojamiento> objects = odb.getObjects(Alojamiento.class);
        
        while(objects.hasNext()) { //Quitamos de la lista los Alojamientos que no cumplen con la valoracion minima.
            Alojamiento a = (Alojamiento) objects.next();
            if(a.getValoracion() < valoracion) {
                objects.remove(a);
            }
        }
        objects.reset();
        
        odb.close();
        return objects;
    }
    
    /**
     * Insert del Alojamiento pasado como parametro.
     * @param alojamiento Alojamiento a insertar.
     */
    public static void insert(Alojamiento alojamiento) {
        ODB odb = ODBFactory.open(database);
        odb.store(alojamiento);
        odb.close();
        System.out.println("Insert realizado.");
    }
}
