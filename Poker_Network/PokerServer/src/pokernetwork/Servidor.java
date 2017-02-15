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
    Ideas Por Hacer:
        //@todo: Al finalizar cualquier accion en el Server, hacer que lo ultimo que envie sea la Fase en la que se encuentra el Juego y adecuar la GUI del cliente en funcion.
        //@todo: Mas adelante podria implementar que solo se acabe la ronda de apuestas si ninguno sube o todos pasan.
*/

/**
 * todo: implementar comparacion de cartas, palos y demas para saber quien gana o que tiene cada jugador.
 * todo: implementar forma de repartir las fichas y que se sumen al jugador que gana.
 * fixme: Bug - juego comenzado, cierro cliente, abro nuevo e intento unirme -> Salta selector switch primera opcion. Arreglarlo.
 * Proyecto Online juego Oscar -> Poker Texas Hold'em!. Parte Servidor.
 * La forma de uso de llamado de los metodos es -> Servidor -> Juego -> Instancia Fase -> Conexion.
 * @author Mario Codes Sánchez
 * @since 09/02/2017
 * @see https://es.wikipedia.org/wiki/Texas_hold_'em
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
        juego.getFase().repartoCartasJugador(juego.getCartasJugador());
        if(juego.terminarTurno()) {
            FaseApuestas fa = new FaseApuestas();
            fa.setFasePrevia("PreFlop");
            fa.cambioFase(juego);
        }
    }
    
    /**
     * Pasamos a la siguiente fase correcta al terminar la actual de apuestas.
     * @param fa Fase de Apuestas actual.
     * @return Fase siguiente a la cual cambiar el estado del juego.
     */
    private static Fase getFaseCorrecta(FaseApuestas fa) {
        switch(fa.getFasePrevia()) {
                case "PreFlop":
                    return new FaseFlop();
                case "Flop":
                    return new FaseTurn();
                case "Turn":
                    return new FaseRiver();
                default:
                    System.out.println("Switch cambio fase apostar default().");
                    return null;
        }
    }
    
    /**
     * Ronda de apuestas simple.
     */
    private static void apostar() {
        juego.getFase().apostar(juego);
        
        try {
            if(juego.terminarTurno()) {
                Fase fase = getFaseCorrecta((FaseApuestas) juego.getFase());
                fase.cambioFase(juego);
            }
        }catch(ClassCastException ex) {
            System.out.println("Cambio de Fase no correcta. Chequear Servidor.Apostar(). " +ex.getLocalizedMessage()); //@todo: arreglar la GUI para que solo se puedan realizar acciones conforme a la fase correcta.
        }
    }
    
    /**
     * Accion de retirarse, se pone su ID en la lista.
     */
    private static void retirarse() {
        String id = juego.getFase().retirarse();
        if(id != null) {
            if(juego.retirarse(id)) {
                System.out.println("Se ha retirado al Jugador " +id);
            }
            else System.out.println("El jugador ya estaba retirado.");
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
                    Conexion.sendBooleano(true);
                    Conexion.sendFocus(juego);
                    break;
                case 2: //Reparto cartas cada Jugador.
                    repartoCartasPropias();
                    break;
                case 3: //Reparto de cartas Comunes.
                    juego.getFase().repartoCartasComunes(juego);
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
