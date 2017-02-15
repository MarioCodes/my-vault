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
    private String fasePrevia; //Como esta fase se llama durante toda la ronda, necesito saber cual ha sido la previa para saber cual es la siguiente.
    
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
    public void repartoCartasComunes(ArrayList<Carta> cartas) {
        Conexion.sendBooleano(false);
    }

    @Override
    public void apostar(Juego juego) {
        Conexion.sendBooleano(true);
        Conexion.getApuesta(juego);
    }

    @Override
    public String retirarse() {
        Conexion.sendBooleano(true);
        return Conexion.getID();
    }
    
    @Override
    public String toString() {
        return "Apuestas";
    }

    /**
     * @return the fasePrevia
     */
    public String getFasePrevia() {
        return fasePrevia;
    }

    /**
     * @param fasePrevia the fasePrevia to set
     */
    public void setFasePrevia(String fasePrevia) {
        this.fasePrevia = fasePrevia;
    }
}
