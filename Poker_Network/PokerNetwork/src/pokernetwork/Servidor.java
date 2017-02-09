/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokernetwork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import entidades.Juego;

/**
 * Proyecto Online juego Oscar -> Poker Texas Hold'em!. Parte Servidor.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 */
public class Servidor {
    private static Juego juego = new Juego();
    
    private static final int PUERTO = 8143;
    private static Socket socket = null;
    
    /**
     * Gestor de acciones una vez ya se ha comenzado el juego.
     * Lo pongo con numeros excluyentes del otro selector por si acaso hubiera cruce (no deberia).
     */
    private static void gestorAccionesJuego() {
        try {
            Conexion.aperturaCabecerasConexion(socket);
            Conexion.envioInt(juego.getIdJugadorFocus());
            
            int opcion = Conexion.reciboInt();

            switch(opcion) {
                case 2: //Reparto cartas cada Jugador.
                    System.out.println("SU"); //fixme: borrar.
//                    Conexion.repartoCartasJugadores(juego);
                    
//                    Conexion.repartirCartasJugadores(juego, juego.repartoManoJugador());
                    break;
                case 3: //Reparto de cartas Comunes.
                    Conexion.repartirCartasJugadores(juego, juego.getCartasComunes());
                    break;
                case 4: //Fase de Apuestas.
//                    faseApuestas();
                    break;
                default:
                    System.out.println("Comprobar selector de Acciones (version juego).");
                    break;
            }
            Conexion.cerrarCabecerasConexion(); //fixme: chequear si da problemas, no se si deberia estar o no.
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Cambios de los elementos necesarios para comenzar con el juego.
     */
    private static void preparacionJuego() {
        juego.setJuegoComenzado(true); //todo: mirar si quitarlo o no al final.
    }
    
    /**
     * Gestor de acciones para el menu principal antes de que se comience el juego.
     * Una vez se haya comenzado se usara el otro gestor.
     */
    private static void gestorAccionesMenu() {
        try {
            Conexion.aperturaCabecerasConexion(socket);
            int opcion = Conexion.reciboInt();
            
//            System.out.println(opcion); //fixme: borrar al final del todo.
            switch(opcion) {
                case 0: //Join de un jugador.
                    Conexion.aniadirJugador(juego);
                    break;
                case 1: //Join del ultimo jugador.
                    Conexion.aniadirUltimoJugador(juego);
                    preparacionJuego();
                    break;
                default:
                    System.out.println("Comprobar selector de Acciones (version menu principal).");
                    break;
            }
            
            Conexion.cerrarCabecerasConexion();
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
