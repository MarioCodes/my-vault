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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
//    private static final String PATH_JSON = "ficheros/alojamientos.json"; //Ruta donde quiero el .json
//    private static final String URL_CUSTOM_PHP = "http://localhost/generarJSONAlojamientos.php"; //fixme: Acordarme que en casa cambie los puertos de acceso a :8088 por problemas de compatibilidad con VirtualBox.
    
    private EjecucionJSON() {} //Clase estatica, no quiero que se pueda instanciar.
    
//    /**
//     * Check previo, no quiero sobreescribirlo siempre si ya existe.
//     * @return True si existe.
//     */
//    private static boolean checkFicheroJSONexiste() {
//        File fileTmp = new File(PATH_JSON);
//        return fileTmp.isFile(); //.exist() no diferencia entre fichero y directorio, esto si. Evito posibles problemas.
//    }
//    
//    /**
//     * Check para comprobar si el fichero esta vacio.
//     * @return True si esta vacio.
//     */
//    private static boolean checkFicheroJSONVacio() {
//        File fileTmp = new File(PATH_JSON);
//        return (fileTmp.length() == 0);
//    }
//    
//    /**
//     * Deja el fichero en blanco. No lo puedo borrar ya que Windows no me deja al haberlo abierto Netbeans.
//     */
//    public static void borrarContenidoFicheroJSON() {
//        try {
//            File fichero = new File(PATH_JSON);
//            PrintWriter pw = new PrintWriter(fichero);
//            pw.write("");
//            pw.close();
//        }catch(FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
//    }
    
    /**
     * Ejecuta el .php que hace el get a la BDD y escribe el JSON que devuelve este, en un fichero local en la localizacion del proyecto.
     */
    public static void ejecucionPHPgeneracionArchivoJSON() {
        try {
//            if(!checkFicheroJSONexiste() || checkFicheroJSONVacio()) { //Que solo lo sobreescriba si no existe o este se encuentra vacio.
                PeticionPost post = new PeticionPost("http://localhost/listadoAlojamientos.php");
                post.generacionArchivoJSON();
//            }
        }catch(IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            System.exit(0); //Si hay excepcion en este punto probablemente sera por los parametros de conexion. Cierro programa porque si no seran problemas por todos lados.
        }
    }
    
    /**
     * Lectura del archivo .json local y cargado de los datos de este al HashMap miembro.
     * @return 
     */
    public static Collection<AlojamientoDTO> cargadoJSONlocalAJava(JSONArray jsonArray) {
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
     * Inner Class pasada por Angel. De lo pasado solo necesito 1 metodo y esta relacionado con EjecucionJSON. Lo meto aqui aunque lo mantengo separado.
     */
    private static class PeticionPost {
        private final URL URL;
        private final String DATA;

        PeticionPost(String url) throws MalformedURLException {
            this.URL = new URL(url);
            DATA="";
        }

        /**
         * Ejecuta el .php que hace el get a la BDD y escribe el JSON que devuelve este, en un fichero local en la localizacion del proyecto.
         * @throws IOException 
         */
        private JSONArray generacionArchivoJSON() throws IOException {
//            File file = new File("ficheros/alojamientos.json");
//            file.getParentFile().mkdirs();
//            PrintWriter writer = new PrintWriter(file, "UTF-8");
            try {
                URLConnection conn = URL.openConnection(); //abrimos la conexión
                conn.setDoOutput(true); //especificamos que vamos a escribir
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); //obtenemos el flujo de escritura
                wr.write(DATA); //escribimos
                wr.close(); //cerramos la conexión

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); //obtenemos el flujo de lectura
                String linea;
                String buffer = "";

                while ((linea = rd.readLine()) != null) { //procesamos al salida
                    buffer += linea;
                }

                return new JSONArray(buffer); //todo: check de .close().
            }catch(JSONException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
