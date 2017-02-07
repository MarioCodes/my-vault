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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import juego.Carta;
import juego.Juego;

/**
 * Proyecto Online juego Oscar -> Poker Texas Hold'em!. Parte Servidor.
 * @author Mario Codes Sánchez
 * @since 07/02/2017
 */
public class Servidor {
    private static ArrayList<Boolean> accionJugador;
    
    private static Juego juego;
    private static int numeroJugadores = 0;
    
    private static int identificadorJugadorActualRonda = 1; //todo: eliminarlo, usare la AL<> para chequeo de turnos.
    private static int jugadoresRepartidos = 0; //todo: eliminarlo igual.
    
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
     * (Re)inicializacion de la AL que uso para los turnos de cada jugador.
     *  La idea es usar el identificador del jugador -1 para acceder a la AL. True si aun no ha usado su accion en el turno, false para que no pueda operar.
     */
    private static void inicializacionALTurnosJugada() {
        accionJugador = new ArrayList<>();
        
        for (int i = 0; i < numeroJugadores; i++) {
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
        System.out.println("Acabado de repartir manos a todos los jugadores");
    }
    
//    private static void repartirComunesJugadores() {
//        
////        boolean jugadorRepartido = false;
////        int jugadoresRepartidosCorrectamente = 0;
////        identificadorJugadorActualRonda = 1;
////        
////        while(jugadoresRepartidosCorrectamente < numeroJugadores) {
////            while(!jugadorRepartido) {
////                jugadorRepartido = envioCartasComunes(identificadorJugadorActualRonda);
////                identificadorJugadorActualRonda++;
////            }
////            
////            jugadorRepartido = false;
////            jugadoresRepartidosCorrectamente++;
////        }
//        
//        System.out.println("Cartas comunes repartidas a todos los jugadores");
//    }
    
//    private static boolean envioCartasComunes() {
//        ArrayList<Carta> cartasComunes = juego.getCARTAS_MESA();
//        
//        try {
//            for (int i = 0; i < cartasComunes.size(); i++) {
//                String carta = cartasComunes.get(i).toString();
//                String valor = carta.substring(0, 1);
//                String palo = carta.substring(2);
//
//                oos.writeObject(valor);
//                oos.flush();
//
//                oos.writeObject(palo);
//                oos.flush();
//            }
//
//            identificadorJugadorActualRonda++;
//            jugadoresRepartidos++;
//        }catch(IOException ex) {
//            ex.printStackTrace();
//        }
//        
//        return true;
//    }
    
    private static boolean apostar() {
        System.out.println("Comenzada parte Apuestas.");
        
        try {
            juego.sumarApuesta(ois.readInt());
            oos.writeInt(juego.getFichasApuestas());
            oos.flush();

            System.out.println("Apostado!");
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return true;
    }
    
    /**
     * Aniadido de un jugador al juego. Se usara cuando este no sea el ultimo.
     * @throws IOException
     */
    private static void aniadirJugador() throws IOException {
        oos.writeInt(++numeroJugadores);
        oos.flush();
        
        System.out.println("Jugador añadido.");
    }
    
    /**
     * Aniadido del ultimo jugador e inicializacion de los elementos necesarios para comenzar con el Juego.
     * @throws IOException 
     */
    private static void aniadirUltimoJugador() throws IOException {
        oos.writeInt(++numeroJugadores);
        oos.flush();
        System.out.println("Ultimo jugador añadido. Comenzando el Juego con " +numeroJugadores +" jugadores.");
        
        inicializacionALTurnosJugada();
        juego = new Juego(numeroJugadores);
        juego.comienzoJuego();
        
        System.out.println("Cartas de la mesa repartidas.");
    }
    
    private static void gestionAcciones() {
        try {
            aperturaCabecerasConexion();
            
            byte opcion = (byte) ois.readInt();
//            System.out.println(opcion); //fixme: borrar al final del todo.
            switch(opcion) {
                case 0: //Join de un jugador.
                    aniadirJugador();
                    break;
                case 1: //Join del ultimo jugador.
                    aniadirUltimoJugador();
                    break;
                case 2: //Reparto cartas cada Jugador.
                    repartirCartasJugadores(juego.repartoManoJugador());
                    break;
                case 3:
                    repartirCartasJugadores(juego.getCARTAS_MESA());
//                    envioCartasComunes();
                    if(jugadoresRepartidos == numeroJugadores) apostar(); //fixme: arreglar esto.
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
