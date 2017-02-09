/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fases;

import entidades.Carta;
import entidades.Juego;
import java.util.ArrayList;
import pokernetwork.Conexion;

/**
 * Fase de Flop.
 * @author Mario Codes Sánchez
 * @since 09/02/2017
 */
public class FaseFlop implements Fase {

    @Override
    public void cambioFase(Juego juego) {
        System.out.println("Fase de Flop");
        juego.setFase(this);
    }

    @Override
    public void repartoCartasJugador(ArrayList<Carta> cartas) {
        Conexion.sendBooleano(false);
    }

    @Override
    public void repartoCartasComunes(ArrayList<Carta> cartas) {
        Conexion.sendBooleano(true);
        Conexion.repartoCartas(cartas);
    }

    @Override
    public void apostar(Juego juego) {
        Conexion.sendBooleano(false);
    }

    @Override
    public String retirarse() {
        Conexion.sendBooleano(false);
        return null;
    }
    
    @Override
    public String toString() {
        return "Flop";
    }
}
