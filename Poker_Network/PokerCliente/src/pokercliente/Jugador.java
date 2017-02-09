/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokercliente;

import entidades.Carta;
import entidades.Mano;
import java.util.ArrayList;

/**
 * Representacion de un Jugador unico.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 */
public class Jugador {
    private Mano mano = new Mano();
    
    private int fichasApuestas = 1000;
    private int identificadorJugador;
    
    public void sumarFichas(int fichas) {
        this.fichasApuestas += fichas;
    }
    
    //fixme: Para testeo mientras desarrollo. Borrar
    public void verCartasPropias() {
        if(mano.getCartasPropias() != null) {
            System.out.println("\nCartas Propias.");
            for(Carta c : mano.getCartasPropias()) System.out.println(c);
        }
    }
    
    //fixme: Para testeo mientras desarrollo. Borrar
    public void verCartasComunes() {
        if(mano.getCartas_mesa() != null) {
            System.out.println("\nCartas Comunes");
            for(Carta c : mano.getCartas_mesa()) System.out.println(c);
        }
    }
    
    /**
     * Accion de apostar. Restamos al Jugador la cantidad antes de mandarla.
     * @param fichas Fichas a apostar.
     * @return Cantidad total de la Pool.
     */
    public int apostar(int fichas) {
        fichasApuestas -= fichas;
        return Conexion.apostar(fichas);
    }
    
    /**
     * Devuelve un booleano que indica si es este jugador quien debe hablar.
     * @return True si tiene el focus en el juego.
     */
    public boolean habla() {
        return Conexion.getIDTurno() == identificadorJugador;
    }
    
    /**
     * Obtenemos las 2 cartas unicas de este Jugador (Las quitamos de la baraja Obviamente).
     */
    public void obtenerCartasPersonales() {
        ArrayList<Carta> cartas = Conexion.getCartas(2);
        if(cartas != null) mano.aniadirCartaPropias(cartas);
    }
    
    /**
     * Obtenemos las cartas comunes que se hayan sacado hasta ahora. 
     *      3 al principio, 4 despues y 5 al final.
     */
    public void obtenerCartasComunes() {
        mano.aniadirCartaMesa(Conexion.getCartas(3));
    }
    
    /**
     * @return the identificadorJugador
     */
    public int getNumeroJugador() {
        return identificadorJugador;
    }

    /**
     * @param numeroJugador the identificadorJugador to set
     */
    public void setNumeroJugador(int numeroJugador) {
        this.identificadorJugador = numeroJugador;
    }

    /**
     * @return the fichasApuestas
     */
    public int getFichasApuestas() {
        return fichasApuestas;
    }

    /**
     * @return the mano
     */
    public Mano getMano() {
        return mano;
    }
}
