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
import juego.Carta;
import juego.Juego;

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
     * Envio del check sobre si es posible realizar una accion al Cliente.
     * @param b Booleano de la accion.
     * @throws IOException 
     */
    public static void envioCheckAccion(boolean b) throws IOException {
        oos.writeBoolean(b);
        oos.flush();
    }
    
    /**
     * Recibo de un int del Cliente.
     * @return Entero desde el Cliente.
     * @throws java.io.IOException
     */
    public static int reciboInt() throws IOException {
        return ois.readInt();
    }
    
    /**
     * Apertura de las posibles cabeceras necesarias para la transmision de datos. ¡Se deberan cerrar despues!
     * @param socket Socket por el cual abrimos el resto de conexiones.
     */
    public static void aperturaCabecerasConexion(Socket socket) {
        try {
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
    public static void cerrarCabecerasConexion() {
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
     * Deconstruyo y envio la carta. No se porque, me da problemas al reconstruirla en el Cliente si envio la carta entera. 
     * Envio sus datos y la reconstruyo en este.
     * @param carta Carta a deconstruir y enviar.
     * @throws IOException
     */
    private static void deconstruccionEnvioCarta(Carta carta) throws IOException {
        String cartaDecons = carta.toString();
        String valor = cartaDecons.substring(0, 1);
        String palo = cartaDecons.substring(2);
        
        oos.writeObject(valor);
        oos.flush();
        
        oos.writeObject(palo);
        oos.flush();
    }
    
    /**
     * Accion propia de repartir cartas a un jugador especifico.
     */
    private static void repartirCartasJugador(ArrayList<Carta> cartas) {
        try {
            oos.writeInt(cartas.size()); //Cartas a recibir en el jugador.
            oos.flush();
            
            for (int i = 0; i < cartas.size(); i++) {
                deconstruccionEnvioCarta(cartas.get(i));
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Accion de repartir cartas a un jugador. Se repetira hasta que todos tengan su mano.
     * Esta automatizado completamente, simplemente se le pasa la coleccion con las cartas a enviar y el mismo se encarga de todo.
     * @param juego
     * @param cartas
     */
    public static void repartirCartasJugadores(Juego juego, ArrayList<Carta> cartas) {
        try {
            ArrayList<Boolean> accionJugador = juego.getAccionJugador();
            
            while(accionJugador.contains(true)) {
                int idJugador = ois.readInt()-1;
                if(accionJugador.get(idJugador)) { //Si este jugador no ha gastado su turno.
                    oos.writeBoolean(true); //Se le indica al Jugador.
                    oos.flush();
                    
                    repartirCartasJugador(cartas);
                    
                    accionJugador.set(idJugador, false);
                } else {
                    oos.writeBoolean(false); //Si no, se le indica que ya lo ha gastado.
                    oos.flush();
                } 
            }
            juego.reseteoALTurnosJugada();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Aniadido de un jugador al juego. Se usara cuando este no sea el ultimo.
     * @param juego
     * @throws IOException
     */
    public static void aniadirJugador(Juego juego) throws IOException {
        juego.aniadirJugador();
        oos.writeInt(juego.getNumeroJugadores());
        oos.flush();
        System.out.println("Jugador añadido.");
    }
    
    /**
     * Aniadido del ultimo jugador e inicializacion de los elementos necesarios para comenzar con el Juego.
     * @param juego
     * @throws IOException 
     * @throws java.lang.InterruptedException 
     */
    public static void aniadirUltimoJugador(Juego juego) throws IOException, InterruptedException {
        aniadirJugador(juego);
        juego.inicializacionALTurnosJugada();
        juego.rebarajar();
        System.out.println("Ultimo jugador añadido. Comenzando el Juego con " +juego.getNumeroJugadores() +" jugadores.");
    }
    
    /**
     * Reparto de cartas a los jugadores.
     * @param juego
     * @throws IOException 
     */
    public static void repartoCartasJugadores(Juego juego) throws IOException {
        envioCheckAccion(juego.getFase().checkRepartoCartasPersonales());
        juego.getFase().repartoCartasPersonales();
    }
}
