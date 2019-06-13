/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import DTO.Alojamiento;
import DTO.Habitacion;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

/**
 *
 * @author Alumno
 */
public class XML {

    static String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist
    static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/ColeccionPruebas"; //URI colección   
    static String usu = "admin"; //Usuario
    static String usuPwd = ""; //Clave
    static Collection col = null;

    public static Collection conectar() {
        try {
            Class cl = Class.forName(driver); //Cargar del driver 
            Database database = (Database) cl.newInstance(); //Instancia de la BD
            DatabaseManager.registerDatabase(database); //Registro del driver
            col = DatabaseManager.getCollection(URI, usu, usuPwd);
            return col;
        } catch (XMLDBException e) {
            System.out.println("Error al inicializar la BD eXist.");
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error en el driver.");
            //e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Error al instanciar la BD.");
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Error al instanciar la BD.");
            //e.printStackTrace();
        }
        return null;
    }

    public boolean insertarAlojamiento(Alojamiento aloja) {
        try {
            if (conectar() != null) {
                if (checkAlojamiento(aloja)) {
                    return false;
                } else {
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    ResourceSet result = servicio.query("update insert " + aloja.toString() + " into /Alojamientos");
                    col.close();
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean insertarHabitacion(Alojamiento aloja, Habitacion habitacion) {
        try {
            if (checkHabitacion(aloja, habitacion)) {
                return false;
            } else {
                if (conectar() != null) {
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    ResourceSet result = servicio.query("update insert " + habitacion.toString() + " into /Alojamientos/Alojamiento[Razon_Social='" + aloja.getRazonSocial() + "']/Habitaciones");
                    col.close();
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Alojamiento getAlojamiento(String razSoc) {
        try {
            if (conectar() != null) {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query("/Alojamientos/Alojamiento[Razon_Social='" + razSoc + "']");
                ResourceIterator i;
                Alojamiento alTmp = null;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    return null;
                } else {
                    Resource r = i.nextResource();
                    String xml = (String) r.getContent();

                    Document miDoc = null;
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    miDoc = db.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
                    Element raiz = miDoc.getDocumentElement();
                    Element alojamiento = (Element) raiz; //Chequear que el 0 sea el index correcto.

                    String nombre = alojamiento.getElementsByTagName("Nombre").item(0).getTextContent();
                    String telefono_contacto = alojamiento.getElementsByTagName("Telefono").item(0).getTextContent();
                    String direccion_social = alojamiento.getElementsByTagName("Dir_Social").item(0).getTextContent();
                    String razon_social = alojamiento.getElementsByTagName("Razon_Social").item(0).getTextContent();
                    String descripcion = alojamiento.getElementsByTagName("Descripcion").item(0).getTextContent();
                    int valoracion = Integer.parseInt(alojamiento.getElementsByTagName("Valoracion").item(0).getTextContent());
                    String fecha_apertura = alojamiento.getElementsByTagName("Fecha_Apertura").item(0).getTextContent();
                    int numero_habitaciones = Integer.parseInt(alojamiento.getElementsByTagName("Num_Habitaciones").item(0).getTextContent());
                    String provincia = alojamiento.getElementsByTagName("Provincia").item(0).getTextContent();
                    ArrayList<Habitacion> arrayListHabitacion = new ArrayList<>();
                    alTmp = new Alojamiento(arrayListHabitacion, nombre, telefono_contacto, direccion_social, razon_social, descripcion, valoracion, fecha_apertura, numero_habitaciones, provincia);
                }

                col.close();
                return alTmp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean eliminarAlojamiento(String razSoc) {
        try {
            if (conectar() != null) {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query(
                        "update delete /Alojamientos/Alojamiento[Razon_Social ='" + razSoc + "']");
                col.close();
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean eliminarHabitacion(Alojamiento aloja, Habitacion hab) {
        try {
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = servicio.query(
                    "update delete /Alojamientos/Alojamiento[Razon_Social='" + aloja.getRazonSocial() + "']/Habitaciones/Habitacion[ID=" + hab.getId_habitacion() + "]");
            col.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public ArrayList<Habitacion> getHabitaciones() {
        ArrayList<Alojamiento> aloja = listadoCompleto();
        ArrayList<Habitacion> set = new ArrayList<>();
        aloja.forEach(f -> {
            f.getArrayListHabitaciones().forEach(a -> {
                if (!set.contains(a)) {
                    set.add(a);
                }
            });
        });
        return set;
    }

    /**
     * Obtengo el XML <Habitaciones> a tratar con todas las habitaciones del
     * ALojamiento dentro.
     */
    private String getHabitaciones(Alojamiento aloja) {
        try {
            String habitaciones = null;
            if (conectar() != null) {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query("/Alojamientos/Alojamiento[Razon_Social='" + aloja.getRazonSocial() + "']/Habitaciones");
                ResourceIterator i;
                Habitacion habTmp = null;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    return null;
                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    habitaciones = (String) r.getContent();
                }
                col.close();
                return habitaciones;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean modificarAlojamiento(Alojamiento a) {
        try {
            if (conectar() != null) {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query(
                        "update replace /Alojamientos/Alojamiento[Razon_Social='" + a.getRazonSocial() + "'] with" + a.toString());

                col.close();
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void modificarHabitacion(Alojamiento aloja, Habitacion hab) {
        try {
            if (conectar() != null) {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query("update replace /Alojamientos/Alojamiento[Razon_Social='" + aloja.getRazonSocial() + "']/Habitaciones/Habitacion[ID=" + hab.getId_habitacion() + "] with" + hab.toString());

                col.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Obtiene la tag <Alojamientos> conteniendo todos los Alojamientos y la
     * pasa a una String.
     *
     * @return String con toda la informacion XML en crudo.
     */
    private String getAlojamientos() {
        String alojamientos = null;
        if (conectar() != null) {
            try {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query("for $al in /Alojamientos return $al");
                // recorrer los datos del recurso.  
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    alojamientos = (String) r.getContent();
                }
                col.close();
            } catch (XMLDBException e) {
                System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }
        return alojamientos;
    }

    public ArrayList<Habitacion> listadoHabitaciones(Alojamiento aloja) {
        String habitacionesXML = getHabitaciones(aloja);
        ArrayList<Habitacion> habitacionesAL = new ArrayList<>();

        Document miDoc = null;
        Habitacion habTmp = null;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            miDoc = db.parse(new ByteArrayInputStream(habitacionesXML.getBytes("UTF-8")));
            Element raiz = miDoc.getDocumentElement();

            NodeList habitaciones = raiz.getElementsByTagName("Habitacion");

            //Recorremos la lista de etiquetas 'Habitacion' guardando los datos.
            for (int i = 0; i < habitaciones.getLength(); i++) {
                Element habitacionEL = (Element) habitaciones.item(i);

                String numero = habitacionEL.getElementsByTagName("ID").item(0).getTextContent();
                String tipo = habitacionEL.getElementsByTagName("Tipo").item(0).getTextContent();
                String precio = habitacionEL.getElementsByTagName("Precio").item(0).getTextContent();
                int precioI = Integer.parseInt(precio);
                String extras = habitacionEL.getElementsByTagName("Extras").item(0).getTextContent();
                String resenias = habitacionEL.getElementsByTagName("Resenias").item(0).getTextContent();

                //Instanciacion del alojamiento leido y almacenado en la ArrayList local temporal.
                habTmp = new Habitacion(numero, extras, tipo, resenias, precioI);
                habitacionesAL.add(habTmp);
            }
        } catch (NumberFormatException | ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }

        return habitacionesAL;
    }

    /**
     * Listado completo de todos los Alojamientos que hay en la BDD, cada uno
     * con su Habitacion anidada.
     *
     * @return ArrayList<Alojamiento> con el XML convertido a Alojamientos
     * insttanciados.
     */
    public ArrayList<Alojamiento> listadoCompleto() {
        String alojamientosXML = getAlojamientos();

        ArrayList<Alojamiento> arrayListAlojamientosTmp = new ArrayList<>();
        Document miDoc = null;
        Alojamiento alTmp = null;
        Habitacion habTmp = null;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            miDoc = db.parse(new ByteArrayInputStream(alojamientosXML.getBytes("UTF-8")));
            Element raiz = miDoc.getDocumentElement();

            NodeList alojamientos = raiz.getElementsByTagName("Alojamiento");

            //Recorremos la lista de etiquetas 'Alojamiento' guardando los datos.
            for (int i = 0; i < alojamientos.getLength(); i++) {
                Element alojamiento = (Element) alojamientos.item(i);

                String nombre = alojamiento.getElementsByTagName("Nombre").item(0).getTextContent();
                String telefono_contacto = alojamiento.getElementsByTagName("Telefono").item(0).getTextContent();
                String direccion_social = alojamiento.getElementsByTagName("Dir_Social").item(0).getTextContent();
                String razon_social = alojamiento.getElementsByTagName("Razon_Social").item(0).getTextContent();
                String descripcion = alojamiento.getElementsByTagName("Descripcion").item(0).getTextContent();
                int valoracion = Integer.parseInt(alojamiento.getElementsByTagName("Valoracion").item(0).getTextContent());
                String fecha_apertura = alojamiento.getElementsByTagName("Fecha_Apertura").item(0).getTextContent();
                int numero_habitaciones = Integer.parseInt(alojamiento.getElementsByTagName("Num_Habitaciones").item(0).getTextContent());
                String provincia = alojamiento.getElementsByTagName("Provincia").item(0).getTextContent();

                //Parte Habitaciones de cada Alojamiento.
                ArrayList<Habitacion> arrayListHabitacion = new ArrayList<>();
                NodeList habitaciones = alojamiento.getElementsByTagName("Habitacion");

                for (int x = 0; x < habitaciones.getLength(); x++) {
                    Element habitacion = (Element) habitaciones.item(x);

                    String id_habitacion = habitacion.getElementsByTagName("ID").item(0).getTextContent();
                    String extras_habitacion = habitacion.getElementsByTagName("Extras").item(0).getTextContent();
                    String tipo_habitacion = habitacion.getElementsByTagName("Tipo").item(0).getTextContent();
                    String reseñas = habitacion.getElementsByTagName("Resenias").item(0).getTextContent();
                    int precio = Integer.parseInt(habitacion.getElementsByTagName("Precio").item(0).getTextContent());

                    habTmp = new Habitacion(id_habitacion, extras_habitacion, tipo_habitacion, reseñas, precio);
                    arrayListHabitacion.add(habTmp);
                }

                //Instanciacion del alojamiento leido y almacenado en la ArrayList local temporal.
                alTmp = new Alojamiento(arrayListHabitacion, nombre, telefono_contacto, direccion_social, razon_social, descripcion, valoracion, fecha_apertura, numero_habitaciones, provincia);
                arrayListAlojamientosTmp.add(alTmp);
            }
        } catch (NumberFormatException | ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }

        //ArrayList local temporal ya terminada de rellenar con todos los datos del XML. Set de la ArrayList Interna en Alojamientos.
        return arrayListAlojamientosTmp;
    }

    public boolean checkAlojamiento(Alojamiento aloja) {
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query("/Alojamientos/Alojamiento[Razon_Social='" + aloja.getRazonSocial() + "']");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return false;
    }

    public boolean checkHabitacion(Alojamiento aloja, Habitacion hab) {
        //Devuelve true si el dep existe
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query(
                        "/Alojamientos/Alojamiento[Razon_Social='" + aloja.getRazonSocial() + "']/Habitaciones/Habitacion[ID=" + hab.getId_habitacion() + "]");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return false;
    }
}
