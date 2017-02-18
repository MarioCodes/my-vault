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
 * Fase de River, seguida de la Fase de Turn; Es la ultima fase del juego antes de una de apuestas.
 * Se destapa otra carta y se pasa de nuevo a apuestas; con esa ya terminara el juego.
 * @author Mario Codes Sánchez
 * @since 15/02/2017
 */
public class FaseRiver implements Fase {
    private boolean apostar = false;
    
    @Override
    public void cambioFase(Juego juego) {
        juego.setFase(this);
        System.out.println("Fase de River");
    }

    @Override
    public void repartoCartasJugador(Juego juego) {
        Conexion.sendBooleano(false);
    }

    @Override
    public void repartoCartasComunes(Juego juego) {
        if(juego.getCartasComunes().size() == 4) juego.extraerCartaComun();
        ArrayList<Carta> cartas = juego.getCartasComunes();
        Conexion.sendBooleano(true);
        Conexion.repartoCartas(cartas);
        
        if(juego.terminarTurno()) {
            apostar = true;
            System.out.println("Ronda de apuestas.");
        }
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
        return "River";
    }
}
