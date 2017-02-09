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
 * Fase de Pre-Flop. 
 * Es la fase en la cual se reparte dos cartas individuales a cada Jugador.
 * Despues de repartir se hace una fase para introducir las Ciegas, y despues uan ronda de Apuestas.
 * @author Mario Codes Sánchez
 * @since 09/02/2017
 */
public class FasePreFlop implements Fase{

    @Override
    public void cambioFase(Juego juego) {
        juego.setFase(this);
        System.out.println("Nueva Ronda. Fase de PreFlop.");
    }

    @Override
    public void repartoCartasJugador(ArrayList<Carta> cartas) {
        Conexion.sendBooleano(true);
        Conexion.repartoCartas(cartas);
    }

    @Override
    public void repartoCartasComunes(ArrayList<Carta> cartas) {
        Conexion.sendBooleano(false);
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
        return "PreFlop";
    }
}
