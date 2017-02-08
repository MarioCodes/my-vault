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
//    private static final int BUFFER_LENGTH = 8192; //fixme: mirar si lo puedo utilizar mas adelante, de momento que le den.
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
    
    //fixme: Desarrollar.
    public static int getPoolFichasApostadas() {
        return 0;
    }
    
    //fixme: desarrollar.
    public static int apostarJugador(int idJugador, int fichas) {
        int totalPool = -1;
        
        try {
            oos.writeInt(idJugador);
            oos.flush();
            
            boolean accionLeft = ois.readBoolean();
            if(accionLeft) {
                oos.writeInt(fichas);
                oos.flush();
                
                totalPool = ois.readInt();
            } else {
                
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return totalPool;
    }
    
    /**
     * Debido a los problemas que tengo para enviar Cartas por Socket como (Object) las deconstruyo en el server a sus valores base y las reconstruyo aqui.
     * @return Carta reconstruida para aniadir.
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    private static Carta recibirReconstruirCarta() throws IOException, ClassNotFoundException {
        String valor = ois.readObject().toString();
        String palo = ois.readObject().toString();
        
        return new Carta(valor, palo);
    }
    
    /**
     * Envio al server de la accion que deseamos realizar y el ID unico del Jugador.
     * @param accion Accion a realizar.
     * @param id ID propio del Jugador.
     * @throws IOException 
     */
    private static void envioAccionEID(int id, int accion) throws IOException {
        oos.writeInt(accion);
        oos.flush();

        oos.writeInt(id);
        oos.flush();
    }
    
    /**
     * Obtencion de Cartas desde el Servidor. Esta automatizado independientemente del numero de cartas.
     *  Lo uso tanto para obtener las cartas propias del jugador como las comunes de la mesa.
     * @param idJugador ID unico del jugador para comprobar si ya ha realizado la accion en su turno.
     * @param accion Accion a realizar en el Server (2 recibo cartas propias, 3 recibo cartas comunes).
     * @return ArrayList de Cartas con las Cartas segun requiera la ocasion.
     */
    public static ArrayList<Carta> obtenerCartas(int idJugador, int accion) {
        ArrayList<Carta> cartas = new ArrayList<>();
        try {
            aperturasCabeceraConexion();
            
            envioAccionEID(idJugador, accion); //Obtencion de las cartas.

            boolean turnoDisponible = ois.readBoolean();
            if(turnoDisponible) {
                int cartasARecibir = ois.readInt(); //Numero de cartas a Recibir.
                
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
     * Realizacion de la conexion al Server para que tenga en cuenta a este Jugador.
     * @param tipoConexion tipo de conexion. 0 = un jugador mas; 1 = ultimo jugador a añadir.
     * @return Int. Numero propio del jugador. -1 si hubiera algun tipo de problema (no deberia).
     */
    public static int realizarConexion(int tipoConexion) {
        try {
            aperturasCabeceraConexion();
            
            in = new BufferedInputStream(socket.getInputStream());
            
            oos.writeInt(tipoConexion);
            oos.flush();
            
            int IDJugador = ois.readInt();
            
            cerradoCabecerasConexion();
            return IDJugador;
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
