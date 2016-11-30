/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.datos;

import controlador.DTO.AlojamientoDTO;
import aplicacion.facade.Facade;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Clase encargada de Obtener JSON a traves del Servidor web y cargar este a Java. Desgrano los .java de Angel cacho a cacho para ver como funciona. 
 * @author Mario Codes Sánchez
 * @since 22/11/2016
 */
public class EjecucionJSON {
    private static final String SHARED_BASE_URL = "http://localhost:8088/ServerWeb/"; //fixme: Acordarme que en casa cambie los puertos de acceso a :8088 por problemas de compatibilidad con VirtualBox.
    
    /**
     * Da de Alta un Alojamiento en la BDD con los datos intrinsecos del Alojamiento pasado como parametro.
     * @param alDTO AlojamientoDTO del que sacamos los datos para introducir en la BDD.
     */
    public void altaAlojamiento(AlojamientoDTO alDTO) {
        try {
            PeticionPost post = new PeticionPost(SHARED_BASE_URL +"altaAlojamiento.php");
            post.add("nombre", alDTO.getNombre());
            post.add("dirSocial", alDTO.getDir_Social());
            post.add("razSoc", alDTO.getRazon_Social());
            post.add("telefono", alDTO.getTelefono_Contacto());
            post.add("desc", alDTO.getDescripcion());
            post.add("valoracion", Integer.toString(alDTO.getValoracion()));
            post.add("fecha", alDTO.getFecha_Apertura());
            post.add("numHabitaciones", Integer.toString(alDTO.getNumero_Habitaciones()));
            post.add("provincia", alDTO.getProvincia());
            post.getRespueta();
        }catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Da de baja el Alojamiento deseado.
     * @param alDTO AlojamientoDTO que deseamos dar de Baja.
     */
    public void bajaAlojamiento(AlojamientoDTO alDTO) {
        try {
            PeticionPost post = new PeticionPost(SHARED_BASE_URL +"bajaAlojamiento.php");
            post.add("id", Integer.toString(alDTO.getId()));
            post.add("nombre", alDTO.getNombre());
            post.getRespueta();
        }catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Se le introduce una ID y devuelve el Alojamiento indicado.
     * @param id ID del Alojamiento que queremos obtener.
     * @return AlojamientoDTO con los datos del ALojamiento que queremos obtener.
     */
    public AlojamientoDTO buscarAlojamientoPorIdJSON(int id) {
        try {
            PeticionPost post = new PeticionPost(SHARED_BASE_URL +"buscarAlojamientoPorID.php");
            post.add("idBuscar", Integer.toString(id));
            JSONArray locs = new JSONArray(post.getRespueta());

            AlojamientoDTO alDTO = null;
            for (int i = 0; i < locs.length(); i++) {
                JSONObject rec = locs.getJSONObject(i);

                int idLocal = rec.getInt("ID_ALOJAMIENTO");
                String nombre = rec.getString("NOMBRE");
                String dirSocial = rec.getString("DIRECCION_SOCIAL");
                String razonSocial = rec.getString("RAZON_SOCIAL");
                String telefono = rec.getString("TELEFONO_CONTACTO");
                String descripcion = rec.getString("DESCRIPCION");
                int valoracion = rec.getInt("VALORACION");
                String fechaApertura = rec.getString("FECHA_APERTURA");
                int numHabitaciones = rec.getInt("NUM_HABITACIONES");
                String provincia = rec.getString("PROVINCIA");

                alDTO = new AlojamientoDTO(idLocal, nombre, telefono, dirSocial, razonSocial, descripcion, valoracion, fechaApertura, numHabitaciones, provincia);
            }
            
            return alDTO;
        }catch(IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Ejecuta el .php y obtiene una JSONArray con todos los alojamientos disponibles.
     * @return Collection de AlojamientoDTO con toda la informacion de la BDD.
     */
    public Collection<AlojamientoDTO> listadoAlojamientosJSON() {
        try {
            PeticionPost post = new PeticionPost(SHARED_BASE_URL +"listadoAlojamientos.php");
            JSONArray locs = new JSONArray(post.getRespueta());
            ArrayList<AlojamientoDTO> arrayListTmpAlojamientos = new ArrayList<>();
            
            for (int i = 0; i < locs.length(); i++) {
                JSONObject rec = locs.getJSONObject(i);

                int id = rec.getInt("ID_ALOJAMIENTO");
                String nombre = rec.getString("NOMBRE");
                String dirSocial = rec.getString("DIRECCION_SOCIAL");
                String razonSocial = rec.getString("RAZON_SOCIAL");
                String telefono = rec.getString("TELEFONO_CONTACTO");
                String descripcion = rec.getString("DESCRIPCION");
                int valoracion = rec.getInt("VALORACION");
                String fechaApertura = rec.getString("FECHA_APERTURA");
                int numHabitaciones = rec.getInt("NUM_HABITACIONES");
                String provincia = rec.getString("PROVINCIA");

                AlojamientoDTO alDTOTmp = new AlojamientoDTO(id, nombre, telefono, dirSocial, razonSocial, descripcion, valoracion, fechaApertura, numHabitaciones, provincia);
                arrayListTmpAlojamientos.add(alDTOTmp);
            }
            
            return arrayListTmpAlojamientos;
        }catch(IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Listado de Alojamientos, version filtradas por Provincia.
     * @param provincia provincia de la que queremos obtener todos los Alojamientos.
     * @return Collection con la informacion JSON transformada.
     */
    public Collection<AlojamientoDTO> listadoAlojamientosJSON(String provincia) {
        try {
            PeticionPost post = new PeticionPost(SHARED_BASE_URL +"listadoAlojamientosFiltrado.php");
            post.add("provincia", provincia);
            JSONArray locs = new JSONArray(post.getRespueta());
            ArrayList<AlojamientoDTO> arrayListTmpAlojamientos = new ArrayList<>();
            
            for (int i = 0; i < locs.length(); i++) {
                JSONObject rec = locs.getJSONObject(i);

                int id = rec.getInt("ID_ALOJAMIENTO");
                String nombre = rec.getString("NOMBRE");
                String dirSocial = rec.getString("DIRECCION_SOCIAL");
                String razonSocial = rec.getString("RAZON_SOCIAL");
                String telefono = rec.getString("TELEFONO_CONTACTO");
                String descripcion = rec.getString("DESCRIPCION");
                int valoracion = rec.getInt("VALORACION");
                String fechaApertura = rec.getString("FECHA_APERTURA");
                int numHabitaciones = rec.getInt("NUM_HABITACIONES");
                String prov = rec.getString("PROVINCIA");

                AlojamientoDTO alDTOTmp = new AlojamientoDTO(id, nombre, telefono, dirSocial, razonSocial, descripcion, valoracion, fechaApertura, numHabitaciones, prov);
                arrayListTmpAlojamientos.add(alDTOTmp);
            }
            
            return arrayListTmpAlojamientos;
        }catch(IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Inner Class pasada por Angel. Como no tiene sentido su existencia sin la clase base contenedora, lo meto aqui.
     */
    private class PeticionPost {
	private URL url;
	private String data;

        public PeticionPost(String url) throws MalformedURLException{
            this.url = new URL(url);
            data="";
        }

        public void add(String propiedad, String valor) throws UnsupportedEncodingException {
            //codificamos cada uno de los valores
            if (data.length()>0)
            data+= "&"+ URLEncoder.encode(propiedad, "UTF-8")+ "=" +URLEncoder.encode(valor, "UTF-8");
            else
            data+= URLEncoder.encode(propiedad, "UTF-8")+ "=" +URLEncoder.encode(valor, "UTF-8");
        }

        public String getRespueta() throws IOException {
            String respuesta = "";
            //abrimos la conexiÃ³n
            URLConnection conn = url.openConnection();
            //especificamos que vamos a escribir
            conn.setDoOutput(true);
            //obtenemos el flujo de escritura
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            //escribimos
            wr.write(data);
            //cerramos la conexiÃ³n
              wr.close();

            //obtenemos el flujo de lectura
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String linea;
            //procesamos al salida
            while ((linea = rd.readLine()) != null) {
               respuesta+= linea;
            }
            return respuesta;
        }

    }
}
