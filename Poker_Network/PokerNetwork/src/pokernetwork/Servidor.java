/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokernetwork;

import fases.FasePreFlop;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import juego.Carta;
import juego.Juego;

/**
 * Proyecto Online juego Oscar -> Poker Texas Hold'em!. Parte Servidor.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 */
public class Servidor {
    private static Semaphore mutex = new Semaphore(1); //todo: Preguntarle a Oscar, no se si hara algo al ser Static.
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
     * (Re)inicializacion de la AL que uso para los turnos de cada jugador.
     *  La idea es usar el identificador del jugador -1 para acceder a la AL. True si aun no ha usado su accion en el turno, false para que no pueda operar.
     */
    private static void inicializacionALTurnosJugada() throws InterruptedException {
        mutex.acquire();
        accionJugador = new ArrayList<>();
        
        for (int i = 0; i < juego.getNumeroJugadores(); i++) {
            accionJugador.add(true);
        }
        mutex.release();
    }
    
    /**
     * Reseteo de las acciones de cada jugador a True. Se hara cuando se acabe una fase.
     * @throws InterruptedException 
     */
    private static void reseteoALTurnosJugada() throws InterruptedException {
        mutex.acquire();
        for (int i = 0; i < juego.getNumeroJugadores(); i++) {
            accionJugador.set(i, Boolean.TRUE);
        }
        mutex.release();
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
                int idJugador = ois.readInt()-1;
                if(accionJugador.get(idJugador)) { //Si este jugador no ha gastado su turno.
                    oos.writeBoolean(true); //Se le indica al Jugador.
                    oos.flush();
                    
                    repartirCartasJugador(cartas);
                    
                    mutex.acquire();
                    accionJugador.set(idJugador, false);
                    mutex.release();
                } else {
                    oos.writeBoolean(false); //Si no, se le indica que ya lo ha gastado.
                    oos.flush();
                } 
            }
        }catch(IOException|InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Accion de apostar en si misma.
     * @throws IOException 
     */
    private static void apostar() throws IOException {
        int apuesta = ois.readInt();
        oos.writeInt(juego.apostar(apuesta));
        oos.flush();
    }
    
    /**
     * Fase de realizar apuestas por los jugadores. Estos deberan hablar por turnos.
     */
    private static void faseApuestas() {
        try {
            //Quitado el while(true).
            int idJugador = ois.readInt()-1;
            if(accionJugador.get(idJugador)) {
                oos.writeBoolean(true);
                oos.flush();

                apostar();

                mutex.acquire();
                accionJugador.set(idJugador, false);
                mutex.release();
            } else {
                oos.writeBoolean(false);
                oos.flush();
            }
        }catch(IOException|InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
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
    private static void aniadirUltimoJugador() throws IOException, InterruptedException {
        aniadirJugador();
        
        inicializacionALTurnosJugada();
        juego.rebarajar();
    }
    
    /**
     * Gestor de acciones una vez ya se ha comenzado el juego.
     * Lo pongo con numeros excluyentes del otro selector por si acaso hubiera cruce (no deberia).
     */
    private static void gestorAccionesJuego() {
        try {
            aperturaCabecerasConexion();
            byte opcion = (byte) ois.readInt();

            switch(opcion) {
                case 2: //Reparto cartas cada Jugador.
                    repartirCartasJugadores(juego.repartoManoJugador());
                    reseteoALTurnosJugada();
                    break;
                case 3: //Reparto de cartas Comunes.
                    repartirCartasJugadores(juego.getCartasComunes());
                    reseteoALTurnosJugada();
                    System.out.println("Cartas Repartidas");
                    break;
                case 4: //Fase de Apuestas.
                    System.out.println("Comienzo con fase de apuestas");
                    faseApuestas();
                    reseteoALTurnosJugada();
                    System.out.println("Fin de fase de apuestas");
                    break;
                default:
                    System.out.println("Comprobar selector de Acciones (version juego).");
                    break;
            }
            
            cerrarCabecerasConexion(); //fixme: chequear si da problemas, no se si deberia estar o no.
        }catch(IOException|InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Gestor de acciones para el menu principal antes de que se comience el juego.
     * Una vez se haya comenzado se usara el otro gestor.
     */
    private static void gestorAccionesMenu() {
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
                    aniadirUltimoJugador();
                    System.out.println("Ultimo jugador añadido. Comenzando el Juego con " +juego.getNumeroJugadores() +" jugadores.");
                    new FasePreFlop().cambioFase(juego); //Cambiamos la fase a la inicial del Juego.
                    juego.setJuegoComenzado(true); //todo: mirar si quitarlo o no al final.
                    break;
                default:
                    System.out.println("Comprobar selector de Acciones (version menu principal).");
                    break;
            }
            
            cerrarCabecerasConexion();
        }catch(IOException|InterruptedException ex) {
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
                
                if(!juego.isJuegoComenzado()) new Thread(() -> gestorAccionesMenu()).start(); //En funcion de si el juego ha comenzado o no, se entrara en un switch u otro.
                else new Thread(() -> gestorAccionesJuego()).start();
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
