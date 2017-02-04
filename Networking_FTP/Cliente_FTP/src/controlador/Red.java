/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
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
 * @since 04/02/2017
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
    private DataOutputStream dout = null;
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
     * Metodo para el envio de un fichero del Cliente al Server.
     * @param nombreFich Nombre del fichero a mandar.
     * @return Estado de la envioFichero.
     */
    public boolean envioFichero(String nombreFich) {
        try {
            cabeceraComienzoConexion();
            
            long inicio = System.currentTimeMillis(); //Comienzo a medir del tiempo.
            
            File file = new File(nombreFich); //fixme: hacer el nombre del fichero variable segun seleccionado en el JTree. Tambien habra que manipular la ruta en ambos.
            byte[] bytes = new byte[BUFFER_LENGTH];

            InputStream in = new FileInputStream(file);
            OutputStream out = socket.getOutputStream();
            DataOutputStream dout = new DataOutputStream(out);

            dout.write(1); //Set de la accion en el server. (1 = recibir fichero).

            byte[] bytess = "Suuuu.txt".getBytes(); //fixme: investigar. Esto no se que hace.
            dout.write(bytess.length);
            dout.write(bytess);

            int count;
            while((count = in.read(bytes)) > 0) {
                dout.write(bytes, 0, count);
            }

            dout.close();
            out.close();
            in.close();
            socket.close();
            
            System.out.println("Tiempo de Ejecucion: " +(System.currentTimeMillis()-inicio));
            return true;
        }catch(ConnectException ex) {
            System.out.println("Problema en la conexion. " +ex.getLocalizedMessage());
            return false;
        }catch(IllegalArgumentException ex) {
            System.out.println("Numero de argumentos erroneo. Comprobar que el puerto este dentro de rango.\t " +ex.getLocalizedMessage());
            return false;
        }catch(IOException ex) {
            System.out.println("Problema de IO.");
            ex.printStackTrace();
            return false;
        }finally {
            cabeceraFinConexion();
        }
    }
    
    /**
     * Obtencion del JTree mapeado del directorio del Servidor.
     * @return JTree mapeado del Server.
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
            
            oos.writeByte(0);
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
