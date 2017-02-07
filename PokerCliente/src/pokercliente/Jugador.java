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
    private ArrayList<Carta> cartas = new ArrayList<>(2);
    private int identificadorJugador;

    public void obtenerMano() {
        cartas = Conexion.obtenerManoJugador(identificadorJugador);
        for(Carta carta: cartas) {
            System.out.println(carta);
        }
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
     * @return the cartas
     */
    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    /**
     * @param cartas the cartas to set
     */
    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }
}
