/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.util.ArrayList;

/**
 * Gestion de la logica del juego.
 * @author Mario Codes Sánchez
 * @since 06/02/2017
 */
public class Juego {
    private final ArrayList<Carta> CARTAS_MESA = new ArrayList<>();
    private final int NUMERO_JUGADORES;
    private final Baraja BARAJA;
    
    /**
     * Constructor por defecto, inicializa la Baraja ya Shuffleada.
     * @param numeroJugadores Numero total de Jugadores.
     */
    public Juego(int numeroJugadores) {
        this.BARAJA = new Baraja();
        this.NUMERO_JUGADORES = numeroJugadores;
    }
    
    /**
     * Extraccion de las 3 cartas de la baraja comunes para la mesa.
     */
    public void comienzoJuego() {
        CARTAS_MESA.addAll(BARAJA.extraerCartas(3));
    }

    /**
     * Repartimos las 2 cartas necesarias propias para el jugador.
     * @return ArrayList con las 2 cartas extraidas de la baraja.
     */
    public ArrayList<Carta> repartoManoJugador() {
        ArrayList<Carta> cartas = new ArrayList<>();
        cartas.addAll(BARAJA.extraerCartas(2));
        return cartas;
    }
    
    /**
     * @return the CARTAS_MESA
     */
    public ArrayList<Carta> getCARTAS_MESA() {
        return CARTAS_MESA;
    }
}
