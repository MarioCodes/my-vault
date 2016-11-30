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
     * Ejecuta el .php y obtiene una JSONArray con todos los alojamientos disponibles.
     * @return Collection de AlojamientoDTO con toda la informacion de la BDD.
     */
    public Collection<AlojamientoDTO> listadoAlojamientos() {
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
        }catch(IOException ex) {
            throw new RuntimeException(ex);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public Collection<AlojamientoDTO> listadoAlojamientos(String provincia) {
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
        }catch(IOException ex) {
            throw new RuntimeException(ex);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Lectura del archivo .json local y cargado de los datos de este al HashMap miembro.
     * @return 
     */
    public Collection<AlojamientoDTO> cargadoJSONlocalAJava(JSONArray jsonArray) {
        try {
            ArrayList<AlojamientoDTO> arrayListTmpAlojamientos = new ArrayList<>();
            
//            HashMap<Integer, AlojamientoDTO> hashMapTmpLocalAlojamientos = new HashMap<>();

//            FileReader in = new FileReader("ficheros/alojamientos.json");
//            BufferedReader rd = new BufferedReader(in); //obtenemos el flujo de lectura
//            
//            String bufferLinea;
//            String respuestaFinal = "";
//            while ((bufferLinea = rd.readLine()) != null) { //procesamos la salida
//               respuestaFinal+= bufferLinea;
//            }
//        
            //Parte de tratamiento del JSON.
            JSONArray locs = jsonArray;
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
//                hashMapTmpLocalAlojamientos.put(alDTOTmp.getId(), alDTOTmp);
            }
        
//            OperacionesJSON.setAlojamientosJSONHashmap(hashMapTmpLocalAlojamientos);            
            return arrayListTmpAlojamientos;
        } catch (JSONException ex) {
//            JOptionPane.showMessageDialog(null, "Error. Revise si hay conexion con la BDD MySQL.");
//            Facade fachada = new Facade(); //Si no hay conexion la primera vez, el fichero se corrompe con el msg de error del .PHP, como no se cambiar esto en php, borro el fichero y lo intento cargar de nuevo.
//            fachada.recargarDatosDesdeBDDySobreescribirDatosJavaLocales();
//            System.exit(0);
            throw new RuntimeException(ex);
        }
    }
//    
//    /**
//     * Escritura del archivo JSON con la JSONArray que se le pasa como parametro conteniendo todos los datos.
//     * @param jsonArrayDatos JSONArray con todos los datos ya transformados.
//     */
//    public static void escrituraArchivoJSON(JSONArray jsonArrayDatos) {
//        File file = new File("ficheros/alojamientos.json");
//        file.getParentFile().mkdirs();
//        FileWriter fw = null;
//        
//        try {
//            fw = new FileWriter(file);
//            fw.write(jsonArrayDatos.toString());
//            
//            JOptionPane.showMessageDialog(null, "¡Cambios Guardados!");
//        }catch(IOException ex) {
//            ex.printStackTrace();
//        }finally {
//            try {
//                if(fw != null) fw.close();
//            }catch(IOException ex) {
//                ex.printStackTrace();
//            }
//        }
        
//    }
    
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
