/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokercliente;

import entidades.Carta;
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
 * @since 09/02/2017
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
     * Envio de un int al Server.
     * @param i Int a enviar.
     */
    private static void sendInt(int i) throws IOException {
        oos.writeInt(i);
        oos.flush();
    }
    
    /**
     * Get de un int del Server.
     * @return Int a recibir.
     */
    private static int getInt() throws IOException {
        return ois.readInt();
    }
    
    /**
     * Apertura de las cabeceras necesarias para la conexion.
     */
    private static void aperturasCabeceraConexion() {
        try {
            socket = new Socket(SERVER_IP, PUERTO);

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
    
    /**
     * Envio al server de la accion que deseamos realizar para que la lea en el Switch y actue.
     * @param accion Accion a realizar.
     * @param id ID propio del Jugador.
     * @throws IOException 
     * @return Booleano indicando si la accion a realizar es posible.
     */
    private static boolean accionMenu(int accion) throws IOException {
        oos.writeInt(accion);
        oos.flush();
        
        return ois.readBoolean();
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
     * Obtencion de Cartas desde el Servidor. Esta automatizado independientemente del numero de cartas.
     *  Lo uso tanto para obtener las cartas propias del jugador como las comunes de la mesa.
     * @param accion Accion a realizar en el Server (2 recibo cartas propias, 3 recibo cartas comunes).
     * @return ArrayList de Cartas con las Cartas segun requiera la ocasion.
     */
    public static ArrayList<Carta> getCartas(int accion) {
        ArrayList<Carta> cartas = new ArrayList<>();
        try {
            aperturasCabeceraConexion();
            
            if(accionMenu(accion)) {
                int cartasARecibir = ois.readInt(); //Numero de cartas a Recibir.

                for (int i = 0; i < cartasARecibir; i++) {
                    cartas.add(recibirReconstruirCarta());
                }

                return cartas;
            }
        }catch(ClassNotFoundException | ClassCastException | IOException ex) {
            ex.printStackTrace();
        }finally {
            cerradoCabecerasConexion();
        }
        
        return null;
    }
    
    /**
     * Accion de apostar pasada al Servidor..
     * @param fichas Fichas que queremos apostar.
     * @return Fichas totales que hay en la pool comun. -1 si error.
     */
    public static int apostar(int fichas) {
        try {
            aperturasCabeceraConexion();
            
            if(accionMenu(4)) {
                sendInt(fichas);
                
                return getInt();
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return -1;
    }
    
    /**
     * Aviso al Server para que aniada a este Jugador.
     * @param selectorMenu Accion del Menu a ejecutar.
     * @return ID del Jugador.
     */
    private static int addJugadorServer(int selectorMenu) {
        try {
            aperturasCabeceraConexion();
            accionMenu(selectorMenu);
            
            int IDJugador = ois.readInt();
            
            return IDJugador;
        }catch(ConnectException ex) {
            System.out.println("Problema en la conexion. " +ex.getLocalizedMessage());
        }catch(IllegalArgumentException ex) {
            System.out.println("Numero de argumentos erroneo. Comprobar que el puerto este dentro de rango.\t" +ex.getLocalizedMessage());
        }catch(IOException ex) {
            System.out.println("Problema de IO." +ex.getLocalizedMessage());
        }finally{
            cerradoCabecerasConexion();
        }
        
        return -1;
    }
    
    /**
     * Aniadido de un Jugador mas.
     * @return ID del Jugador.
     */
    public static int addJugador() {
        return addJugadorServer(0);
    }
    
    /**
     * Aniadido del ultimo Jugador.
     * @return ID del Jugador.
     */
    public static int addUltimoJugador() {
        return addJugadorServer(1);
    }
    
    /**
     * Obtenemos el ID del jugador a hablar.
     * @return ID del jugador o -1 si error.
     */
    public static int getIDTurno() {
        try {
            aperturasCabeceraConexion();
            accionMenu(1);
            int id = ois.readInt();
            return id;
        }catch(IOException ex) {
            ex.printStackTrace();
        }finally {
            cerradoCabecerasConexion();
        }
        
        return -1;
    }
}
