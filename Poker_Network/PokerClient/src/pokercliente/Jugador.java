/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokercliente;

import entidades.Carta;
import entidades.Jugadas;
import entidades.Mano;
import java.util.ArrayList;

/**
 * Representacion de un Jugador unico.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 */
public class Jugador {
    private Mano mano = new Mano();
    
    private int fichas = 1000;
    private String id;
    private boolean jugando = true;
    
    public void sumarFichas(int fichas) {
        this.fichas += fichas;
    }
    
    //fixme: Testeo. Sout Cartas. Borrar Cuando no sea necesario.
    public void verCartasPropias() {
        if(mano.getCartasPropias() != null) {
            System.out.println("\nCartas Propias.");
            for(Carta c : mano.getCartasPropias()) System.out.println(c);
        }
    }
    
    //fixme: Testeo. Sout Cartas. Borrar Cuando no sea necesario.
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
        this.fichas -= fichas;
        return Conexion.apostar(fichas);
    }
    
    /**
     * Accion de retirarse del juego.
     * @return 
     */
    public boolean retirarse() {
        if(jugando && Conexion.retirarse(id)) {
            jugando = false;
            return true;
        } else return false;
    }
    
    public void enviarJugada() {
        Jugadas.checkJugada(mano.getCartasPropias(), mano.getCartas_mesa());
        Conexion.sendJugada(id, Jugadas.jugada);
    }
    
    /**
     * Devuelve un booleano que indica si es este jugador quien debe hablar.
     * @return True si tiene el focus en el juego.
     */
    public boolean habla() {
        return Conexion.getIDFocus().matches(id);
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
        mano.setCartas_mesa(new ArrayList<Carta>());
        ArrayList<Carta> cartas = Conexion.getCartas(3);
        if(cartas != null) mano.aniadirCartaMesa(cartas);
    }
    
    /**
     * @return the identificadorJugador
     */
    public String getID() {
        return id;
    }

    /**
     * @param id the identificadorJugador to set
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * @return the fichasApuestas
     */
    public int getFichas() {
        return fichas;
    }

    /**
     * @return the mano
     */
    public Mano getMano() {
        return mano;
    }
}
