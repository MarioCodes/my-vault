/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import fases.Fase;
import fases.FasePreFlop;
import java.util.ArrayList;
import pokernetwork.Conexion;

/**
 * Gestion de la logica del juego.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 */
public class Juego {
    private Fase fase = null; //Fase actual en la que se encuentra el Juego.
    
    private boolean faseRealizada; //Accion propia de la fase realizada. Para saber cuando pasar a apuestas.
    private boolean comenzado = false; //Se usa para no volver a pasar por el primer menu.
    
    private final ArrayList<String> JUGADORES = new ArrayList<>();
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
     * Calculo de la puntuacion de cada jugada.
     * @param s String con la jugada.
     * @return Valor numerico de esa jugada.
     */
    private int getPuntuacion(String s) {
        switch(s) {
            case "Escalera Real":
                return 2000;
            case "Escalera de Color":
                return 1800;
            case "Poker":
                return 1600;
            case "Full":
                return 1400;
            case "Color":
                return 1200;
            case "Escalera":
                return 1000;
            case "Trio":
                return 800;
            case "Doble Pareja":
                return 600;
            case "Pareja":
                return 400;
            case "Carta Alta":
                return 200;
            default:
                System.out.println("Default Switch en puntuacionJugada().");
                return 0;
        }
    }
    
    /**
     * Obtencion de todos los valores de cada jugador. ID == indice de acceso +1.
     * @return int[] con todos los valores.
     */
    public int[] getValores() {
        int[] valores = new int[JUGADORES.size()];
        for (int i = 0; i < JUGADORES.size(); i++) {
            valores[i] = Conexion.getValor(Integer.toString(i+1));
        }
        
        return valores;
    }
    
//    public String getJugada() {
//        return Conexion.getJugada();
//    }
    
    /**
     * Obtencion de todas  las jugadas de todos los jugadores.
     * @return String[] con las jugadas de cada uno (ID = indice de acceso).
     */
    public ArrayList getJugadas() {
        return Conexion.getJugada();
    }
    
    /**
     * Obtiene el ID del ganador, pasandole como parametro una String[] con las jugadas de cada uno y una int[] con el valor de la jugada propio (desempates).
     * @param jugadas String[] con el nombre de la jugada que tiene cada jugador.
     * @param puntuacionJugador int[] con la puntuacion que suman las cartas de ese jugador.
     * @return ID del ganador.
     */
    public String getGanador(String[] jugadas, int[] puntuacionJugador) {
        String idGanador;
        int[] puntuacion = new int[jugadas.length];
        
        for (int i = 0; i < jugadas.length; i++) {
            puntuacion[i] = getPuntuacion(jugadas[i]);
            puntuacion[i] += puntuacionJugador[i];
        }
        
        idGanador = "0";
        int puntuacionGanador = puntuacion[0];
        for (int i = 0; i < puntuacion.length; i++) {
            if(puntuacion[i] > puntuacionGanador) {
                idGanador = Integer.toString(i);
                puntuacionGanador = puntuacion[i];
            }
        }
        
        return idGanador;
    }
    
    /**
     * Gestion de los turnos. Si un jugador se ha retirado se le salta el turno y pasa al siguiente.
     * @return True si todos han terminado.
     */
    public boolean terminarTurno() {
        if(++idFocus > JUGADORES.size()) {
            idFocus = 1;
            return true;
        } else {
            while(!JUGADORES.contains(Integer.toString(idFocus))) {
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
        if(JUGADORES.contains(id)) {
            JUGADORES.remove(id);
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
     * Sumar +1 al numero de JUGADORES.
     */
    public void aniadirJugador() {
        JUGADORES.add(Integer.toString(JUGADORES.size()+1));
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
     * @return the JUGADORES
     */
    public ArrayList<String> getJUGADORES() {
        return JUGADORES;
    }

    /**
     * @return the faseRealizada
     */
    public boolean isFaseRealizada() {
        return faseRealizada;
    }

    /**
     * @param faseRealizada the faseRealizada to set
     */
    public void setFaseRealizada(boolean faseRealizada) {
        this.faseRealizada = faseRealizada;
    }
}
