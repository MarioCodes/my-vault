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
 * Fase de River, seguida de la Fase de Turn; Es la ultima fase del juego antes de una de apuestas.
 * Se destapa otra carta y se pasa de nuevo a apuestas; con esa ya terminara el juego.
 * @author Mario Codes Sánchez
 * @since 15/02/2017
 */
public class FaseRiver implements Fase {

    @Override
    public void cambioFase(Juego juego) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void repartoCartasJugador(ArrayList<Carta> cartas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void repartoCartasComunes(Juego juego) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void apostar(Juego juego) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String retirarse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
