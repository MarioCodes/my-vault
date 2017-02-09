/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokernetwork;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import entidades.Carta;
import entidades.Juego;

/**
 * Todo lo relacionado con la conexion del Server con los clientes.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 */
public class Conexion {
    private static Socket socket = null;
    
    private static InputStream in = null;
    private static OutputStream out = null;
    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;
    
    /**
     * Recibo de un int del Cliente. Usado para la gestion de los Menus en Servidor.
     * @return Entero desde el Cliente.
     * @throws java.io.IOException
     */
    public static int getInt() throws IOException {
        return ois.readInt();
    }
    
    /**
     * Envio de un int al Cliente.
     * @param i Entero a enviar.
     * @throws IOException 
     */
    public static void sendInt(int i) throws IOException {
        oos.writeInt(i);
        oos.flush();
    }
    
    /**
     * Recibo de un Booleano del Clietne.
     * @return Booleano desde el Cliente.
     * @throws IOException 
     */
    public static boolean getBooleano() throws IOException {
        return ois.readBoolean();
    }
    
    /**
     * Envio de un booleano al Cliente.
     * @param b Booleano a enviar.
     * @throws IOException 
     */
    public static void sendBooleano(boolean b) {
        try {
            oos.writeBoolean(b);
            oos.flush();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    /**
     * Apertura de las posibles cabeceras necesarias para la transmision de datos. ¡Se deberan cerrar despues!
     * @param socket Socket por el cual abrimos el resto de conexiones.
     */
    public static void aperturaConexion(Socket socket) {
        try {
            Conexion.socket = socket;
            in = socket.getInputStream();
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Cerrado de todas las conexiones abiertas previamente para la conexion.
     */
    public static void cerradoConexion() {
        try {
            if(oos != null) oos.close();
            if(ois != null) ois.close();
            if(out != null) out.close();
            if(in != null) in.close();
            if(socket != null) socket.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Envio del ID del Jugador a quien le toca Hablar.
     * @param juego Juego en curso.
     * @throws IOException 
     */
    public static void sendFocus(Juego juego) throws IOException {
        oos.writeInt(juego.getIdFocus());
        oos.flush();
    }
    
    /**
     * Envio de los valores de la carta a traves del Socket.
     * @param valor Valor propio de la carta.
     * @param palo Palo al que pertenece.
     * @throws IOException 
     */
    private static void sendCarta(String valor, String palo) throws IOException {
        oos.writeObject(valor);
        oos.flush();
        
        oos.writeObject(palo);
        oos.flush();
    }
    
    /**
     * Deconstruyo y envio la carta. No se porque, me da problemas al reconstruirla en el Cliente si envio la carta entera. 
     * Envio sus datos y la reconstruyo en este.
     * @param carta Carta a deconstruir y enviar.
     * @throws IOException
     */
    private static void deconstruccionCarta(Carta carta) throws IOException {
        String cartaDecons = carta.toString();
        String valor = cartaDecons.substring(0, 1);
        String palo = cartaDecons.substring(2);
        
        sendCarta(valor, palo);
    }
    
    /**
     * Repartido y envio de cartas a un jugador.
     * Automatizado, primero enviar un Int con la cantidad de cartas a enviar, y luego manda los valores de estas.
     * @param cartas Cartas que queremos enviar.
     */
    public static void repartoCartas(ArrayList<Carta> cartas) {
        try {
            oos.writeInt(cartas.size()); //Cartas a recibir en el jugador.
            oos.flush();
            
            for (int i = 0; i < cartas.size(); i++) {
                deconstruccionCarta(cartas.get(i));
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Realizacion de una apuesta por parte de un jugador y devuelta del total de la Pool.
     * @param juego Juego actual, necesario para obtener y enviar al cliente le total de la Pool con su apuesta.
     */
    public static void getApuesta(Juego juego) {
        try {
            juego.apostar(ois.readInt());
            sendInt(juego.getFichasApuestas());
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Get ID propia de un Jugador.
     * Lo usare para cuando este se quiera retirar.
     * @return ID del jugador o -1 si error.
     */
    public static int getID() {
        try {
            return getInt();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return -1;
    }
    
    /**
     * Aniadido de un jugador al juego. Se usara cuando este no sea el ultimo.
     * @param juego Juego para comenzar.
     * @throws IOException
     */
    public static void addJugador(Juego juego) throws IOException {
        juego.aniadirJugador();
        oos.writeInt(juego.getTotalJugadores());
        oos.flush();
        
        oos.writeBoolean(true);
        oos.flush();
        
        System.out.println("Jugador añadido.");
    }
    
    /**
     * Aniadido del ultimo jugador y output al Usuario.
     * @param juego Juego para comenzar.
     * @throws IOException 
     * @throws java.lang.InterruptedException 
     */
    public static void addUltimoJugador(Juego juego) throws IOException, InterruptedException {
        addJugador(juego);
        System.out.println("Ultimo jugador añadido. Comenzando el Juego con " +juego.getTotalJugadores() +" jugadores.");
    }

    /**
     * @return the socket
     */
    public static Socket getSocket() {
        return socket;
    }
}
