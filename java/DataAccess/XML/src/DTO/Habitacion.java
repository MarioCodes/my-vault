/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @version 1.0
 * @author Mario Codes Sánchez
 * @since 28/10/16
 */
public class Habitacion {
    private String id_habitacion = "";
    private String extras_habitacion = "";
    private String tipo_habitacion = "";
    private String reseñas = "";
    private int precio = 0;

    /**
     * Constructor a Utilizar para instanciar una Habitacion.
     * @param id_habitacion ID Primario de la habitacion.
     * @param alojamiento_id_alojamiento ID Foranea que la relaciona con el Alojamiento (Pensando en BDDs).
     * @param extras_habitacion Equipamiento aparte del que dispone.
     * @param tipo_habitacion Simple, Doble, Triple...
     * @param reseñas Reseñas dejadas por el resto de usuarios que la han utilizado.
     * @param precio Precio actual al que se alquila.
     * @param cuarto_baño Si dispone de cuarto de baño o se debe utilizar uno comun (S/N).
     */
    public Habitacion(String id_habitacion, String extras_habitacion, String tipo_habitacion, String reseñas, int precio) {
        this.id_habitacion = id_habitacion;
        this.extras_habitacion = extras_habitacion;
        this.tipo_habitacion = tipo_habitacion;
        this.reseñas = reseñas;
        this.precio = precio;
    }
    
    /**
     * @return the id_habitacion
     */
    public String getId_habitacion() {
        return id_habitacion;
    }

    /**
     * @param id_habitacion the id_habitacion to set
     */
    public void setId_habitacion(String id_habitacion) {
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
     * @return the reseñas
     */
    public String getReseñas() {
        return reseñas;
    }

    /**
     * @param reseñas the reseñas to set
     */
    public void setReseñas(String reseñas) {
        this.reseñas = reseñas;
    }

    /**
     * @return the precio
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("<Habitacion>");
        sb.append("<ID>").append(id_habitacion).append("</ID>");
        sb.append("<Tipo>").append(tipo_habitacion).append("</Tipo>");
        sb.append("<Precio>").append(precio).append("</Precio>");
        sb.append("<Extras>").append(extras_habitacion).append("</Extras>");
        sb.append("<Resenias>").append(reseñas).append("</Resenias>");
        sb.append("</Habitacion>");
        
        return sb.toString();
    }
}
