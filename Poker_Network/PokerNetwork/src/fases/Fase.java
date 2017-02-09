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
 * Implementacion de una maquina de estados para saber en todo momento en que fase se encuentra el Juego.
 * De esta forma tengo control total sobre lo que se puede y lo que no se puede hacer en cada fase del juego.
 * La forma de uso de llamado es -> Servidor -> Juego -> Instancia Fase -> Conexion.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 * @see https://en.wikipedia.org/wiki/State_pattern
 * @see https://www.tutorialspoint.com/design_pattern/state_pattern.htm
 */
public interface Fase {
    public void cambioFase(Juego juego);
    
    public void repartoCartasJugador(ArrayList<Carta> cartas);
    
    public void repartoCartasComunes();
    
    public int apostarCiegas(int fichas);
    
    public int apostar(int fichas);
    
    public boolean retirarse();
}
