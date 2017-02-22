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
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 * Clase funcional que recopila todo lo necesario para operar mediante NeoDatis.
 * @author Mario Codes Sánchez
 * @since 22/02/2017
 */
public class NeoDatis {
    private static final String DATABASE = "neodatis.alojamientos";
    
    public static Alojamiento getAlojamiento(String nombre) {
        Alojamiento al = null;
        ODB odb = ODBFactory.open(DATABASE);
        IQuery query = new CriteriaQuery(Alojamiento.class, Where.like("nombre", nombre));
        al = (Alojamiento) odb.getObjects(query).getFirst();
        odb.close();
        return al;
    }
    
    /**
     * Obtenemos los objetos Alojamiento de la BDD.
     * @return Objects con los Alojamientos dentro de la BDD.
     */
    public static Objects<Alojamiento> getAlojamientos() {
        ODB odb = ODBFactory.open(DATABASE);
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
        ODB odb = ODBFactory.open(DATABASE);
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
        ODB odb = ODBFactory.open(DATABASE);
        odb.store(alojamiento);
        odb.close();
        System.out.println("Insert realizado.");
    }
    
    public static void delete(Alojamiento alojamiento) {
        Alojamiento a = null;
        ODB odb = ODBFactory.open(DATABASE);
        IQuery query = new CriteriaQuery(Alojamiento.class, Where.like("nombre", alojamiento.getNombre()));
        a = (Alojamiento) odb.getObjects(query).getFirst();
        odb.delete(a);
        odb.close();
        System.out.printf("Alojamiento %s eliminado.", alojamiento.getNombre());
    }
}
