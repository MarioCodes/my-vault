/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokercliente;

import java.util.ArrayList;

/**
 * Representacion de un Jugador.
 * @author Mario Codes Sánchez
 * @since 07/02/2017
 */
public class Jugador {
    private ArrayList<Carta> cartasPropias = new ArrayList<>(2);
    private ArrayList<Carta> cartasComunes = new ArrayList<>(3);
    
    private int fichasApuestas = 1000;
    private int identificadorJugador;

    public void sumarFichas(int fichas) {
        this.fichasApuestas += fichas;
    }
    
    public int apostar(int fichas) {
        int totalPool = Conexion.apostarJugador(fichas);
        this.fichasApuestas -= fichas;
        return totalPool;
    }
    
    public void obtenerMano() {
        cartasPropias = Conexion.obtenerManoJugador(identificadorJugador);
    }
    
    public void obtenerCartasComunes() {
        cartasComunes = Conexion.obtenerCartasComunes(identificadorJugador);
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
     * @param cartas the cartasPropias to set
     */
    public void setCartas(ArrayList<Carta> cartas) {
        this.cartasPropias = cartas;
    }

    /**
     * @return the fichasApuestas
     */
    public int getFichasApuestas() {
        return fichasApuestas;
    }
}
