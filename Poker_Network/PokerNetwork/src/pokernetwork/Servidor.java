/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokernetwork;

import estados.Estado;
import estados.EstadoCiegas;
import estados.EstadoPreparacion;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import juego.Carta;
import juego.Juego;

/**
 * Proyecto Online juego Oscar -> Poker Texas Hold'em!. Parte Servidor.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 */
public class Servidor {
    private static ArrayList<Boolean> accionJugador;
    
    private static Juego juego = new Juego();
    
    private static final int PUERTO = 8143;
    private static Socket socket = null;
    
    private static InputStream in = null;
    private static OutputStream out = null;
    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;
    
    /**
     * Apertura de las posibles cabeceras necesarias para la transmision de datos. ¡Se deberan cerrar despues!
     */
    private static void aperturaCabecerasConexion() {
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
    private static void cerrarCabecerasConexion() {
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
     * Cambiamos la Fase en la que se encuentra el Juego.
     * @param estado Estado al cual cambiamos.
     */
    private static synchronized void avanzarFaseJuego(Estado estado) {
        System.out.println("Juego en Fase: " +estado.toString());
        estado.cambioFase(juego);
    }
    
    /**
     * Ponemos el juego cuando se acaba de crear en estado de Preparacion.
     */
    private static void setJuegoPrimeraFase() {
        if(juego.getEstado() == null) avanzarFaseJuego(new EstadoPreparacion());
    }
    
    /**
     * (Re)inicializacion de la AL que uso para los turnos de cada jugador.
     *  La idea es usar el identificador del jugador -1 para acceder a la AL. True si aun no ha usado su accion en el turno, false para que no pueda operar.
     */
    private static void inicializacionALTurnosJugada() {
        accionJugador = new ArrayList<>();
        
        for (int i = 0; i < juego.getNumeroJugadores(); i++) {
            accionJugador.add(true);
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
     */
    private static void repartirCartasJugadores(ArrayList<Carta> cartas) {
        try {
            while(accionJugador.contains(true)) {
                int idJugador = ois.readInt();
                if(accionJugador.get(idJugador-1)) { //Si este jugador no ha gastado su turno.
                    oos.writeBoolean(true); //Se le indica al Jugador.
                    oos.flush();
                    
                    repartirCartasJugador(cartas);
                    accionJugador.set(idJugador-1, false);
                } else {
                    oos.writeBoolean(false); //Si no, se le indica que ya lo ha gastado.
                    oos.flush();
                } 
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        inicializacionALTurnosJugada(); //Reinicializamos para la siguiente.
    }
    
//    private static boolean apostar() {
//        System.out.println("Comenzada parte Apuestas.");
//        
//        try {
//            juego.sumarApuesta(ois.readInt());
//            oos.writeInt(juego.getFichasApuestas());
//            oos.flush();
//
//            System.out.println("Apostado!");
//        }catch(IOException ex) {
//            ex.printStackTrace();
//        }
//        
//        return true;
//    }
    
    /**
     * Aniadido de un jugador al juego. Se usara cuando este no sea el ultimo.
     * @throws IOException
     */
    private static void aniadirJugador() throws IOException {
        juego.aniadirJugador();
        oos.writeInt(juego.getNumeroJugadores());
        oos.flush();
    }
    
    /**
     * Aniadido del ultimo jugador e inicializacion de los elementos necesarios para comenzar con el Juego.
     * @throws IOException 
     */
    private static void aniadirUltimoJugador() throws IOException {
        aniadirJugador();
        
        inicializacionALTurnosJugada();
        juego.rebarajar();
    }
    
    private static void gestionAcciones() {
        try {
            aperturaCabecerasConexion();
            
            byte opcion = (byte) ois.readInt();
//            System.out.println(opcion); //fixme: borrar al final del todo.
            switch(opcion) {
                case 0: //Join de un jugador.
                    aniadirJugador();
                    System.out.println("Jugador añadido.");
                    break;
                case 1: //Join del ultimo jugador.
                    setJuegoPrimeraFase();
                    aniadirUltimoJugador();
                    System.out.println("Ultimo jugador añadido. Comenzando el Juego con " +juego.getNumeroJugadores() +" jugadores.");
                    break;
                case 2: //Reparto cartas cada Jugador.
                    repartirCartasJugadores(juego.repartoManoJugador());
                    break;
                case 3: //Reparto de cartas Comunes.
                    repartirCartasJugadores(juego.getCartasComunes());
//                    if(jugadoresRepartidos == numeroJugadores) apostar(); //fixme: arreglar esto.
                    break;
                default:
                    System.out.println("Comprobar selector de Acciones.");
                    break;
            }
            
            cerrarCabecerasConexion();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Ejecucion de la accion del Server.
     */
    public static void ejecucionServidor() {
        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO); //Espera y escucha la llegada de los clientes. Una vez establecida, devuelve el Socket.; 
        
            while(true) {
                socket = serverSocket.accept(); /* El ServerSocket me da el Socket.
                                                        Bloquea el programa en esta linea y solo avanza cuando un cliente se conecta.*/
                new Thread(() -> gestionAcciones()).start(); //Comienzo de la faena en un Hilo aparte.
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ejecucionServidor();
    }
}
