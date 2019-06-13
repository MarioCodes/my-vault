/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.datos;

import controlador.dto.Alojamiento;
import controlador.dto.Habitacion;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 * Clase funcional que recopila todo lo necesario para operar mediante NeoDatis.
 * @author Mario Codes Sánchez
 * @since 01/03/2017
 */
public class NeoDatis {
    private static final String DATABASE = "neodatis.alojamientos";
    
    /**
     * Get de todos los Alojamiento existentes en la base de datos.
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
        IQuery query = new CriteriaQuery(Alojamiento.class, Where.ge("valoracion", valoracion));
        Objects<Alojamiento> objects = odb.getObjects(query);
        odb.close();
        return objects;
    }
    
    /**
     * Get de todas las habitaciones de un Alojamiento.
     * @return Objects de NeoDatis conteniendo todas las habitaciones.
     */
    public static Objects<Habitacion> getHabitaciones() {
        ODB odb = ODBFactory.open(DATABASE);
        Objects<Habitacion> objects = odb.getObjects(Habitacion.class);
        odb.close();
        return objects;
    }
    
    /**
     * Get de las habitaciones por tipo que tengan.
     * @param tipo Tipo de la habitacion, simple, doble...
     * @return Objects de NeoDatis conteniendo las habitaciones filtradas por tipo.
     */
    public static Objects<Habitacion> getHabitaciones(String tipo) {
        ODB odb = ODBFactory.open(DATABASE);
        IQuery query = new CriteriaQuery(Habitacion.class, Where.equal("tipoHabitacion", tipo));
        Objects<Habitacion> objects = odb.getObjects(query);
        odb.close();
        return objects;
    }
    
    /**
     * Get de todas las habitaciones con el numero introducido.
     * @param numero Numero que queremos que tengan las habitaciones.
     * @return Objects de NeoDatis conteniendo las habitaciones filtradas por numero.
     */
    public static Objects<Habitacion> getHabitacionesNumero(String numero) {
        ODB odb = ODBFactory.open(DATABASE);
        IQuery query = new CriteriaQuery(Habitacion.class, Where.equal("numero", numero));
        Objects<Habitacion> objects = odb.getObjects(query);
        odb.close();
        return objects;
    }
    
    public static Objects<Habitacion> getHabitacionesAlojamiento(String nombre, String razSoc) {
        ODB odb = ODBFactory.open(DATABASE);
        IQuery query = new CriteriaQuery(Alojamiento.class, Where.and().add(Where.equal("nombre", nombre)).add(Where.like("razonSocial", razSoc)));
        Alojamiento aloja = (Alojamiento) odb.getObjects(query).getFirst();
        
        IQuery query2 = new CriteriaQuery(Habitacion.class, Where.and().add(Where.equal("alojamiento.nombre", aloja.getNombre())).add(Where.equal("alojamiento.razonSocial", aloja.getRazonSocial())));
        Objects<Habitacion> objects = odb.getObjects(query2);
        odb.close();
        
        return objects;
    }
    
    /**
     * Insert del Alojamiento pasado como parametro.
     * @param objeto Alojamiento a insertar.
     */
    public static void insert(Object objeto) {
        ODB odb = ODBFactory.open(DATABASE);
        odb.store(objeto);
        odb.close();
        System.out.println("Insert realizado.");
    }
    
    /**
     * Cargado de datos de el ALojamiento nuevo al original para modificarlo.
     * @param original Alojamiento original de donde obtener los datos.
     * @param nuevo Alojamiento nuevo de donde sacar lso datos.
     */
    private static void cargarDatos(Alojamiento original, Alojamiento nuevo) {
        if(nuevo.getDescripcion() != null) original.setDescripcion(nuevo.getDescripcion());
        if(nuevo.getDireccionSocial() != null) original.setDireccionSocial(nuevo.getDireccionSocial());
        if(nuevo.getFechaApertura() != null) original.setFechaApertura(nuevo.getFechaApertura());
        original.setNumHabitaciones(nuevo.getNumHabitaciones());
        if(nuevo.getProvincia() != null) original.setProvincia(nuevo.getProvincia());
        if(nuevo.getRazonSocial() != null) original.setRazonSocial(nuevo.getRazonSocial());
        if(nuevo.getTelefonoContacto() != null) original.setTelefonoContacto(nuevo.getTelefonoContacto());
        original.setValoracion(nuevo.getValoracion());
    }
    
    /**
     * Update de los datos de un Alojamiento ya existente.
     * Obtenemos el anterior mediante el nombre del nuevo, y el resto de datos los sobreescribimos.
     * @param nuevo Alojamiento con los nuevos datos.
     * @return Booleano con el resultado de la operacion.
     */
    public static boolean updateAlojamiento(Alojamiento nuevo) {
        ODB odb = ODBFactory.open(DATABASE);
        IQuery query = new CriteriaQuery(Alojamiento.class, Where.and().add(Where.equal("nombre", nuevo.getNombre())).add(Where.equal("razonSocial", nuevo.getRazonSocial())));
        
        try {
            Alojamiento original = (Alojamiento) odb.getObjects(query).getFirst();

            cargarDatos(original, nuevo);

            odb.store(original);
            odb.commit();
            odb.close();
            return true;
        }catch(IndexOutOfBoundsException ex) {
            System.out.println("Ahora mismo no se puede cambiar el nombre del Alojamiento. (Fallo en Update): " +ex.getLocalizedMessage());
        }
        
        odb.close();
        return false;
    }

    /**
     * @return the DATABASE
     */
    public static String getDATABASE() {
        return DATABASE;
    }
}
