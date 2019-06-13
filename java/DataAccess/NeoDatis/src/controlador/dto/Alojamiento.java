package controlador.dto;
// Generated 31-ene-2017 13:00:46 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * Alojamiento's DTO done by Hibernate.
 */
public class Alojamiento  implements java.io.Serializable {
    private String nombre;
    private String direccionSocial;
    private String razonSocial;
    private String telefonoContacto;
    private String descripcion;
    private int valoracion;
    private String fechaApertura;
    private Integer numHabitaciones;
    private String provincia;
    private Set<Habitacion> setHabitaciones;

    public Alojamiento(String nombre, String razonSocial, String fechaApertura) {
        this.nombre = nombre;
        this.razonSocial = razonSocial;
        this.fechaApertura = fechaApertura;
        setHabitaciones = new HashSet<>();
    }
    
    public Alojamiento(String nombre, String direccionSocial, String razonSocial, String telefonoContacto, String descripcion, int valoracion, String fechaApertura, Integer numHabitaciones, String provincia, Set<Habitacion> setHabitaciones) {
       this.nombre = nombre;
       this.direccionSocial = direccionSocial;
       this.razonSocial = razonSocial;
       this.telefonoContacto = telefonoContacto;
       this.descripcion = descripcion;
       this.valoracion = valoracion;
       this.fechaApertura = fechaApertura;
       this.numHabitaciones = numHabitaciones;
       this.provincia = provincia;
       this.setHabitaciones = setHabitaciones;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccionSocial() {
        return this.direccionSocial;
    }
    
    public void setDireccionSocial(String direccionSocial) {
        this.direccionSocial = direccionSocial;
    }
    public String getRazonSocial() {
        return this.razonSocial;
    }
    
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    public String getTelefonoContacto() {
        return this.telefonoContacto;
    }
    
    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public int getValoracion() {
        return this.valoracion;
    }
    
    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }
    public String getFechaApertura() {
        return this.fechaApertura;
    }
    
    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }
    public Integer getNumHabitaciones() {
        return this.numHabitaciones;
    }
    
    public void setNumHabitaciones(Integer numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }
    public String getProvincia() {
        return this.provincia;
    }
    
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * @return the setHabitaciones
     */
    public Set<Habitacion> getSetHabitaciones() {
        return setHabitaciones;
    }

    /**
     * @param setHabitaciones the setHabitaciones to set
     */
    public void setSetHabitaciones(Set<Habitacion> setHabitaciones) {
        this.setHabitaciones = setHabitaciones;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
}


