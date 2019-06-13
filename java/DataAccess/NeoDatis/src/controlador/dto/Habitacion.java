package controlador.dto;
// Generated 31-ene-2017 13:00:46 by Hibernate Tools 4.3.1

/**
 * Habitacion DTO generado por Hibernate.
 */
public class Habitacion  implements java.io.Serializable {
    private String numero;
    private String extrasHabitacion;
    private long precio;
    private String tipoHabitacion;
    private String resenias;
    private Alojamiento alojamiento;
    
    public Habitacion() {
    }

	
    public Habitacion(String idHabitacion, Alojamiento alojamiento) {
        this.numero = idHabitacion;
        this.alojamiento = alojamiento;
    }
    public Habitacion(String idHabitacion, String extrasHabitacion, long precio, String tipoHabitacion, String resenias, Alojamiento alojamiento) {
       this.numero = idHabitacion;
       this.extrasHabitacion = extrasHabitacion;
       this.precio = precio;
       this.tipoHabitacion = tipoHabitacion;
       this.resenias = resenias;
       this.alojamiento = alojamiento;
    }
   
    public String getNumero() {
        return this.numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getExtrasHabitacion() {
        return this.extrasHabitacion;
    }
    
    public void setExtrasHabitacion(String extrasHabitacion) {
        this.extrasHabitacion = extrasHabitacion;
    }
    public long getPrecio() {
        return this.precio;
    }
    
    public void setPrecio(long precio) {
        this.precio = precio;
    }

    public String getTipoHabitacion() {
        return this.tipoHabitacion;
    }
    
    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }
    public String getResenias() {
        return this.resenias;
    }
    
    public void setResenias(String resenias) {
        this.resenias = resenias;
    }

    /**
     * @return the alojamiento
     */
    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    /**
     * @param alojamiento the alojamiento to set
     */
    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
    }
}


