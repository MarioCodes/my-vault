/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * @version 1.0
 * @author Mario Codes Sánchez
 * @since 28/10/16
 */
public class Alojamiento {
    private String nombre;
    private String telefono;
    private String direccionSocial;
    private String razonSocial;
    private String descripcion;
    private int valoracion;
    private String fechaApertura;
    private int num_habitaciones;
    private String provincia;
    private ArrayList <Habitacion> arrayListHabitaciones;
    
    /**
     * Nuevo constructor. Incluye la arrayList de Habitaciones para poder meterlas de forma directa en cada 'Alojamiento'.
     * @param id ID intrinseco del Alojamiento.
     * @param arrayListHabitaciones ArrayList que contiene las Habitaciones que pertenecen a cada Alojamiento.
     * @param nombre Nombre del Alojamiento.
     * @param telefono Telefono de contacto del alojamiento.
     * @param direccionSocial Direccion Social.
     * @param razonSocial Razon Social.
     * @param descripcion Descripcion.
     * @param valoracion Valoracion media de los usuarios.
     * @param fechaApertura Date en la cual se inauguro el alojamiento.
     * @param num_habitaciones Numero de habitaciones TEORICO que tiene cada habitacion.
     * @param provincia Provincia donde esta localizado el Alojamiento.
     */
    public Alojamiento(ArrayList <Habitacion> arrayListHabitaciones, String nombre, String telefono, String direccionSocial, String razonSocial, String descripcion, int valoracion, String fechaApertura, int num_habitaciones, String provincia) {
        this.arrayListHabitaciones = arrayListHabitaciones;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccionSocial = direccionSocial;
        this.razonSocial = razonSocial;
        this.descripcion = descripcion;
        this.valoracion = valoracion;
        this.fechaApertura = fechaApertura;
        this.num_habitaciones = num_habitaciones;
        this.provincia = provincia;
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
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the direccionSocial
     */
    public String getDireccionSocial() {
        return direccionSocial;
    }

    /**
     * @param direccionSocial the direccionSocial to set
     */
    public void setDireccionSocial(String direccionSocial) {
        this.direccionSocial = direccionSocial;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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
     * @return the fechaApertura
     */
    public String getFechaApertura() {
        return fechaApertura;
    }

    /**
     * @param fechaApertura the fechaApertura to set
     */
    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    /**
     * @return the num_habitaciones
     */
    public int getNum_habitaciones() {
        return num_habitaciones;
    }

    /**
     * @param num_habitaciones the num_habitaciones to set
     */
    public void setNum_habitaciones(int num_habitaciones) {
        this.num_habitaciones = num_habitaciones;
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

    /**
     * @return the arrayListHabitaciones
     */
    public ArrayList <Habitacion> getArrayListHabitaciones() {
        return arrayListHabitaciones;
    }

    /**
     * @param arrayListHabitaciones the arrayListHabitaciones to set
     */
    public void setArrayListHabitaciones(ArrayList <Habitacion> arrayListHabitaciones) {
        this.arrayListHabitaciones = arrayListHabitaciones;
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        
        sb.append("<Alojamiento>");
        sb.append("<Nombre>").append(nombre).append("</Nombre>");
        sb.append("<Telefono>").append(telefono).append("</Telefono>");
        sb.append("<Razon_Social>").append(razonSocial).append("</Razon_Social>");
        sb.append("<Dir_Social>").append(direccionSocial).append("</Dir_Social>");
        sb.append("<Descripcion>").append(descripcion).append("</Descripcion>");
        sb.append("<Valoracion>").append(valoracion).append("</Valoracion>");
        sb.append("<Fecha_Apertura>").append(fechaApertura).append("</Fecha_Apertura>");
        sb.append("<Num_Habitaciones>").append(num_habitaciones).append("</Num_Habitaciones>");
        sb.append("<Provincia>").append(provincia).append("</Provincia>");

        
        if(arrayListHabitaciones.size()>0) {
        sb.append("<Habitaciones>");        
            for(Habitacion h: arrayListHabitaciones) sb.append(h);
        sb.append("</Habitaciones>");
        } else sb.append("<Habitaciones/>");
        
        sb.append("</Alojamiento>");
        return sb.toString();
    }
}
