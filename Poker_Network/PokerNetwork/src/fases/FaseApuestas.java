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
 * Fase para Ronda de Apuestas.
 * Realmente no es considerada una fase en el Juego en si. Pero me simplifica la reutilizacion de codigo y establece la forma para considerar cuando se debe apostar.
 * @author Mario Codes Sánchez
 * @since 09/02/2017
 */
public class FaseApuestas implements Fase {

    @Override
    public void cambioFase(Juego juego) {
        juego.setFase(this);
        System.out.println("Fase de Apuestas.");
    }

    @Override
    public void repartoCartasJugador(ArrayList<Carta> cartas) {
        Conexion.sendBooleano(false);
    }

    @Override
    public void repartoCartasComunes() {
        Conexion.sendBooleano(false);
    }

    @Override
    public void apostar(Juego juego) {
        Conexion.sendBooleano(true);
        Conexion.getApuesta(juego);
    }

    @Override
    public int retirarse() {
        Conexion.sendBooleano(true);
        return Conexion.getID();
    }
    
    @Override
    public String toString() {
        return "Apuestas";
    }
}
