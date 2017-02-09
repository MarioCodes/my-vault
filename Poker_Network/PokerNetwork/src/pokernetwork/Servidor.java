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
import fases.*;

/*
    todo: idea -> al finalizar cualquier accion en el Server, hacer que lo ultimo que envie sea la Fase en la que se encuentra el Juego y adecuar la GUI en funcion.
*/

/**
 * Proyecto Online juego Oscar -> Poker Texas Hold'em!. Parte Servidor.
 * @author Mario Codes Sánchez
 * @since 09/02/2017
 */
public class Servidor {
    private static Juego juego = new Juego();
    
    private static final int PUERTO = 8143;
    private static Socket socket = null;
    
    /**
     * Reparto de las cartas propias a cada Jugador.
     * Cuando acaban pasamos a la primera ronda de apuestas.
     */
    private static void repartoCartasPropias() {
        juego.getFase().repartoCartasJugador(juego.obtenerCartasJugador());
        if(juego.terminarTurno()) new FaseApuestas().cambioFase(juego);
    }
    
    /**
     * Ronda de apuestas simple.
     * todo: Mas adelante podria implementar que solo se acabe la ronda de apuestas si ninguno sube o todos pasan.
     */
    private static void apostar() {
        juego.getFase().apostar(juego);
//        if(juego.terminarTurno()) new FasePreFlop().cambioFase(juego); //Fixme: cambiar por Flop cuando este.
    }
    
    /**
     * Accion de retirarse, se pone su ID en la lista. @fixme: quitarlo y poner una lista con IDs positivos en vez de negativos, quitarlo de esta cuando eso.
     */
    private static void retirarse() {
        int id = juego.getFase().retirarse();
        if(id != -1) {
            juego.getIdRetirados().add(id);
            System.out.println("Se ha retirado al Jugador " +id);
        } else {
            System.out.println("Problemas con el retirar a un jugador.");
        }
    }
    
    /**
     * Gestor de acciones una vez ya se ha comenzado el juego.
     */
    private static void accionesJuego() {
        try {
            Conexion.aperturaConexion(socket);

            int opcion = Conexion.getInt();
            switch(opcion) {
                case 1: //Get de la ID de quien Habla.
                    Conexion.sendFocus(juego);
                    break;
                case 2: //Reparto cartas cada Jugador.
                    repartoCartasPropias();
                    break;
                case 3: //Reparto de cartas Comunes.
//                    Conexion.repartirCartasJugadores(juego, juego.getCartasComunes());
                    break;
                case 4: //Apuestas.
                    apostar();
                    break;
                case 5: //Retirarse.
                    retirarse();
                    break;
                default:
                    System.out.println("Comprobar selector de Acciones (version juego).");
                    break;
            }
            Conexion.cerradoConexion();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Cambios de los elementos necesarios para comenzar con el juego.
     */
    private static void startJuego() {
        juego.rebarajar();
        juego.setComenzado(true);
    }
    
    /**
     * Gestor de acciones para el menu principal antes de que se comience el juego.
     * Una vez se haya comenzado se usara el otro gestor.
     */
    private static void accionesMenu() {
        try {
            Conexion.aperturaConexion(socket);
            int opcion = Conexion.getInt();
            
            switch(opcion) {
                case 0: //Join de un jugador.
                    Conexion.addJugador(juego);
                    break;
                case 1: //Join del ultimo jugador.
                    Conexion.addUltimoJugador(juego);
                    startJuego();
                    break;
                default:
                    System.out.println("Comprobar selector de Acciones (version menu principal).");
                    break;
            }
            
            Conexion.cerradoConexion();
        }catch(IOException|InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Ejecucion de la accion del Server.
     * Orden de las cabeceras:
     *      Server Input int menu. Server Output booleano posible. -> Resto de info.
     */
    public static void ejecucionServidor() {
        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO); //Espera y escucha la llegada de los clientes. Una vez establecida, devuelve el Socket.; 
        
            while(true) {
                socket = serverSocket.accept(); /* El ServerSocket me da el Socket.
                                                        Bloquea el programa en esta linea y solo avanza cuando un cliente se conecta.*/
                
                if(!juego.isComenzado()) new Thread(() -> accionesMenu()).start(); //En funcion de si el juego ha comenzado o no, se entrara en un switch u otro.
                else new Thread(() -> accionesJuego()).start();
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
