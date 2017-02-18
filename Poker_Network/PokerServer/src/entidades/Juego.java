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
    
    private boolean finRonda;
    private boolean comenzado = false;
    
    private ArrayList<String> jugadores = new ArrayList<>();
    private int idFocus = 1; //ID del Jugador al cual le toca realizar accion. //fixme: cambiar a String y operar mediante Strings.
    
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
     * Gestion de los turnos. Si un jugador se ha retirado se le salta el turno y pasa al siguiente.
     * @return True si todos han terminado.
     */
    public boolean terminarTurno() {
        if(++idFocus > jugadores.size()) {
            idFocus = 1;
            return true;
        } else {
            while(!jugadores.contains(Integer.toString(idFocus))) {
                idFocus++;
            }
            return false;
        }
    }
    
    /**
     * Retiramos al jugador deseado.
     * @param id ID del jugador a retirar.
     * @return True si se ha podido retirar. 
     */
    public boolean retirarse(String id) {
        if(jugadores.contains(id)) {
            jugadores.remove(id);
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
     * Extrae y aniade una Carta a las comunes.
     */
    public void extraerCartaComun() {
        CARTAS_MESA.add(baraja.extraerCarta());
    }
    
    /**
     * Extraccion de las 3 cartas de la baraja comunes para la mesa en la fase de Flop..
     * @return ArrayList de Carta con las Cartas Comunes.
     */
    public ArrayList<Carta> getCartasComunes() {
        if(CARTAS_MESA.isEmpty()) CARTAS_MESA.addAll(baraja.extraerCartas(3));
        return CARTAS_MESA;
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
    public ArrayList<Carta> getCartasJugador() {
        ArrayList<Carta> cartas = new ArrayList<>();
        cartas.addAll(baraja.extraerCartas(2));
        return cartas;
    }
    
    /**
     * Sumar +1 al numero de jugadores.
     */
    public void aniadirJugador() {
        jugadores.add(Integer.toString(jugadores.size()+1));
    }

    /**
     * @return the poolApuestas
     */
    public int getFichasApuestas() {
        return apuestas;
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
    public String getIdFocus() {
        return Integer.toString(idFocus);
    }

    /**
     * @return the jugadores
     */
    public ArrayList<String> getJugadores() {
        return jugadores;
    }

    /**
     * @return the finRonda
     */
    public boolean isFinRonda() {
        return finRonda;
    }

    /**
     * @param finRonda the finRonda to set
     */
    public void setFinRonda(boolean finRonda) {
        this.finRonda = finRonda;
    }
}
