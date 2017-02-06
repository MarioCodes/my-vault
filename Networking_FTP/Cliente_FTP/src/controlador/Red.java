/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import javax.swing.JTree;

/**
 * Recopilacion de la implementacion logica del Red.
 * @author Mario Codes Sánchez
 * @since 05/02/2017
 * @todo: al cerrar la conexion del cliente directamente sin cerrar el socket y demas peta el server. Poner un Shutdown Hook.
 */
public class Red {
    private static final int BUFFER_LENGTH = 8192;
    private final int PUERTO;
    private final String SERVER_IP;
    
    private BufferedReader br = null;
    private BufferedWriter bw = null;
    private Socket socket = null;
        
    private InputStream in = null;
    private OutputStream out = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
            
    /**
     * Constructor a utilizar por defecto.
     * @param serverIP IP del Servidor a conectarse.
     * @param puerto Puerto del Server para la conexion.
     */
    public Red(String serverIP, int puerto) {
        this.SERVER_IP = serverIP;
        this.PUERTO = puerto;
    }
    
    /**
     * Pasos necesarios a realizar antes de ejecutar cualquier accion.
     *  Al final habra que usar la cabecera de fin para cerrar estas conexiones.
     * @throws IOException Posible excepcion lanzada por las conexiones al abrirse.
     */
    private void cabeceraComienzoConexion() throws IOException, IllegalArgumentException {
        socket = new Socket(SERVER_IP, PUERTO); //IP y PORT del Server.

        bw = new BufferedWriter(new PrintWriter(socket.getOutputStream()));
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    /**
     * Para colocar en el 'finally'. Cierra las conexiones estandar usadas, abiertas por la cabecera de comienzo.
     * @fixme: comprobar todas las conexiones miembro y cerrarlas todas.
     */
    private void cabeceraFinConexion() {
        try {
            if(br != null) br.close();
            if(bw != null) bw.close();
            if(socket != null) socket.close();
        }catch(IOException ex) {
            System.out.println("Problema al cerrar las conexiones.");
            ex.printStackTrace();
        }
    }
    
    /**
     * Metodo para el recibimiento de un fichero Server -> Cliente.
     * @param rutaServer Ruta del Servidor recortada de donde pillamos el fichero.
     * @param rutaLocal Ruta local donde queremos el fichero.
     * @param nombreFichero Nombre del fichero recortado.
     */
    public void reciboFicheroServerCliente(String rutaServer, String rutaLocal, String nombreFichero) {
        try {
            cabeceraComienzoConexion();
            
            in = socket.getInputStream();
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);
            
            oos.writeInt(3);
            oos.flush();
            
            System.out.println("SU!");
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Metodo para el envio de un fichero del Cliente al Server.
     * @param rutaServer Ruta del Servidor donde queremos el  fichero.
     * @param rutaLocal Ruta local recortada (sin incluir el fichero) de donde lo pillamos.
     * @param nombreFichero Nombre recortado del fichero suelto.
     */
    public void envioFicheroClienteServer(String rutaServer, String rutaLocal, String nombreFichero) {
        try {
            cabeceraComienzoConexion();
            
            long inicio = System.currentTimeMillis(); //Envio y medida del tiempo tardado.
            
            File file = new File(rutaLocal+nombreFichero);
            byte[] bytes = new byte[BUFFER_LENGTH];

            in = new FileInputStream(file);
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            
            oos.writeInt(2);
            oos.flush(); //fixme: investigar si es necesario, no lo se. De momento peta al darle al boton de enviar varias veces.
            
            byte[] bytesRutaFich = rutaServer.getBytes();
            oos.writeByte(bytesRutaFich.length);
            oos.write(bytesRutaFich);
            oos.flush();
            
            byte[] bytesNombreFich = nombreFichero.getBytes();
            oos.writeByte(bytesNombreFich.length);
            oos.write(bytesNombreFich);
            oos.flush();
            
            int count;
            while((count = in.read(bytes)) > 0) {
                oos.write(bytes, 0, count);
            }
            
            System.out.println("Tiempo de Ejecucion: " +(System.currentTimeMillis()-inicio));
        }catch(IOException ex) {
            ex.printStackTrace();
        }finally {
//            cabeceraFinConexion();
        }
    }
    
    /**
     * Obtencion del JTree mapeado del directorio del Servidor.
     * @return JTree mapeado del Server.
     * @deprecated Era la idea original, no me da tiempo.
     */
    public JTree obtencionMapeoServer() {
        try {
            cabeceraComienzoConexion();
            
            in = new BufferedInputStream(socket.getInputStream());
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);
            
            oos.writeByte(1);
            oos.flush();
            
            JTree treeServer = (JTree) ois.readObject();
            
            ois.close();
            oos.close();
            out.close();
            in.close();
            
            return treeServer;
        }catch(ClassCastException|ClassNotFoundException ex) {
            System.out.println("Problema de cast de clase.");
            ex.printStackTrace();
        }catch(IOException ex) {
            System.out.println("Problema de IO");
            ex.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Comprobacion de que el servidor esta alcanzable mediante los parametros introducidos.
     * @return Estado de la conexion.
     */
    public boolean checkConexion() {
        try {
            cabeceraComienzoConexion();
            
            in = new BufferedInputStream(socket.getInputStream());
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);
            
            oos.writeInt(0);
            oos.flush();
            
            boolean estado = ois.readBoolean(); //Leemos la respuesta del server.
            
            ois.close();
            oos.close();
            out.close();
            in.close();
            
            return estado;
        }catch(ConnectException ex) {
            System.out.println("Problema en la conexion. " +ex.getLocalizedMessage());
            return false;
        }catch(IllegalArgumentException ex) {
            System.out.println("Numero de argumentos erroneo. Comprobar que el puerto este dentro de rango.\t" +ex.getLocalizedMessage());
            return false;
        }catch(IOException ex) {
            System.out.println("Problema de IO.");
            ex.printStackTrace();
            return false;
        }finally {
            cabeceraFinConexion();
        }
    }
}
