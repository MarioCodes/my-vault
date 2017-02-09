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
    
    private boolean juegoComenzado = false;
    private int numeroJugadores = 0;
    private int idJugadorFocus = 1;
    
    private Baraja baraja = null;
    private ArrayList<Carta> CARTAS_MESA = new ArrayList<>();
    private ArrayList<Boolean> accionJugador;
    private int poolApuestas = 0;

    /**
     * Constructor por defecto.
     */
    public Juego() {
        this.fase = new FasePreFlop();
    }
    
    /**
     * Gestionamos el avanzar al siguiente Jugador.
     */
    public void avanzarJugador() {
        if(++idJugadorFocus >= numeroJugadores) idJugadorFocus = 1;
    }
    
    /**
     * (Re)inicializacion de la AL que uso para los turnos de cada jugador.
     *  La idea es usar el identificador del jugador -1 para acceder a la AL. True si aun no ha usado su accion en el turno, false para que no pueda operar.
     */
    public void inicializacionALTurnosJugada() {
        accionJugador = new ArrayList<>();
        
        for (int i = 0; i < numeroJugadores; i++) {
            accionJugador.add(true);
        }
    }
    
    /**
     * Reseteo de las acciones de cada jugador a True. Se hara cuando se acabe una fase.
     * @throws InterruptedException 
     */
    public void reseteoALTurnosJugada() {
        for (int i = 0; i < getNumeroJugadores(); i++) {
            accionJugador.set(i, Boolean.TRUE);
        }
    }
    
    /**
     * Accion de apostar, sumamos las fichas y devolvemos el total de la pool.
     * @param fichas Fichas que sumamos a la pool.
     * @return Pool total hasta ahora.
     */
    public int apostar(int fichas) {
        poolApuestas += fichas;
        return poolApuestas;
    }
    
    /**
     * Extraccion de las 3 cartas de la baraja comunes para la mesa.
     */
    private void sacarCartasComunes() {
        CARTAS_MESA.addAll(baraja.extraerCartas(3));
    }
    
    /**
     * Recogemos las cartas de la mesa y ponemos las fichas de apuestas a 0;
     */
    private void recoger() {
        CARTAS_MESA.removeAll(CARTAS_MESA);
        poolApuestas = 0;
    }
    
    /**
     * Creamos una baraja aleatoria nueva.
     */
    public void rebarajar() {
        recoger();
        this.baraja = new Baraja();
        
        baraja.quemarCartas(3);
        sacarCartasComunes();
        System.out.println("Ronda nueva Comenzada. Cartas de la mesa repartidas.");
    }

    /**
     * Repartimos las 2 cartas necesarias propias para el jugador.
     * @return ArrayList con las 2 cartas extraidas de la baraja.
     */
    public ArrayList<Carta> repartoManoJugador() {
        ArrayList<Carta> cartas = new ArrayList<>();
        cartas.addAll(baraja.extraerCartas(2));
        return cartas;
    }
    
    /**
     * Sumar +1 al numero de jugadores.
     */
    public void aniadirJugador() {
        this.numeroJugadores++;
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
        return poolApuestas;
    }

    /**
     * @return the numeroJugadores
     */
    public int getNumeroJugadores() {
        return numeroJugadores;
    }

    /**
     * @return the juegoComenzado
     */
    public boolean isJuegoComenzado() {
        return juegoComenzado;
    }

    /**
     * @param juegoComenzado the juegoComenzado to set
     */
    public void setJuegoComenzado(boolean juegoComenzado) {
        this.juegoComenzado = juegoComenzado;
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
     * @return the accionJugador
     */
    public ArrayList<Boolean> getAccionJugador() {
        return accionJugador;
    }

    /**
     * @param accionJugador the accionJugador to set
     */
    public void setAccionJugador(ArrayList<Boolean> accionJugador) {
        this.accionJugador = accionJugador;
    }

    /**
     * @return the idJugadorFocus
     */
    public int getIdJugadorFocus() {
        return idJugadorFocus;
    }
}
