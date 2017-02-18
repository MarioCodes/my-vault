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
 * @since 18/02/2017
 */
public class FasePreFlop implements Fase{
    private boolean apostar = false; //Para tener en cuenta si ya se ha realizado la accion propia de la Fase y se puede apostar.
    
    @Override
    public void cambioFase(Juego juego) {
        juego.setFase(this);
        System.out.println("Nueva Ronda. Fase de PreFlop.");
    }

    @Override
    public void repartoCartasJugador(Juego juego) {
        ArrayList<Carta> cartas = juego.getCartasJugador();
        Conexion.sendBooleano(true);
        Conexion.repartoCartas(cartas);
        
        if(juego.isFaseRealizada()) {
            apostar = true;
            System.out.println("Rondas de Apuestas.");
        }
    }

    @Override
    public void repartoCartasComunes(Juego juego) {
        Conexion.sendBooleano(false);
    }
    
    @Override
    public void apostar(Juego juego) {
        if(!apostar) Conexion.sendBooleano(false);
        else {
            Conexion.sendBooleano(true);
            Conexion.getApuesta(juego);
        }
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
