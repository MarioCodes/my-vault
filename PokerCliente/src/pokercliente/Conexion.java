/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokercliente;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar y coordinar la conexion del Programa con el Servidor.
 * @author Mario Codes Sánchez
 * @since 07/02/2017
 */
public class Conexion {
//    private static final int BUFFER_LENGTH = 8192;
    private static final int PUERTO = 8143;
    private static final String SERVER_IP = "127.0.0.1";
    
    private static Socket socket = null;
    
    private static InputStream in = null;
    private static OutputStream out = null;
    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;
    
    /**
     * Apertura de las cabeceras necesarias para la conexion.
     */
    private static void aperturasCabeceraConexion() {
        try {
            socket = new Socket(SERVER_IP, PUERTO); //IP y PORT del Server.

            in = socket.getInputStream();
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Cerrado de las aperturas realizadas.
     */
    private static void cerradoCabecerasConexion() {
        try {
            if(ois != null) ois.close();
            if(oos != null) oos.close();
            if(out != null) out.close();
            if(in != null) in.close();
            if(socket != null) socket.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static ArrayList<Carta> obtenerCartasComunes(int identificadorJugador) {
        ArrayList<Carta> comunes = new ArrayList<>();
        try {
            aperturasCabeceraConexion();
            
            oos.writeInt(3);
            oos.flush();
            
            int cartasARecibir = ois.readInt(); //Para usar mas adelante.
            
            String valor = ois.readObject().toString(); //Carta1
            String palo = ois.readObject().toString();
            comunes.add(new Carta(valor, palo));

            valor = ois.readObject().toString(); //Carta2
            palo = ois.readObject().toString();
            comunes.add(new Carta(valor, palo));

            valor = ois.readObject().toString(); //Carta3
            palo = ois.readObject().toString();
            comunes.add(new Carta(valor, palo));

            return comunes;
            
        }catch(ClassNotFoundException|ClassCastException ex) {
            ex.printStackTrace();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static int apostarJugador(int fichas) {
        int totalPool = -1;
        
        try {
            oos.writeInt(fichas);
            oos.flush();

            totalPool = ois.readInt();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return totalPool;
    }
    
    private static void envioAccionEID(int accion, int id) throws IOException {
        oos.writeInt(accion);
        oos.flush();

        oos.writeInt(id);
        oos.flush();
    }
    
    private static Carta recibirReconstruirCarta() throws IOException, ClassNotFoundException {
        String valor = ois.readObject().toString();
        String palo = ois.readObject().toString();
        
        return new Carta(valor, palo);
    }
    
    public static ArrayList<Carta> obtenerCartas(int idJugador, int accion) {
        ArrayList<Carta> cartas = new ArrayList<>();
        try {
            aperturasCabeceraConexion();
            
            envioAccionEID(accion, idJugador); //Obtencion de las cartas propias.

            boolean turnoDisponible = ois.readBoolean();

            if(turnoDisponible) {
                int cartasARecibir = ois.readInt(); //Para usar mas adelante.
                
                for (int i = 0; i < cartasARecibir; i++) {
                    cartas.add(recibirReconstruirCarta());
                }

                return cartas;
            }
        }catch(ClassNotFoundException|ClassCastException ex) {
            ex.printStackTrace();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Realizacion de la conexion al server.
     * @param tipoConexion tipo de conexion. 0 = un jugador mas; 1 = ultimo jugador a añadir.
     * @return Int. Numero propio del jugador.
     */
    public static int realizarConexion(int tipoConexion) {
        try {
            aperturasCabeceraConexion();
            
            in = new BufferedInputStream(socket.getInputStream());
            
            oos.writeInt(tipoConexion);
            oos.flush();
            
            return ois.readInt();
//            boolean estado = ois.readBoolean(); //Leemos la respuesta del server.
            
//            finConexion();
//            return estado;
        }catch(ConnectException ex) {
            System.out.println("Problema en la conexion. " +ex.getLocalizedMessage());
        }catch(IllegalArgumentException ex) {
            System.out.println("Numero de argumentos erroneo. Comprobar que el puerto este dentro de rango.\t" +ex.getLocalizedMessage());
        }catch(IOException ex) {
            System.out.println("Problema de IO.");
            ex.printStackTrace();
        }
        
        return -1;
    }
}
