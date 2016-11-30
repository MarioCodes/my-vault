/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.DTO;

/**
 * Clase DTO (Data Transfer Object). Objeto que representa a la entidad 'Alojamiento' con todos sus atributos intrinsecos.
 * Simplemente contiene los constructores, un 'override' de .toString() y setters / getters.
 * @author Mario Codes Sánchez
 * @since 11/11/2016
 */
public class AlojamientoDTO {
    private int id;
    private int valoracion;
    private String nombre;
    private String dir_Social;
    private String razon_Social;
    private String telefono_Contacto;
    private String descripcion;
    private String fecha_Apertura;
    private int numero_Habitaciones;
    private String provincia;
    
    /**
     * Constructor completo con todos los datos intrinsecos de un Alojamiento.
     * @param id Id Primaria.
     * @param nombre Nombre por el cual se le conoce.
     * @param telefono Telefono principal de contacto.
     * @param dirSocial Direccion Social.
     * @param razonSocial Razon Social.
     * @param descrip Descripcion breve del Alojamiento.
     * @param valoracion Valoracion media de los clientes.
     * @param fecha Fecha de Construccion del Alojamiento o fundacion de la empresa.
     * @param num_habitaciones Numero de Habitaciones disponibles actualmente.
     * @param provincia Provincia donde se encuentra el Alojamiento.
     */
    public AlojamientoDTO(int id, String nombre, String telefono, String dirSocial, String razonSocial, String descrip, int valoracion, String fecha,
                            int num_habitaciones, String provincia) {
        this.id = id;
        this.nombre = nombre;
        this.telefono_Contacto = telefono;
        this.dir_Social = dirSocial;
        this.razon_Social = razonSocial;
        this.descripcion = descrip;
        this.valoracion = valoracion;
        this.fecha_Apertura = fecha;
        this.numero_Habitaciones = num_habitaciones;
        this.provincia = provincia;
    }
    
    /**
     * Constructor con todos los datos de Alojamiento menos el ID. 
     * Version para hacer alta de un Alojamiento (este se metera de forma AutoIncremental o mediante Secuencias).
     * @param nombre Nombre por el cual se le conoce.
     * @param telefono Telefono principal de contacto.
     * @param dirSocial Direccion Social.
     * @param razonSocial Razon Social.
     * @param descrip Descripcion breve del Alojamiento.
     * @param valoracion Valoracion media de los clientes.
     * @param fecha Fecha de Construccion del Alojamiento o fundacion de la empresa.
     * @param num_habitaciones Numero de Habitaciones disponibles actualmente.
     * @param provincia Provincia donde se encuentra el Alojamiento.
     */
    public AlojamientoDTO(String nombre, String telefono, String dirSocial, String razonSocial, String descrip, int valoracion, String fecha,
                            int num_habitaciones, String provincia) {
        this.nombre = nombre;
        this.telefono_Contacto = telefono;
        this.dir_Social = dirSocial;
        this.razon_Social = razonSocial;
        this.descripcion = descrip;
        this.valoracion = valoracion;
        this.fecha_Apertura = fecha;
        this.numero_Habitaciones = num_habitaciones;
        this.provincia = provincia;
    }
    
    /**
     * Constructor vacio de Alojamiento. Se deberan meter los datos a mano mediante getters / setters.
     */
    public AlojamientoDTO() {
    }
    
    /**
     * Comportamiento de la clase al hacer un 'sout' directo de ella misma. Mostrara esto en vez de la direccion de memoria.
     *  Lo utilizo a la vez que desarrollo para ir comprobando que los Objetos AlojamientoDTO se estan rellenando con los datos
     *      de manera correcta.
     * @return Manera de representar los datos cuando hagamos un 'sout' directo.
     */
    @Override
    public String toString() {
        return ("Alojamiento #" +id +", nombre: " +nombre +", dirSocial: " +dir_Social +", razonSocial: " +razon_Social +", descrip: " +descripcion +", valoracion: " +valoracion 
                +", fechaApertura: " +fecha_Apertura +", numHabitaciones: " +numero_Habitaciones +", provincia: " +provincia);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the valoracion
     */
    public int getValoracion() {
        return valoracion;
    }

    /**
     * @param valoracion the valoracion to set
     */
    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the dir_Social
     */
    public String getDir_Social() {
        return dir_Social;
    }

    /**
     * @param dir_Social the dir_Social to set
     */
    public void setDir_Social(String dir_Social) {
        this.dir_Social = dir_Social;
    }

    /**
     * @return the razon_Social
     */
    public String getRazon_Social() {
        return razon_Social;
    }

    /**
     * @param razon_Social the razon_Social to set
     */
    public void setRazon_Social(String razon_Social) {
        this.razon_Social = razon_Social;
    }

    /**
     * @return the telefono_Contacto
     */
    public String getTelefono_Contacto() {
        return telefono_Contacto;
    }

    /**
     * @param telefono_Contacto the telefono_Contacto to set
     */
    public void setTelefono_Contacto(String telefono_Contacto) {
        this.telefono_Contacto = telefono_Contacto;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the fecha_Apertura
     */
    public String getFecha_Apertura() {
        return fecha_Apertura;
    }

    /**
     * @param fecha_Apertura the fecha_Apertura to set
     */
    public void setFecha_Apertura(String fecha_Apertura) {
        this.fecha_Apertura = fecha_Apertura;
    }

    /**
     * @return the numero_Habitaciones
     */
    public int getNumero_Habitaciones() {
        return numero_Habitaciones;
    }

    /**
     * @param numero_Habitaciones the numero_Habitaciones to set
     */
    public void setNumero_Habitaciones(int numero_Habitaciones) {
        this.numero_Habitaciones = numero_Habitaciones;
    }

    /**
     * @return the provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * @param provincia the provincia to set
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
