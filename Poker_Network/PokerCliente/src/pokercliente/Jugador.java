/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokercliente;

import estados.Estado;
import java.util.ArrayList;

/**
 * Representacion de un Jugador unico.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 */
public class Jugador {
    private Estado estado;
    
    private ArrayList<Carta> cartasPropias = new ArrayList<>(2);
    private ArrayList<Carta> cartasComunes = new ArrayList<>(5);
    
    private int fichasApuestas = 1000;
    private int identificadorJugador;

    public void sumarFichas(int fichas) {
        this.fichasApuestas += fichas;
    }
    
    /**
     * Accion de apostar mediante la interfaz puesta.
     * @param fichas Numero de fichas a apostar.
     * @return Numero de fichas que hay en la pool.
     */
    public int apostar(int fichas) {
        fichasApuestas -= fichas;
        return estado.apostar(fichas);
    }
    
    //fixme: Para testeo mientras desarrollo. Borrar
    public void verCartasPropias() {
        if(cartasPropias != null) {
            System.out.println("\nCartas Propias.");
            for(Carta c : cartasPropias) System.out.println(c);
        }
    }
    
    //fixme: Para testeo mientras desarrollo. Borrar
    public void verCartasComunes() {
        if(cartasComunes != null) {
            System.out.println("\nCartas Comunes");
            for(Carta c : cartasComunes) System.out.println(c);
        }
    }
    
    /**
     * Obtenemos las 2 cartas unicas de este Jugador (Las quitamos de la baraja Obviamente).
     */
    public void obtenerMano() {
        cartasPropias = Conexion.obtenerCartas(identificadorJugador, 2);
    }
    
    /**
     * Obtenemos las cartas comunes que se hayan sacado hasta ahora. 
     *      3 al principio, 4 despues y 5 al final.
     */
    public void obtenerCartasComunes() {
        cartasComunes = Conexion.obtenerCartas(identificadorJugador, 3);
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
     * @return the cartasPropias
     */
    public ArrayList<Carta> getCartas() {
        return cartasPropias;
    }

    /**
     * @return the fichasApuestas
     */
    public int getFichasApuestas() {
        return fichasApuestas;
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
