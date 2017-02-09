/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fases;

import entidades.Carta;
import entidades.Juego;
import java.util.ArrayList;

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
    }

    @Override
    public void repartoCartasJugador(ArrayList<Carta> cartas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void repartoCartasComunes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int apostarCiegas(int fichas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int apostar(int fichas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retirarse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return "Apuestas";
    }
}
