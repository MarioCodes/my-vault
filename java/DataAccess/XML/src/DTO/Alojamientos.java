/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

//import XML.DocumentoXML;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @version 1.0
 * @author Mario Codes Sánchez
 * @since 28/10/16
 */
public class Alojamientos {
    private static ArrayList <Alojamiento> arrayListAlojamientos; //ArrayList con la cual se trabajara de forma interna. En ella meto los datos del XML y con ella los exporto de nuevo.

    /**
     * Vacia la ArrayList interna y la vuelve a cargar desde el XML.
     */
    public static void cargadoArrayListDeNuevo() {
        arrayListAlojamientos.removeAll(arrayListAlojamientos);
//        DocumentoXML.lecturaFicheroAArrayListInterna(ProyectoAccesoXML.file_path);
    }
    
    /**
     * Pilla todos los datos que contiene el Alojamiento (inc. Habitaciones) y los almacena en una ArrayList ya preparados para mostrar.
     * @return ArrayList. Strings con el contenido de la ArrayList Interna, preparado para mostrarlo por pantalla.
     */
    public static ArrayList obtencionDatosArray() {
        Iterator it = arrayListAlojamientos.iterator(); //Iterator con la ArrayList principal.
        ArrayList arrayListTextoOutput = new ArrayList(); //ArrayList a devolver.
        
        while(it.hasNext()) {
            Alojamiento alTmp = (Alojamiento) it.next();
            Iterator itHab = alTmp.getArrayListHabitaciones().iterator();
            
            //Añadido de los datos intrinsecos de alojamiento.
            arrayListTextoOutput.add("Alojamiento:" 
                    +"\nNombre: " +alTmp.getNombre() 
                    +"\nTelefono: " +alTmp.getTelefono() 
                    +"\nDireccion Social: " +alTmp.getDireccionSocial() 
                    +"\nRazon Social: " +alTmp.getRazonSocial() 
                    +"\nDescripcion: " +alTmp.getDescripcion() 
                    +"\nValoracion: " +alTmp.getValoracion() 
                    +"\nFecha Apertura: " +alTmp.getFechaApertura() 
                    +"\nNumero de Habitaciones: " +alTmp.getNum_habitaciones() 
                    +"\nProvincia: " +alTmp.getProvincia() +"\n");
            
            //Añadido de los datos de CADA habitacion que contiene ESE alojamiento en particular.
            while(itHab.hasNext()) {
                Habitacion habTmp = (Habitacion) itHab.next();
                
                arrayListTextoOutput.add("\tHabitacion #" +habTmp.getId_habitacion()
                        +"\n\tTipo Habitacion: " +habTmp.getTipo_habitacion() 
                        +"\n\tPrecio: " +habTmp.getPrecio() 
                        +"\n\tExtras Habitacion: " +habTmp.getExtras_habitacion() 
                        +"\n\tReseñas: " +habTmp.getReseñas());
            }
        }
        
        return arrayListTextoOutput;
    }
    
    /**
     * @return the arrayListAlojamientos
     */
    public static ArrayList <Alojamiento> getArrayListAlojamientos() {
        return arrayListAlojamientos;
    }

    /**
     * @param aArrayListAlojamientos the arrayListAlojamientos to set
     */
    public static void setArrayListAlojamientos(ArrayList <Alojamiento> aArrayListAlojamientos) {
        arrayListAlojamientos = aArrayListAlojamientos;
    }
}
