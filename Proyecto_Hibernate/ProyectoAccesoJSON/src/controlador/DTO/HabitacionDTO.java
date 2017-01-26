/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.DTO;

/**
 * Clase DTO (Data Transfer Object) de la entidad 'Habitacion'. Analoga a 'AlojamientoDTO'.
 * @author Mario Codes Sánchez.
 * @since 11/11/2016
 */
public class HabitacionDTO {
    private int id_habitacion;
    private String extras_habitacion;
    private float precio;
    private boolean cuarto_banio;
    private String tipo_habitacion;
    private String resenias;
    private int habitacion_id_foranea_alojamiento;

    /**
     * Constructor completo. Contiene todos los atributos posibles de una Habitación.
     * @param idHabitacion ID Primaria de Habitacion.
     * @param extrasHabitacion Extras con los que cuenta la habitación.
     * @param precio Precio por noche.
     * @param cuartoBanio Si dispone de cuarto de banio privado o debe usar uno comun.
     * @param tipoHabitacion Tipo de habitacion que es.
     * @param resenias Reseñas o posibles quejas / mejoras a implementar de anteriores clientes.
     * @param habitacionIdForaneaAlojamiento Clave foranea Habitacion - Alojamiento. ID del Alojamiento al que pertenece la Habitacion.
     */
    public HabitacionDTO(int idHabitacion, String extrasHabitacion, float precio, boolean cuartoBanio, String tipoHabitacion, String resenias, int habitacionIdForaneaAlojamiento) {
        this.id_habitacion = idHabitacion;
        this.extras_habitacion = extrasHabitacion;
        this.precio = precio;
        this.cuarto_banio = cuartoBanio;
        this.tipo_habitacion = tipoHabitacion;
        this.resenias = resenias;
        this.habitacion_id_foranea_alojamiento = habitacionIdForaneaAlojamiento;
    }
    
    /**
     * Constructor con to.do menos el ID. 
     * Se metera de forma automatica en la BDD con AutoIncrementales o Secuencias.
     * @param extrasHabitacion Extras con los que cuenta la habitación.
     * @param precio Precio por noche.
     * @param cuartoBanio Si dispone de cuarto de banio privado o debe usar uno comun.
     * @param tipoHabitacion Tipo de habitacion que es.
     * @param resenias Reseñas o posibles quejas / mejoras a implementar de anteriores clientes.
     * @param habitacionIdForaneaAlojamiento Clave foranea Habitacion - Alojamiento. ID del Alojamiento al que pertenece la Habitacion.
     */
    public HabitacionDTO(String extrasHabitacion, float precio, boolean cuartoBanio, String tipoHabitacion, String resenias, int habitacionIdForaneaAlojamiento) {
        this.extras_habitacion = extrasHabitacion;
        this.precio = precio;
        this.cuarto_banio = cuartoBanio;
        this.tipo_habitacion = tipoHabitacion;
        this.resenias = resenias;
        this.habitacion_id_foranea_alojamiento = habitacionIdForaneaAlojamiento;
    }
    
    /**
     * Constructor vacio, se deberan meter los datos a mano.
     */
    public HabitacionDTO() {
    }
    
    @Override
    public String toString() {
        return "Habitacion ID# " +id_habitacion +", clave foranea: " +habitacion_id_foranea_alojamiento;
    }
    
    /**
     * @return the id_habitacion
     */
    public int getId_habitacion() {
        return id_habitacion;
    }

    /**
     * @param id_habitacion the id_habitacion to set
     */
    public void setId_habitacion(int id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    /**
     * @return the extras_habitacion
     */
    public String getExtras_habitacion() {
        return extras_habitacion;
    }

    /**
     * @param extras_habitacion the extras_habitacion to set
     */
    public void setExtras_habitacion(String extras_habitacion) {
        this.extras_habitacion = extras_habitacion;
    }

    /**
     * @return the precio
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     * @return the cuarto_banio
     */
    public boolean isCuarto_banio() {
        return cuarto_banio;
    }

    /**
     * @param cuarto_banio the cuarto_banio to set
     */
    public void setCuarto_banio(boolean cuarto_banio) {
        this.cuarto_banio = cuarto_banio;
    }

    /**
     * @return the tipo_habitacion
     */
    public String getTipo_habitacion() {
        return tipo_habitacion;
    }

    /**
     * @param tipo_habitacion the tipo_habitacion to set
     */
    public void setTipo_habitacion(String tipo_habitacion) {
        this.tipo_habitacion = tipo_habitacion;
    }

    /**
     * @return the resenias
     */
    public String getResenias() {
        return resenias;
    }

    /**
     * @param resenias the resenias to set
     */
    public void setResenias(String resenias) {
        this.resenias = resenias;
    }

    /**
     * @return the habitacion_id_foranea_alojamiento
     */
    public int getHabitacion_id_foranea_alojamiento() {
        return habitacion_id_foranea_alojamiento;
    }

    /**
     * @param habitacion_id_foranea_alojamiento the habitacion_id_foranea_alojamiento to set
     */
    public void setHabitacion_id_foranea_alojamiento(int habitacion_id_foranea_alojamiento) {
        this.habitacion_id_foranea_alojamiento = habitacion_id_foranea_alojamiento;
    }
}
