/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import fases.Fase;
import fases.FasePreFlop;
import java.util.ArrayList;

/**
 * Gestion de la logica del juego.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 */
public class Juego {
    private Fase fase = null; //Fase actual en la que se encuentra el Juego.
    
    private boolean comenzado = false;
    
    private int totalJugadores = 0;
    private int idFocus = 1; //ID del Jugador al cual le toca realizar accion.
    
    private Baraja baraja = null;
    private final ArrayList<Carta> CARTAS_MESA = new ArrayList<>();
    
    private int apuestas = 0;

    /**
     * Constructor por defecto.
     */
    public Juego() {
        this.fase = new FasePreFlop();
        System.out.println("Juego nuevo Creado. Fase de Pre-Flop.");
    }
    
    /**
     * El Jugador actual termina su turno y pasamos al siguiente.
     * @return Devuelve true cuando todos los jugadores hayan realizado su accion.
     */
    public boolean terminarTurno() {
        if(++idFocus >= totalJugadores) {
            idFocus = 1;
            return true;
        } else return false;
    }
    
    /**
     * Accion de apostar, sumamos las fichas y devolvemos el total de la pool.
     * @param fichas Fichas que sumamos a la pool.
     * @return Pool total hasta ahora.
     */
    public int apostar(int fichas) {
        apuestas += fichas;
        return apuestas;
    }
    
    /**
     * Extraccion de las 3 cartas de la baraja comunes para la mesa.
     */
    private void obtenerCartasComunes() {
        CARTAS_MESA.addAll(baraja.extraerCartas(3));
    }
    
    /**
     * Recogemos las cartas de la mesa y obtenemos las fichas que habia en la pool comun.
     */
    private int recogerApuestas() {
        CARTAS_MESA.removeAll(CARTAS_MESA);
        int pool = apuestas;
        apuestas = 0;
        return pool;
    }
    
    /**
     * Creamos una baraja aleatoria nueva.
     */
    public void rebarajar() {
        this.baraja = new Baraja();
        
        System.out.println("\nRonda nueva Comenzada. Reparto de Cartas.");
    }

    /**
     * Repartimos las 2 cartas necesarias propias para el jugador.
     * @return ArrayList con las 2 cartas extraidas de la baraja.
     */
    public ArrayList<Carta> obtenerCartasJugador() {
        ArrayList<Carta> cartas = new ArrayList<>();
        cartas.addAll(baraja.extraerCartas(2));
        return cartas;
    }
    
    /**
     * Sumar +1 al numero de jugadores.
     */
    public void aniadirJugador() {
        this.totalJugadores++;
    }
    
    
    
    /**
     * @return the CARTAS_MESA
     */
    public ArrayList<Carta> getCartasComunes() {
        return CARTAS_MESA;
    }

    /**
     * @return the poolApuestas
     */
    public int getFichasApuestas() {
        return apuestas;
    }

    /**
     * @return the numeroJugadores
     */
    public int getTotalJugadores() {
        return totalJugadores;
    }

    /**
     * @return the juegoComenzado
     */
    public boolean isComenzado() {
        return comenzado;
    }

    /**
     * @param comenzado the juegoComenzado to set
     */
    public void setComenzado(boolean comenzado) {
        this.comenzado = comenzado;
    }

    /**
     * @return the fase
     */
    public Fase getFase() {
        return fase;
    }

    /**
     * NO UTILIZAR. HACER EL CAMBIO MEDIANTE Fase.cambioFase().
     * Me hace falta para hacer el cambio de forma interna pero no utilizarlo fuera de esto.
     * @param fase the fase to set
     */
    public void setFase(Fase fase) {
        this.fase = fase;
    }

    /**
     * @return the idJugadorFocus
     */
    public int getIdFocus() {
        return idFocus;
    }
}
